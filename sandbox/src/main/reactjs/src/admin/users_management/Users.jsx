// Users.js
import React, { useState, useEffect } from "react";
import { Search,ListFilter ,Pencil} from "lucide-react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { MoreHorizontal } from "lucide-react";
import { Link } from "react-router-dom";
import "../../assets/css/usersearch.css";
import { CSVLink } from "react-csv";
import DeleteUserDialog from "./DeleteUserDialog";
import CreateUser from "./CreateUser";
import CustomPagination from "../../components/custom-ui/Pagination";

import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { Tabs, TabsContent } from "@/components/ui/tabs";
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
} from "@/components/ui/table";
import EditUsers from "./EditUsers";
import { ResetIcon } from "@radix-ui/react-icons";

const Users = () => {
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);
  const [pageSize, setPageSize] = useState(4);
  const [showEnabled, setShowEnabled] = useState(true);
  const [showDisabled, setShowDisabled] = useState(true);
  const [totalPages, setTotalPages] = useState(10);
  const [searchCriteria, setSearchCriteria] = useState({
    startDate: "",
    endDate: "",
    phone: "",
    email: "",
    fullName: "",
  });
  const [openEditDialog, setOpenEditDialog] = useState(false);
  const [editUserId, setEditUserId] = useState(null);

  const generateCSVData = () => {
    const csvDataWithTitles = [
      ["ID", "Username", "Full Name", "Email", "Phone", "State", "Created at"],
      ...filteredUsers.map((user) => [
        user.userId,
        user.username,
        user.fullName,
        user.email,
        user.phone,
        user.isActive ? "Active" : "Inactive", // Assuming isActive is a boolean
        user.createdDate,
      ]),
    ];

    const csvRows = csvDataWithTitles.map((row) => row.join(","));

    setCsvData(csvRows);
  };

  useEffect(() => {
    fetchUsers();
  }, [page, pageSize]);

  const fetchUsers = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/console/users?page=${page}&size=${pageSize}`
      );
      if (response.ok) {
        const data = await response.json();
        setUsers(data.content);
        console.log("Fetched users:", data.content);
        setTotalPages(data.totalPages);
      } else {
        console.error("Failed to fetch data:", response.status);
      }
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  const filteredUsers = users.filter((user) => {
    if (showEnabled && showDisabled) {
      return true; // Show all users
    } else if (showEnabled) {
      return user.enabled; // Show only enabled users
    } else if (showDisabled) {
      return !user.enabled; // Show only disabled users
    }
    return false;
  });

  const handlePageChange = async (pageNumber) => {
    if (pageNumber < 0 || pageNumber >= totalPages) {
      return; // Prevent fetching data for invalid page numbers
    }
    setPage(pageNumber);
    try {
      const response = await fetch(
        `http://localhost:8080/console/users?page=${pageNumber}&size=${pageSize}`
      );
      if (!response.ok) {
        throw new Error(`Error fetching data: ${response.status}`);
      }
      const fetchedData = await response.json();
      setUsers(fetchedData.content);
      setTotalPages(fetchedData.totalPages);
      console.log("Fetched users:", fetchedData);
    } catch (error) {
      console.error("Error fetching user data:", error);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setSearchCriteria((prevCriteria) => ({
      ...prevCriteria,
      [name]: value,
    }));
  };

  const handleSearch = async () => {
    try {
      const isCriteriaNotEmpty = Object.values(searchCriteria).some(
        (value) => value !== ""
      );
      if (!isCriteriaNotEmpty) {
        console.error("At least one search criteria must be provided");
        return;
      }

      const response = await fetch(
        `http://localhost:8080/console/users/search?page=${page}&size=${pageSize}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            accept: "application/json",
          },
          body: JSON.stringify(searchCriteria),
        }
      );

      if (response.ok) {
        const searchData = await response.json();
        setUsers(searchData.content);
        console.log("Fetched search results:", searchData);
        setPage(0); // Reset pagination to first page
      } else {
        console.error("Failed to fetch search results");
      }
    } catch (error) {
      console.error("Error searching users:", error);
    }
  };

  const handleReset = () => {
    setSearchCriteria({
      startDate: "",
      endDate: "",
      phone: "",
      email: "",
      fullName: "",
    });
    setPage(0); // Reset pagination to first page
    fetchUsers();
  };

  const handlePageSizeChange = (e) => {
    setPageSize(parseInt(e.target.value));
    setPage(0); // Reset to first page when changing page size
  };

  const handleEditUser = (userId) => {
    console.log("Edit user with ID:", userId);
    setOpenEditDialog(true);
    setEditUserId(userId); // Set the userId state for EditUsers component
  };

  const handledeleteUser = (userId) => {
    console.log("Delete user with ID:", userId);
    fetch(`http://localhost:8080/console/users/${userId}`, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          console.log("User deleted successfully");
          fetchUsers();
        } else {
          console.error("Failed to delete user:", response.status);
        }
      })
      .catch((error) => {
        console.error("Error deleting user:", error);
      });
  };

  return (
    <>
      <div className="flex items-center">
        <Breadcrumb className="hidden md:flex">
          <BreadcrumbList>
            <BreadcrumbItem>
              <BreadcrumbLink asChild>
                <Link to="/console">Dashboard</Link>
              </BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator />
            <BreadcrumbItem>
              <BreadcrumbLink asChild>
                <BreadcrumbPage>Users</BreadcrumbPage>
              </BreadcrumbLink>
            </BreadcrumbItem>
          </BreadcrumbList>
        </Breadcrumb>
      </div>
      <div className="p-4">
        <div className="flex space-x p-3 rounded-md">
          <Input
            type="date"
            className="datepicker shadow-lg dark:shadow-lg dark:shadow-cyan-500/40 "
            name="startDate"
            placeholder="Start Date"
            value={searchCriteria.startDate}
            onChange={handleInputChange}
          />
          <Input
            type="date"
            name="endDate"
            placeholder="End Date"
            value={searchCriteria.endDate}
            onChange={handleInputChange}
            className="datepicker shadow-lg dark:shadow-cyan-500/30 "
          />
          <Input
            type="text"
            name="phone"
            placeholder="Phone"
            value={searchCriteria.phone}
            onChange={handleInputChange}
            className="input shadow-lg dark:shadow-lg dark:shadow-cyan-500/30 "
          />
          <Input
            type="email"
            name="email"
            placeholder="Email"
            value={searchCriteria.email}
            onChange={handleInputChange}
            className="input  shadow-lg dark:shadow-cyan-500/30 "
          />
          <Input
            type="text"
            name="fullName"
            placeholder="Full Name"
            value={searchCriteria.fullName}
            onChange={handleInputChange}
            className="input shadow-lg dark:shadow-cyan-500/30 "
          />
          <Button onClick={handleSearch} className="text-sm p-2">
            <Search className="text-sm" />
          </Button>
          <Button onClick={handleReset} className="p-2">
            <ResetIcon></ResetIcon>
          </Button>
        </div>
        <Tabs defaultValue="all" className="justify-center">
          <div className="flex items-center">
            <div className=" flex items-center gap-8 pt-7 mr-12 pb-3">
              <EditUsers
                userId={editUserId}
                users={users}
                fetch={fetchUsers}
                setUsers={setUsers}
                isOpen={openEditDialog}
                onClose={() => setOpenEditDialog(false)}
              />
              <CreateUser setUsers={setUsers} users={users} totalPages={totalPages} page={page}  />
              <CSVLink data={users} filename={"users.csv"}>
                <Button
                  size="sm"
                  onClick={generateCSVData}
                  variant="outline"
                  className="h-8 gap-2"
                >
                  <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                    Export
                  </span>
                </Button>
              </CSVLink>
            </div>
          </div>
          <TabsContent value="all">
            <Card className="carr">
              <div className="select">
                <CardHeader>
                  <CardTitle>USERS</CardTitle>
                </CardHeader>
                <DropdownMenu>
                  <DropdownMenuTrigger asChild>
                    <Button variant="outline" size="sm" className="h-7 gap-1">
                      <ListFilter className="h-3.5 w-3.5" />
                      <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
                        Filter
                      </span>
                    </Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent align="end">
                    <DropdownMenuLabel>Filter by</DropdownMenuLabel>
                    <DropdownMenuSeparator />
                    <DropdownMenuCheckboxItem checked={showEnabled}
                    onCheckedChange={() => setShowEnabled(!showEnabled)}>
                    Enabled
                    </DropdownMenuCheckboxItem>
                   
                    <DropdownMenuCheckboxItem                   
                    checked={showDisabled}
                    onCheckedChange={() => setShowDisabled(!showDisabled)}>
                    Disabled
                    </DropdownMenuCheckboxItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>
              <select
                value={pageSize}
                onChange={handlePageSizeChange}
                className="pagen"
              >
                <option value={4}>4 per page</option>
                <option value={12}>12 per page</option>
                <option value={24}>24 per page</option>
                <option value={52}>52 per page</option>
              </select>

              <CardContent>
                <Table className="tableuser">
                  <TableBody>
                    <TableRow>
                      <TableHead>ID</TableHead>
                      <TableHead>Username</TableHead>
                      <TableHead>Full Name</TableHead>
                      <TableHead>Email</TableHead>
                      <TableHead>Product version</TableHead>
                      <TableHead>Phone</TableHead>
                      <TableHead>State</TableHead>
                      <TableHead>Created at</TableHead>
                      <TableHead>Actions</TableHead>
                    </TableRow>
                    {filteredUsers.map((user) => (
                      <TableRow
                      
                        key={user.userId}
                      >
                        <TableCell>{user.userId}</TableCell>
                        <TableCell>{user.username}</TableCell>
                        <TableCell>{user.fullName}</TableCell>
                        <TableCell>{user.email}</TableCell>
                        <TableCell>
         {user.products && user.products.length > 0 ? (
          user.products.map((product, index) => (
            <span key={index}>
              {product.productId == 1 ? "V3.5" : "V4"} 
              {index !== user.products.length - 1 && " ,"}
            </span>
          ))
        ) : (
          "No Products"
        )} 
      </TableCell>
                        <TableCell>{user.phone}</TableCell>
                        <TableCell>
                          <Badge
                            variant="outline"
                            className={!user.enabled ? "c bg-red-500 text-white" : "bg-green-600 text-white"}
                            >
                            {user.isActive}
                          </Badge>
                        </TableCell>
                        <TableCell>{user.createdDate}</TableCell>
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
                              <DropdownMenuLabel>Actions:</DropdownMenuLabel>
                              <DropdownMenuLabel
                                onClick={() => handleEditUser(user.userId)}
                                className="cursor-pointer flex justify-center"
                              >
                                  <Pencil size={20} color="#0d6efd" strokeWidth={2} />
                             
                              </DropdownMenuLabel>
                              <DropdownMenuLabel className="cursor-pointer flex justify-center">
                                <DeleteUserDialog
                                  onDelete={handledeleteUser}
                                  userId={user.userId}
                                />
                              </DropdownMenuLabel>
                            </DropdownMenuContent>
                          </DropdownMenu>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </CardContent>
              <CardFooter className="flex ">
                <div className="text-xs text-muted-foreground">
                  <CustomPagination
                    currentPage={page}
                    totalPages={users.totalPages}
                    onPageChange={handlePageChange}
                  />
                </div>
              </CardFooter>
            </Card>
          </TabsContent>
        </Tabs>
      </div>
    </>
  );
};

export default Users;
