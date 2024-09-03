import React, { useEffect, useState } from "react";
import axios from "axios";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { toast } from "@/components/ui/use-toast";

const FormSchema = z.object({
  items: z.array(z.string()).refine((value) => value.some((item) => item), {
    message: "You have to select at least one item.",
  }),
});

const ProductChecklist = ({ userId }) => {
  const [allProducts, setAllProducts] = useState([]);
  const [userProducts, setUserProducts] = useState([]);
  const [isLoading, setIsLoading] = useState(true);

  const form = useForm({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      items: [],
    },
  });

  useEffect(() => {
    // Fetch all products
    axios.get("http://localhost:8080/sandbox-ui/products/")
      .then(response => { 
        setAllProducts(response.data);
        // Fetch user-owned products
        return axios.get(`http://localhost:8080/sandbox-ui/users/${userId}/products`);
      })
      .then(response => {
        setUserProducts(response.data.map(product => product.productId));
        setIsLoading(false);
      })
      .catch(error => {
        console.error('Error fetching products and user products:', error);
      });
  }, [userId]);

  const handleCheckboxChange = (productId) => {
    setUserProducts(prevState => {
      if (prevState.includes(productId)) {
        return prevState.filter(id => id !== productId);
      } else {
        return [...prevState, productId];
      }
    });
  };

  function onSubmit(data) {
    toast({
      title: "You submitted the following values:",
      description: (
        <pre className="mt-2 w-[340px] rounded-md bg-slate-950 p-4">
          <code className="text-white">{JSON.stringify(data, null, 2)}</code>
        </pre>
      ),
    });
  }

  return (
    <div className="border border-gray-300 p-4 rounded-md">
      <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
          <FormField
            control={form.control}
            name="items"
            render={() => (
              <FormItem>
                <div className="mb-4">
                  <FormLabel className="text-base">User Products</FormLabel>
                  <FormDescription>
                    Select the products owned by the user.
                  </FormDescription>
                </div>
                {isLoading ? (
                  <div>Loading...</div>
                ) : (
                  allProducts.map((product) => (
                    <FormItem
                      key={product.productId}
                      className="flex flex-row items-start space-x-3 space-y-0"
                    >
                      <FormControl>
                        <Checkbox
                          checked={userProducts.includes(product.productId)}
                          onCheckedChange={() => handleCheckboxChange(product.productId)}
                        />
                      </FormControl>
                      <FormLabel className="text-sm font-normal">
                        {product.name}
                      </FormLabel>
                    </FormItem>
                  ))
                )}
                <FormMessage />
              </FormItem>
            )}
          />
        </form>
      </Form>
    </div>
  );
};

export default ProductChecklist;
