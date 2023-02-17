package com.lazyorchest.e_commerce.dto;

import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailRequest {
    private Integer quantity;
}
