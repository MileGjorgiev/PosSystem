import React from "react";
import { jsPDF } from "jspdf";


const generatePdf = (orders) => {
    const doc = new jsPDF();
    let yPosition = 20;

    orders.forEach((order, index) => {
        if (index > 0) {
         
            doc.addPage();
            yPosition = 20;
        }

        doc.text("Order Details", 20, yPosition);
        yPosition += 10;
        doc.text(`Order ID: ${order.id}`, 20, yPosition);
        yPosition += 10;
        doc.text(`Employee Username: ${order.employee.username}`, 20, yPosition);
        yPosition += 10;
        doc.text(`Order Date: ${order.orderDate}`, 20, yPosition);

        if (order.customer && order.customer.email) {
            yPosition += 10;
            doc.text(`Customer Email: ${order.customer.email}`, 20, yPosition);
        }

        yPosition += 10;
        doc.text(`Order Price: МКД${order.totalPrice}`, 20, yPosition);

        yPosition += 10;
        doc.text("Items in Order:", 20, yPosition);

        order.itemInOrders.forEach((item, itemIndex) => {
            yPosition += 10;
            doc.text(`- ${item.item.name}: ${item.quantity} x МКД${item.item.price}`, 30, yPosition);
        });

        
        yPosition += 20;
    });

    return doc;
};

const SinglePdfDownloader = ({orders}) => {
    const handleDownload = () => {
        const doc = generatePdf(orders);
        doc.save('all_orders_details.pdf');
    };

    return (
        <div>
            <button onClick={handleDownload}>Download All Orders as PDF</button>
        </div>
    );
};

export default SinglePdfDownloader;
