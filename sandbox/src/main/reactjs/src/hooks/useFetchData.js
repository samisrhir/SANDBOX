import { useState,useEffect } from "react";
const useFetcheData = (url,initialValue) => {
  const [isLoading,setIsLoading] = useState(false);
    const [dataFetch, setDataFetch] = useState(initialValue);

    useEffect(() => {
      const fetchData = async () => {
        setIsLoading(true)
        try {
          
          const token = sessionStorage.getItem("token");
          const response = await fetch(url,{
            headers: {
              Authorization: `Bearer ${token}` 
            }
          });
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          const data = await response.json();
          setDataFetch(data);
          setIsLoading(false);
        } catch (error) {
          console.error("There was a problem fetching "+ url + " :", error);
        }
      };
      fetchData();
    }, [fetch]);
    return {dataFetch,setDataFetch,isLoading,setIsLoading}
}
export default useFetcheData
