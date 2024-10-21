import React from "react";
import Header from "../Header/Header";
import { Link } from "react-router-dom";
import AddDiscountToItem from "./AddDiscountToItem";

const Discounts = ({ discounts, addDiscountToItem, items, removeDiscountToItem, fetch }) => {
    return (
        <div>
            
            <Link className="btn btn-success" to={'/addDiscount'}>Add Discount</Link>
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">DiscountID</th>
                        <th scope="col">ValidUntil</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Items</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {discounts != null ? discounts.map(disc => (
                        <tr key={disc.id}>
                            <th scope="col">{disc.id}</th>
                            <th scope="col">{disc.validUntil}</th>
                            <th scope="col">{disc.discountAmount}%</th>
                            <th scope="col">
                                {disc.items && disc.items.length > 0 ? (
                                    <ul>
                                        {disc.items.map(item => (
                                            <li key={item.id}>
                                                {item.name} - {item.price}
                                                <form onSubmit={(e) => {
                                                    e.preventDefault();
                                                    removeDiscountToItem(disc.id, item.id);
                                                }}>
                                                    <button id="deleteItem" className="btn btn-danger" type="submit">X</button>
                                                </form>
                                            </li>
                                        ))}
                                    </ul>
                                ) : (
                                    <p>No items associated</p>
                                )}
                            </th>
                            <th>
                                <AddDiscountToItem fetch={fetch} discId={disc.id} items={items} addDiscountToItem={addDiscountToItem} discountItems={disc.items} />
                            </th>
                        </tr>
                    )) : <tr><td colSpan="5">No discounts available</td></tr>}
                </tbody>
            </table>
        </div>
    );
};

export default Discounts;
