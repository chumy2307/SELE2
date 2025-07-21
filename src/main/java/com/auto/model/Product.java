package com.auto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.auto.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class Product {
    private String productName;
    private int productQuantity;
    private double productPrice;
    private double productTotalPrice;

    public Product(String productName, int productQuantity, double productTotalPrice) {
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
    }

    public static List<Product> toProductForCheckout(List<Product> originalList) {
        return originalList.stream().map(p -> new Product(
                        Utils.formatProductName(p.getProductName()),
                        p.getProductQuantity(),
                        p.getProductTotalPrice()
                ))
                .collect(Collectors.toList());
    }
}
