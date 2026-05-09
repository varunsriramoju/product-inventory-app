function SearchBar({ onSearch }) {
    return (
        <div className="search-container">
            <input
                className="search-input"
                placeholder="Search products by name..."
                onChange={event => onSearch(event.target.value)}
            />
        </div>
    );
}

export default SearchBar;
