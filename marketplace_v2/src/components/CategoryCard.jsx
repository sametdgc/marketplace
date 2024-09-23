import React from "react";
import { Link } from "react-router-dom"; 

const CategoryCard = ({ data }) => {
  return (
    <div className="flex flex-1 flex-col w-full max-sm:w-full items-center border-2 border-gray-200 p-4 rounded-lg shadow-lg bg-gray-100">
      {/* Wrap the image in a Link to navigate to the ProductDetails page */}
      <Link to={`/product/${data.name}`} className="block">
        <img
          src={data.imgURL}
          alt={data.name}
          className="w-[200px] h-[200px] object-contain"
        />
      </Link>

      <Link to={`/product/${data.name}`} className="mt-3 bg-white rounded-lg w-full block">
        <h3 className="font-semibold font-palanquin text-center text-gray-800 py-3">
          {data.name}
        </h3>
      </Link>
    </div>
  );
};

export default CategoryCard;
