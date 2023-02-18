package com.lazyorchest.e_commerce.resources;

import com.lazyorchest.e_commerce.dto.ProductRequest;
import com.lazyorchest.e_commerce.models.Product;
import com.lazyorchest.e_commerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }
    @GetMapping
    public ResponseEntity<List<Product>> readProducts(@RequestParam(name = "category") Optional<String> category) {

        return ResponseEntity.ok(productService.readAllProduct(category.orElse("ALL")));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> readProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.readProductById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(productService.updateProduct(request, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        Map<String, String> map = new HashMap<>();
        map.put("message", "product deleted");
        productService.deleteProduct(id);
        return ResponseEntity.ok(map);
    }
}
