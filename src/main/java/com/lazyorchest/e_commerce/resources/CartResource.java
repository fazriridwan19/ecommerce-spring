package com.lazyorchest.e_commerce.resources;

import com.lazyorchest.e_commerce.dto.CartDetailRequest;
import com.lazyorchest.e_commerce.models.Cart;
import com.lazyorchest.e_commerce.models.CartDetail;
import com.lazyorchest.e_commerce.models.User;
import com.lazyorchest.e_commerce.services.CartService;
import com.lazyorchest.e_commerce.services.UserService;
import com.lazyorchest.e_commerce.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartResource {
    private final CartService cartService;
    private final JwtService jwtService;
    private final UserService userService;
    @PostMapping("/add/product/{productId}")
    public ResponseEntity<CartDetail> addProductToCart(HttpServletRequest request, @PathVariable Long productId, @RequestBody CartDetailRequest cartDetailRequest) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.ok(cartService.addProductToCart(productId, cart, cartDetailRequest));
    }
    @GetMapping
    public ResponseEntity<Cart> readAllCartByCurrentUser(HttpServletRequest request) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(cartService.findByCurrentUser(user));
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<CartDetail> readCurrentUserCartDetailById(HttpServletRequest request, @PathVariable Long id) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.ok(cartService.findCartDetailById(cart, id));
    }
    @PutMapping("/detail/{id}")
    public ResponseEntity<CartDetail> updateQuantityCurrentUserCartDetailById(HttpServletRequest request, @PathVariable Long id, @RequestBody CartDetailRequest cartDetailRequest) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        return ResponseEntity.ok(cartService.updateQuantityCartDetail(cart, id, cartDetailRequest));
    }
    @DeleteMapping("/detail/{id}")
    public ResponseEntity<Map<String, String>> deleteCurrentUserCartDetailById(HttpServletRequest request, @PathVariable Long id) {
        final String token = request.getHeader("Authorization").substring(7);
        final String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        Cart cart = cartService.findByCurrentUser(user);
        cartService.deleteCartDetailFromCart(cart, id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "product deleted");
        return ResponseEntity.ok(response);
    }
}
