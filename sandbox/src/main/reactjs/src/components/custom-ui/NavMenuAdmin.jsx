import React,{useState} from 'react'
import { Link } from "react-router-dom"
import {
    Home,
    Package,
    Users,
    Component,
    Settings,
    Cog
  } from "lucide-react"
import { Button } from '../ui/button';


const NavMenuAdmin = () => {
  const [activeLink, setActiveLink] = useState(null);

  const handleLinkClick = (index) => {
    setActiveLink(index);
  };
  return (
    <div className="hidden border-r bg-muted/40 md:block">
    <div className="flex h-full max-h-screen flex-col gap-2">
      <div className="flex h-14 items-center border-b px-4 lg:h-[60px] lg:px-6">
        <Link href="/" className="flex items-center gap-2 font-semibold">
          <img src="/hps-logo.png" alt="hps" height={200} width={100} className='cursor-pointer invert-0 dark:invert '/>
        </Link>
      </div>
      <div className="flex-1">
      <nav className="relative grid items-start px-2 text-sm font-medium lg:px-4 ">
      <Link
        href="#"
        onClick={() => handleLinkClick(0)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2 mt-16 transition-all ${
          activeLink === 0 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >
        <Home className="h-4 w-4" />
        Dashboard
      </Link>
      <Link
        to="users"
        onClick={() => handleLinkClick(1)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2 transition-all ${
          activeLink === 1 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >
        <Users className="h-4 w-4" />
        Users
      </Link>
      <Link
        to={'products'}
        onClick={() => handleLinkClick(2)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2 transition-all ${
          activeLink === 2 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >
        <Package className="h-4 w-4" />
        Products
      </Link>
      <Link
        to={'product-release'}
        onClick={() => handleLinkClick(3)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2  transition-all ${
          activeLink === 3 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >
        <Package className="h-4 w-4" />
        Product Releases
      </Link>
      <Link
        to={'solutions'}
        onClick={() => handleLinkClick(4)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2 transition-all ${
          activeLink === 4 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >
        <Package className="h-4 w-4" />
        Solutions
      </Link>
      <Link
        to={'modules'}
        onClick={() => handleLinkClick(5)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2 transition-all ${
          activeLink === 5 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >
        <Component className="h-4 w-4" />
        Modules
      </Link>
      <Link
        to={'api'}
        onClick={() => handleLinkClick(6)}
        className={`flex items-center gap-3 rounded-lg px-3 py-2 transition-all ${
          activeLink === 6 ? 'bg-muted text-primary' : 'text-muted-foreground'
        } hover:text-primary`}
      >

        <Cog className="h-4 w-4" />
        Apis Management 
      </Link>
    </nav>
    <Link
          to='configuration'
          onClick={() => handleLinkClick(6)}
          className={`flex items-center gap-3 rounded-lg px-4 py-2 mb-10 transition-all absolute bottom-0 ${
            activeLink === 6 ? 'bg-muted text-primary' : 'text-muted-foreground'
          } hover:text-primary`}
        >
            <Settings className="h-5 w-5" />
          Configuration
        </Link>
      </div>
    </div>
  </div>
  )
}

export default NavMenuAdmin