import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { fetchSellerById, addToBlacklist, removeFromBlacklist } from '../api'; 
// import '../styles/sellerProfile.css';

export const SellerProfile2 = () => {
  const { sellerId } = useParams();

  const handleAddToBlacklist = async () => {
    try {
      await addToBlacklist(sellerId);
      alert('Seller added to blacklist');
    } catch (error) {
      console.error('Failed to add seller to blacklist:', error);
    }
  };

  const handleRemoveFromBlacklist = async () => {
    try {
      await removeFromBlacklist(sellerId);
      alert('Seller removed from blacklist');
    } catch (error) {
      console.error('Failed to remove seller from blacklist:', error);
    }
  };

//   if (!seller) {
//     return <div>Loading...</div>;
//   }

//   const { firstName, lastName, email } = seller;

  return (
    <div className='seller-profile'>
      <h2>Seller Profile</h2>
      {/* <p><strong>First Name:</strong> {firstName}</p>
      <p><strong>Last Name:</strong> {lastName}</p>
      <p><strong>Email:</strong> {email}</p> */}
      <div className="blacklist-buttons">
        <button onClick={handleAddToBlacklist}>Add to Blacklist</button>
        <button onClick={handleRemoveFromBlacklist}>Remove from Blacklist</button>
      </div>
    </div>
  );
};
