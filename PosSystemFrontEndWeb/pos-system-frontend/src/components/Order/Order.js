import React from "react";
import "./Order.css"
import PosSystemService from "../../repository/service";

const Order = ({userOrders,itemInOrders,totalPrice,deleteItemInOrder}) => {

    const lastOrder = {...userOrders[0]}
    
    console.log(lastOrder);
    

    return(
        <div className="order-box">
                <div >
                    <div className="order-box-title">Order #{lastOrder.id}</div>
                    <ul className="order-items">
                        {itemInOrders != null ? itemInOrders.map(item => (
                            <li key={item.id} className="order-item">
                            <span className="order-item-name">Name: <b>{item.item.name}</b> - Price: {item.item.price} <span id="dummyUpdate">{localStorage.getItem("dummy")}</span></span>
                            <span className="order-item-details">{item.quantity}</span>
                            <button onClick={() => deleteItemInOrder(item.id)} id="deleteItemInOrder" className="btn btn-danger">X</button>
                        </li>
                        )) : <div><h3>Empty Order</h3></div>}
                        
                    </ul>
                    
                </div>        
                </div>

    )
}

export default Order;