import React, { useEffect } from 'react';
import { useWishlist } from '../context/WishlistContext';
import { Product } from '../pages/shop/product';
import { useNavigate } from 'react-router-dom';

export const Wishlist = () => {
  const { wishlist, fetchWishlist } = useWishlist();
  const navigate = useNavigate();

  const isLoggedIn = !!localStorage.getItem('token'); // Check if the user is logged in

  useEffect(() => {
    if (isLoggedIn) {
      fetchWishlist(); // Fetch wishlist only if the user is logged in
    }
  }, [isLoggedIn]);

  if (!isLoggedIn) {
    return (
      <div>
        <h2>Your Wishlist</h2>
        <p>You need to <span onClick={() => navigate('/login')} style={{ color: 'blue', cursor: 'pointer', textDecoration: 'underline' }}>log in</span> to see your favorites.</p>
      </div>
    );
  }

  return (
    <div>
      <h2>Your Wishlist</h2>
      <div className='products'>
        {wishlist.length > 0 ? (
          wishlist.map(product => (
            <Product key={product.id} data={product} />
          ))
        ) : (
          <p>Your wishlist is empty.</p>
        )}
      </div>
    </div>
  );
};
