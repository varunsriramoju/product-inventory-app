import SearchBar from './components/SearchBar';
import { useState, useEffect } from 'react';
import ProductTable from './components/ProductTable';
import ProductForm from './components/ProductForm';
import './App.css';

const API_URL = 'http://localhost:8080/products';

function App() {
  const [products, setProducts] = useState([]);
  const [editProduct, setEditProduct] = useState(null);

  useEffect(() => {
    loadProducts();
  }, []);

  // ── Load all products from backend ──
  function loadProducts() {
    fetch(API_URL)
      .then(res => res.json())
      .then(data => setProducts(data))
      .catch(err => console.error(err));
  }

  // ── Add a new product ──
  function handleAdd(product) {
    fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product)
    })
      .then(res => res.json())
      .then(() => loadProducts()); // Refresh the list
  }

  // ── Update an existing product ──
  function handleEdit(product) {
    fetch(`${API_URL}/${product.id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(product)
    })
      .then(res => res.json())
      .then(() => {
        loadProducts();
        setEditProduct(null); // Clear edit mode
      });
  }

  // ── Delete a product ──
  function handleDelete(id) {
    if (window.confirm('Are you sure you want to delete this product?')) {
      fetch(`${API_URL}/${id}`, { method: 'DELETE' })
        .then(() => loadProducts()); // Refresh the list
    }
  }

  function handleSearch(searchText) {
    if (searchText.trim() === '') {
      loadProducts(); // If search is empty, show all
      return;
    }
    fetch(`${API_URL}/search?name=${searchText}`)
      .then(res => res.json())
      .then(data => setProducts(data));
  }


  return (
    <div className="app">
      <h1>Product Inventory</h1>
      <div className="stats-bar">
        <div className="stat-card">
          <span className="stat-number">{products.length}</span>
          <span className="stat-label">Total Products</span>
        </div>
        <div className="stat-card">
          <span className="stat-number low">
            {products.filter(p => p.quantity < 5).length}
          </span>
          <span className="stat-label">Low Stock</span>
        </div>
        <div className="stat-card">
          <span className="stat-number">
            {[...new Set(products.map(p => p.category))].length}
          </span>
          <span className="stat-label">Categories</span>
        </div>
      </div>

      <SearchBar onSearch={handleSearch} />
      <ProductForm
        onSubmit={editProduct ? handleEdit : handleAdd}
        editProduct={editProduct}
        onCancel={() => setEditProduct(null)}
      />
      <ProductTable
        products={products}
        onDelete={handleDelete}
        onEdit={setEditProduct}
      />
    </div>
  );
}

export default App;
