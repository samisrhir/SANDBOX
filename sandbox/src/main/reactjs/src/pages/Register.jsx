  import React, { useEffect, useState } from 'react';
  import { Link } from 'react-router-dom';
  import "../assets/css/login.css";
  import { set } from 'date-fns';
  import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
  } from "@/components/ui/alert-dialog"
  
  function Register() {
    const [fullName, setFullName] = useState('');
    const [msg,setMsg] = useState('');
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [phone, setPhone] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [passwordError, setPasswordError] = useState('');
    const [usernameError, setUsernameError] = useState('');
    const [emailError, setEmailError] = useState('');
    const [isOpen, setIsOpen] = useState(false); // State variable to manage dialog visibility

    const clearfields = () => {
      setFullName('');
      setEmail('');
      setUsername('');
      setPhone('');
      setPassword('');
      setConfirmPassword('');
      setPasswordError('');
      setUsernameError('');
      setEmailError('');
    }
    const handleSubmit = async (event) => {
      event.preventDefault();
    
      if (password !== confirmPassword) {
        setPasswordError('Passwords do not match');
        return;
      }
    
      try {
        const response = await fetch('http://localhost:8080/auth/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ fullName, email, phone, password, username })
        });
    
        if (response.status === 201) {
          setMsg('Registration request accepted');
          // Status code 202: Accepted
          console.log('Registration request accepted');
          setIsOpen(true); // Open the dialog after successful registration
          clearfields();
     
        } else if (response.status === 409) {
          try {
            const responseBody = await response.json();
            if (responseBody.error.includes('Username')) {
              setUsernameError('Username already taken');
            } else if (responseBody.error.includes('Email')) {
              setEmailError('Email already exists');
            }
          } catch (error) {
            console.error('Error parsing response body as JSON:', error);
          }
        }
      } catch (error) {
        console.error('Registration failed:', error);
      }
    }
    
    

    return (
      <>
        <div className="registerr">
          <form className="form" onSubmit={handleSubmit}>
            {msg && <p className='msg text-center text-xs text-green-500 font-thin'>{msg}</p> }
            <p className="title">Create Sandbox Account</p>
            <div className="input-group p-2 mt-5">
              <label>
                <input
                  className="input"
                  type="text"
                  placeholder="Full Name"
                  value={fullName}
                  onChange={(e) => setFullName(e.target.value)}
                  required
                />
              </label>
            </div>
            <div className="input-group p-2">
            {usernameError && <p className='errormsg'>{usernameError}</p>}
              <label>

                <input
                  className="input"
                  type="text"
                  placeholder="User Name"
                  value={username}
                  
                  onChange={(e) => {
                    setUsername(e.target.value);
                    setUsernameError(''); // Reset the error message
                  }}
                  required
                />
              </label>
            </div>
            <div className="input-group p-2">
              <label>
              {emailError && <p  className='errormsg'>{emailError}</p>}
                <input
                  className="input"
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => {setEmail(e.target.value);
                    setEmailError(''); 
                  }}
                  required
                />
              </label>
            </div>
            <div className="input-group p-2">
              <label>
                <input
                  className="input"
                  placeholder="Phone"
                  value={phone}
                  onChange={(e) => setPhone(e.target.value.replace(/\D/, ''))}
                  title="Enter your phone number"
                  required
                />
              </label>
            </div>
            <div className="input-group p-2">
              <label>
                <input
                  className="input"
                  placeholder="Password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  minLength={8}
                  required
                />
              </label>
            </div>
            <div className="input-group p-2">
              <label>
                <input
                  className="input"
                  placeholder="Confirm Password"
                  type="password"
                  value={confirmPassword}
                  onChange={(e) => setConfirmPassword(e.target.value)}
                  minLength={8}
                  required
                />
              </label>
            </div>
            {passwordError && <p className='errormsg'>{passwordError}</p>}
            <button className="submit" type="submit">Submit</button>
            <p className="signin">
              <span>Already a client </span>
              <br />
              <Link to={"/login"}><a href="#">Sign in </a></Link>
            </p>
          </form>
          
        </div>
        <div className="lastupdate">
        <p>
            SandBox Version: 2.3.2 - Last Updated: March 07, 2024
         <span>             PWC Version: PWC-4.1.4</span>   
          </p>
        </div>
    
      
      </>
    )
  }

  export default Register;
