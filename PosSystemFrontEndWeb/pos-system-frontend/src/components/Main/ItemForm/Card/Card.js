import React from "react";
import DisplayImage from "../../../AllItems/Image/Image";
const Card = ({item,handleChange}) =>{
    return(
        <div key={item.id} className="item-card">
                            <input type="radio" id={item.id} name="item" value={item.id} onChange={handleChange} className="item-radio" required/>
                            <label htmlFor={item.id} className="item-content">
                                <div className="item-title">{item.name}</div>
                                <div className="item-image">
                                    <DisplayImage imageName={item.itemImage} />
                                </div>
                            </label>
        </div>
    )
}

export default Card;