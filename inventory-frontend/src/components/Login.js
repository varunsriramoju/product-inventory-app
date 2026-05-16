import { useState } from 'react';

function Login({ onLogin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    function handleLogin() {
        if (!username || !password) {
            setError('Please enter username and password');
            return;
        }

        setLoading(true);
        setError('');

        fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        })
            .then(res => res.json())
            .then(data => {
                setLoading(false);
                if (data.status === 'success') {
                    onLogin(data.username); // Tell App.js login succeeded
                } else {
                    setError('Invalid username or password');
                }
            })
            .catch(() => {
                setLoading(false);
                setError('Server error. Is the backend running?');
            });
    }

    // Allow login on Enter key press
    function handleKeyPress(e) {
        if (e.key === 'Enter') handleLogin();
    }

    return (
        <div className="login-page">
            <div className="login-box">
                <h1>Product Inventory</h1>
                <p className="login-subtitle">Sign in to continue</p>

                <div className="login-form">
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={e => setUsername(e.target.value)}
                        onKeyPress={handleKeyPress}
                        className="login-input"
                    />
                    <input
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={e => setPassword(e.target.value)}
                        onKeyPress={handleKeyPress}
                        className="login-input"
                    />
                    {error && <p className="login-error">{error}</p>}
                    <button
                        className="login-btn"
                        onClick={handleLogin}
                        disabled={loading}
                    >
                        {loading ? 'Signing in...' : 'Sign In'}
                    </button>
                </div>

                <p className="login-hint">
                    Default: admin / admin123
                </p>
            </div>
        </div>
    );
}

export default Login;
