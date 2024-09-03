import React from "react";
import { File, MoreHorizontal, Pencil } from "lucide-react";
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
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Tabs, TabsContent } from "@/components/ui/tabs";
import { Link } from "react-router-dom";
import CreateProduct from "./CreateProduct";
import EditProduct from "./EditProduct";
import DeleteProduct from "./DeleteProduct";

const ProductsAdmin = () => {
  const [products, setProducts] = React.useState([]);
  const [selectedProduct, setSelectedProduct] = React.useState(null);
  const [editOpen, setEditOpen] = React.useState(false);

  const handleDelete = async (productId) => {
    try {
      await fetch(`http://localhost:8080/sandbox-ui/products/${productId}`, {
        method: "DELETE",
      });
      console.log("Product deleted successfully",productId);
    } catch (error) {
      console.error("Error deleting product:", error);
    }
  };

  React.useEffect(() => {
    fetch("http://localhost:8080/sandbox-ui/products/")
      .then((response) => response.json())
      .then((data) => {
        setProducts(data);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, []);

  const handleEditClick = (product) => {
    setSelectedProduct(product);
    setEditOpen(true);
  };

  return (
    <>
      <div className="flex flex-col">
        <header className="sticky top-0 z-30 flex h-14 items-center gap-4 border-b bg-background px-4 sm:static sm:h-auto sm:border-0 sm:bg-transparent sm:px-6">
          <Breadcrumb className="hidden md:flex">
            <BreadcrumbList>
              <BreadcrumbItem>
                <BreadcrumbLink asChild>
                  <Link to="/console">Dashboard</Link>
                </BreadcrumbLink>
              </BreadcrumbItem>
              <BreadcrumbSeparator />
              <BreadcrumbItem>
                <BreadcrumbLink>Products</BreadcrumbLink>
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
                <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                  <CreateProduct setProducts={setProducts} products={products} />
                </span>
              </div>
            </div>
            <TabsContent value="all" className="mt-10">
              <Card x-chunk="dashboard-06-chunk-0">
                <CardHeader>
                  <CardTitle>Products</CardTitle>
                  <CardDescription>Manage your products.</CardDescription>
                </CardHeader>
                <CardContent>
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Product Id</TableHead>
                        <TableHead>Product Name</TableHead>
                        <TableHead>Product Description</TableHead>
                        <TableHead className="hidden md:table-cell">
                          Visibility
                        </TableHead>
                        <TableHead className="hidden md:table-cell">
                          Enabled
                        </TableHead>
                        <TableHead>
                          <span className="sr-only">Actions</span>
                        </TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {products &&
                        products.map((product) => (
                          <TableRow key={product.id}>
                            <TableCell className="font-medium">
                              {product.productId}
                            </TableCell>
                            <TableCell>{product.name}</TableCell>
                            <TableCell>{product.description}</TableCell>
                            <TableCell className="hidden md:table-cell">
                              {product.visible ? (
                                <Badge className="bg-green-500 text-white">
                                  Visible
                                </Badge>
                              ) : (
                                <Badge variant="destructive">Invisible</Badge>
                              )}
                            </TableCell>
                            <TableCell className="hidden md:table-cell">
                              {product.enabled ? (
                                <Badge className="bg-green-500 text-white">
                                  Enabled
                                </Badge>
                              ) : (
                                <Badge variant="destructive">Disabled</Badge>
                              )}
                            </TableCell>
                            <TableCell>
                              <DropdownMenu>
                                <DropdownMenuTrigger asChild>
                                  <Button aria-haspopup="true" size="icon" variant="ghost">
                                    <MoreHorizontal className="h-4 w-4" />
                                    <span className="sr-only">Toggle menu</span>
                                  </Button>
                                </DropdownMenuTrigger>
                                <DropdownMenuContent >
                                  <DropdownMenuLabel className="text-center" >Actions</DropdownMenuLabel>
                                  <DropdownMenuItem className="cursor-pointer flex justify-center" onClick={() => handleEditClick(product)}>
                                    <Pencil size={20} color="#0d6efd" strokeWidth={2} />
                                  </DropdownMenuItem>
                                  <DropdownMenuLabel className="cursor-pointer flex justify-center">
                                    <DeleteProduct onDelete={handleDelete} productId={product.productId} />
                                  </DropdownMenuLabel>
                                </DropdownMenuContent>
                              </DropdownMenu>
                            </TableCell>
                          </TableRow>
                        ))}
                    </TableBody>
                  </Table>
                </CardContent>
                <CardFooter>
                  <div className="text-xs text-muted-foreground">products</div>
                </CardFooter>
              </Card>
            </TabsContent>
          </Tabs>
        </main>
      </div>

      {selectedProduct && (
        <EditProduct
          product={selectedProduct}
          setProducts={setProducts}
          open={editOpen}
          onOpenChange={setEditOpen}
        />
      )}
    </>
  );
};

export default ProductsAdmin;
