import { useEffect, useRef, useState } from 'react';
import { BrowserMultiFormatReader } from '@zxing/library';

function BarcodeScanner({ onScanResult, onClose }) {

    // Reference to the video element (the camera feed)
    const videoRef = useRef(null);

    // Reference to the ZXing reader
    const readerRef = useRef(null);

    // Status message shown to the user
    const [status, setStatus] = useState('Starting camera...');

    // Whether we are currently scanning
    const [scanning, setScanning] = useState(true);

    useEffect(() => {
        // Create a new barcode reader instance
        const reader = new BrowserMultiFormatReader();
        readerRef.current = reader;

        // Start scanning from the default camera (usually back camera on mobile)
        reader.decodeFromVideoDevice(
            undefined,         // undefined = use default camera
            videoRef.current,  // the <video> element to show camera in
            (result, error) => {

                if (result) {
                    // Barcode detected!
                    const scannedText = result.getText();
                    setStatus('Barcode found: ' + scannedText);
                    setScanning(false);

                    // Stop the camera
                    reader.reset();

                    // Send the scanned text back to parent
                    onScanResult(scannedText);
                }

                if (error && scanning) {
                    // This fires constantly while scanning
                    // Only show meaningful errors
                    if (!error.message.includes('No MultiFormat')) {
                        setStatus('Scanning... hold the barcode steady');
                    }
                }
            }
        ).then(() => {
            setStatus('Camera ready — point at a barcode');
        }).catch(err => {
            setStatus('Camera error: ' + err.message);
        });

        // Cleanup — stop camera when component closes
        return () => {
            reader.reset();
        };
    }, []);

    function handleClose() {
        if (readerRef.current) {
            readerRef.current.reset(); // Stop camera
        }
        onClose();
    }

    return (
        <div className="modal-overlay" onClick={handleClose}>
            <div
                className="scanner-box"
                onClick={e => e.stopPropagation()}
            >
                <h3 className="modal-title">Scan Barcode</h3>
                <p className="scanner-status">{status}</p>

                {/* The camera feed shows here */}
                <div className="video-wrapper">
                    <video
                        ref={videoRef}
                        className="scanner-video"
                    />
                    {/* Scanning guide box overlaid on camera */}
                    <div className="scan-guide">
                        <div className="scan-line" />
                    </div>
                </div>

                <p className="scanner-hint">
                    Hold the barcode inside the box. Keep steady.
                </p>

                <button className="btn-secondary" onClick={handleClose}>
                    Cancel
                </button>

            </div>
        </div>
    );
}

export default BarcodeScanner;
