import React from 'react'

const PowerCARDCustom = ({title,description,children,isLoading}) => {
  return (
    <section className="pt-10 flex flex-col items-center">
    <div className="text-center ">
      <h1 className="text-4xl font-bold text-blue-900 mb-5"> {title} </h1>
      <p className="text-gray-500 text-md line-clamp-[20]">
        {description}
      </p>
    </div>
    <div className="conte">
        {children}
        {isLoading &&   
         <></>
      }
    </div>
  </section> 
  )
}

export default PowerCARDCustom