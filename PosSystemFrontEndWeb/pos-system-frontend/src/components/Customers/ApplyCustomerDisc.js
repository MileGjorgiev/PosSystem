import React, { useState, useEffect } from "react";

const ApplyCustomerDisc = ({ addCustomerDiscount, selectedCustomer, lastOrder }) => {
    const [discountApplied, setDiscountApplied] = useState(false);
    const [isReady, setIsReady] = useState(false);

    useEffect(() => {
        console.log("lastOrder:", lastOrder);
        if (lastOrder && lastOrder.customer && lastOrder.customer.id) {
            setIsReady(true);
        } else {
            setIsReady(false);
        }
    }, [lastOrder]);

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        if (selectedCustomer && lastOrder) {
            try {
                console.log("Sending request with data:", {
                    id: lastOrder.id,
                    customerId: selectedCustomer
                });
    
                const response = await addCustomerDiscount(lastOrder.id, lastOrder.customer.id);
                console.log("Response received:", response);
                setDiscountApplied(true);
            } catch (error) {
                console.error("Error applying discount:", error.response || error.message || error);
            }
        }
    };
    
    return (
        isReady ? (
            <form className="apply-customer-disc-form" onSubmit={handleSubmit}>
                <input type="hidden" name="id" value={lastOrder?.id || ""} />
                <input type="hidden" name="customerId" value={lastOrder?.customer?.id || ""} />
                <button className="apply-disc-button" type="submit" disabled={discountApplied}>
                    {discountApplied ? "Discount Applied" : "Apply Discount"}
                </button>
            </form>
        ) : (
            <p>Loading data, please wait...</p>
        )
    );
};

export default ApplyCustomerDisc;
