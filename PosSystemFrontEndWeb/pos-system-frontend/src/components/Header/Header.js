import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import PosSystemService from "../../repository/service";
import "./Header.css";

const Header = ({ allOrders = [], deleteOrder, allEmployees = [], dummyIncr }) => {
  const [userRole, setUserRole] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const username = sessionStorage.getItem("JWT");
    if (username && allEmployees.length > 0) {
      const loggedInUser = allEmployees.find(emp => emp.username === username);
      
      if (loggedInUser) {
        setUserRole(loggedInUser.role);
        dummyIncr();
      }
    }
  }, [allEmployees]);

  const userOrders = allOrders.filter(order => order.employee.username === sessionStorage.getItem("JWT"));
  userOrders.sort((a, b) => b.id - a.id);
  
  const lastOrder = userOrders.length > 0 ? userOrders[0] : null;

  const logout = () => {
    if (lastOrder) {
      deleteOrder(lastOrder.id);
    }

    PosSystemService.logout()
      .then(() => {
        sessionStorage.removeItem("JWT");
        navigate("/login");
      })
      .catch(err => {
        console.log("Something went wrong");
      });
  };

  const currUser = sessionStorage.getItem("JWT");

  if (!currUser) return null;

  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary">
      <div className="container-fluid">
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0 ms-auto">
            <li className="nav-item me-2">
              <Link className="nav-link" to={'/home'}>
                Curr Order
              </Link>
            </li>
            {userRole === "ROLE_ADMIN" && (
              <>
                <li className="nav-item me-2">
                  <Link className="nav-link" to={'/allItems'}>
                    Items
                  </Link>
                </li>
                <li className="nav-item me-2">
                  <Link className="nav-link" to={"/allOrders"}>
                    Orders
                  </Link>
                </li>
                <li className="nav-item me-2">
                  <Link className="nav-link" to={"/allUsers"}>
                    Employees
                  </Link>
                </li>
                <li className="nav-item me-2">
                  <Link className="nav-link" to={"/discounts"}>
                    Discounts
                  </Link>
                </li>
                <li className="nav-item me-2">
                  <Link className="nav-link" to={"/customers"}>
                    Customers
                  </Link>
                </li>
              </>
            )}
            <li className="nav-item me-2">
              <button className="btn btn-danger" onClick={logout}>
                Logout
              </button>
            </li>
            <li className="nav-item">
              <p className="nav-link">User: {currUser}</p>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Header;
