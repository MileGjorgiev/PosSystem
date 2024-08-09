import React, { Component } from "react";
import PosSystemService from "../../../repository/service";
import './ItemForm.css';
import ReactPaginate from 'react-paginate'



import Card from "./Card/Card";

class ItemForm extends Component {
    constructor(props) {
        super(props);
        this.state = {
            formData: {
                quantity: 1,
                item: 1,
                order: null
            }
            ,
            page: 0,
            size: 6

        };
    }

    componentDidMount() {
        

        if (this.props.userOrders && this.props.userOrders.length > 0) {
            const lastOrder = { ...this.props.userOrders[0] };
            
            this.setState(prevState => ({
                formData: {
                    ...prevState.formData,
                    order: lastOrder.id
                }
            }));
        }
    }

    componentDidUpdate(prevProps) {
        if (this.props.userOrders !== prevProps.userOrders && this.props.userOrders.length > 0) {
            const lastOrder = { ...this.props.userOrders[0] };
            
            this.setState(prevState => ({
                formData: {
                    ...prevState.formData,
                    order: lastOrder.id
                }
            }));
        }
    }

    handleChange = (e) => {
        const { name, value } = e.target;
        console.log(`q: ${this.state.formData.quantity},i: ${this.state.formData.item},o: ${this.state.formData.order}`);
        
        let newValue;
        if (name === "quantity" || name === "item") {
          newValue = parseInt(value.trim(), 10);
          if (isNaN(newValue)) {
            newValue = 0;
          }
        } else {
          newValue = value.trim();
        }
    
        this.setState({
          formData: {
            ...this.state.formData,
            [name]: newValue
          }
        });
    
        
      }

    onFormSubmit = (e) => {
        e.preventDefault();
        const { quantity, item, order } = this.state.formData;
        console.log(`Submitting form with q: ${quantity}, i: ${item}, o: ${order}`);
        PosSystemService.addItem(quantity, item, order);
        this.props.forceUpdate();
        this.props.navigate('/home');
    }

    render() {
        const offset = this.state.size * this.state.page;
        const nextPageOffset = offset + this.state.size;
        const pageCount = Math.ceil(this.props.items.length / this.state.size);
        const products = this.getProductsPage(offset, nextPageOffset);



        return (
            <div className="form-container">
                <form onSubmit={this.onFormSubmit}>
                    <input type="hidden" onChange={this.handleChange} id="order" value={this.state.formData.order} name="order" />
                    <div className="item-row">
                        {products}
                    </div>
                    <ReactPaginate previousLabel={"back"}
                               nextLabel={"next"}
                               breakLabel={<a href="/#">...</a>}
                               breakClassName={"break-me"}
                               pageClassName={"ml-1"}
                               pageCount={pageCount}
                               marginPagesDisplayed={2}
                               pageRangeDisplayed={5}
                               onPageChange={this.handlePageClick}
                               containerClassName={"pagination m-4 justify-content-center"}
                               activeClassName={"active"}/>
                    <div id="quantityDiv">
                        <label>Quantity: </label>
                        <input type="number" defaultValue={1} onChange={this.handleChange} name="quantity" required />
                    </div>
                    <button onClick={this.props.forceUpdate} id="formItemButton" type="submit">Add</button>
                </form>
            </div>
        );
    }




    handlePageClick = (data) => {
        let selected = data.selected;
        
        this.setState({
            page: selected
        })
    }

    getProductsPage = (offset, nextPageOffset) => {
        if (!Array.isArray(this.props.items)) return null;
        
        return this.props.items.map((item) => {
            return (
                <Card key={item.id} item={item} handleChange={this.handleChange} />
            );
        }).filter((product, index) => {
            return index >= offset && index < nextPageOffset;
        })
    }

}

export default ItemForm;
