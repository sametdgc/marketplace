import React, { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { fetchProductsByCategory } from '../api';
import { Product } from '../pages/shop/product';
import '../pages/shop/shop.css';

export const CategoryPage = () => {
  const { id } = useParams();
  const location = useLocation();
  const [products, setProducts] = useState([]);

  // Get categoryName from state or fallback to a default value
  const categoryName = location.state?.categoryName || 'Category';

  useEffect(() => {
    const getProducts = async () => {
      const response = await fetchProductsByCategory(id);
      setProducts(response.products);
    };
    getProducts();
  }, [id]);

  return (
    <div>
      <h2>Products in {categoryName}</h2>
      <div className='products'>
        {products.map(product => (
          <Product key={product.id} data={product} />
        ))}
      </div>
    </div>
  );
};
