package com.auto.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Order {

    private String orderId;
    private LocalDate localDate;
    private String totalDetail;
}