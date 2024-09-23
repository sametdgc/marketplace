// src/pages/SearchResults.js
import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { searchProducts } from '../api';
import { Product } from '../pages/shop/product';
import '../pages/shop/shop.css';

export const SearchResults = () => {
  const location = useLocation();
  const [products, setProducts] = useState([]);
  const query = new URLSearchParams(location.search).get('query');

  useEffect(() => {
    const fetchSearchResults = async () => {
      try {
        const result = await searchProducts(query);
        setProducts(result.products);
      } catch (error) {
        console.error('Failed to fetch search results:', error);
      }
    };

    if (query) {
      fetchSearchResults();
    }
  }, [query]);

  return (
    <div>
      <h2>Search Results for "{query}"</h2>
      <div className="products">
        {products.length > 0 ? (
          products.map(product => (
            <Product key={product.id} data={product} />
          ))
        ) : (
          <p>No products found for this search query.</p>
        )}
      </div>
    </div>
  );
};
