package com.example.inventoryObs.service;

import com.example.inventoryObs.entity.Inventory;
import com.example.inventoryObs.exception.ResourceNotFoundException;
import com.example.inventoryObs.repository.InventoryRepository;
import com.example.inventoryObs.request.InventoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService{
  @Autowired
  InventoryRepository inventoryRepository;
  @Override
  public Map<String, Object> getAllInventory(Integer pageNo, Integer pageSize, String sortBy, String sortDirection){
    Sort.Direction direction;
    try {
      direction = Sort.Direction.valueOf(sortDirection.trim().toUpperCase());
    } catch (IllegalArgumentException | NullPointerException e) {
      direction = Sort.Direction.ASC;
    }
    Map<String, Object> result = new HashMap<>();
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(direction, sortBy));
    Page<Inventory> pagedResult = inventoryRepository.findAll(paging);
    result.put("data", pagedResult.getContent());
    result.put("total_result", pagedResult.getTotalElements());
    result.put("total_page", pagedResult.getTotalPages());

    return result;
  }

  @Override
  public Inventory getInventoryById(Long id) {
    return inventoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for this id : " + id));
  }

  @Override
  public Inventory createInventory(InventoryRequest inventoryRequest) {
    Inventory inventory = new Inventory();
    inventory.setType(inventoryRequest.getType());
    inventory.setQty(inventoryRequest.getQty());
    inventory.setItem_id(inventoryRequest.getItem_id());
    return inventoryRepository.save(inventory);
  }

  @Override
  public Inventory updateInventory(Long id, InventoryRequest inventoryDetails) {
    Inventory inventory = inventoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));

    inventory.setQty(inventoryDetails.getQty());
    inventory.setType(inventoryDetails.getType());
    inventory.setItem_id(inventoryDetails.getItem_id());

    return inventoryRepository.save(inventory);
  }

  @Override
  public void deleteInventory(Long id) {
    Inventory inventory = inventoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Item not found for this id :: " + id));
    inventoryRepository.delete(inventory);
  }
}
