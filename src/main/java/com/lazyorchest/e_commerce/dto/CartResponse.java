package com.lazyorchest.e_commerce.dto;

import com.lazyorchest.e_commerce.models.CartDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class CurrentUser {
        private String name;
        private String username;
    }
    private CurrentUser currentUser;
    private List<CartDetail> cartDetails;
}
