import React from 'react';
import { Button } from '../ui/button';
import { ChevronRightIcon } from '@radix-ui/react-icons';
import '../../assets/css/cardstyling.css';
const PowerCard = ({ title, description, button, onClick, imagePath, clicked,color }) => {
  const handleClick = (event) => {
    if (!clicked) {
      event.preventDefault(); 
      return;
    }
    onClick(); 
  };

  return (
    <div className="carde" style={{display:"flex",flexDirection:"column",justifyContent:'space-between'}}>
      <div className="headere" style={{backgroundColor:color}}>
        <p className="titlee" >{title}</p>
      </div>
      <div className="infoe">
        <p className="desc">{description}</p>
        {imagePath && (
          <div className="imagee">
            <img src={imagePath} alt={title} style={{ width: '50%', height: '106px' }} />
          </div>
        )}
      </div>
      <div className="footere">
        <Button
          variant={color}
          style={{backgroundColor:color}}
          className={`actione ${!clicked && 'opacity-50 cursor-not-allowed'}`}
          onClick={handleClick}
        >
          {button && button === 'select' ? <ChevronRightIcon className="mr-2 h-4 w-4" /> : ''}
          {button}
        </Button>
      </div>
    </div>
  );
};

export default PowerCard;