package com.obss.marketplace.service;

import com.obss.marketplace.dto.SellerDTO;
import com.obss.marketplace.exception.SellerNotFoundException;
import com.obss.marketplace.mapper.SellerMapper;
import com.obss.marketplace.model.Seller;
import com.obss.marketplace.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Page<SellerDTO> findAll(PageRequest pageRequest) {
        return sellerRepository.findAll(pageRequest).map(SellerMapper.INSTANCE::toDTO);
    }

    public SellerDTO findById(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller with ID " + sellerId + " not found"));
        return SellerMapper.INSTANCE.toDTO(seller);
    }

    public void deleteById(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller with ID " + sellerId + " not found"));
        sellerRepository.delete(seller);
    }

    // search for sellers

    public Page<SellerDTO> searchSellers(String query, PageRequest pageRequest) {
        return sellerRepository.findByUserFirstNameContainingOrUserLastNameContainingOrUserEmailContaining(query, query, query, pageRequest)
                .map(SellerMapper.INSTANCE::toDTO);
    }

    public Seller findByEmail(String username) {
        return sellerRepository.findByUserEmail(username)
                .orElseThrow(() -> new SellerNotFoundException("Seller with email " + username + " not found"));
    }


    public SellerDTO updateSeller(Long sellerId, SellerDTO sellerDTO) {
        Seller existingSeller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with id: " + sellerId));

        existingSeller.getUser().setFirstName(sellerDTO.getFirstName());
        existingSeller.getUser().setLastName(sellerDTO.getLastName());
        existingSeller.getUser().setEmail(sellerDTO.getEmail());

        Seller updatedSeller = sellerRepository.save(existingSeller);

        return SellerMapper.INSTANCE.toDTO(updatedSeller);
    }

}
