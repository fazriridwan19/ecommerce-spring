package com.lazyorchest.e_commerce.services;

import com.lazyorchest.e_commerce.dto.CartDetailRequest;
import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.CartDetail;
import com.lazyorchest.e_commerce.models.Transaction;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.repositories.*;
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
    private final CartDetailRepo cartDetailRepo;
    private final TransactionRepo transactionRepo;
    public void createCart(User user) {
        var cart = Cart.builder()
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        cartRepo.save(cart);
    }
    public CartDetail addProductToCart(Long productId, Cart cart, CartDetailRequest request) {
        CartDetail cartDetail = CartDetail.builder()
                .cart(cart)
                .product(productRepo.findById(productId).orElseThrow())
                .quantity(request.getQuantity())
                .build();
        cartDetailRepo.save(cartDetail);

        transactionRepo.save(
                Transaction.builder()
                        .cartDetail(cartDetail)
                        .isSuccess(false)
                        .totalAmount(cartDetail.getQuantity() * cartDetail.getProduct().getPrice())
                        .createdAt(LocalDateTime.now())
                        .build()
        );
//        var cartDetailDb = cartDetailRepo.findOne(
//                Example.of(cartDetail)
//        ).orElse(cartDetail);
//
//        if (!cartDetailDb.equals(cartDetail)) {
//            cartDetailDb.setQuantity(cartDetailDb.getQuantity() + request.getQuantity());
//            cartDetailRepo.save(cartDetailDb);
//            return  cartDetailDb;
//        }
//
//        cartDetail.setQuantity(request.getQuantity());

        return cartDetail;
    }
    public List<Cart> readAllCart() {
        return cartRepo.findAll();
    }
    public Cart findByCurrentUser(User user) {
        return cartRepo
                .findOne(
                        Example.of(Cart.builder().user(user).build())
                )
                .orElseThrow();
    }
    public List<CartDetail> findAllCartDetailByCurrentUser(User user) {
        Cart cart = Cart.builder().user(user).build();
        Transaction transaction = Transaction.builder().isSuccess(false).build();
        CartDetail cartDetail = CartDetail.builder().cart(cart).transaction(transaction).build();

        return cartDetailRepo.findAll(Example.of(cartDetail));
    }
    public CartDetail findCartDetailById(Cart cart, Long id) {
        return cartDetailRepo.findOne(
                Example.of(
                        CartDetail.builder()
                                .id(id)
                                .cart(cart)
                                .build()
                )
        ).orElseThrow();
    }
    public CartDetail updateQuantityCartDetail(Cart cart, Long id, CartDetailRequest request) {
        CartDetail cartDetail = cartDetailRepo.findOne(
                Example.of(
                        CartDetail.builder()
                                .id(id)
                                .cart(cart)
                                .build()
                )
        ).orElseThrow();
        cartDetail.setQuantity(request.getQuantity());
        cartDetailRepo.save(cartDetail);
        return cartDetail;
    }
    public void deleteCartDetailFromCart(Cart cart, Long id) {
        CartDetail cartDetail = cartDetailRepo.findOne(
                Example.of(
                        CartDetail.builder()
                                .id(id)
                                .cart(cart)
                                .build()
                )
        ).orElseThrow();
        cartDetailRepo.delete(cartDetail);
    }
}
