function ProductTable({ products }) {
  return (
<table>
<thead>
<tr>
<th>Name</th>
<th>Category</th>
<th>Price</th>
<th>Quantity</th>
</tr>
</thead>
<tbody>
        {products.map(product => (
<tr key={product.id}>
<td>{product.name}</td>
<td>{product.category}</td>
<td>Rs. {product.price}</td>
<td>{product.quantity}</td>
</tr>
        ))}
</tbody>
</table>
  );
}

export default ProductTable;
