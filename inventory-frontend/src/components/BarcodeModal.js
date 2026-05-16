function BarcodeModal({ productId, productName, onClose }) {

    // Don't render anything if no product selected
    if (!productId) return null;

    // The URL that returns the barcode PNG from backend
    const barcodeUrl =
        `http://localhost:8080/products/${productId}/barcode`;

    // Download the barcode as a PNG file
    async function handleDownload() {
        try {
            const response = await fetch(barcodeUrl);
            const blob = await response.blob();
            const url = window.URL.createObjectURL(blob);
            const link = document.createElement('a');
            link.href = url;
            link.download = `barcode-PROD-${productId}.png`;
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            window.URL.revokeObjectURL(url);
        } catch (error) {
            console.error("Failed to download barcode", error);
        }
    }

    return (
        // Dark overlay — clicking it closes the modal
        <div className="modal-overlay" onClick={onClose}>

            {/* The white box — clicking inside does NOT close modal */}
            <div
                className="modal-box"
                onClick={e => e.stopPropagation()}
            >
                <h3 className="modal-title">Product Barcode</h3>

                {/* Product info */}
                <div className="modal-product-info">
                    <p className="modal-product-name">{productName}</p>
                    <p className="modal-product-id">ID: PROD-{productId}</p>
                </div>

                {/* Barcode image — loaded directly from backend */}
                <div className="barcode-wrapper">
                    <img
                        src={barcodeUrl}
                        alt={`Barcode for ${productName}`}
                        className="barcode-image"
                    />
                </div>

                {/* Action buttons */}
                <div className="modal-actions">
                    <button
                        className="btn-primary"
                        onClick={handleDownload}
                    >
                        Download PNG
                    </button>
                    <button
                        className="btn-secondary"
                        onClick={onClose}
                    >
                        Close
                    </button>
                </div>

                <p className="modal-hint">
                    Point camera at this barcode to scan it
                </p>

            </div>
        </div>
    );
}

export default BarcodeModal;
