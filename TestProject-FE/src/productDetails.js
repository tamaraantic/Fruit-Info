import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { Container, Row, Col, Card, Spinner } from 'react-bootstrap';
import './index.css';

function ProductDetails() {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchProduct() {
      try {
        const response = await fetch(`http://localhost:8080/api/v1/products/${id}`);
        if (!response.ok) {
          throw new Error('Failed to fetch product');
        }
        const data = await response.json();
        setProduct(data);
      } catch (error) {
        console.error('Error while fetching product:', error);
      } finally {
        setLoading(false);
      }
    }
    fetchProduct();
  }, [id]);

  if (loading) {
    return (
      <Container className="d-flex justify-content-center align-items-center" style={{ height: '80vh' }}>
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
      </Container>
    );
  }

  if (!product) {
    return <p className="text-center">Product not found</p>;
  }

  return (
    <Container>
      <Row className="mt-4">
        <Col xs={12}>
          <div className="product-details-container">
            <Card className="mb-3 product-details-image">
              <Card.Img src={product.imageURL} alt={product.name} />
            </Card>
            <div className="product-details-overlay">
              <div>
                <h2 className="mb-4">{product.name}</h2>
                <h4>Price: ${product.price}</h4>
                <h4 className="mt-4">Available at:</h4>
                <ul>
                  {product.vendors.map(vendor => (
                    <li key={vendor.id}>{vendor.name}</li>
                  ))}
                </ul>
              </div>
            </div>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

export default ProductDetails;
