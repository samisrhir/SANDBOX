import DeleteProductRelease from './DeleteProductRelease';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
  File,
  MoreHorizontal,
  PlusCircle,
} from "lucide-react";
import { Badge } from "@/components/ui/badge";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Tabs, TabsContent } from "@/components/ui/tabs";
import { Link } from 'react-router-dom';
import CreateProductRelease from './CreateProductRelease';

const ProductReleaseAdmin = () => {
  const [productReleases, setProductReleases] = useState([]);

  useEffect(() => {
    fetchData();
  }, []);

  // Fetch data from the server
  const fetchData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/sandbox-ui/product-releases");
      setProductReleases(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // Handle delete action
  const handleDelete = async (productReleaseId) => {
    try {
      await axios.delete(`http://localhost:8080/sandbox-ui/product-releases/${productReleaseId}`);
      fetchData(); // Fetch the updated data after deletion
    } catch (error) {
      console.error("Error deleting product release:", error);
    }
  };


  return (
    <div className="flex flex-col">
      <header className="sticky top-0 z-30 flex h-14 items-center gap-4 border-b bg-background px-4 sm:static sm:h-auto sm:border-0 sm:bg-transparent sm:px-6">
        <Breadcrumb className="hidden md:flex">
          <BreadcrumbList>
            <BreadcrumbItem>
              <BreadcrumbLink asChild>
                <Link to='/console'>Dashboard</Link>
              </BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator />
            <BreadcrumbItem>
              <BreadcrumbLink>Product Release</BreadcrumbLink>
            </BreadcrumbItem>
          </BreadcrumbList>
        </Breadcrumb>
      </header>
      <main className="grid flex-1 items-start gap-4 p-4 sm:px-6 sm:py-0 md:gap-8">
        <Tabs defaultValue="all">
          <div className="flex items-center">
            <div className="ml-auto flex items-center gap-2">
              <Button size="sm" variant="outline" className="h-7 gap-1">
                <File className="h-3.5 w-3.5" />
                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                  Export
                </span>
              </Button>
              <CreateProductRelease fetchData={fetchData} setProductRelease={setProductReleases} productReleases={productReleases} />
            </div>
          </div>
          <TabsContent value="all" className="mt-10">
            <Card x-chunk="dashboard-06-chunk-0">
              <CardHeader>
                <CardTitle>Product Releases</CardTitle>
                <CardDescription>
                  Manage your product releases.
                </CardDescription>
              </CardHeader>
              <CardContent>
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead>Product Release Id</TableHead>
                      <TableHead>Product Release Name</TableHead>
                      <TableHead>Product Release Description</TableHead>
                      <TableHead className="hidden md:table-cell">Visibility</TableHead>
                      <TableHead className="hidden md:table-cell">Enabled</TableHead>
                      <TableHead>
                        <span className="sr-only">Actions</span>
                      </TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {productReleases && productReleases.map(productRelease => (
                      <TableRow key={productRelease.productReleaseId}>
                        <TableCell className="font-medium">{productRelease.productReleaseId}</TableCell>
                        <TableCell>{productRelease.name}</TableCell>
                        <TableCell>{productRelease.description}</TableCell>
                        <TableCell className="hidden md:table-cell">
                          {productRelease.visible ? <Badge variant="outline">Visible</Badge> : <Badge variant="destructive">Invisible</Badge>}
                        </TableCell>
                        <TableCell className="hidden md:table-cell">
                          {productRelease.enabled ? <Badge variant="outline">Enabled</Badge> : <Badge variant="destructive">Disabled</Badge>}
                        </TableCell>
                        <TableCell>
                          <DropdownMenu>
                            <DropdownMenuTrigger asChild>
                              <Button aria-haspopup="true" size="icon" variant="ghost">
                                <MoreHorizontal className="h-4 w-4" />
                                <span className="sr-only">Toggle menu</span>
                              </Button>
                            </DropdownMenuTrigger>
                            <DropdownMenuContent align="end">
                              <DropdownMenuLabel>Actions</DropdownMenuLabel>
                              <DropdownMenuItem>Edit</DropdownMenuItem>
                              <DropdownMenuItem asChild>
                                <DeleteProductRelease refreshData={fetchData} onDelete={handleDelete} productReleaseId={productRelease.productReleaseId} />
                              </DropdownMenuItem>
                            </DropdownMenuContent>
                          </DropdownMenu>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
              <CardFooter>
                <div className="text-xs text-muted-foreground">Product Releases</div>
              </CardFooter>
            </Card>
          </TabsContent>
        </Tabs>
      </main>
    </div>
  );
}

export default ProductReleaseAdmin;
