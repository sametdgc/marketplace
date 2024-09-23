import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom
import { fetchAllProducts } from '../api/api'; // Adjust the path to your API function
import ProductCard from '../components/ProductCard'; // Adjust the path to your ProductCard component

const NewProducts = () => {
  const [products, setProducts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [pageSize, setPageSize] = useState(2); // Default size is 2

  const loadProducts = async (page = 0, size = 2) => {
    try {
      setLoading(true);
      const response = await fetchAllProducts(page, size);
      setProducts(response.products);
      setTotalPages(response.totalPages);
    } catch (error) {
      console.error('Error loading products:', error);
    } finally {
      setLoading(false);
    }
  };
  
  useEffect(() => {
    loadProducts(currentPage, pageSize);
  }, [currentPage, pageSize]);

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  const handlePageSizeChange = (e) => {
    setPageSize(parseInt(e.target.value, 10));
    setCurrentPage(0); // Reset to the first page whenever the size changes
  };

  return (
    <section
      id="home"
      className="w-full flex xl:flex-row flex-col justify-center min-h-screen gap-10 max-container"
    >
      <h1 className="text-2xl font-bold mb-4 text-center font-palanquin">New <span className='font-montserrat text-gray-800 font-semibold'>Products</span> </h1>

      <div className="container mx-auto p-4 max-w-screen-lg">

        {/* Page Size Selector */}
        <div className="text-center mb-4">
          <label htmlFor="pageSize" className="mr-2 font-montserrat">Items per page:</label>
          <select 
            id="pageSize" 
            value={pageSize} 
            onChange={handlePageSizeChange} 
            className="border p-2"
          >
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="4">4</option>
            <option value="6">6</option>
            <option value="8">8</option>
          </select>
        </div>

        {loading ? (
          <p>Loading products...</p>
        ) : (
          <div>
            <div 
              className="grid lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 grid-cols-1 gap-4"
            >
              {products.map((product) => (
                <ProductCard key={product.id} product={product} />
              ))}
            </div>

            {/* Pagination Controls */}
            <div className="flex justify-center mt-4">
              {Array.from({ length: totalPages }).map((_, index) => (
                <button
                  key={index}
                  type="button"
                  className={`mx-1 px-3 py-1 border ${index === currentPage ? 'bg-gray-100 text-black' : 'bg-gray-400'}`}
                  onClick={() => handlePageChange(index)}
                >
                  {index + 1}
                </button>
              ))}
            </div>

            {/* Browse All Button */}
            <div className="flex justify-center mt-8">
              <Link 
                to="/shop" 
                className="bg-blue-500 text-white px-4 py-2 rounded font-montserrat hover:bg-blue-600"
              >
                Browse All
              </Link>
            </div>
          </div>
        )}
        
      </div>
    </section>
  );
};

export default NewProducts;
