import React from 'react';
import { Link } from 'react-router-dom'; // Import Link from react-router-dom
import { headerLogo } from '../assets/images';
import { hamburger } from '../assets/icons';

const Nav = () => {
  return (
    <header className="padding-x py-8 absolute z-10 w-full">
      <nav className="flex justify-between items-center max-container">
        {/* Logo and Brand Name */}
        <div className="flex items-center">
          <Link to="/"> 
            <img src={headerLogo} alt="logo" width={200} height={10} />
          </Link>
        </div>

        {/* Navigation Links */}
        <ul className="flex-1 flex justify-end items-center gap-16 max-lg:hidden">
          <li>
            <Link to="/" className="font-montserrat leading-normal text-lg text-slate-gray">Home</Link>
          </li>
          <li>
            <Link to="/shop" className="font-montserrat leading-normal text-lg text-slate-gray">Shop</Link>
          </li>
          <li>
            <Link to="/login" className="font-montserrat leading-normal text-lg text-slate-gray">Login</Link>
          </li>
        </ul>

        {/* Hamburger Icon for Mobile */}
        <div className="hidden max-lg:block">
          <img 
            src={hamburger} 
            alt="Hamburger" 
            width={20}  // Adjusted width to make it smaller
            height={20} // Adjusted height to make it smaller
            className="p-1" // Optional: Add padding to make it more balanced
          />
        </div>
      </nav>
    </header>
  );
};

export default Nav;
