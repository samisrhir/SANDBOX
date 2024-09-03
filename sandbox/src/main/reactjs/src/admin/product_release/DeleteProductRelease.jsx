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
  } from "@/components/ui/alert-dialog";
  import { Trash2 } from "lucide-react";
  import React, { useRef } from "react";
  
  export default function DeleteProductRelease({ onDelete, productReleaseId, refreshData }) {
    const cancelRef = useRef(null);
  
    // Handle the delete action and refresh the data
    const handleDelete = () => {
      onDelete(productReleaseId);
      refreshData(); // Call the function to refresh data
    };
  
    return (
      <AlertDialog>
        <AlertDialogTrigger asChild>
          <div>
            <Trash2 size={20} color="#dc3545" strokeWidth={2} />
          </div>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you sure you want to delete this Release?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone. This will permanently delete the product release and remove its data from the database.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel ref={cancelRef}>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={handleDelete}>Delete</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    );
  }
  