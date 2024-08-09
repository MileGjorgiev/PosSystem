import React from 'react';
import { Link } from 'react-router-dom';
const Customers = ({customers}) => {
    return(
        <div>
            <Link className='btn btn-success' to={"/addCustomer"} >Add Customer</Link>
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Email</th>
                    <th scope="col">Name</th>
                    <th scope="col">Phone number</th>
                    <th scope="col">Points</th>
                  </tr>
                </thead>
                <tbody>
                    {customers.map(cust => (
                        <tr key={cust.id}>
                            <td>{cust.email}</td>
                            <td>{cust.name}</td>
                            <td>{cust.phoneNumber}</td>
                            <td>{cust.points}</td>
                        </tr>
                    ))}
                  
                </tbody>
            </table>
        </div>
    )
}


export default Customers;