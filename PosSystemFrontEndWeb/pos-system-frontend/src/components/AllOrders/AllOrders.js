import React from 'react'
import Header from '../Header/Header';
import SinglePdfDownloader from '../PdfDownloader/AllOrdersPDF'
import { useState } from 'react';
import { useNavigate } from 'react-router';
const AllOrders = ({orders,filter,emp}) => {

    const [formData, updateFormData] = useState({
        employeeId: null,
    })
    
    const navigate = useNavigate();
    
    const handleChange = (e) => {
        console.log(formData.employeeId);
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();
        const employeeId = formData.employeeId;
        filter(employeeId);
        navigate("/allOrders")
    };

    return (
        
        <div>
            <SinglePdfDownloader orders={orders}/>
            <div>
            <form  onSubmit={onFormSubmit}>
                <div className="form-group">
              <label>Employee: </label>
              <select name="employeeId" style={{width: "15%"}}  onChange={handleChange} >
                <option></option>
                {emp.map(emp => {
                     return <option key={emp.username} value={emp.username}>{emp.username}</option>
                })}
              </select>
              <button className="filterButton" type="submit">Filter</button>
            </div>
            </form>
            </div>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">OrderID</th>
                    <th scope="col">Date</th>
                    <th scope="col">Employee</th>
                    <th scope="col">TotalPrice</th>
                    <th scope="col">ItemIds</th>
                  </tr>
                </thead>
                <tbody>
                    {orders.map(order => (
                        <tr key={order.id}>
                            <td>{order.id}</td>
                            <td>{order.orderDate}</td>
                            <td>{order.employee.username}</td>
                            <td>{order.totalPrice}</td>
                            <td>{order.itemInOrders.map(item => (
                                <p key={item.id}>/Quantity: {item.quantity} -  ItemId{item.item.id}/</p>
                            ))}</td>
                        </tr>
                    ))}
                  
                </tbody>
            </table>
        </div>
    )
}


export default AllOrders;