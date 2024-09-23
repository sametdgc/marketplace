import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import Form from 'react-bootstrap/Form';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { fetchCategories, logout } from '../../api';
import './navbar.css';
import { useAuth } from '../../context/AuthContext'; // Import useAuth hook

function NavScroll() {
  const [categories, setCategories] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();
  const { isLoggedIn, role, logout: handleLogout } = useAuth(); // Use role and logout from useAuth

  useEffect(() => {
    const getCategories = async () => {
      try {
        const data = await fetchCategories();
        setCategories(data);
      } catch (error) {
        console.error(error);
      }
    };

    getCategories();
  }, []);

  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.trim()) {
      navigate(`/search?query=${encodeURIComponent(searchQuery.trim())}`);
    }
  };

  return (
    <Navbar expand="lg" bg="dark" variant="dark" data-bs-theme="dark">
      <Container fluid>
        <Navbar.Brand as={Link} to="/">Marketplace</Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav className="me-auto my-2 my-lg-0" style={{ maxHeight: '100px' }} navbarScroll>
            {categories.map(category => (
              <Nav.Link
                key={category.id}
                as={Link}
                to={`/category/${category.id}`}
                state={{ categoryName: category.name }}
              >
                {category.name}
              </Nav.Link>
            ))}
          </Nav>
          <Form className="d-flex" onSubmit={handleSearch}>
            <Form.Control
              type="search"
              placeholder="Search"
              className="me-2"
              aria-label="Search"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <Button variant="outline-success" type="submit">Search</Button>
          </Form>
          <Nav className="ms-auto">
            <Nav.Link as={Link} to="/wishlist">
              <i className="bi bi-heart"></i> Wishlist
            </Nav.Link>
            {isLoggedIn ? (
              <>
                {role === 'ROLE_USER' && (
                  <Nav.Link as={Link} to="/user-profile">User Profile</Nav.Link>
                )}
                {role === 'ROLE_SELLER' && (
                  <Nav.Link as={Link} to="/profile">Seller Dashboard</Nav.Link>
                )}
                {role === 'ROLE_ADMIN' && (
                  <Nav.Link as={Link} to="/admin/dashboard">Admin Dashboard</Nav.Link>
                )}
                <Button variant="outline-danger" onClick={handleLogout}>Logout</Button>
              </>
            ) : (
              <Button variant="outline-primary" as={Link} to="/login">Login</Button>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default NavScroll;
