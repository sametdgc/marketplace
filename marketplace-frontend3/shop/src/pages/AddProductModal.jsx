import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

export const AddProductModal = ({ show, onHide, onSave }) => {
  const [productData, setProductData] = useState({
    name: '',
    description: '',
    price: '',
    quantity: '',
    imageUrl: '',
    categoryName: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProductData({ ...productData, [name]: value });
  };

  const handleSubmit = () => {
    onSave(productData);
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>Add New Product</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form>
          <Form.Group controlId="formProductName">
            <Form.Label>Product Name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              value={productData.name}
              onChange={handleChange}
              placeholder="Enter product name"
            />
          </Form.Group>

          <Form.Group controlId="formProductDescription">
            <Form.Label>Description</Form.Label>
            <Form.Control
              type="text"
              name="description"
              value={productData.description}
              onChange={handleChange}
              placeholder="Enter product description"
            />
          </Form.Group>

          <Form.Group controlId="formProductPrice">
            <Form.Label>Price</Form.Label>
            <Form.Control
              type="number"
              name="price"
              value={productData.price}
              onChange={handleChange}
              placeholder="Enter product price"
            />
          </Form.Group>

          <Form.Group controlId="formProductQuantity">
            <Form.Label>Quantity</Form.Label>
            <Form.Control
              type="number"
              name="quantity"
              value={productData.quantity}
              onChange={handleChange}
              placeholder="Enter product quantity"
            />
          </Form.Group>

          <Form.Group controlId="formProductImageUrl">
            <Form.Label>Image URL</Form.Label>
            <Form.Control
              type="text"
              name="imageUrl"
              value={productData.imageUrl}
              onChange={handleChange}
              placeholder="Enter image URL"
            />
          </Form.Group>

          <Form.Group controlId="formProductCategory">
            <Form.Label>Category Name</Form.Label>
            <Form.Control
              type="text"
              name="categoryName"
              value={productData.categoryName}
              onChange={handleChange}
              placeholder="Enter category name"
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Cancel
        </Button>
        <Button variant="primary" onClick={handleSubmit}>
          Save Product
        </Button>
      </Modal.Footer>
    </Modal>
  );
};
