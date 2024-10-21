import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import PosSystemService from "../../repository/service";
import './Register.css'
import { Link } from "react-router-dom";

const Register = (props) => {

    const navigate = useNavigate();
    const [formData, updateFormData] = useState({
        username : "",
        password : "",
        name: "",
        role: ""
    })
    const [error, setError] = useState("");

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        PosSystemService.register(formData.username, formData.password, formData.name, formData.role)
            .then(() => {
                navigate("/");
            })
            .catch((err) => {
                setError(err.response?.data?.message || "Username already taken. Please try again.");
            });
    }


    return(
        <div className="container">
        <form onSubmit={onFormSubmit} className="register-form">
            <h2>Register</h2>
            {error && <p className="error">{error}</p>}
            <div className="form-group">
                <label htmlFor="username">Username</label>
                <input type="text"
                id="username"
                name="username" 
                onChange={handleChange}
                placeholder="Enter username"
                required/>
            </div>
            <div className="form-group">
                <label htmlFor="password">Password</label>
                <input type="password"
                 id="password" 
                 name="password"
                 onChange={handleChange}
                 placeholder="Enter password" 
                 required/>
            </div>
            <div className="form-group">
                <label htmlFor="name">Name</label>
                <input type="text"
                id="name"
                name="name"
                onChange={handleChange}
                placeholder="Enter name"
                required/>
            </div>
            <div >
                <label htmlFor="ROLE_USER">Employee</label>
                <input type="radio"
                id="ROLE_USER"
                name="role"
                value={'ROLE_USER'}
                onChange={handleChange}
                />

                <label htmlFor="ROLE_ADMIN">Admin</label>
                <input type="radio"
                id="ROLE_ADMIN"
                name="role"
                value={'ROLE_ADMIN'}
                onChange={handleChange}
                />
            </div>
            <button style={{marginTop: "10px"}} id="submit" type="submit">Register</button>
        </form>
        <Link className="btn btn-secondary" to={'/'}>Login</Link>
    </div>
    )
}

export default Register;