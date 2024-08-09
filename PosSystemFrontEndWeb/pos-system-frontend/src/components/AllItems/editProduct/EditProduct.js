import React from "react";
import { useNavigate } from "react-router";
import {useState} from 'react'
import { useEffect } from "react";
const EditProduct = ({itemType,itemTypesGender,onEditProduct,selectedItem}) => {

    
    
    const [formData, updateFormData] = useState({
        name: "",
        description: "",
        quantityInStock: 0,
        price: 0,
        itemType:   "DRESS",
        typeSex:   "FEMALE",
        itemImage :  ""
    })

    useEffect(() => {
        if (selectedItem) {
          updateFormData({
            name: selectedItem.name || "",
            description: selectedItem.description || "",
            quantityInStock: selectedItem.quantityInStock || 0,
            price: selectedItem.price || 0,
            itemType: selectedItem.itemType || "DRESS",
            typeSex: selectedItem.typeSex || "FEMALE",
            itemImage: selectedItem.itemImage 
          });
        }
      }, [selectedItem]);

    const navigate = useNavigate();

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const name = formData.name !== "" ? formData.name : selectedItem.name;
        const description = formData.description !== "" ? formData.description : selectedItem.description;
        const quantityInStock = formData.quantityInStock !== 0 ? formData.quantityInStock : selectedItem.quantityInStock;
        const price = formData.price !== 0 ? formData.price : selectedItem.price;
        const itemType = formData.itemType !== "" ? formData.itemType : selectedItem.itemType;
        const typeSex = formData.typeSex !== "" ? formData.typeSex : selectedItem.typeSex;
        const itemImage = formData.itemImage !== ""? formData.itemImage : selectedItem.itemImage;




        onEditProduct(selectedItem.id, name, description, quantityInStock, price, itemType,typeSex,itemImage);
        navigate("/allItems");
    }

    return (
        <div className="form-container">
          <form onSubmit={onFormSubmit} >
            <div className="form-group">
              <label>Name:</label>
              <input type="text"
               name="name"
            placeholder={selectedItem.name} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Description:</label>
              <textarea name="description"  placeholder={selectedItem.description} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Quantity In Stock:</label>
              <input type="number" name="quantityInStock"   placeholder={selectedItem.quantityInStock} onChange={handleChange} required />
            </div>
            <div className="form-group">
              <label>Price:</label>
              <input type="number" name="price"   onChange={handleChange} placeholder={selectedItem.price} required />
            </div>
            <div className="form-group">
              <label>Item Type:</label>
              <select name="itemType"  onChange={handleChange} required>
                {itemType.map(type => {
                    if(selectedItem.itemType !== undefined &&
                        selectedItem.itemType === type)
                        return <option key={type} selected={selectedItem.itemType} value={type}>{type}</option>
                    else return <option key={type} value={type}>{type}</option>
                })}
              </select>
            </div>
            <div className="form-group">
              <label>Gender:</label>
              <select name="typeSex"  onChange={handleChange} required>
              {itemTypesGender.map(type => {
                    if(selectedItem.typeSex !== undefined &&
                        selectedItem.typeSex === type)
                        return <option key={type} selected={selectedItem.typeSex} value={type}>{type}</option>
                    else return <option key={type} value={type}>{type}</option>
                })}
              </select>
            </div>
            <label>Item Image:</label>
            <input type="text" name="itemImage" value={formData.itemImage} onChange={handleChange}  />
            <div className="button-container">
              <button type="submit">Edit Item</button>
            </div>
          </form>
        </div>
      );

}


export default EditProduct;