package com.example.inventoryObs.repository;

import com.example.inventoryObs.entity.Inventory;
import com.example.inventoryObs.projection.InventoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
  @Query(value = "SELECT qty, type FROM inventory WHERE item_id = :itemId", nativeQuery = true)
  List<InventoryProjection> getInventoryByItemId(Long itemId);
}
