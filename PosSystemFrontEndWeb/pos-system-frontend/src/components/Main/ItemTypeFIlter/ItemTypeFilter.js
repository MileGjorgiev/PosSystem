import React from "react";
import "./ItemTypeFilter.css"
const ItemTypeFilter = ({handleChange,onFormSubmit,itemType,itemTypesGender}) => {
    return (
        <>
        <form className="itemTypeFilter" onSubmit={onFormSubmit}>
        <div className="form-group">
              <label>Item Type:</label>
              <select name="itemType"  onChange={handleChange} >
                <option></option>
                {itemType.map(type => {
                     return <option key={type} value={type}>{type}</option>
                })}
              </select>
            </div>
            <div className="form-group">
              <label>Gender:</label>
              <select name="typeSex"  onChange={handleChange} >
                <option></option>
              {itemTypesGender.map(type => {
                     return <option key={type} value={type}>{type}</option>
                })}
              </select>
            </div>
            <button className="filterButton" type="submit">Filter</button>
        </form>
    </>
    )
}

export default ItemTypeFilter;