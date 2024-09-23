package com.obss.marketplace.service;

import com.obss.marketplace.dto.ProductDTO;
import com.obss.marketplace.dto.SellerDTO;
import com.obss.marketplace.dto.UserDTO;
import com.obss.marketplace.dto.UserUpdateDTO;
import com.obss.marketplace.exception.ProductNotFoundException;
import com.obss.marketplace.exception.SellerNotFoundException;
import com.obss.marketplace.exception.UserNotFoundException;
import com.obss.marketplace.mapper.ProductMapper;
import com.obss.marketplace.mapper.SellerMapper;
import com.obss.marketplace.mapper.UserMapper;
import com.obss.marketplace.model.Product;
import com.obss.marketplace.model.Role;
import com.obss.marketplace.model.Seller;
import com.obss.marketplace.model.User;
import com.obss.marketplace.repository.ProductRepository;
import com.obss.marketplace.repository.RoleRepository;
import com.obss.marketplace.repository.SellerRepository;
import com.obss.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.obss.marketplace.exception.RoleNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       ProductRepository productRepository,
                       SellerRepository sellerRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.roleRepository = roleRepository;
    }

    public Page<User> findAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long userId, UserUpdateDTO userDTO) {
        User existingUser = findById(userId);
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    public void deleteById(Long userId) {
        User existingUser = findById(userId);
        userRepository.delete(existingUser);
    }

    // wishlist specific methods

    public void addProductToWishlist(Long userId, Long productId) {
        User user = findById(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        user.getFavoriteProducts().add(product);
        userRepository.save(user);
    }

    public Set<ProductDTO> getWishlist(Long userId) {
        User user = findById(userId);
        return user.getFavoriteProducts().stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toSet());
    }

    public void removeProductFromWishlist(Long userId, Long productId) {
        User user = findById(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));
        user.getFavoriteProducts().remove(product);
        userRepository.save(user);
    }

    // blacklist specific methods

    public void addSellerToBlacklist(Long userId, Long sellerId) {
        User user = findById(userId);
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller with ID " + sellerId + " not found"));
        user.getBlacklistedSellers().add(seller);
        userRepository.save(user);
    }

    public Set<SellerDTO> getBlacklist(Long userId) {
        User user = findById(userId);
        return user.getBlacklistedSellers().stream()
                .map(SellerMapper.INSTANCE::toDTO)
                .collect(Collectors.toSet());
    }

    public void removeSellerFromBlacklist(Long userId, Long sellerId) {
        User user = findById(userId);
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new SellerNotFoundException("Seller with ID " + sellerId + " not found"));
        user.getBlacklistedSellers().remove(seller);
        userRepository.save(user);
    }

    // search for user
    public Page<UserDTO> searchUsers(String query, PageRequest pageRequest) {
        return userRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(query, query, query, pageRequest)
                .map(UserMapper.INSTANCE::toDTO);
    }

    // user upgrade to seller

    @Transactional
    public void upgradeToSeller(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        Role sellerRole = roleRepository.findByName("ROLE_SELLER")
                .orElseThrow(() -> new RoleNotFoundException("Seller role not found"));

        if (user.getRole().getName().equals("ROLE_SELLER")) {
            throw new IllegalStateException("User is already a seller");
        }

        user.setRole(sellerRole);

        Seller seller = new Seller();
        seller.setUser(user);
        sellerRepository.save(seller);

        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }
}
