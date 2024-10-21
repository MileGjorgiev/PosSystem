import React, { useEffect, useRef, useState } from "react";
import { useNavigate } from "react-router";
import PdfDownloader from '../../PdfDownloader/PdfDownloader '; 
import "./FinnishOrder.css";

const FinnishOrder = ({ handleClear, finnishOrder, userOrders, totalPrice, inputValue, forceUpdate }) => {
    const navigate = useNavigate();
    const lastOrder = userOrders.length > 0 ? { ...userOrders[0] } : {};
    const lastOrderIdRef = useRef(lastOrder.id);

    const [formData, updateFormData] = useState({
        id: lastOrder.id || "",
        username: sessionStorage.getItem("JWT") || ""
    });

    const [buttonDisabled, setButtonDisabled] = useState(true);
    const [autoDownload, setAutoDownload] = useState(false);

    useEffect(() => {
        if (lastOrder.id && lastOrder.id !== lastOrderIdRef.current) {
            updateFormData((prevData) => ({
                ...prevData,
                id: lastOrder.id
            }));
            lastOrderIdRef.current = lastOrder.id;
        }
        setButtonDisabled(!(inputValue >= totalPrice && totalPrice > 0));
    }, [lastOrder.id, inputValue, totalPrice]);

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    };

    const onFormSubmit = (e) => {
        e.preventDefault();
        const { id, username } = formData;
        console.log(formData);
        console.log(lastOrder);
        forceUpdate();
        finnishOrder(id, username);
        setButtonDisabled(true);
        setAutoDownload(true); 
        navigate("/home");
    };

    return (
        <>
            <form className="discForm" onSubmit={onFormSubmit}>
                <input type="hidden" onChange={handleChange} id="id" value={formData.id} name="id" />
                <input type="hidden" onChange={handleChange} id="username" value={formData.username} name="username" />
                <button onClick={handleClear} className="finnishButton" type="submit" disabled={buttonDisabled}>
                    Finish Order
                </button>
            </form>
            <PdfDownloader lastOrder={lastOrder} orderData={formData} autoDownload={autoDownload} />
        </>
    );
};

export default FinnishOrder;
