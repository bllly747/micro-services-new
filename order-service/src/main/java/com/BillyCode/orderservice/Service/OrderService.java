package com.BillyCode.orderservice.Service;

import com.BillyCode.orderservice.Dtos.InventoryResponse;
import com.BillyCode.orderservice.Model.Order;
import com.BillyCode.orderservice.Model.OrderLineItems;
import com.BillyCode.orderservice.Model.OrderLineItemsDto;
import com.BillyCode.orderservice.Model.RequestOrder;
import com.BillyCode.orderservice.Repository.OrderLineItemsRepository;
import com.BillyCode.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient.Builder webClient;

    public void placeOrder(RequestOrder requestOrder)
    {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = requestOrder.getOrderLineItemsDtosList().stream()
                .map(this::mapDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setOrder(order);
        orderLineItems.getOrder().setId(order.getId());
        var skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode)
                .toList();

        // call an HTTP Get for the inventory service
        InventoryResponse[] result = webClient.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean present = Arrays.stream(result).allMatch(InventoryResponse::isInStock);

        if(present)
        {
            orderLineItemsRepository.saveAll(orderLineItemsList);
            orderRepository.save(order);

        }else {
            throw new IllegalArgumentException("item is not in stock , tr again later");
        }
    }

    private OrderLineItems mapDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return  orderLineItems;
    }
}
