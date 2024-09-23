import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetchSellersProducts, createProduct, deleteProduct } from '../api';
import { AddProductModal } from './AddProductModal';
import '../styles/SellerProfile.css';

export const SellerProfile = () => {
  const [products, setProducts] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const getSellerProducts = async () => {
      try {
        const { products } = await fetchSellersProducts();
        setProducts(products);
      } catch (error) {
        console.error('Failed to fetch seller products:', error);
      }
    };
    getSellerProducts();
  }, []);

  const handleAddProduct = async (newProductData) => {
    try {
      await createProduct(newProductData);
      const { products } = await fetchSellersProducts();
      setProducts(products); // Refresh the list after adding
      setShowModal(false);
    } catch (error) {
      console.error('Failed to add product:', error);
    }
  };

  const handleDeleteProduct = (productId) => {
    deleteProduct(productId)
      .then(() => setProducts(products.filter(p => p.id !== productId)))
      .catch(error => console.error('Failed to delete product:', error));
  };

  return (
    <div className='seller-profile'>
      <h2>Your Products</h2>
      <button onClick={() => setShowModal(true)}>Add New Product</button>
      <div className='products'>
        {products.map(product => (
          <div key={product.id} className='product'>
            <img
              src={`/products/${product.imageUrl}`}
              alt={product.name}
              className='product-image'
            />
            <p>{product.name}</p>
            <p>${product.price}</p>
            <button onClick={() => navigate(`/edit-product/${product.id}`)}>Update</button>
            <button onClick={() => handleDeleteProduct(product.id)}>Delete</button>
          </div>
        ))}
      </div>

      <AddProductModal
        show={showModal}
        onHide={() => setShowModal(false)}
        onSave={handleAddProduct}
      />
    </div>
  );
};
