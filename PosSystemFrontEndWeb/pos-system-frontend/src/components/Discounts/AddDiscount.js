import React from "react";
import { useNavigate } from "react-router";
import moment from "moment";

const AddDiscount = ({ addDiscount }) => {
    const [formData, updateFormData] = React.useState({
        validUntil: "",
        discountAmount: 0,
    });
    const navigate = useNavigate();

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    };

    const onFormSubmit = (e) => {
        e.preventDefault();

        
        const formattedDate = moment(formData.validUntil).format("YYYY-MM-DDTHH:mm:ss");
        const discountAmount = formData.discountAmount;

        
        addDiscount(formattedDate, discountAmount);
        navigate("/discounts");
    };

    return (
        <form onSubmit={onFormSubmit}>
            <div>
                <label htmlFor="validUntil">Valid Until:</label>
                <input
                    type="datetime-local"
                    id="validUntil"
                    name="validUntil"
                    value={formData.validUntil}
                    onChange={handleChange}
                />
            </div>
            <div>
                <label htmlFor="discountAmount">Discount Amount:</label>
                <input
                    type="number"
                    id="discountAmount"
                    name="discountAmount"
                    value={formData.discountAmount}
                    onChange={handleChange}
                />
            </div>
            <button type="submit">Add Discount</button>
        </form>
    );
};

export default AddDiscount;
