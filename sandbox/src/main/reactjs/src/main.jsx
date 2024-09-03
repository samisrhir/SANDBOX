import React from 'react'
import ReactDOM from 'react-dom/client'
import './globals.css'
import { ThemeProvider } from "@/components/theme-provider"
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import Login from './pages/Login.jsx';
import Home from './pages/Home.jsx'
import Product from './pages/Product.jsx';
import ProductRelease from './pages/ProductRelease.jsx';
import Layout from './pages/Layout.jsx';
import Solution from "@/pages/Solution.jsx";
import { GlobalProvider } from './store/store-ids.jsx';
import Module from './pages/Modules';
import Dashboard from './admin/Dashboard.jsx';
import Users from './admin/users_management/Users.jsx';
import Statistics from './admin/Statistics.jsx';
import Register from './pages/Register.jsx';
import PrivateRoute from './utils/PrivateRoutes.jsx';
import { AuthProvider } from './hooks/useAuth.jsx';
import Swagger from './pages/Swagger.jsx';
import ProductsAdmin from './admin/products/ProductsAdmin.jsx';
import ProductReleaseAdmin from './admin/product_release/ProductReleaseAdmin.jsx';
import SolutionAdmin from './admin/solutions/SolutionAdmin.jsx';
import ModulesAdmin from './admin/module/ModulesAdmin.jsx';
import Configuration from './admin/Configuration.jsx';
import APi from './admin/apis/APi.jsx';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      { index: true, element: <Home /> },
      { path: 'products', element: <PrivateRoute><Product /></PrivateRoute> },
      { path: 'product-release/:id', element: <PrivateRoute><ProductRelease /></PrivateRoute> },
      { path: 'solution/:solutionId', element: <PrivateRoute><Solution /></PrivateRoute> },
      { path: 'module/:solutionId', element: <PrivateRoute><Module /></PrivateRoute> },
      { path: 'swagger', element: <PrivateRoute> <Swagger /></PrivateRoute> },
      { path: 'login', element: <Login /> },
      { path: 'register', element: <Register /> },
      // or if you meant to use `Lib` component
      // { path: 'lib', element: <Lib /> }
    ]
  },
  {
    path: '/console',
    element: <Dashboard />,
    children: [
      { index: true, element: <Statistics /> },
      { path: 'users', element: <Users /> },
      { path: 'products', element: <ProductsAdmin /> },
      { path: 'product-release', element: <ProductReleaseAdmin /> },
      { path: 'solutions', element: <SolutionAdmin /> },
      { path: 'modules', element: <ModulesAdmin /> },
      { path: 'api', element: <APi /> },
      { path: 'configuration', element: <Configuration /> }
    ]
  }
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ThemeProvider defaultTheme="dark" storageKey="sandbox-ui-theme">
      <AuthProvider>
        <GlobalProvider>
          <RouterProvider router={router} />
        </GlobalProvider>
      </AuthProvider>
    </ThemeProvider>
  </React.StrictMode>,
);
