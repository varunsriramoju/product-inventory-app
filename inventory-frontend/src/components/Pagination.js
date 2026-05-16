function Pagination({ currentPage, totalPages, onPageChange }) {

    // Don't show pagination if only 1 page
    if (totalPages <= 1) return null;

    // Create array of page numbers [0, 1, 2, ...]
    const pages = Array.from({ length: totalPages }, (_, i) => i);

    return (
        <div className="pagination">

            {/* Previous button */}
            <button
                className="page-btn"
                onClick={() => onPageChange(currentPage - 1)}
                disabled={currentPage === 0}
            >
                Previous
            </button>

            {/* Page number buttons */}
            {pages.map(page => (
                <button
                    key={page}
                    className={`page-btn ${currentPage === page ? 'active' : ''}`}
                    onClick={() => onPageChange(page)}
                >
                    {page + 1}
                </button>
            ))}

            {/* Next button */}
            <button
                className="page-btn"
                onClick={() => onPageChange(currentPage + 1)}
                disabled={currentPage === totalPages - 1}
            >
                Next
            </button>

            <span className="page-info">
                Page {currentPage + 1} of {totalPages}
            </span>

        </div>
    );
}

export default Pagination;
