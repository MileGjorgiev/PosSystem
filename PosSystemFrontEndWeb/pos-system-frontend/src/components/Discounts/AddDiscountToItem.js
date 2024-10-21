import React from "react";
import { useNavigate } from "react-router";
import './Disc.css'

const AddDiscountToItem = ({ addDiscountToItem, items, discId,fetch }) => {
    const [formData, updateFormData] = React.useState({
        id: discId,
        itemId: 0,
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

        const id = formData.id;
        const itemId = formData.itemId;

        addDiscountToItem(id, itemId);
        fetch();
        navigate("/discounts");
    };

    return (
        <form onSubmit={onFormSubmit}>
            <input type="hidden" name="id" value={formData.id} />

            <div>
                <label htmlFor="itemId">Select Item:</label>
                <select
                    id="itemId"
                    name="itemId"
                    
                    onChange={handleChange}
                    required
                >
                    <option value="">Select an item</option>
                    {items.map(item => (
                        <option key={item.id} value={item.id}>
                            {item.name}
                        </option>
                    ))}
                </select>
            </div>

            <button id="addDiscToItems" className="btn btn-success" type="submit">Add Discount to Item</button>
        </form>
    );
};

export default AddDiscountToItem;
