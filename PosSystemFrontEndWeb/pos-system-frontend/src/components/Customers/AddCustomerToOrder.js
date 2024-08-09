import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import PosSystemService from "../../repository/service";
import "./addCTO.css";
import ApplyCustomerDisc from "./ApplyCustomerDisc";

const AddCustomerToOrder = ({ customers, lastOrder, addCustomerToOrder,forceUpdate,addCustomerDiscount }) => {
    const [selectedCustomer, setSelectedCustomer] = useState("");
    const [isButtonDisabled, setButtonDisabled] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        
        if (lastOrder && lastOrder.customer && lastOrder.customer.id) {
            setSelectedCustomer(lastOrder.customer.id);
            setButtonDisabled(true); 
        }
    }, [lastOrder]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (selectedCustomer && lastOrder) {
            PosSystemService.addCustomerToOrder(lastOrder.id, selectedCustomer)
                .then(() => {
                    setButtonDisabled(true);
                    forceUpdate();
                    navigate("/home"); 
                })
                .catch((error) => {
                    console.error("Error adding customer to order:", error);
                });
        }
    };

    const handleCustomerChange = (e) => {
        const value = e.target.value;
        setSelectedCustomer(value);
        forceUpdate();
        setButtonDisabled(false); 
    };

    return (
        <div>
            <form className="customer-order-form" onSubmit={handleSubmit}>
                <label className="customer-label">
                    Select Customer:
                    <select
                        className="customer-select"
                        value={selectedCustomer}
                        onChange={handleCustomerChange}
                        disabled={!!selectedCustomer} 
                    >
                        <option value="">Select a customer</option>
                        {customers.map((customer) => (
                            <option key={customer.id} value={customer.id}>
                                {customer.name} - {customer.points}
                            </option>
                        ))}
                    </select>
                </label>
                <input type="hidden" value={lastOrder?.id || ""} />
                <button className="submit-button" type="submit" disabled={isButtonDisabled}>
                    Add Customer to Order
                </button>
            </form>
            <ApplyCustomerDisc addCustomerDiscount={addCustomerDiscount} selectedCustomer={selectedCustomer} lastOrder={lastOrder} />
        </div>
    );
};

export default AddCustomerToOrder;
