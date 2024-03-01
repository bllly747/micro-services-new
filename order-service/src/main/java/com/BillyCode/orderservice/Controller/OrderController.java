package com.BillyCode.orderservice.Controller;

import com.BillyCode.orderservice.Model.RequestOrder;
import com.BillyCode.orderservice.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")

public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(
            @RequestBody  RequestOrder requestOrder)
    {
        orderService.placeOrder(requestOrder);
        return "Order Placed Successfully";
    }
}
