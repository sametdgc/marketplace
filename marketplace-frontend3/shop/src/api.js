import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

// Create an Axios instance
const api = axios.create({
  baseURL: API_URL,
});

// Set the Authorization header if the token exists
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers['Authorization'] = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

api.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      localStorage.removeItem('token');
      delete api.defaults.headers.common['Authorization'];
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);


export const login = async (email, password) => {
  try {
    const response = await api.post('/auth/login', { email, password });
    const { token, role } = response.data;

    // Store the token and role in localStorage
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);

    // Return the token and role for further use
    return { token, role };
  } catch (error) {
    console.error('Login failed:', error);
    throw error;
  }
};

export const signup = async (firstName, lastName, email, password) => {
  try {
    const response = await api.post('/auth/signup', { firstName, lastName, email, password });
    return response.data;
  } catch (error) {
    console.error('Signup failed:', error);
    throw error;
  }
};

// Logout function
export const logout = () => {
  localStorage.removeItem('token');
  delete api.defaults.headers.common['Authorization'];
};

// Fetch all categories
export const fetchCategories = async () => {
  try {
    const response = await api.get('/public/categories');
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch categories:', error);
    throw error;
  }
};

// Fetch category by ID and get products for that category
export const fetchProductsByCategory = async (categoryId, page = 0, size = 10) => {
  try {
    const response = await api.get(`/public/categories/${categoryId}/products`, {
      params: { page, size },
    });
    return {
      products: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to fetch products by category:', error);
    throw error;
  }
};


// Fetch all products with pagination
export const fetchAllProducts = async (page = 0, size = 2) => {
  try {
    const response = await api.get('/public/products', {
      params: { page, size },
    });
    return {
      products: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to fetch all products:', error);
    throw error;
  }
};


// Fetch user's wishlist
export const fetchWishlist = async () => {
  try {
    const response = await api.get('/public/user-special/wishlist');
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch wishlist:', error);
    throw error;
  }
};

// Add product to wishlist
export const addToWishlist = async (productId) => {
  try {
    await api.post(`/public/user-special/wishlist/${productId}`);
  } catch (error) {
    console.error('Failed to add product to wishlist:', error);
    throw error;
  }
};

// Remove product from wishlist
export const removeFromWishlist = async (productId) => {
  try {
    await api.delete(`/public/user-special/wishlist/${productId}`);
  } catch (error) {
    console.error('Failed to remove product from wishlist:', error);
    throw error;
  }
};

// get product details (by id)
export const fetchProductById = async (productId) => {
  try {
    const response = await api.get(`/public/products/${productId}`);
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch product details:', error);
    throw error;
  }
};


// search for products
export const searchProducts = async (query, page = 0, size = 10) => {
  try {
    const response = await api.get('/public/products/search', {
      params: { query, page, size },
    });
    return {
      products: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to search products:', error);
    throw error;
  }
};


export const createProduct = async (productData) => {
  try {
    const response = await api.post('/seller/products', productData);
    return response.data;
  } catch (error) {
    console.error('Failed to create product:', error);
    throw error;
  }
};

// Get all users (admin access)
export const fetchAllUsers = async (page = 0, size = 10) => {
  try {
    const response = await api.get('/admin/users', {
      params: { page, size },
    });
    return {
      users: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to fetch users:', error);
    throw error;
  }
};

// Get all sellers (admin access)
export const fetchAllSellers = async (page = 0, size = 10) => {
  try {
    const response = await api.get('/admin/sellers', {
      params: { page, size },
    });
    return {
      sellers: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to fetch sellers:', error);
    throw error;
  }
};

// Delete a user (admin access)
export const deleteUser = async (userId) => {
  try {
    const response = await api.delete(`/admin/users/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Failed to delete user:', error);
    throw error;
  }
};

// Delete a seller (admin access)
export const deleteSeller = async (sellerId) => {
  try {
    const response = await api.delete(`/admin/sellers/${sellerId}`);
    return response.data;
  } catch (error) {
    console.error('Failed to delete seller:', error);
    throw error;
  }
};

// Delete a product (seller access)
export const deleteProduct = async (productId) => {
  try {
    const response = await api.delete(`/seller/products/${productId}`);
    return response.data;
  } catch (error) {
    console.error('Failed to delete product:', error);
    throw error;
  }
};

// Get all products for the currently logged-in seller
export const fetchSellersProducts = async (page = 0, size = 10) => {
  try {
    const response = await api.get('/seller/products', {
      params: { page, size },
    });
    return {
      products: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to fetch seller\'s products:', error);
    throw error;
  }
};


export const searchUsers = async (query, page = 0, size = 10) => {
  try {
    const response = await api.get('/admin/users/search', {
      params: { query, page, size },
    });
    return {
      users: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to search users:', error);
    throw error;
  }
};


export const searchSellers = async (query, page = 0, size = 10) => {
  try {
    const response = await api.get('/admin/sellers/search', {
      params: { query, page, size },
    });
    return {
      sellers: response.data.data.content,
      totalPages: response.data.data.totalPages,
      totalElements: response.data.data.totalElements,
    };
  } catch (error) {
    console.error('Failed to search sellers:', error);
    throw error;
  }
};

export const updateProduct = async (productId, productData) => {
  try {
    const response = await api.put(`/seller/products/${productId}`, productData);
    return response.data;
  } catch (error) {
    console.error('Failed to update product:', error);
    throw error;
  }
};


// Function to update a user
export const updateUser = async (userId, userData) => {
  try {
    const response = await api.put(`/public/users/${userId}`, userData);
    return response.data;
  } catch (error) {
    console.error('Failed to update user:', error);
    throw error;
  }
};


// // Update Seller function
// export const updateSeller = async (sellerId, sellerData) => {
//   try {
//     const response = await api.put(`/admin/sellers/${sellerId}`, sellerData);
//     return response.data;
//   } catch (error) {
//     console.error('Failed to update seller:', error);
//     throw error;
//   }
// };

export const fetchUserProfile = async () => {
  try {
    const response = await api.get('/public/users/me');
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch user profile:', error);
    throw error;
  }
};

export const fetchBlacklist = async () => {
  try {
    const response = await api.get('/public/user-special/blacklist');
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch blacklist:', error);
    throw error;
  }
};


export const fetchSellerById = async (sellerId) => {
  try {
    const response = await api.get(`/public/sellers/${sellerId}`);
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch seller info:', error);
    throw error;
  }
};

export const addToBlacklist = async (sellerId) => {
  try {
    await api.post(`/public/user-special/blacklist/${sellerId}`);
  } catch (error) {
    console.error('Failed to add seller to blacklist:', error);
    throw error;
  }
};

export const removeFromBlacklist = async (sellerId) => {
  try {
    await api.delete(`/public/user-special/blacklist/${sellerId}`);
  } catch (error) {
    console.error('Failed to remove seller from blacklist:', error);
    throw error;
  }
};

export default api;
