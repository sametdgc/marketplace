package com.obss.marketplace.controller;

import com.obss.marketplace.dto.SellerDTO;
import com.obss.marketplace.response.ApiResponse;
import com.obss.marketplace.service.SellerService;
import com.obss.marketplace.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/admin/sellers")
    public ResponseEntity<ApiResponse<Page<SellerDTO>>> getAllSellers(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SellerDTO> sellers = sellerService.findAll(pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sellers retrieved successfully", sellers));
    }

    @GetMapping("/admin/sellers/{sellerId}")
    public ResponseEntity<ApiResponse<SellerDTO>> getSellerById(@PathVariable Long sellerId) {
        SellerDTO sellerDTO = sellerService.findById(sellerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seller retrieved successfully", sellerDTO));
    }

    @DeleteMapping("/admin/sellers/{sellerId}")
    public ResponseEntity<ApiResponse<Void>> deleteSeller(@PathVariable Long sellerId) {
        sellerService.deleteById(sellerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seller deleted successfully", null));
    }

    // search for sellers endpoint
    @GetMapping("/admin/sellers/search")
    public ResponseEntity<ApiResponse<Page<SellerDTO>>> searchSellers(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<SellerDTO> sellers = sellerService.searchSellers(query, pageRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sellers found successfully", sellers));
    }


    @PutMapping("/admin/sellers/{sellerId}")
    public ResponseEntity<ApiResponse<SellerDTO>> updateSeller(@PathVariable Long sellerId, @RequestBody SellerDTO sellerDTO) {
        SellerDTO updatedSeller = sellerService.updateSeller(sellerId, sellerDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Seller updated successfully", updatedSeller));
    }

}
