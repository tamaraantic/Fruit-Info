import React, { useEffect } from "react";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import ProductList from "./productList";
import "./index.css"
import ProductDetails from "./productDetails";

function App() {
  return (
<div className="App">
  <h1>Welcome!</h1>
  <h2><i>Browse for fruit here</i></h2>
        <Routes>
          <Route path="/" element={<ProductList/>} />
          <Route path="/products/:id" element={<ProductDetails/>} />
        </Routes>
      </div>
  );
}

export default App;
