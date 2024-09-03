import React from 'react'
import {
  File,
  ListFilter,
  MoreHorizontal,
  PlusCircle,
} from "lucide-react"
import { Badge } from "@/components/ui/badge"
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb"
import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Input } from "@/components/ui/input"
import { Sheet, SheetContent, SheetTrigger } from "@/components/ui/sheet"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import {
  Tabs,
  TabsContent,
} from "@/components/ui/tabs"

import { Link } from 'react-router-dom'


const ModulesAdmin = () => {

  const [modules, setModules] = React.useState([]);

  React.useEffect(() => {
    fetch('http://localhost:8080/console/modules')
      .then(response => response.json())
      .then(data => {
        setModules(data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []); 

  return (
    <>
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
                <BreadcrumbLink >
                  Module
                </BreadcrumbLink>
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
                <Button size="sm" className="h-7 gap-1">
                  <PlusCircle className="h-3.5 w-3.5" />
                  <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                    Add Module
                  </span>
                </Button>
              </div>
            </div>
            <TabsContent value="all" className="mt-10">
              <Card x-chunk="dashboard-06-chunk-0">
                <CardHeader>
                  <CardTitle>Modules</CardTitle>
                  <CardDescription>
                    Manage your Modules.
                  </CardDescription>
                </CardHeader>
                <CardContent>
                  <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Module Id</TableHead>
                        <TableHead className="hidden w-[100px] sm:table-cell">
                          <span className="sr-only">Image</span>
                        </TableHead>
                        <TableHead>Module Name</TableHead>
                        <TableHead>solution name </TableHead>
                        <TableHead>solution Id </TableHead>
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
                      {modules && modules.map(module => (
                         <TableRow>
                         <TableCell className="font-medium">
                           {module.moduleId}
                         </TableCell>
                         <TableCell className="hidden sm:table-cell">
                          <img
                            alt="module image"
                            className="aspect-square rounded-md object-cover"
                            height="64"
                            src={module.imagePath}
                            width="64"
                          />
                        </TableCell>
                         <TableCell>
                           {module.name}
                         </TableCell>
                         <TableCell>{module.solution.name}</TableCell>
                         <TableCell>{module.solution.solutionId}</TableCell>
                         <TableCell className="hidden md:table-cell">
                           {module.visible ?   <Badge variant="outline">visibile</Badge>  : 
                             <Badge variant="destructive">invisibile</Badge>}
                         </TableCell>
                         <TableCell className="hidden md:table-cell">
                         {module.enabled ?   <Badge variant="outline">enabled</Badge>  : 
                             <Badge variant="destructive">disabled</Badge>}
                         </TableCell>
                         <TableCell>
                           <DropdownMenu>
                             <DropdownMenuTrigger asChild>
                               <Button
                                 aria-haspopup="true"
                                 size="icon"
                                 variant="ghost"
                               >
                                 <MoreHorizontal className="h-4 w-4" />
                                 <span className="sr-only">Toggle menu</span>
                               </Button>
                             </DropdownMenuTrigger>
                             <DropdownMenuContent align="end">
                               <DropdownMenuLabel>Actions</DropdownMenuLabel>
                               <DropdownMenuItem>Edit</DropdownMenuItem>
                               <DropdownMenuItem>Delete</DropdownMenuItem>
                             </DropdownMenuContent>
                           </DropdownMenu>
                         </TableCell>
                       </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </CardContent>
                <CardFooter>
                  <div className="text-xs text-muted-foreground">
                    Modules
                  </div>
                </CardFooter>
              </Card>
            </TabsContent>
          </Tabs>
        </main>
      </div>
    </>
  )
}

export default ModulesAdmin