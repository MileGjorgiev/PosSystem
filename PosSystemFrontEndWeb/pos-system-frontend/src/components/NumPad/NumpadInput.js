import React, { useState, useEffect } from "react";
import Numpad from "./NumPad";
import "./NumpadInput.css";
import FinnishOrder from "../Main/FinnishOrder/FinnishOrder"
import AddCustomerToOrder from "../Customers/AddCustomerToOrder";

const NumpadInput = ({ userOrders,finnishOrder,customers,addCustomerToOrder,addCustomerDiscount,forceUpdate }) => {
  const [inputValue, setInputValue] = useState("");
  const [returnAmount, setReturnAmount] = useState(0);

  const lastOrder = userOrders.length > 0 ? { ...userOrders[0] } : { totalPrice: 0 };

  useEffect(() => {
    
    const inputAmount = parseFloat(inputValue) || 0;
    const calculatedReturn = inputAmount - lastOrder.totalPrice;
    setReturnAmount(calculatedReturn >= 0 ? calculatedReturn : 0);
  }, [inputValue, lastOrder.totalPrice]);

  const handleNumberClick = (number) => {
    setInputValue((prevValue) => prevValue + number);
  };

  const handleClear = () => {
    setInputValue("");
  };

  const handleDelete = () => {
    setInputValue((prevValue) => prevValue.slice(0, -1));
  };

  return (
    <div className="input-Container">
      <p>Total price: {lastOrder.totalPrice} Ден.</p>
      <label>Customer paid:</label>
      <input type="text" className="no-border" value={inputValue} placeholder="Enter amount" readOnly />
      <p>To return: {returnAmount} Ден.</p>
      <Numpad onNumberClick={handleNumberClick} onClear={handleClear} onDelete={handleDelete} />
      <AddCustomerToOrder forceUpdate={forceUpdate} addCustomerDiscount={addCustomerDiscount} addCustomerToOrder={addCustomerToOrder} customers={customers} lastOrder={lastOrder}  />
      <FinnishOrder  forceUpdate={forceUpdate} totalPrice={lastOrder.totalPrice} inputValue={inputValue} handleClear={handleClear} userOrders={userOrders} finnishOrder={finnishOrder} />
    </div>
  );
};

export default NumpadInput;
