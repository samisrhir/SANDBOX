import { Button } from '@/components/ui/button';
import { cn } from "@/lib/utils"
import { Link , useNavigate } from 'react-router-dom';
import { SlashIcon } from "@radix-ui/react-icons"
import useFetcheData from '../hooks/useFetchData';
import {
    Breadcrumb,
    BreadcrumbItem,
    BreadcrumbLink,
    BreadcrumbList,
    BreadcrumbSeparator,
} from "@/components/ui/breadcrumb"
import PowerCARDCustom from '../components/custom-ui/PowerCARDCustom'
import { useGlobalState,useGlobalDispatch } from '../store/store-ids';
import SolutionCard from '../components/custom-ui/SolutionCard';

const Solution = () => {

    const { productId,productReleaseId } = useGlobalState();

    let navigate = useNavigate();
    const dispatch = useGlobalDispatch();

    const {dataFetch: solutions} = useFetcheData(`http://localhost:8080/sandbox-ui/solutions/${productReleaseId}/solution`,[])

    function onSelect(id){
      dispatch({ type: 'SET_SOLUTION_ID', payload: id });
      navigate(`/module/our-modules`)
    }
    
    function goBack(){
      // const id = localStorage.getItem('prod-release')
      navigate(`/product-release/releases`)
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
          <BreadcrumbLink onClick={goBack} className={cn("cursor-pointer")}>
             Releases
          </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator>
          <SlashIcon />
        </BreadcrumbSeparator>
        <BreadcrumbItem>
          <BreadcrumbLink>
             Solutions
          </BreadcrumbLink>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    </div>
      <PowerCARDCustom
      title="PowerCARD Connect'API Solutions"
      description="PowerCARD Connect’ API is designed to match FinTechs creating emerging payments solutions directly with providers,
       enabling end users to benefit from the latest technology in the payments industry, such as mobile and contactless payments, tokenisation and biometrics,
       thus delivering an enhanced digital-first and secure user experience for both consumers and merchants."
       key={solutions}
      >
             {solutions.map(solution=> solution.visible &&(
             <SolutionCard
              releasetitle={solution.productRelease.name} 
             imagePath={solution.imageUrl}
             title={solution.name}
             description={solution.description}
             key={solution.productId}
             button="Explore"
             onClick={()=>onSelect(solution.solutionId)}
             clicked={solution.enabled}  
             />)
            )} 
      </PowerCARDCustom>
        </>
    );
}
export default Solution;