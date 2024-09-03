import React from "react";
import { Button } from "../ui/button";
import { MagnifyingGlassIcon, CalendarIcon } from "@radix-ui/react-icons";
import { Link } from "react-router-dom";
import "../../assets/css/hero.css";

const Hero = () => {


  return (<>
      <div className="register">
        <div className="wrapperr">
        <div className="col px-4 md:px-0">
          <div className="grid grid-cols-1 lg:grid-cols-7 gap-6 relative">
            <div className="lg:col-span-7 bg-main-sub rounded-lg relative">
              <div className="main-section-content">
                <div className="portal-logo-container">
                  <img
                    alt="Portal Logo"
                    style={{ width: "267px", height: "243px" }}    
                    className="portal-logo"
                    src="portal_logo.png"
                  />
                  <div className="portal-logo-text">
                    <h3>PowerCARD CONNECT' API</h3>{" "}
                    <h5
                      style={{
                        color: "white",
                        marginTop: "-9px",
                        fontFamily: "monospace",
                      }}
                      className="portal-logo-sub-text"
                    >
                      Integrate Easier, Faster and More Secure With PowerCARD
                      APIs
                    </h5>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <p className="descc">
        Integrate Easier, Faster and More Secure With PowerCARD
        Integrate Easier, Faster and More Secure With PowerCARD
        Integrate Easier, Faster and More Secure With PowerCARD
        Integrate Easier, Faster and More Secure With PowerCARD
          </p>
          
        <div className="btnn-wrapper">
          
          <Link to={"/products"}>
            <Button className="btnn">
              Explore our APIs
              <svg fill="currentColor" viewBox="0 0 24 24" class="icon">
                <path
                  clip-rule="evenodd"
                  d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zm4.28 10.28a.75.75 0 000-1.06l-3-3a.75.75 0 10-1.06 1.06l1.72 1.72H8.25a.75.75 0 000 1.5h5.69l-1.72 1.72a.75.75 0 101.06 1.06l3-3z"
                  fill-rule="evenodd"
                ></path>
              </svg>
            </Button>
          </Link>
          <Link to={'/register'}>
          <Button className="btnn">
            Request a Demo
            <svg fill="currentColor" viewBox="0 0 24 24" class="icon">
              <path
                clip-rule="evenodd"
                d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75s4.365 9.75 9.75 9.75 9.75-4.365 9.75-9.75S17.385 2.25 12 2.25zm4.28 10.28a.75.75 0 000-1.06l-3-3a.75.75 0 10-1.06 1.06l1.72 1.72H8.25a.75.75 0 000 1.5h5.69l-1.72 1.72a.75.75 0 101.06 1.06l3-3z"
                fill-rule="evenodd"
              ></path>
            </svg>
          </Button></Link>
        </div>
       
        </div>
        
        </div>
        <div className="lastupdate">
        <p>
            SandBox Version: 2.3.2 - Last Updated: March 07, 2024
         <span>             PWC Version: PWC-4.1.4</span>   
          </p>
        </div>
        
        </>
      
          

     
  );
};
export default Hero;
