import React, { useEffect } from 'react';
import { jsPDF } from 'jspdf';

const PdfDownloader = ({ orderData, autoDownload,lastOrder }) => {
    useEffect(() => {
        if (autoDownload) {
            generatePdf();
        }
    }, [autoDownload]);

    const generatePdf = () => {
        const doc = new jsPDF();
        
        let yPosition = 20;
        
        doc.text("Order Details", 20, yPosition);
        
        yPosition += 10; 
        doc.text(`Order ID: ${orderData.id}`, 20, yPosition);
        
        yPosition += 10;
        doc.text(`Employee Username: ${sessionStorage.getItem("JWT")}`, 20, yPosition);
        
        yPosition += 10;
        doc.text(`Order Date: ${lastOrder.orderDate}`, 20, yPosition);
        
        if (lastOrder.customer && lastOrder.customer.email) {
            yPosition += 10;
            doc.text(`Customer Email: ${lastOrder.customer.email}`, 20, yPosition);
        }
        
        yPosition += 10;
        doc.text(`Order Price: МКД${lastOrder.totalPrice}`, 20, yPosition);
        
        yPosition += 10;
        doc.text("Items in Order:", 20, yPosition);
        
        lastOrder.itemInOrders.forEach((item, index) => {
            yPosition += 10; // Move down for each item
            doc.text(`- ${item.item.name}: ${item.quantity} x МКД${item.item.price}`, 30, yPosition);
        });
        
        doc.save('order_details.pdf');
    };
    
    
    

    return null; 
};

export default PdfDownloader;
