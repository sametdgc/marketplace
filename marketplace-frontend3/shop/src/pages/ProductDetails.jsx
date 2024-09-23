import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { fetchProductById } from '../api';
import { useWishlist } from '../context/WishlistContext';  
import '../styles/productDetails.css';

export const ProductDetails = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const { addToWishlist, removeFromWishlist } = useWishlist(); 

  useEffect(() => {
    const getProduct = async () => {
      try {
        const response = await fetchProductById(id);
        setProduct(response);
      } catch (error) {
        console.error('Failed to fetch product details:', error);
      }
    };
    getProduct();
  }, [id]);

  if (!product) {
    return <div>Loading...</div>;
  }

  const { name, description, price, quantity, imageUrl, categoryName, sellerFirstName, sellerLastName, sellerId } = product;

  return (
    <div className='product-details'>
      <img src={`/products/${imageUrl}`} alt={name} className='product-image' />
      <div className='product-info'>
        <h1>{name}</h1>
        <p>{description}</p>
        <p><strong>Price:</strong> ${price}</p>
        <p><strong>Quantity:</strong> {quantity}</p>
        <p><strong>Category:</strong> {categoryName}</p>
        <p className='seller-info'>
          <strong>Seller:</strong> 
          <Link to={`/seller-profile/${sellerId}`}>
            {sellerFirstName} {sellerLastName}
          </Link>
        </p>
        <div className="wishlist-buttons">
          <button className='addToWishlist' onClick={() => addToWishlist(id)}>
            Add To Wishlist
          </button>
          <button className='removeFromWishlist' onClick={() => removeFromWishlist(id)}>
            Remove From Wishlist
          </button>
        </div>
      </div>
    </div>
  );
};
