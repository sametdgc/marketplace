import axios from "axios";

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
});


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


export const fetchCategories = async () => {
  try {
    const response = await api.get('/public/categories');
    return response.data.data;
  } catch (error) {
    console.error('Failed to fetch categories:', error);
    throw error;
  }
};



