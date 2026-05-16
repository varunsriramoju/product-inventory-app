function ProductCard({ product, onClose }) {

    // Don't render if no product
    if (!product) return null;

    // Decide stock status color
    const stockStatus = product.quantity < 5
        ? { text: 'Low Stock', color: '#e53e3e' }
        : { text: 'In Stock', color: '#38a169' };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div
                className="product-card"
                onClick={e => e.stopPropagation()}
            >
                {/* Header */}
                <div className="card-header">
                    <div className="card-icon">📦</div>
                    <div>
                        <h2 className="card-product-name">{product.name}</h2>
                        <p className="card-product-id">ID: PROD-{product.id}</p>
                    </div>
                </div>

                {/* Details */}
                <div className="card-details">
                    <div className="card-row">
                        <span className="card-label">Category</span>
                        <span className="card-value">{product.category}</span>
                    </div>
                    <div className="card-row">
                        <span className="card-label">Price</span>
                        <span className="card-value">
                            Rs. {product.price.toLocaleString()}
                        </span>
                    </div>
                    <div className="card-row">
                        <span className="card-label">Quantity</span>
                        <span className="card-value">
                            {product.quantity} units
                        </span>
                    </div>
                    <div className="card-row">
                        <span className="card-label">Status</span>
                        <span
                            className="card-value"
                            style={{ color: stockStatus.color, fontWeight: 'bold' }}
                        >
                            {stockStatus.text}
                        </span>
                    </div>
                </div>

                <button className="btn-primary" onClick={onClose}>
                    Close
                </button>
            </div>
        </div>
    );
}

export default ProductCard;
