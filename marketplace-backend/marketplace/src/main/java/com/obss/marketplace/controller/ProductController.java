package com.obss.marketplace.controller;

import com.obss.marketplace.dto.ProductDTO;
import com.obss.marketplace.dto.ProductUpdateDTO;
import com.obss.marketplace.model.Seller;
import com.obss.marketplace.response.ApiResponse;
import com.obss.marketplace.service.ProductService;
import com.obss.marketplace.service.SellerService;
import com.obss.marketplace.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final SellerService sellerService;

    @Autowired
    public ProductController(ProductService productService, SellerService sellerService) {
        this.productService = productService;
        this.sellerService = sellerService;
    }

    @GetMapping("/public/products")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getAllProducts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.findAll(pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products retrieved successfully", products));
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.findAllByCategory(categoryId, pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products retrieved successfully", products));
    }

    //get sellers products
    @GetMapping("/seller/products")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> getSellerProducts(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Seller seller = sellerService.findByEmail(userDetails.getUsername());
        Long sellerId = seller.getId();

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.findAllBySeller(sellerId, pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products retrieved successfully", products));
    }

    // find product by id
    @GetMapping("/public/products/{productId}")
    public ResponseEntity<ApiResponse<ProductDTO>> getProductById(@PathVariable Long productId) {
        ProductDTO productDTO = productService.findById(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product retrieved successfully", productDTO));
    }

    //     seller create product endpoint
    @PostMapping("/seller/products")
    public ResponseEntity<ApiResponse<ProductDTO>> createProduct(@RequestBody ProductDTO productDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Seller seller = sellerService.findByEmail(userDetails.getUsername());
        Long sellerId = seller.getId();

        ProductDTO createdProduct = productService.createProduct(productDTO, sellerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product created successfully", createdProduct));
    }


//
//    @PutMapping("/seller/products/{productId}")
//    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
//            @PathVariable Long productId,
//            @RequestBody ProductDTO productDTO,
//            @RequestParam Long sellerId) {
//        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO, sellerId);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", updatedProduct));
//    }

    // seller update product endpoint
@PutMapping("/seller/products/{productId}")
public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(
        @PathVariable Long productId,
        @RequestBody ProductUpdateDTO productDTO) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);

    return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", updatedProduct));
}


    @DeleteMapping("/seller/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable Long productId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        productService.deleteProduct(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product deleted successfully", null));
    }

    // product search endpoint admin

    @GetMapping("/public/products/search")
    public ResponseEntity<ApiResponse<Page<ProductDTO>>> searchProducts(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.searchProducts(query, pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Products found successfully", products));
    }


}
