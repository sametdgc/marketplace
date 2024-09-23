import React, { useEffect, useState } from 'react';
import { Product } from './product';
import './shop.css';
import ControlledCarousel from '../../components/carousel';
import { fetchAllProducts } from '../../api';

export const Shop = () => {
  const [products, setProducts] = useState([]);
  
  useEffect(() => {
    const getProducts = async () => {
      try {
        const response = await fetchAllProducts();
        setProducts(response.products); // Update the state with the fetched products
      } catch (error) {
        console.error('Failed to fetch products:', error);
      }
    };
    getProducts();
  }, []);

  return (
    <div className='shop'>
      <div><ControlledCarousel /></div>
      <div className="products">
        {products.map((product) => (
          <Product key={product.id} data={product} />
        ))}
      </div>
    </div>
  );
};
