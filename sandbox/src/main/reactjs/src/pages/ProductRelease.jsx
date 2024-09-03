import React from 'react'
import PowerCard from '../components/custom-ui/ProductCard';
import { Link,useNavigate} from 'react-router-dom';
import { SlashIcon } from "@radix-ui/react-icons"
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb"
import { useGlobalState,useGlobalDispatch } from '../store/store-ids';
import PowerCARDCustom from '../components/custom-ui/PowerCARDCustom'
import useFetcheData from '../hooks/useFetchData';
const ProductRelease = () => {

    const navigate = useNavigate();
    const { productId } = useGlobalState();
    const dispatch = useGlobalDispatch();
    const {dataFetch: productReleases} = useFetcheData(`http://localhost:8080/sandbox-ui/product-releases/${productId}/release`,[])

    function onSelect(productReleaseId){
      dispatch({ type: 'SET_PRODUCT_RELEASE_ID', payload: productReleaseId });
      navigate(`/solution/our-solutions`)
    }

  return (
    <main className="profile-page version-page">
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
             Releases
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    </div>
      <PowerCARDCustom
      title="PowerCARD Connect'API Releases and Updates"
      description="Welcome to our hub for Connect'API Releases! Here you'll find information on our key
      releases to date. Check back for new information every trimester"
      key={productReleases}
      >
        {productReleases.map(productRelease => productRelease.visible && (
            <PowerCard
          title={productRelease.name}
          description={productRelease.description ? productRelease.description : "product releases"}
          key={productRelease.productReleaseId} 
          button="Learn More"
          onClick={() => onSelect(productRelease.productReleaseId)}
          clicked={productRelease.enabled}
          color = {productId === 2 ? "rgb(37, 99, 235)" : "#bb281b"}
            />
        ))}
      </PowerCARDCustom>
    </main>
  )
}

export default ProductRelease