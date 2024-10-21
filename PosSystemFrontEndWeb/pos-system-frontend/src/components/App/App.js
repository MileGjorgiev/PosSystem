import React, {Component} from "react";
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Register from "../Register/Register";
import Login from "../Login/Login";
import Main from "../Main/Main";
import PosSystemService from "../../repository/service";
import AllItems from '../AllItems/AllItems';
import AddItem from '../AllItems/addItem/addItem';
import EditProduct from '../AllItems/editProduct/EditProduct';
import AllOrders from "../AllOrders/AllOrders";
import AllUsers from "../AllUsers/AllUsers";
import Discounts from "../Discounts/Discounts";
import AddDiscount from "../Discounts/AddDiscount";
import Header from "../Header/Header";
import Customers from "../Customers/Customers";
import AddCustomer from "../Customers/AddCustomer"

class App extends Component{
  constructor(props){
    super(props);
    this.state = {
      itemInOrder: [],
      dummy: JSON.parse(localStorage.getItem('dummy')) || 0,
      items: [],
      itemTypes: [],
      itemTypesGender: [],
      selectedItem: {},
      allOrders: [],
      allEmployees: [],
      filteredItems: [],
      discounts: [],
      userRole: '',
      customers: [],
      allItems: []
    }
  }

  findUserRole = () => {
    const username = sessionStorage.getItem('JWT');
    if (username) {
      const loggedInUser = this.state.allEmployees.find(emp => emp.username === username);
      if (loggedInUser) {
        this.setState({ userRole: loggedInUser.role });
      }
    }
  }

  updateFilteredItems = (filteredItems) => {
    this.setState({ filteredItems });
};

  render(){
    const { userRole } = this.state;
    return(
      <Router>
        <div>
          
          {window.location.pathname !== "/" && window.location.pathname !== "/register" && (
            <Header allOrders={this.state.allOrders} deleteOrder={this.deleteOrder}  dummyIncr={this.dummyIncr} allEmployees={this.state.allEmployees} />
          )}
          <main>
            <Routes>
              <Route path={"/register"} element={<Register/>}/>
              <Route path={"/"} element={<Login fetch={this.fetch} dummyIncr={this.dummyIncr}/>}/>
              <Route path={"/home"} element={<Main empDiscount={this.empDiscount} deleteItemInOrder={this.deleteItemInOrder} updateFilteredItems={this.updateFilteredItems}
                filteredItems={this.state.filteredItems} itemTypes={this.state.itemTypes} itemTypesGender={this.state.itemTypesGender}
                forceUpdate={this.dummyIncr} itemInOrder={this.state.itemInOrder} filterByType={this.filterByType} filterItemsByName={this.filterItemsByName}
                finnishOrder={this.finnishOrder} fetch={this.fetch}
                customers={this.state.customers} addCustomerToOrder={this.addCustomerToOrder} addCustomerDiscount={this.addCustomerDiscount}/>} />
              
              {userRole === "ROLE_ADMIN" && (
                <>
                  <Route path={"/allItems"} element={<AllItems  onDelete={this.deleteItem} onEdit={this.getItem} items={this.state.allItems} />} />
                  <Route path={"/addItem"} element={<AddItem loadAllItems={this.loadAllItems} itemType={this.state.itemTypes} itemTypesGender={this.state.itemTypesGender} onAddProduct={this.addProduct} />} />
                  <Route path={"/editItem/:id"} element={<EditProduct onEditProduct={this.editItem} itemType={this.state.itemTypes} selectedItem={this.state.selectedItem} itemTypesGender={this.state.itemTypesGender} />} />
                  <Route path={"/allOrders"} element={<AllOrders emp={this.state.allEmployees} orders={this.state.allOrders} filter={this.filterOrdersByEmployee} />} />
                  <Route path={"/allUsers"} element={<AllUsers employees={this.state.allEmployees} />} />
                  <Route path={'/discounts'} element={<Discounts fetch={this.fetch} items={this.state.items} addDiscountToItem={this.addDiscountToItem} removeDiscountToItem={this.removeDiscountToItem} discounts={this.state.discounts} />} />
                  <Route path={'/addDiscount'} element={<AddDiscount addDiscount={this.addDiscount} />} />
                  <Route path={'/customers'} element={<Customers customers={this.state.customers} />} />
                  <Route path={'/addCustomer'} element={<AddCustomer addCustomer={this.addCustomer}/>} />
                </>
              )}
            </Routes>
          </main>
        </div>
      </Router>
    )
  }

  componentDidMount(){
    this.loadItemInOrder();
    this.loadItems();
    this.loadItemTypes();
    this.loadItemTypesGender();
    this.loadOrders();
    this.loadEmployees();
    this.loadDiscounts();
    this.loadCustomers();
    this.loadAllItems();
  }

  fetch = () => {
    this.loadItemInOrder();
    this.loadItems();
    this.loadItemTypes();
    this.loadItemTypesGender();
    this.loadOrders();
    this.loadEmployees();
    this.loadDiscounts();
    this.loadCustomers();
    this.loadAllItems();
  }

  loadEmployees = () => {
    PosSystemService.getEmployees()
      .then(data => {
        this.setState({ allEmployees: data.data }, () => {
          this.findUserRole();
        });
      });
  }

  dummyIncr = () => {
    let dummy = JSON.parse(localStorage.getItem('dummy')) || 0;
    dummy += 1;
    localStorage.setItem('dummy', JSON.stringify(dummy));
    this.setState({ dummy });
  }
  
  loadItemInOrder = () => {
    PosSystemService.getItemInOrder()
        .then((data) => {
            this.setState({
                itemInOrder: data.data
            })
        });
  }

  loadItems = () => {
    PosSystemService.getItems()
      .then((data) => {
        this.setState({
          items: data.data
        })
      })
  }

  loadAllItems = () => {
    PosSystemService.getAllItems()
      .then((data) => {
        this.setState({
          allItems: data.data
        })
      })
  }

  addProduct = (name, description, quantityInStock, price, itemType, typeSex, itemImage) => {
    PosSystemService.addProduct(name, description, quantityInStock, price, itemType, typeSex, itemImage)
      .then(() => {
        this.loadItems();
      })
  }

  loadItemTypes = () => {
    PosSystemService.getItemTypes()
      .then((data) => {
        this.setState({
          itemTypes: data.data
        })
      })
  }

  loadItemTypesGender = () => {
    PosSystemService.getItemTypeGender()
      .then((data) => {
        this.setState({
          itemTypesGender: data.data
        })
      })
  }

  deleteItem = (id) => {
    PosSystemService.deleteItem(id)
      .then(() => {
        this.loadAllItems();
      });
  }

  editItem = (id, name, description, quantityInStock, price, itemType, typeSex, itemImage) => {
    PosSystemService.editItem(id, name, description, quantityInStock, price, itemType, typeSex, itemImage)
      .then(() => {
        this.loadAllItems();
      });
  }

  getItem = (id) => {
    PosSystemService.getItem(id)
      .then((data) => {
        this.setState({
          selectedItem: data.data
        })
      })
  }

  loadOrders = () => {
    PosSystemService.getAllOrders()
      .then(data => {
        this.setState({
          allOrders: data.data
        })
      })
  }

  filterByType = (itemType, typeSex) => {
    PosSystemService.filterItemsByType(itemType, typeSex)
      .then((data) => {
        this.setState({
          filteredItems: data.data
        })
      })
  }

  deleteItemInOrder = (id) => {
    PosSystemService.deleteItemInOrder(id)
      .then(() => {
        this.dummyIncr();
      })
  }

  empDiscount = (id, username) => {
    PosSystemService.applyEmpDiscount(id, username)
      .then(() => {
        this.dummyIncr();
      })
  }

  loadDiscounts = () => {
    PosSystemService.getDiscounts()
      .then((data) => {
        this.setState({
          discounts: data.data
        })
      })
  }

  addDiscount = (validUntil, discountAmount) => {
    PosSystemService.createDiscount(validUntil, discountAmount)
      .then(() => {
        this.loadDiscounts();
      })
  }

  addDiscountToItem = (id, itemId) => {
    PosSystemService.addDiscountToItem(id, itemId)
      .then(() => {
        this.loadDiscounts();
      });
  }

  removeDiscountToItem = (id, itemId) => {
    PosSystemService.removeDiscountToItem(id, itemId)
      .then(() => {
        this.loadDiscounts();
        this.dummyIncr()
      });
  }


  finnishOrder = (id, username) => {
    PosSystemService.finnishOrder(id, username)
      .then(() => {
        this.dummyIncr();
      })
  }

  deleteOrder = (id) => {
    PosSystemService.deleteOrder(id);
  }


  addCustomer = (name,email ,phoneNumber) => {
    PosSystemService.createCustomer(name, email,phoneNumber)
      .then(() => {
        this.loadCustomers();
      })
  }

  loadCustomers = () => {
    PosSystemService.fetchCustomers()
      .then(data => {
        this.setState({
          customers: data.data
        })
      })
  }

  addCustomerToOrder = (id, customerId) => {
    PosSystemService.addCustomerToOrder(id, customerId)
      .then(() => {
        this.dummyIncr()
      });
  }

  addCustomerDiscount = (id, customerId) => {
    PosSystemService.applyCustomerDisc(id, customerId)
      .then(() => {
        this.dummyIncr()
      });
  }


  filterItemsByName = (name) => {
    PosSystemService.filterItemByName(name)
    .then((data) => {
      this.setState({
        filteredItems: data.data
      })
    })
  }

  filterOrdersByEmployee = (employeeId) => {
    PosSystemService.filterOrdersByEmployee(employeeId)
    .then((data) => {
      this.setState({
        allOrders: data.data
      })
    })
  }
}

export default App;
