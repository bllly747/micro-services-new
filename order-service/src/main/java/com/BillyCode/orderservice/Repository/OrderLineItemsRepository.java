package com.BillyCode.orderservice.Repository;

import com.BillyCode.orderservice.Model.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems,Long> {
}
