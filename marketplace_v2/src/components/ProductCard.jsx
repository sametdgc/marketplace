import React from 'react';
import { Link } from 'react-router-dom';

const ProductCard = ({ product }) => {
  const { id, name, imageUrl, price } = product;

  return (
    <div className="flex flex-1 flex-col w-full max-sm:w-full items-center border-2 border-gray-200 p-4 rounded-lg shadow-lg bg-gray-100">
      <Link to={`/product/${id}`}>
        <img
          src={`/products/${imageUrl}`}
          alt={name}
          className="w-48 h-48 object-cover"
        />
        <div className="p-4">
          <h3 className="text-lg font-semibold mb-2">{name}</h3>
          <p className="text-green-600 font-bold">${price}</p>
        </div>
      </Link>
    </div>
  );
};

export default ProductCard;
