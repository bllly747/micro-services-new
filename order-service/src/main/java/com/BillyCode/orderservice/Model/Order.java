package com.BillyCode.orderservice.Model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    private String orderNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<OrderLineItems> orderLineItemsList;
}
