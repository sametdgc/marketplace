import React, { useEffect, useState } from 'react';
import { fetchUserProfile, fetchBlacklist } from '../api'; // Assume these functions are implemented in api.js

const UserProfile = () => {
  const [user, setUser] = useState(null);
  const [blacklist, setBlacklist] = useState([]);

  useEffect(() => {
    const getUserProfile = async () => {
      try {
        const userData = await fetchUserProfile();
        setUser(userData);
      } catch (error) {
        console.error('Failed to fetch user profile:', error);
      }
    };

    const getBlacklist = async () => {
      try {
        const blacklistData = await fetchBlacklist();
        setBlacklist(blacklistData);
      } catch (error) {
        console.error('Failed to fetch blacklist:', error);
      }
    };

    getUserProfile();
    getBlacklist();
  }, []);

  return (
    <div className="user-profile">
      <h2>User Profile</h2>
      {user ? (
        <div>
          <p><strong>First Name:</strong> {user.firstName}</p>
          <p><strong>Last Name:</strong> {user.lastName}</p>
          <p><strong>Email:</strong> {user.email}</p>
        </div>
      ) : (
        <p>Loading user information...</p>
      )}

      <h3>Blacklisted Sellers</h3>
      {blacklist.length > 0 ? (
        <ul>
          {blacklist.map(seller => (
            <li key={seller.id}>{seller.name}</li>
          ))}
        </ul>
      ) : (
        <p>No blacklisted sellers.</p>
      )}
    </div>
  );
};

export default UserProfile;
