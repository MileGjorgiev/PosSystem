import React from "react";
import Header from "../Header/Header"

const AllUsers = ({employees}) => {
    return (
        <div>
           
            <table class="table">
                <thead>
                  <tr>
                    <th scope="col">Username</th>
                    <th scope="col">Role</th>
                    
                  </tr>
                </thead>
                <tbody>
                    {employees.map(emp => (
                        <tr key={emp.username}>
                            <td>{emp.username}</td>
                            <td>{emp.role}</td>
                        </tr>
                    ))}
                  
                </tbody>
            </table>
        </div>
    )
}


export default AllUsers;