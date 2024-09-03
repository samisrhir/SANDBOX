import {
    Pagination,
    PaginationContent,
    PaginationEllipsis,
    PaginationItem,
    PaginationLink,
    PaginationNext,
    PaginationPrevious,
  } from "@/components/ui/pagination"
  
export default function CustomPagination({ currentPage, totalPages, onPageChange }) {
    const isFirstPage = currentPage === 0;
    const isLastPage = currentPage === totalPages-1;
  
    const handlePreviousPage = () => {
      if (!isFirstPage) {
        onPageChange(currentPage - 1);
      }
    };
  
    const handleNextPage = () => {
      if (!isLastPage) {
        onPageChange(currentPage + 1);
      }
    };
  
    return (
      <Pagination>
        <PaginationContent>
          <PaginationItem>
            <PaginationPrevious onClick={handlePreviousPage} disabled={isFirstPage} />
          </PaginationItem>
          <PaginationItem >
            <PaginationNext onClick={handleNextPage} disabled={isLastPage}  />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    );
  }
  
  