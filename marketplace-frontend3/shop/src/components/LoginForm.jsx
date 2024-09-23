import React, { useState, useContext } from 'react';
import { login as loginApi, signup as signupApi } from '../api'; // Import API function
import { AuthContext } from '../context/AuthContext'; // Import AuthContext
import '../styles/LoginSignup.css';
import { useNavigate } from 'react-router-dom';

function LoginSignupForms() {
  const [activeTab, setActiveTab] = useState('login');
  const [formData, setFormData] = useState({ email: '', password: '', firstName: '', lastName: '' });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const { login } = useContext(AuthContext); // Use login function from AuthContext
  const navigate = useNavigate();

  const handleTabSwitch = (tab) => {
    setActiveTab(tab);
    setError('');
    setSuccess('');
  };

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleLoginSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await loginApi(formData.email, formData.password);
      const { token, role } = response;
      login(token, role); // Save token and update context
      setSuccess('Login successful!');
      setError('');

      // Navigate based on role
      if (role === 'ROLE_ADMIN') {
        navigate('/admin/dashboard');
      } else if (role === 'ROLE_SELLER') {
        navigate('/profile');
      } else {
        navigate('/');
      }
    } catch (err) {
      alert('Failed to login. Please check your credentials.');
      setError('Failed to login. Please check your credentials.');
      setSuccess('');
    }
  };

  const handleSignUpSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await signupApi(formData.firstName, formData.lastName, formData.email, formData.password);

      login(response.token, response.role); // Assuming signup also returns a token and role
      setSuccess('Signup successful!');
      setError('');
      navigate('/');
    } catch (err) {
      setError('Failed to signup. Please try again.');
      setSuccess('');
    }
  };

  return (
    <div className="login-signup-container">
      <div className="tabs">
        <button
          className={`tab-button ${activeTab === 'login' ? 'active' : ''}`}
          onClick={() => handleTabSwitch('login')}
        >
          Login
        </button>
        <button
          className={`tab-button ${activeTab === 'signup' ? 'active' : ''}`}
          onClick={() => handleTabSwitch('signup')}
        >
          Signup
        </button>
      </div>

      <div className="form-container">
        {error && <div className="error-message">{error}</div>}
        {success && <div className="success-message">{success}</div>}
        {activeTab === 'login' && (
          <form className="login-form" onSubmit={handleLoginSubmit}>
            <h2>Login</h2>
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleInputChange}
              className="input-field"
              required
            />
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleInputChange}
              className="input-field"
              required
            />
            <button type="submit" className="submit-button">Login</button>
          </form>
        )}

        {activeTab === 'signup' && (
          <form className="signup-form" onSubmit={handleSignUpSubmit}>
            <h2>Signup</h2>
            <input
              type="text"
              name="firstName"
              placeholder="First Name"
              value={formData.firstName}
              onChange={handleInputChange}
              className="input-field"
              required
            />
            <input
              type="text"
              name="lastName"
              placeholder="Last Name"
              value={formData.lastName}
              onChange={handleInputChange}
              className="input-field"
              required
            />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleInputChange}
              className="input-field"
              required
            />
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleInputChange}
              className="input-field"
              required
            />
            <button type="submit" className="submit-button">Signup</button>
          </form>
        )}
      </div>
    </div>
  );
}

export default LoginSignupForms;
