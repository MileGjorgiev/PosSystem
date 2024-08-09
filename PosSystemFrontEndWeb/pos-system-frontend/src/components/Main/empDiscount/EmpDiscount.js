import React, { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./EmpDiscount.css"; 

const EmpDiscount = ({ empDiscount, userOrders }) => {
    const navigate = useNavigate();
    const lastOrder = userOrders.length > 0 ? { ...userOrders[0] } : {};
    const lastOrderIdRef = useRef(lastOrder.id);

    const [formData, updateFormData] = useState({
        id: lastOrder.id || "",
        username: sessionStorage.getItem("JWT") || ""
    });

    const [buttonDisabled, setButtonDisabled] = useState(false);

    
    useEffect(() => {
        if (lastOrder.id && lastOrder.id !== lastOrderIdRef.current) {
            updateFormData((prevData) => ({
                ...prevData,
                id: lastOrder.id
            }));
            lastOrderIdRef.current = lastOrder.id;
            setButtonDisabled(false);  
        }
    }, [lastOrder.id]);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    };

    const onFormSubmit = (e) => {
        e.preventDefault();
        const { id, username } = formData;
        console.log('ID:', id); 
        console.log('Username:', username);  

        empDiscount(id, username);
        setButtonDisabled(true);
        navigate("/home");
    };

    return (
        <>
            <form className="discForm" onSubmit={onFormSubmit}>
                <input type="hidden" onChange={handleChange} id="id" value={formData.id} name="id" />
                <input type="hidden" onChange={handleChange} id="username" value={formData.username} name="username" />
                <button className="redButton"  type="submit" disabled={buttonDisabled}>Add Discount</button>
            </form>
        </>
    );
};

export default EmpDiscount;
