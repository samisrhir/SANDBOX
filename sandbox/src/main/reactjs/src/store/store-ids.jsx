import React, { createContext, useContext, useReducer } from 'react';

const initialState = {
  productId: sessionStorage.getItem("productId"),
  productReleaseId : sessionStorage.getItem("productReleaseId"),
  solutionId: sessionStorage.getItem("solutionId"),
  moduleId: sessionStorage.getItem("moduleId"),
};


const SET_PRODUCT_ID = 'SET_PRODUCT_ID';
const SET_PRODUCT_RELEASE_ID = 'SET_PRODUCT_RELEASE_ID'
const SET_SOLUTION_ID = 'SET_SOLUTION_ID';
const SET_MODULE_ID = 'SET_MODULE_ID';


function reducer(state, action) {
  switch (action.type) {
    case SET_PRODUCT_ID:
      sessionStorage.setItem("productId",action.payload)
      return { ...state, productId: action.payload };
    case SET_PRODUCT_RELEASE_ID:
      sessionStorage.setItem("productReleaseId",action.payload)
        return { ...state, productReleaseId: action.payload };
    case SET_SOLUTION_ID:
      sessionStorage.setItem("solutionId",action.payload)
      return { ...state, solutionId: action.payload };
    case SET_MODULE_ID:
      sessionStorage.setItem("moduleId",action.payload)
      return { ...state, moduleId: action.payload };
    default:
      return state;
  }
}

const GlobalStateContext = createContext();
const GlobalDispatchContext = createContext();


export function useGlobalState() {
  return useContext(GlobalStateContext);
}


export function useGlobalDispatch() {
  return useContext(GlobalDispatchContext);
}


export function GlobalProvider({ children }) {
  const [state, dispatch] = useReducer(reducer, initialState);

  return (
    <GlobalStateContext.Provider value={state}>
      <GlobalDispatchContext.Provider value={dispatch}>
        {children}
      </GlobalDispatchContext.Provider>
    </GlobalStateContext.Provider>
  );
}
