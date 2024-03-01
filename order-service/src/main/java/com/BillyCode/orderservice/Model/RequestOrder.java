package com.BillyCode.orderservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@AllArgsConstructor
@Component
@RequiredArgsConstructor
public class RequestOrder {
    private List<OrderLineItemsDto> orderLineItemsDtosList;
}
