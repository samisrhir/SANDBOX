import React,{useState,useEffect} from 'react'
import ModeToggle from '../mode-toggle'
import {Button} from '../ui/button'

import { NavMenu } from './NavMenu'
import {useMediaQuery} from '../../hooks/useMediaQuery'
import {Linkedin , Youtube , Twitter, MenuIcon} from 'lucide-react'
import Icon from '../ui/Icon'
import { Drawer, DrawerContent, DrawerTrigger } from '../ui/drawer'
import {Link} from 'react-router-dom'
import '../../assets/css/header.css'
import { useAuth} from '../../hooks/useAuth'


const Header = () => {
  const auth = useAuth();
  
  const isMobile = useMediaQuery("(max-width: 768px)");

  return (
    <div className='head' >
    <header className='container w-full flex justify-around p-5 ' >
      {!isMobile ? 
      <>
     <Link to={''}> <img src="/hps-logo.png" alt="hps" style={{marginLeft:"-2px",marginRight:"430px"}} height={200} width={120} className='cursor-pointer invert-0 dark:invert'/> </Link>
          <div className='flex space-x-1	'>
            <NavMenu />
            <div className='flex pl-9 space-x-2 items-center'>
              <Icon url="https://www.linkedin.com/company/hps.io/" Icon={Linkedin}/>
              <Icon url="https://www.youtube.com/channel/UCG8SC8iSUZlsqR9oOyvG7nA" Icon={Youtube}/>
              <Icon url="https://twitter.com/i/flow/login?redirect_after_login=%2FHPS_worldwide" Icon={Twitter}/>
            </div> 
         </div>
        <div className='flex space-x-10 text-center justify-content-center'>
          {auth.isAuthenticated() ? <>
            <h3 style={{marginLeft:'70px'}} className='text-gray-400'> {auth.user.sub || sessionStorage.getItem('user') } </h3>
            <Button   onClick={()=>auth.logout()}>Logout</Button>
          </> : <Link to='register'><Button  style={{marginLeft:'70px'}}>CREATE AN ACCOUNT</Button></Link> }
       
        <ModeToggle  />
      </div>
      </>
  
      :
      <>
     <Link to={''}>
       <img src="/hps-logo.png" alt="hps" height={90} width={60} className='cursor-pointer invert-0 dark:invert space-x-2'/>
      </Link>
     

        <ModeToggle />
      <Drawer direction="right">
        <DrawerTrigger>
          <MenuIcon/>
        </DrawerTrigger>
        <DrawerContent >
          nav menu here
        </DrawerContent>
      </Drawer>
      </>
      }
    </header></div>
  )
}

export default Header