import React from 'react';
import { Link } from 'react-router-dom';
import { useWishlist } from '../../context/WishlistContext';
import './shop.css';
import '../../styles/wishlist.css';

export const Product = (props) => {
  const { id, name: productName, imageUrl: productImage, price } = props.data;
  const { addToWishlist, removeFromWishlist } = useWishlist();

  const imageSrc = `/products/${productImage}`;

  return (
    <div className='product'>
      <Link to={`/product/${id}`}>
        <img src={imageSrc} alt={productName} />
        <div className='description'>
          <p>
            <b>{productName}</b>
          </p>
          <p>${price}</p>
        </div>
      </Link>
      <button className='addToWishlist' onClick={() => addToWishlist(id)}>
        Add To Wishlist
      </button>
      <button className='removeFromWishlist' onClick={() => removeFromWishlist(id)}>
        Remove From Wishlist
      </button>
    </div>
  );
};
