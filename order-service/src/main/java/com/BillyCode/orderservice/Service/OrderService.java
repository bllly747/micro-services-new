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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemsRepository orderLineItemsRepository;

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
        orderLineItemsRepository.saveAll(orderLineItemsList);
        orderRepository.save(order);

    }

    private OrderLineItems mapDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return  orderLineItems;
    }
}
