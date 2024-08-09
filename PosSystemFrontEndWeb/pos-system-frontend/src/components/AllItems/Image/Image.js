import React from 'react';

const DisplayImage = ({ imageName,cardImage }) => {
    const imageUrl = `http://localhost:8080/uploads/${imageName}`;
    return (
        
    
        
               <img src={imageUrl} className='cardImage' alt="Uploaded" />
        
    );
};

export default DisplayImage;
