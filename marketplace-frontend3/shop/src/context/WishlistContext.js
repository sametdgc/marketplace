import React, { createContext, useContext, useState } from 'react';
import api  from '../api'; // Import the Axios instance

export const WishlistContext = createContext();

export const WishlistProvider = ({ children }) => {
  const [wishlist, setWishlist] = useState([]);

  const fetchWishlist = async () => {
    try {
      const response = await api.get('/public/user-special/wishlist');
      setWishlist(response.data.data);
    } catch (error) {
      console.error('Failed to fetch wishlist:', error);
    }
  };

  const addToWishlist = async (productId) => {
    try {
      await api.post(`/public/user-special/wishlist/${productId}`);
      await fetchWishlist(); // Refresh the wishlist after adding a product
    } catch (error) {
      console.error('Failed to add product to wishlist:', error);
    }
  };

  const removeFromWishlist = async (productId) => {
    try {
      await api.delete(`/public/user-special/wishlist/${productId}`);
      await fetchWishlist(); // Refresh the wishlist after deleting a product
    } catch (error) {
      console.error('Failed to delete product from wishlist:', error);
    }
  };

  return (
    <WishlistContext.Provider value={{ wishlist, fetchWishlist, addToWishlist, removeFromWishlist }}>
      {children}
    </WishlistContext.Provider>
  );
};

export const useWishlist = () => useContext(WishlistContext);
