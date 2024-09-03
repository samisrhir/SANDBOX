import React  from 'react'
import PowerCard from '../components/custom-ui/ProductCard'
import { Link, useNavigate } from 'react-router-dom';
import { SlashIcon } from "@radix-ui/react-icons"
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb"
import PowerCARDCustom from '../components/custom-ui/PowerCARDCustom'
import useFetcheData from '../hooks/useFetchData';
import { useGlobalState,useGlobalDispatch } from '../store/store-ids';


const Product = () => {
  const navigate = useNavigate();
  const dispatch = useGlobalDispatch();


  const {dataFetch:products,isLoading} = useFetcheData('http://localhost:8080/sandbox-ui/products/',[])

  function onSelect(id){
    //localStorage.setItem("prod-release",id)
    dispatch({ type: 'SET_PRODUCT_ID', payload: id });
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
        Products
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    </div>
    <PowerCARDCustom
      title="PowerCARD Connect'API Products"
      description="Welcome to our hub for Connect'API Releases! Here you'll find information on our key
      releases to date. Check back for new information every trimester"
      isLoading = {isLoading}
      key={products}
      >
      {products.length > 0 ? (
          products.some(product => product.visible) ? (
            products.map(product => (
              product.visible && (
                <PowerCard
                  title={product.name}
                  description={product.description}
                  key={product.productId}
                  button="select"
                  onClick={() => onSelect(product.productId)}
                  clicked = {product.enabled}
                  className={product.name === "POWERCARD CONNECTAPI V4"}
                  color = {product.productId === 2 ? "rgb(37, 99, 235)" : "#bb281b"}

                />
              )
            ))
          ) : (
            <div className="flex justify-center items-center">
            <p className="text-center text-red-600">There are no enabled products!</p>
          </div>
          )
        ) : (
          <p>No products available!</p>
        )}
     
      </PowerCARDCustom>
    </>
  )
}

export default Product