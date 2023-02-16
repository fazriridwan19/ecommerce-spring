package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.dto.CartDetailRequest;
import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.CartDetail;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.repositories.CartDetailRepo;
import com.lazyorchest.e_commerce.repositories.CartRepo;
import com.lazyorchest.e_commerce.repositories.ProductRepo;
import com.lazyorchest.e_commerce.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final CartDetailRepo cartDetailRepo;
    public void createCart(User user) {
        var cart = Cart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cartRepo.save(cart);
    }
    public Cart findByCurrentUser(User user) {
        return cartRepo.findOne(Example.of(Cart.builder().user(user).build())).orElseThrow();
    }
    public CartDetail addCartDetail(Cart cart, CartDetailRequest request) {
        // TODO Bagaimana cara agar saat produk yg sama diinput akan menambah quantity ? (Optional)
        // TODO Jika bisa sih tambah property total amount utk setiap cartDetail (Optional)

        var cartDetail = CartDetail.builder()
                .cart(cart)
                .product(productRepo.findById(request.getProductId()).orElseThrow())
                .quantity(request.getQuantity())
                .build();
        cartDetailRepo.save(cartDetail);
        return cartDetail;
    }

    public List<Cart> readAllCart() {
        return cartRepo.findAll();
    }

    // TODO Delete product list dalam cart
    // TODO Fitur checkout dan payment
}
