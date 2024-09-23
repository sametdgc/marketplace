package com.obss.marketplace.controller;

import com.obss.marketplace.dto.ProductDTO;
import com.obss.marketplace.dto.SellerDTO;
import com.obss.marketplace.dto.UserDTO;
import com.obss.marketplace.dto.UserUpdateDTO;
import com.obss.marketplace.mapper.SellerMapper;
import com.obss.marketplace.mapper.UserMapper;
import com.obss.marketplace.response.ApiResponse;
import com.obss.marketplace.service.UserService;
import com.obss.marketplace.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserDTO> users = userService.findAll(pageRequest).map(UserMapper.INSTANCE::toDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Users retrieved successfully", users));
    }

    @GetMapping("/public/users/me")
    public ResponseEntity<ApiResponse<UserDTO>> getMyInfo() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(userService.findById(userId));
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", userDTO));
    }

    @GetMapping("/public/users/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = UserMapper.INSTANCE.toDTO(userService.findById(userId));
        return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully", userDTO));
    }

    @PostMapping("/admin/users")
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = UserMapper.INSTANCE.toDTO(userService.save(UserMapper.INSTANCE.toEntity(userDTO)));
        return ResponseEntity.ok(new ApiResponse<>(true, "User created successfully", createdUser));
    }

//    @PutMapping("/public/users/{userId}")
//    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
//        UserDTO updatedUser = UserMapper.INSTANCE.toDTO(userService.updateUser(userId, userDTO));
//        return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
//    }
@PutMapping("/public/users")
public ResponseEntity<ApiResponse<UserDTO>> updateUser(
        @RequestBody UserUpdateDTO userDTO) {

    // Retrieve the currently authenticated user
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Long userId = userService.findByEmail(userDetails.getUsername()).getId();  // Assuming `findByEmail` method exists
    // Perform the update using the service and mapper
    UserDTO updatedUser = UserMapper.INSTANCE.toDTO(userService.updateUser(userId, userDTO));
    return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
}


    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
    }

    // wishlist specific endpoints

//    @GetMapping("/public/user-special/{userId}/wishlist")
//    public ResponseEntity<ApiResponse<Set<ProductDTO>>> getWishlist(@PathVariable Long userId) {
//        Set<ProductDTO> wishlist = userService.getWishlist(userId);
//        return ResponseEntity.ok(new ApiResponse<>(true, "Product added to wishlist.", wishlist));
//    }
    //get users wishlist
    @GetMapping("/public/user-special/wishlist")
    public ResponseEntity<ApiResponse<Set<ProductDTO>>> getWishlist() {
        // Retrieve the currently authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();  // Assuming `findByEmail` method exists

        Set<ProductDTO> wishlist = userService.getWishlist(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Wishlist retrieved successfully.", wishlist));
    }
    @PostMapping("/public/user-special/wishlist/{productId}")
    public ResponseEntity<ApiResponse<Void>> addProductToWishlist(@PathVariable Long productId) {
        // Retrieve the currently authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Retrieve the userId based on the authenticated user's email
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();

        // Add the product to the user's wishlist
        userService.addProductToWishlist(userId, productId);

        return ResponseEntity.ok(new ApiResponse<>(true, "Product added to wishlist", null));
    }

    @DeleteMapping("/public/user-special/wishlist/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeProductFromWishlist(@PathVariable Long productId) {
        // Retrieve the currently authenticated user from the JWT token
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();  // Assuming `findByEmail` method exists

        // Call the service to remove the product from the user's wishlist
        userService.removeProductFromWishlist(userId, productId);

        return ResponseEntity.ok(new ApiResponse<>(true, "Product removed from wishlist", null));
    }

    // blacklist specific endpoints

    @GetMapping("/public/user-special/blacklist")
    public ResponseEntity<ApiResponse<Set<SellerDTO>>> getBlacklist() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        Set<SellerDTO> blacklist = userService.getBlacklist(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Blacklist retrieved successfully", blacklist));
    }

    @PostMapping("/public/user-special/blacklist/{sellerId}")
    public ResponseEntity<ApiResponse<Void>> addSellerToBlacklist(@PathVariable Long sellerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        userService.addSellerToBlacklist(userId, sellerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seller added to blacklist", null));
    }

    @DeleteMapping("/public/user-special/blacklist/{sellerId}")
    public ResponseEntity<ApiResponse<Void>> removeSellerFromBlacklist(@PathVariable Long sellerId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userService.findByEmail(userDetails.getUsername()).getId();
        userService.removeSellerFromBlacklist(userId, sellerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seller removed from blacklist", null));
    }

    // user search endpoint

    @GetMapping("/admin/users/search")
    public ResponseEntity<ApiResponse<Page<UserDTO>>> searchUsers(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<UserDTO> users = userService.searchUsers(query, pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Users found successfully", users));
    }

    // upgrade to seller endpoint
    @PutMapping("/admin/users/{userId}/upgrade-to-seller")
    public ResponseEntity<ApiResponse<Void>> upgradeToSeller(@PathVariable Long userId) {
        userService.upgradeToSeller(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User upgraded to seller successfully", null));
    }

}
