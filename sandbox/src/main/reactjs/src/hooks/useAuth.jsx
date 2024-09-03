import React, { createContext, useContext, useState } from 'react';
import { jwtDecode } from "jwt-decode";
const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState({});

  const login = (token) => {
    sessionStorage.setItem('token', token); 
    const decodedToken = jwtDecode(token);
    setUser(decodedToken); 
    console.log(decodedToken)
    sessionStorage.setItem('user',decodedToken.sub)
  };

  const logout = () => {
    sessionStorage.removeItem('token'); 
    sessionStorage.removeItem('user');
    setUser({}); 
  };

  const isAuthenticated = () => {
    return !!sessionStorage.getItem('token'); 
  };

  return (
    <AuthContext.Provider value={{ user, isAuthenticated, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
