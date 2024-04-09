import React, { useState, useEffect } from 'react';
import { Card, Button, Container, Row, Col } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import "./index.css"

function ProductCard({ product }) {
  return (
    <Col xs={12} sm={6} md={4} lg={3} xl={2}>
      <Card className="mb-3">
        <Card.Img variant="top" className='card-img' src={product.imageURL} />
        <Card.Body>
          <Card.Title>{product.name}</Card.Title>
          <Link 
            to={`/products/${product.id}`}
            key={product.id}
            >
          <Button variant="primary">Show Details</Button>
        </Link>
        </Card.Body>
      </Card>
    </Col>
  );
}

function ProductList() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const response = await fetch(
        "http://localhost:8080/api/v1/products",
        {
          method: "GET",
        }
      )
      .then((response) => response.json())
      .then((data) => console.log(setProducts(data)))
      .catch((error) =>
      console.error("Error while fetching buildings:", error)
    )}
  fetchData();
  }, []);

  return (
    <Container>
      <Row xs={1} sm={2} md={3} lg={4} xl={5} className="g-4">
        {products.map(product => (
          <ProductCard key={product.id} product={product} />
        ))}
      </Row>
    </Container>
  );
}

export default ProductList;
