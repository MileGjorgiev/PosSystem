import React from "react";
import './AllItems.css'
import { Link } from "react-router-dom";
import DisplayImage from "./Image/Image";
import Header from '../Header/Header'
const AllItems = ({items,onDelete,onEdit}) => {

    

    return(
        <div>
            
        <Link to={'/addItem'} className="add-button">Add Item</Link>
        <div className="card-container">
            
            {items.map(item => (
                <div key={item.id} className="card">
                    <h2>{item.name}</h2>
                    <DisplayImage  imageName={item.itemImage} />
                    <p>Price: {item.price} - Quantity {item.quantityInStock}</p>
                    <div className="card-buttons">
                        <Link to={`/editItem/${item.id}`} onClick={() => onEdit(item.id)}   className="edit-button">Edit</Link>
                        <button onClick={() => onDelete(item.id)} className="delete-button">Delete</button>
                    </div>
                </div>
            ))}
        
       </div>
    </div>
    )
}

export default AllItems;