import React from 'react'
import Header from '../Header/Header';
import SinglePdfDownloader from '../PdfDownloader/AllOrdersPDF'

const AllOrders = ({orders}) => {
    return (
        
        <div>
            <SinglePdfDownloader orders={orders}/>
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