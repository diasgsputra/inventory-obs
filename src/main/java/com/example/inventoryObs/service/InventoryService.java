package com.example.inventoryObs.service;

import com.example.inventoryObs.entity.Inventory;
import com.example.inventoryObs.entity.Item;
import com.example.inventoryObs.request.InventoryRequest;

import java.util.Map;

public interface InventoryService {
  Map<String, Object> getAllInventory(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
  Inventory getInventoryById(Long id);
  Inventory createInventory(InventoryRequest item);
  Inventory updateInventory(Long id, InventoryRequest inventoryDetails);
  void deleteInventory(Long id);
}
