package com.BillyCode.orderservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderLineItems {
    @Id
    @GeneratedValue
    private Long id;

    private String skuCode;

    private BigDecimal price;

    private Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(
            name = "orderId"
    )
    private Order order;
}
