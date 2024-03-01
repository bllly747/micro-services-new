package com.BillyCode.orderservice.Service;

import com.BillyCode.orderservice.Model.Order;
import com.BillyCode.orderservice.Model.OrderLineItems;
import com.BillyCode.orderservice.Model.OrderLineItemsDto;
import com.BillyCode.orderservice.Model.RequestOrder;
import com.BillyCode.orderservice.Repository.OrderLineItemsRepository;
import com.BillyCode.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;
    private final WebClient webClient;

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

        // call an HTTP Get for the inventory service
        var result = webClient.get()
                .uri("http://localhost:8081/api/inventory")
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if(Boolean.TRUE.equals(result))
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
