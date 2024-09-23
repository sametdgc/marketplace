import React from "react";
import { useParams } from "react-router-dom";

const ProductDetails = () => {
  const { id } = useParams(); // Get the product ID from the URL

  // Fetch or display product details based on the ID
  return (
    <div>
      <h1>Product Details for ID: {id}</h1>
      {/* Display the product details here */}
    </div>
  );
};

export default ProductDetails;
