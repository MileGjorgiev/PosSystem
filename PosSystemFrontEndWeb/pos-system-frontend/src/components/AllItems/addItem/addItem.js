import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './addItem.css';



const AddItem = ({ onAddProduct, itemTypesGender, itemType,loadAllItems }) => {
    
  const navigate = useNavigate();
  const [formData, updateFormData] = useState({
    name: "",
    description: "",
    quantityInStock: 0,
    price: 0,
    itemType:   "DRESS",
    typeSex:   "FEMALE",
});
  const [selectedFile, setSelectedFile] = useState(null);


  const handleChange = (e) => {
    updateFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = new FormData();
    data.append('file', selectedFile);
    data.append('name', formData.name);
    data.append('description', formData.description);
    data.append('quantityInStock', formData.quantityInStock);
    data.append('price', formData.price);
    data.append('itemType', formData.itemType);
    data.append('typeSex', formData.typeSex);

    try {
        const response = await axios.post('http://localhost:8080/api/items/add', data, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
        console.log('Item added successfully', response.data);
        loadAllItems();
        navigate('/allItems');
    } catch (error) {
        console.error('Error adding item', error);
    }
};

  return (
    <div className="form-container">
      <form onSubmit={handleSubmit} encType="multipart/form-data">
        <div className="form-group">
          <label>Name:</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Description:</label>
          <textarea name="description" value={formData.description} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Quantity In Stock:</label>
          <input type="number" name="quantityInStock" value={formData.quantityInStock} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Price:</label>
          <input type="number" name="price" value={formData.price} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Item Type:</label>
          <select name="itemType" value={formData.itemType} onChange={handleChange} required>
            {itemType.map(type => (
                <option key={type} value={type}>{type}</option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <label>Gender:</label>
          <select name="typeSex" value={formData.typeSex} onChange={handleChange} required>
            {itemTypesGender.map(type => (
                <option  key={type} value={type}>{type}</option>     
            ))}
            
          </select>
        </div>
        <div className="form-group">
          <label>Item Image:</label>
          <input type="file" name="itemImage" onChange={handleFileChange} required />
        </div>
        <div className="button-container">
          <button type="submit">Add Item</button>
        </div>
      </form>
    </div>
  );
};

export default AddItem;
