import React from 'react';
import { Link,useNavigate } from 'react-router-dom';
import { cn } from "@/lib/utils"
import { SlashIcon } from '@radix-ui/react-icons';
import PowerCARDCustom from '../components/custom-ui/PowerCARDCustom';
import ModuleCard from '../components/custom-ui/ModuleCard';
import '../assets/css/cardstyling.css';
import useFetcheData from '../hooks/useFetchData';
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb';
import { useGlobalState,useGlobalDispatch } from '../store/store-ids';

const Module = () => {
  const { productId,productReleaseId,solutionId } = useGlobalState();

   console.log('solutionId',solutionId)
  let navigate = useNavigate();

  const {dataFetch: modules} = useFetcheData(`http://localhost:8080/sandbox-ui/modules/${solutionId}/modules`,[])
  function goBackToProductRelease(){
    // const id = localStorage.getItem('prod-release')
    navigate(`/product-release/releases`)
  }
  function goBackToSolution(){
    navigate(`/solution/our-solutions`)
  }

  return (
    <>
  <div className='container flex flex-col items-start pt-5'>
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink asChild>
          <Link to="/">
            Home
            </Link>
          </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator>
          <SlashIcon />
        </BreadcrumbSeparator>
        <BreadcrumbItem>
          <BreadcrumbLink asChild>
          <Link to="/products">
             Products
          </Link>
          </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator>
          <SlashIcon />
        </BreadcrumbSeparator>
        <BreadcrumbItem>
          <BreadcrumbLink onClick={goBackToProductRelease} className={cn("cursor-pointer")}>
             Releases
          </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator>
          <SlashIcon />
        </BreadcrumbSeparator>
        <BreadcrumbItem>
          <BreadcrumbLink onClick={goBackToSolution} className={cn("cursor-pointer")}>
             Solutions
          </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator>
          <SlashIcon />
        </BreadcrumbSeparator>
        <BreadcrumbItem>
          <BreadcrumbLink>
             Modules
          </BreadcrumbLink>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    </div>
      <PowerCARDCustom
        title="PowerCARD Connect'API modules"
        description="Welcome to our hub for Connect'API Modules! Here you'll find information on our key modules. Check back for updates."
      >
       
          <div className="conteer">
            {modules.map((module) => (module.visible &&(
              <ModuleCard 
               onClick={()=>navigate(`/swagger`)}
                imagePath={module.imagePath}
                key={module.moduleId} 
                button = {module.name} 
                clicked={module.enabled}
              />)
            ))}
          </div>
      </PowerCARDCustom>
    </>
  );
};

export default Module;
