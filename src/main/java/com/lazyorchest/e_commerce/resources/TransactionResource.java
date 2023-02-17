package com.lazyorchest.e_commerce.resources;

import com.lazyorchest.e_commerce.dto.PaymentRequest;
import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.CartDetail;
import com.lazyorchest.e_commerce.models.Transaction;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.repositories.TransactionRepo;
import com.lazyorchest.e_commerce.services.CartService;
import com.lazyorchest.e_commerce.services.TransactionService;
import com.lazyorchest.e_commerce.services.UserService;
import com.lazyorchest.e_commerce.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionResource {
    private final TransactionService transactionService;
    private final CartService cartService;
    private final JwtService jwtService;
    private final UserService userService;
    @PostMapping("/checkout/cart-detail/{cartDetailId}")
    public ResponseEntity<Transaction> checkout(HttpServletRequest request, @PathVariable Long cartDetailId, @RequestBody PaymentRequest paymentRequest) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        CartDetail cartDetail = cartService.findCartDetailById(cart, cartDetailId);
        return ResponseEntity.ok(transactionService.createTransaction(cartDetail, paymentRequest.getAmount()));
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllCurrentUserTransaction(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.ok(transactionService.getAllCurrentUserTransaction(cart));
    }
}
