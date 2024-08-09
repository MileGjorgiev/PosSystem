import React from "react";
import { useNavigate } from "react-router";

const FilterItemByName = ({ handleChangeName, handleSubmitName }) => {
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault(); 
        try {
            await handleSubmitName(e); 
            navigate('/home'); 
        } catch (error) {
            console.error('Error filtering items:', error);
        }
    };

    return (
        <form className="itemTypeFilter" onSubmit={handleSubmit}>
            <div className="form-group">
                <input  style={{marginTop: "28px"}}
                    type="text" 
                    name="name" 
                    placeholder="Filter By Name"
                    onChange={handleChangeName} 
                />
            </div>
            <button  className="filterButton" type="submit">Filter</button>
        </form>
    );
};

export default FilterItemByName;
