import React, { useEffect } from "react";
import { Button } from "@/components/ui/button";
import axios from "axios";
import ProductChecklist from "../users_management/ProductCheckList"; // Assuming the component is in the same directory
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { z } from "zod";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Switch } from "@/components/ui/switch";
 
const formSchema = z.object({
  username: z.string().min(2, {
    message: "Username must be at least 2 characters.",
  }),
  password: z.string().min(3),
  email: z.string().email({
    message: "Invalid email format.",
  }),
  phone: z.string().min(2).max(10),
  enabled: z.boolean().default(false),
  fullName: z.string().min(3),
});
 
export default function EditUsers({ userId, isOpen, onClose,fetch}) {
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      username: "",
      fullName: "",
      email: "",
      phone: "",
      enabled: false,
    },
  });
 
 
  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/console/users/${userId}`);
        if (response.status === 200) {
          const userData = response.data;
          form.setValue("username", userData.username);
          form.setValue("fullName", userData.fullName);
          form.setValue("email", userData.email);
          form.setValue("phone", userData.phone);
          form.setValue("enabled", userData.enabled);
        } else {
          console.error("Failed to fetch user data:", response.status);
        }
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    };
 
    if (isOpen && userId) {
      fetchUser();
    }
  }, [isOpen, userId, form]);
 
  async function onSubmit(data) {
    try {
      console.log(data);
      const response = await axios.put(
        `http://localhost:8080/console/users/${userId}`,
        JSON.stringify(data),
        {
          headers: {
            "Content-Type": "application/json",
            accept: "application/json",
          },
        }
      );
     
      if (response.status === 200) {
        console.log("User updated successfully:", response.data);
      } else {
        console.error("Failed to update user:", response.status);
      }
     
      form.reset();
      onClose();
      fetch();
    } catch (error) {
      console.error("Error updating user:", error);
    }
  }
 
  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogTrigger asChild>
 
      </DialogTrigger>
      <DialogContent className="sm:max-w-[485px]">
        <Form {...form}>
          <form  onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
            <DialogHeader>
              <DialogTitle>Edit User</DialogTitle>
              <DialogDescription>
                Update the user account details here. Click save when you're
                done.
              </DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4 min-w-12">
              <FormField
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Username</FormLabel>
                    <FormControl>
                      <Input placeholder="Username" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="fullName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Full Name</FormLabel>
                    <FormControl>
                      <Input placeholder="Full Name" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Email</FormLabel>
                    <FormControl>
                      <Input  placeholder="Email" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="phone"
               
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Phone</FormLabel>
                    <FormControl>
                      <Input placeholder="Phone" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="enabled"
                render={({ field }) => (
                  <FormItem className="flex flex-row items-center justify-between rounded-lg border p-4">
                  <div className="space-y-0.5">
                    <FormLabel className="text-base">
                      is Enable
                    </FormLabel>
                    <FormDescription>
                      enable user account.
                    </FormDescription>
                  </div>
                  <FormControl>
                    <Switch
                      checked={field.value}
                      onCheckedChange={field.onChange}
                    />
                  </FormControl>
                </FormItem>
                )}
              />
            </div>
            <DialogFooter>
              <Button type="submit" onClick={() => onSubmit(form.getValues())}>Save</Button>
            </DialogFooter>
          </form>
        </Form>
        {/* <ProductChecklist userId={userId} /> */}
      </DialogContent>
    </Dialog>
  );
}