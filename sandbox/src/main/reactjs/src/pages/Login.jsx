  import React,{useState} from 'react';
  import { useAuth } from '../hooks/useAuth';
  import { useNavigate,useLocation } from 'react-router-dom';
  import { zodResolver } from "@hookform/resolvers/zod"
  import { useForm } from "react-hook-form"
  import { z } from "zod"

  import { Button } from "@/components/ui/button"
  import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
  } from "@/components/ui/form"
  import { Input } from "@/components/ui/input"
  import "../assets/css/login.css";


  
  const formSchema = z.object({
    username: z.string().min(1, {
      message: "Username or Email is required",
    }).trim(),
    password : z.string().min(1, {
      message: "password is required",
      
   })
  })

  function Login() {
    const auth = useAuth();
    const navigate = useNavigate();
    const location = useLocation();
    const from = location.state?.from?.pathname || "/products";
    const [loginError, setloginError] = useState('');

    const form = useForm({
      resolver: zodResolver(formSchema),
      defaultValues: {
          username: "",
          password : "",
        },
    });
 
    async function onSubmit(data) {
      try {
        const response = await fetch('http://localhost:8080/auth/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(data)
        });
        
        if (!response.ok) {
          throw new Error('Login failed: Server error');
        }
        
        const responseData = await response.json();
        if (responseData.error) {
          throw new Error('' + responseData.error);
        }
    
        auth.login(responseData.token);
        navigate(from, { replace: true });
      } catch (error) {
        setloginError(''+ error.message);
      }
    }
    
    
    return (
      <>
        <div className="registerr">
          <Form className="form" {...form}>
          

          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8 form">
          <div className="input-group">
            <FormField
              control={form.control}
              name="username"
              render={({ field }) => (
                <FormItem >
            <div class="flex items-start justify-center">
<p className='errorms' style={{fontSize:'11px'}}>{loginError}</p></div>
                  <FormLabel >Email or Username</FormLabel>
                  <FormControl>
                    <Input placeholder="" {...field} className="input"/>
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            </div>
            <div className="input-group">
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Password</FormLabel>
                  <FormControl>
                    <Input  {...field} className="input" type="password"/>
                  </FormControl>
                  <FormMessage />
                  
                </FormItem>
                
              )}
            />
            </div>
            <Button type="submit" className="submit">Login</Button>
            
              <p className="signin">
                
              <span>Lost your Password? </span>
              <br />
              <a href="#">Recover it easily</a>
            </p>
            
          </form>
        </Form>
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

  export default Login;
