import React from 'react'
import Header from '../components/custom-ui/Header'
import { Outlet } from 'react-router-dom'
import Footer from '../components/custom-ui/Footer'

const Layout = () => {
  return (
    <>
        <Header />
        <Outlet />
        {/* <Footer/> */}
    </>
  )
}

export default Layout