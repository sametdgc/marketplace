import React from 'react';
import LoginSignupForms from '../components/LoginForm'; // Import the combined login/signup form
import '../styles/LoginSignup.css';  

export const LoginSignup = () => {
  return (
    <div className='login-page'>
      <h2 className='login-heading'>Login or Sign Up</h2>
      <LoginSignupForms />
    </div>
  );
};
