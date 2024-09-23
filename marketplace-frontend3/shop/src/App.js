import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import NavScroll from './components/navbarBS/navbarBS';
import { Shop } from './pages/shop/shop';
import { CategoryPage } from './pages/CategoryPage';
import { Wishlist } from './pages/Wishlist';
import { LoginSignup } from './pages/LoginSignup';
import { WishlistProvider } from './context/WishlistContext';
import 'bootstrap/dist/css/bootstrap.min.css';
import { ProductDetails } from './pages/ProductDetails';
import { SearchResults } from './pages/SearchResults'; 
import { AuthContext, AuthProvider, useAuth } from './context/AuthContext';
import { SellerProfile } from './pages/SellerProfile';
import { AdminDashboard } from './pages/AdminDashboard';
import UserProfile from './pages/UserProfile';
import { SellerProfile2 } from './pages/SellerProfile2';

function App() {
  const { role, isLoggedIn } = useAuth();

  console.log('Role:', role);
  console.log('IsLoggedIn:', isLoggedIn);
  return (
    <WishlistProvider>
      <Router>
        <NavScroll />
        <Routes>
          <Route path="/" element={<Shop />} />
          <Route path="/category/:id" element={<CategoryPage />} />
          <Route path="/wishlist" element={<Wishlist />} />
          <Route path="/login" element={<LoginSignup />} />
          <Route path="/product/:id" element={<ProductDetails />} />
          <Route path="/search" element={<SearchResults />} />
          <Route path="/user-profile" element={<UserProfile />} />
          <Route path="/seller-profile/:id" element={<SellerProfile2 />} />

          {/* Conditional Routes based on role */}
          {role === 'ROLE_SELLER' && <Route path="/profile" element={<SellerProfile />} />}
          {role === 'ROLE_ADMIN' && <Route path="/admin/dashboard" element={<AdminDashboard />} />}

          {/* Redirect based on role after login */}
          {isLoggedIn && role === 'ROLE_ADMIN' && <Route path="*" element={<Navigate to="/admin/dashboard" />} />}
          {isLoggedIn && role === 'ROLE_SELLER' && <Route path="*" element={<Navigate to="/profile" />} />}
        </Routes>
      </Router>
    </WishlistProvider>
  );
}

export default App;
