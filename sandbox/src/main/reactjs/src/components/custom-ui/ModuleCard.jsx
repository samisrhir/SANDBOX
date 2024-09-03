import React from 'react';
import { Button } from '../ui/button';
import { ChevronRightIcon } from '@radix-ui/react-icons';
import '../../assets/css/modulecard.css';

const ModuleCard = ({ releasetitle,title, description, button, onClick, imagePath, clicked }) => {
  const handleClick = (event) => {
    if (!clicked) {
      event.preventDefault();
      return;
    }
    onClick();
  };

  return (
    <div className="cardd" style={{display:"flex",flexDirection:"column",justifyContent:'space-between'}}>
        <div>
            
      <div className="titleupp" style={{display:'flex',alignItems:'center'}}>
      <div className="imagee">
          <img src={imagePath} alt={title} style={{width:"40%"}} />
        </div>
     
      
      </div>
      
      
      <div className="info">
        <p className="desc">{description}</p>
      </div></div>
      <div className="footer">
        <Button
          variant="destructive"
          className={`actionn ${!clicked && 'opacity-50 cursor-not-allowed'}`}
          onClick={handleClick}
        >
          {button && button === 'select' ? <ChevronRightIcon className="mr-2 h-4 w-4" /> : ''}
          {button}
        </Button>
      </div></div>
  );
};

export default ModuleCard;
