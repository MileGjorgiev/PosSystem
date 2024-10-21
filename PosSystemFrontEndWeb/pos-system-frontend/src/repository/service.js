import axios from '../axios/axios'




const PosSystemService = {
    register: (username,password,name,role) => {
        return axios.post("/register", {
            "username": username,
            "password" : password,
            "name": name,
            "role": role
        })
    },
     login: (username, password) => {
        return axios.post('/login', {
            "username": username,
            "password": password
        });
    },
    logout: () => {
        return axios.get('/logout',{withCredentials: true})
    },
    createOrder: (employee) => {
        return axios.post('/orders/add', {
            "username": employee
        })
    },
    getOrders: () =>{
        return axios.get('/orders')
    },
    getItems: () => {
        return axios.get('/items')
    },
    getAllItems: () => {
        return axios.get('/items/allItems')
    },
    addItem: (quantity,item,order) => {
        return axios.post("/itemsInOrder/add", {
            "quantity": quantity,
            "item" : item,
            "order": order
        })
    },
    getItemInOrder: () => {
        return axios.get('/itemsInOrder')
    },
    addProduct: (name, description, quantityInStock, price, itemType, typeSex, itemImage) => {
        const formData = new FormData();
        formData.append('name', name);
        formData.append('description', description);
        formData.append('quantityInStock', quantityInStock);
        formData.append('price', price);
        formData.append('itemType', itemType);
        formData.append('typeSex', typeSex);
        formData.append('file', itemImage);
    
        return axios.post('/items/add', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });
    },
    getItemTypes: () => {
        return axios.get('/items/itemType')
    },
    getItemTypeGender: () => {
        return axios.get("/items/typeSex")
    },
    deleteItem: (id) => {
        return axios.delete(`/items/delete/${id}`);
    },
    editItem: (id,name, description, quantityInStock, price, itemType, typeSex,itemImage) => {
        return axios.put(`/items/edit/${id}`, {
            "name": name,
            "description": description,
            "quantityInStock" : quantityInStock,
            "price": price,
            "itemType": itemType,
            "typeSex": typeSex,
            "itemImage": itemImage
        });
    },
    getItem: (id) => {
        return axios.get(`/items/${id}`);
    },
    getAllOrders: () => {
        return axios.get("/orders")
    },
    getEmployees: () => {
        return axios.get("/employees")
    },
    filterItemsByType: (itemType,typeSex) => {
        return axios.get('/items/filterByTypes', {
            params:{
                itemType: itemType,
                typeSex : typeSex
            }
            
        })
    },
    deleteItemInOrder : (id) => {
        return axios.delete(`/itemsInOrder/delete/${id}`);
    },

    applyEmpDiscount: (id,username) => {
        return axios.post("/orders/empDiscount",{
            
                "id":id,
                "username":username
            
        })
    },
    createDiscount: (validUntil,discountAmount) => {
        return axios.post('/discounts/add', {
            "validUntil" : validUntil,
            "discountAmount": discountAmount
        })
    },
    getDiscounts: () => {
        return axios.get('/discounts')
    },
    addDiscountToItem: (id,itemId) => {
        return axios.post(`/discounts/addDiscount/${id}`, {
            itemId: itemId
        })
    },
    removeDiscountToItem: (id,itemId) => {
        return axios.post(`/discounts/removeDiscount/${id}`, {
            itemId: itemId
        })
    },

    finnishOrder: (id,username) => {
        return axios.post("/orders/finnishOrder",{
                "id":id,
                "username":username
            
        })
    },
    deleteOrder: (id) => {
        return axios.delete(`/orders/delete/${id}`);
    },
    createCustomer: (name,email,phoneNumber) => {
        return axios.post('/customers/add', {
            "name" : name,
            "email": email,
            "phoneNumber": phoneNumber
        })
    },
    fetchCustomers: () => {
        return axios.get('/customers')
    },
    addCustomerToOrder: (id, customerId) => {
        return axios.post('/orders/addCustomerToOrder', null, {
            params: {
                id: id,
                customerId: customerId
            }
        });
    },

    applyCustomerDisc: (id, customerId) => {
        return axios.post('/orders/applyCustomerDiscount', {
            
                "id": id,
                "customerId": customerId
            
        });
    },
    filterItemByName : (name) => {
        return axios.get('/items/filterByName',{
            params:{
                name : name
            }
            
        })
    },

    filterOrdersByEmployee : (employeeId) => {
        return axios.get('/orders/employeeOrders',{
            params: {
                employeeId: employeeId
            }
        }
        );
    }
    
 

}

export default PosSystemService;