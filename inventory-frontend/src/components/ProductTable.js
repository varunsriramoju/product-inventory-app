function ProductTable({ products, onDelete, onEdit, onBarcode }) {
  return (
    <table>
      <thead>
        <tr>
          <th>Name</th>
          <th>Category</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {products.map(product => (
          <tr key={product.id} className={product.quantity < 5 ? 'low-stock' : ''}>
            <td>{product.name}</td>
            <td>{product.category}</td>
            <td>Rs. {product.price}</td>
            <td>
              {product.quantity}
              {product.quantity < 5 && (
                <span className="low-stock-badge">Low Stock</span>
              )}
            </td>

            <td>
              <button className="btn-edit" onClick={() => onEdit(product)}>
                Edit
              </button>
              <button className="btn-delete" onClick={() => onDelete(product.id)}>
                Delete
              </button>
              <button
                className="btn-barcode"
                onClick={() => onBarcode(product.id, product.name)}
              >
                Barcode
              </button>
            </td>


          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default ProductTable;
