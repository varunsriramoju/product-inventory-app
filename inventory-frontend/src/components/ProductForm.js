import { useState, useEffect } from 'react';

function ProductForm({ onSubmit, editProduct, onCancel }) {

    // Form state — holds what the user is typing
    const [form, setForm] = useState({
        name: '',
        category: '',
        price: '',
        quantity: ''
    });

    // When editProduct changes, fill the form with its data
    // When editProduct is null, clear the form
    useEffect(() => {
        if (editProduct) {
            setForm(editProduct);
        } else {
            setForm({ name: '', category: '', price: '', quantity: '' });
        }
    }, [editProduct]);

    // Update state when user types in any field
    function handleChange(event) {
        setForm({
            ...form,
            [event.target.name]: event.target.value
        });
    }

    // Called when Add or Update button is clicked
    function handleSubmit() {
        if (!form.name || !form.price) {
            alert('Name and Price are required!');
            return;
        }
        // Convert price and quantity to numbers before sending
        onSubmit({
            ...form,
            price: parseFloat(form.price),
            quantity: parseInt(form.quantity) || 0
        });
        // Clear form after submit
        setForm({ name: '', category: '', price: '', quantity: '' });
    }

    return (
        <div className="form-container">
            <h3>{editProduct ? 'Edit Product' : 'Add New Product'}</h3>
            <div className="form-row">
                <input
                    name="name"
                    placeholder="Product Name *"
                    value={form.name}
                    onChange={handleChange}
                />
                <input
                    name="category"
                    placeholder="Category"
                    value={form.category}
                    onChange={handleChange}
                />
                <input
                    name="price"
                    placeholder="Price *"
                    type="number"
                    value={form.price}
                    onChange={handleChange}
                />
                <input
                    name="quantity"
                    placeholder="Quantity"
                    type="number"
                    value={form.quantity}
                    onChange={handleChange}
                />
                <button className="btn-primary" onClick={handleSubmit}>
                    {editProduct ? 'Update Product' : 'Add Product'}
                </button>
                {editProduct && (
                    <button className="btn-secondary" onClick={onCancel}>
                        Cancel
                    </button>
                )}
            </div>
        </div>
    );
}

export default ProductForm;
