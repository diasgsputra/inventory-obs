package com.example.inventoryObs.repository;

import com.example.inventoryObs.entity.Order;
import com.example.inventoryObs.projection.InventoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  @Query(value = "SELECT max(id) FROM orders", nativeQuery = true)
  Long getMaxId();

}
