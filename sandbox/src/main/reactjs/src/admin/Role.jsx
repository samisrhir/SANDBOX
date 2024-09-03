import React from 'react'
import {
    TabsContent,
} from "@/components/ui/tabs"
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from "@/components/ui/table"
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import {
    PlusCircle,
} from "lucide-react"

const Role = () => {
  return (
    <TabsContent value="Role">
    <Card x-chunk="dashboard-06-chunk-0">
      <CardHeader>
        <CardTitle>Role & Permission</CardTitle>
      </CardHeader>
      <CardContent>
      <div className="flex items-center">
      <div className="ml-auto flex items-center gap-2">
        <Button size="sm" className="h-7 gap-1">
          <PlusCircle className="h-3.5 w-3.5" />
          <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
            Add Role
          </span>
        </Button>
      </div>
    </div>
    <Table>
                    <TableHeader>
                      <TableRow>
                        <TableHead>Role Id</TableHead>
               
                        <TableHead>Role Name</TableHead>
                        <TableHead>Role description</TableHead>
         
                        <TableHead>
                          <span className="sr-only">Actions</span>
                        </TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                     
                    </TableBody>
                  </Table>
      </CardContent>
    </Card>
  </TabsContent>
  )
}

export default Role