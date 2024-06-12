package com.example.inventoryObs.request;

import lombok.Data;

@Data
public class OrderRequest {
  private String order_no;
  private Long item_id;
  private Long qty;
}
