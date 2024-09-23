import React, { useEffect, useState } from 'react';
import {
  fetchAllUsers,
  fetchAllSellers,
  fetchAllProducts,
  deleteUser,
  deleteProduct,
  deleteSeller,
  searchUsers,
  searchSellers,
  searchProducts,
  updateProduct,
  updateUser,
} from '../api';
import { UpdateProductModal } from './UpdateProductModal';
import { UpdateUserModal } from './UpdateUserModal';
import '../styles/AdminDashboard.css';

export const AdminDashboard = () => {
  const [users, setUsers] = useState([]);
  const [sellers, setSellers] = useState([]);
  const [products, setProducts] = useState([]);
  const [userSearchQuery, setUserSearchQuery] = useState('');
  const [sellerSearchQuery, setSellerSearchQuery] = useState('');
  const [productSearchQuery, setProductSearchQuery] = useState('');

  const [selectedProduct, setSelectedProduct] = useState(null);
  const [selectedUser, setSelectedUser] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [showUserModal, setShowUserModal] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const { users } = await fetchAllUsers();
        const { sellers } = await fetchAllSellers();
        const { products } = await fetchAllProducts();

        setUsers(users);
        setSellers(sellers);
        setProducts(products);
      } catch (error) {
        console.error('Failed to fetch data:', error);
      }
    };

    fetchData();
  }, []);

  const handleDeleteUser = (userId) => {
    deleteUser(userId)
      .then(() => setUsers(users.filter(user => user.id !== userId)))
      .catch(error => console.error('Failed to delete user:', error));
  };

  const handleDeleteSeller = (sellerId) => {
    deleteSeller(sellerId)
      .then(() => setSellers(sellers.filter(seller => seller.sellerId !== sellerId)))
      .catch(error => console.error('Failed to delete seller:', error));
  };

  const handleDeleteProduct = (productId) => {
    deleteProduct(productId)
      .then(() => setProducts(products.filter(product => product.id !== productId)))
      .catch(error => console.error('Failed to delete product:', error));
  };

  const handleUpdateProduct = (productId, updatedProduct) => {
    updateProduct(productId, updatedProduct)
      .then(() => {
        setProducts(products.map(product => (product.id === productId ? updatedProduct : product)));
        setShowModal(false);
      })
      .catch(error => console.error('Failed to update product:', error));
  };

  const handleUpdateUser = (userId, updatedUser) => {
    updateUser(userId, updatedUser)
      .then(() => {
        setUsers(users.map(user => (user.id === userId ? updatedUser : user)));
        setShowUserModal(false);
      })
      .catch(error => console.error('Failed to update user:', error));
  };

  const handleEditProduct = (product) => {
    setSelectedProduct(product);
    setShowModal(true);
  };

  const handleEditUser = (user) => {
    setSelectedUser(user);
    setShowUserModal(true);
  };

  const handleSearchUsers = async (e) => {
    e.preventDefault();
    try {
      const { users } = await searchUsers(userSearchQuery);
      setUsers(users);
    } catch (error) {
      console.error('Failed to search users:', error);
    }
  };

  const handleSearchSellers = async (e) => {
    e.preventDefault();
    try {
      const { sellers } = await searchSellers(sellerSearchQuery);
      setSellers(sellers);
    } catch (error) {
      console.error('Failed to search sellers:', error);
    }
  };

  const handleSearchProducts = async (e) => {
    e.preventDefault();
    try {
      const { products } = await searchProducts(productSearchQuery);
      setProducts(products);
    } catch (error) {
      console.error('Failed to search products:', error);
    }
  };

  return (
    <div className='admin-dashboard'>
      <h2>Admin Dashboard</h2>

      {/* Users Table */}
      <div className='users-section'>
        <h3>Users</h3>
        <form onSubmit={handleSearchUsers}>
          <input
            type='text'
            placeholder='Search users...'
            value={userSearchQuery}
            onChange={(e) => setUserSearchQuery(e.target.value)}
          />
          <button type='submit'>Search</button>
        </form>
        <table className='data-table'>
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map(user => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.email}</td>
                <td>{user.firstName}</td>
                <td>{user.lastName}</td>
                <td>
                  <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
                  <button onClick={() => handleEditUser(user)}>Update</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Sellers Table */}
      <div className='sellers-section'>
        <h3>Sellers</h3>
        <form onSubmit={handleSearchSellers}>
          <input
            type='text'
            placeholder='Search sellers...'
            value={sellerSearchQuery}
            onChange={(e) => setSellerSearchQuery(e.target.value)}
          />
          <button type='submit'>Search</button>
        </form>
        <table className='data-table'>
          <thead>
            <tr>
              <th>ID</th>
              <th>Email</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sellers.map(seller => (
              <tr key={seller.sellerId}>
                <td>{seller.sellerId}</td>
                <td>{seller.email}</td>
                <td>{seller.firstName}</td>
                <td>{seller.lastName}</td>
                <td>
                  <button onClick={() => handleDeleteSeller(seller.sellerId)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Products Table */}
      <div className='products-section'>
        <h3>Products</h3>
        <form onSubmit={handleSearchProducts}>
          <input
            type='text'
            placeholder='Search products...'
            value={productSearchQuery}
            onChange={(e) => setProductSearchQuery(e.target.value)}
          />
          <button type='submit'>Search</button>
        </form>
        <table className='data-table'>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Price</th>
              <th>Quantity</th> {/* Added this column */}
              <th>Category</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {products.map(product => (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>${product.price}</td>
                <td>{product.quantity}</td> {/* Added this column */}
                <td>{product.categoryName}</td>
                <td>
                  <button onClick={() => handleDeleteProduct(product.id)}>Delete</button>
                  <button onClick={() => handleEditProduct(product)}>Update</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Update Modals */}
      {selectedProduct && (
        <UpdateProductModal
          show={showModal}
          onHide={() => setShowModal(false)}
          product={selectedProduct}
          onSave={(updatedProduct) => handleUpdateProduct(selectedProduct.id, updatedProduct)}
        />
      )}
      {selectedUser && (
        <UpdateUserModal
          show={showUserModal}
          onHide={() => setShowUserModal(false)}
          user={selectedUser}
          onSave={(updatedUser) => handleUpdateUser(selectedUser.id, updatedUser)}
        />
      )}
    </div>
  );
};
