import React, { useState, useEffect, useRef, createContext } from "react";
import PosSystemService from "../../repository/service";
import Header from "../Header/Header";
import Order from "../Order/Order";
import ItemForm from "./ItemForm/ItemForm";
import { useReducer } from "react";
import './Main.css'
import { useNavigate } from "react-router";
import ItemTypeFilter from "./ItemTypeFIlter/ItemTypeFilter";
import NumpadInput from "../NumPad/NumpadInput"
import EmpDiscount from "./empDiscount/EmpDiscount";
import FilterItemByName from "./filterItemsByName/FilterItemByName";




const Main = ({updateFilteredItems,filterItemsByName,addCustomerDiscount,finnishOrder,itemInOrder,forceUpdate,filterByType,addCustomerToOrder,itemTypes,itemTypesGender,filteredItems,deleteItemInOrder,empDiscount,customers }) => {
    const [orders, setOrders] = useState([]);
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const hasLoggedLastOrder = useRef(false);
    const [, setDummyState] = useState(false);
    console.log(filteredItems);

    const navigate = useNavigate();

  

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                
                const response = await PosSystemService.getOrders();
                const getItems = await PosSystemService.getItems();
                
                setItems(getItems.data); 
                setOrders(response.data);
                
            } catch (error) {
                setError('Failed to fetch orders');
            } finally {
                setLoading(false);
            }
        };
        fetchOrders();
    }, [localStorage.getItem('dummy')]);

    const [formData, updateFormData] = useState({
        itemType: null,
        typeSex: null
    })

    const [formDataName, updateFormDataName] = useState({
        name :""
    })

    const handleChange = (e) => {
        console.log(formData.itemType);
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = async (e) => {
        e.preventDefault();
        const itemType = formData.itemType;
        const typeSex = formData.typeSex;

        try {
            const filteredItems = await PosSystemService.filterItemsByType(itemType, typeSex);
            setItems(filteredItems.data || []);
           
            
        } catch (error) {
            setError('Failed to fetch filtered items');
        }
    };


    const handleChangeName = (e) => {
        updateFormDataName({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    }

    const handleSubmitName = async (e) => {
        console.log('Event received:', e);
        e.preventDefault();
        const { name } = formDataName; 
        try {
            const filteredItems = await PosSystemService.filterItemByName(name);
            setItems(filteredItems.data || []);
            console.log(`Filtered ${filteredItems}`);
        } catch (error) {
            console.error('Error filtering items:', error);
        }
    };

    const userOrders = orders.filter(order => order.employee.username === sessionStorage.getItem("JWT"));
    userOrders.sort((a, b) => b.id - a.id);
    
    
    const lastOrder = userOrders.length > 0 ? userOrders[0] : null;
    const itemsOrders = lastOrder ? lastOrder.itemInOrders : [];

    
    if (lastOrder && !hasLoggedLastOrder.current) {
        
        hasLoggedLastOrder.current = true;
    }

    

    return (
        <div >
            
            
            <div className="main-container">
            <ItemTypeFilter itemType={itemTypes} itemTypesGender={itemTypesGender} handleChange={handleChange} onFormSubmit={onFormSubmit}    />
            <FilterItemByName 
             handleChangeName={handleChangeName} 
             handleSubmitName={handleSubmitName} 
             updateFilteredItems={updateFilteredItems} 
             filterItemsByName={filterItemsByName}  
             filteredItems={filteredItems} 
            />
            <span id="dummyMain">{localStorage.getItem("dummy")}</span>
            {loading ? (
                <p>Loading...</p>
            ) : error ? (
                <p>{error}</p>
            ) : (
                <Order deleteItemInOrder={deleteItemInOrder} userOrders={userOrders}  itemInOrders={itemsOrders} />
            )}
            <ItemForm  items={items} navigate={navigate}   forceUpdate={forceUpdate}   userOrders={userOrders} />
            <NumpadInput forceUpdate={forceUpdate} addCustomerDiscount={addCustomerDiscount} addCustomerToOrder={addCustomerToOrder} customers={customers} finnishOrder={finnishOrder} empDiscount={empDiscount} userOrders={userOrders}/>
            <EmpDiscount forceUpdate={forceUpdate} userOrders={userOrders} empDiscount={empDiscount}/>

            </div>
        </div>
    );
};

export default Main;
