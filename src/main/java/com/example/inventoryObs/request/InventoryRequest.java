package com.example.inventoryObs.request;

import lombok.Data;

@Data
public class InventoryRequest {
  private Long item_id;
  private Long qty;
  private String type;
}
