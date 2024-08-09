import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import PosSystemService from "../../repository/service";
import './Login.css'; 
import { Link } from "react-router-dom";



const Login = (props) => {
    const navigate = useNavigate();

    const [formData, updateFormData] = useState({
        username: "",
        password: ""
    });

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    };

    const onFormSubmit = (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        PosSystemService.login(formData.username, formData.password)
        .then(resp => {
            sessionStorage.setItem("JWT", resp.data);
            localStorage.setItem('dummy', 0);
            
            return PosSystemService.createOrder(formData.username);
        })
        .then(() => {
            console.log("Order Created");
            return PosSystemService.getOrders(); 
        })
        .then(orders => {
            console.log("Orders fetched:", orders);
            
            navigate("/home");
        })
        .catch(error => {
            console.error('Error occurred:', error);
        })
        .finally(() => {
            
            setLoading(false); 
        });
    };

    return (
        <div className="login-container">
            <form className="login-form" onSubmit={onFormSubmit}>
                <div className="form-group">
                    <label htmlFor="username">Username</label>
                    <input
                        type="text"
                        name="username"
                        id="username"
                        placeholder="Enter username"
                        required
                        onChange={handleChange}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        name="password"
                        id="password"
                        placeholder="Enter password"
                        required
                        onChange={handleChange}
                    />
                </div>
                {error && <div className="error-message">{error}</div>}
                <button
                    type="submit"
                    className="submit-button-login"
                    disabled={loading}
                >
                    {loading ? "Loading..." : "Submit"}
                </button>
                <div className="register-link">
                    <Link to="/register">Register</Link>
                </div>
            </form>

        </div>
    );
};

export default Login;
