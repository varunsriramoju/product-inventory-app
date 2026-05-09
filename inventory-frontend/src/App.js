import ProductTable from './components/ProductTable';
import { useState, useEffect } from 'react';
import './App.css';

// The base URL of your Spring Boot backend
const API_URL = 'http://localhost:8080/products';

function App() {

  // State to store the list of products
  const [products, setProducts] = useState([]);

  // This runs once when the page loads
  useEffect(() => {
    loadProducts();
  }, []);

  // Function to fetch all products from backend
  function loadProducts() {
    fetch(API_URL)
      .then(response => response.json())
      .then(data => setProducts(data))
      .catch(error => console.error('Error:', error));
  }

  return (
<div className="app">
<h1>Product Inventory</h1>
<p>Total products: {products.length}</p>

<ProductTable products={products} />
</div>
  );
}

export default App;
