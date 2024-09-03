import React from 'react';
import { Button } from '../ui/button';
import { ChevronRightIcon } from '@radix-ui/react-icons';
import '../../assets/css/solutioncard.css';

const SolutionCard = ({ releasetitle,title, description, button, onClick, imagePath, clicked }) => {
  const handleClick = (event) => {
    if (!clicked) {
      event.preventDefault();
      return;
    }
    onClick();
  };

  return (
    <div className="card" style={{display:"flex",flexDirection:"column",justifyContent:'space-between'}}>
        <div>
    <h2 style={{color:'gray',fontSize:'13px',opacity:'0.6'}}>{releasetitle}</h2>
      <div className="titleup" style={{display:'flex',alignItems:'center'}}>
      <div className="image">
          <img src={imagePath} alt={title} style={{ marginRight:'60px',height:"90px",padding:'10px'}} />
        </div>
        <div className="header">

        <p className="title">{title}</p>
      </div></div>
      
      
      <div className="info">
        <p className="desc">{description}</p>
      </div></div>
      <div className="footer">
        <Button
          variant="destructive"
          className={`action ${!clicked && 'opacity-50 cursor-not-allowed'}`}
          onClick={handleClick}
        >
          {button && button === 'select' ? <ChevronRightIcon className="mr-2 h-4 w-4" /> : ''}
          {button}
        </Button>
      </div></div>
  );
};

export default SolutionCard;
