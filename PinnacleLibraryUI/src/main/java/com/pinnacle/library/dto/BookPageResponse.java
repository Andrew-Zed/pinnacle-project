package com.pinnacle.library.dto;

import java.util.List;

public class BookPageResponse {
    private List<BookResponse> content;
    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;

    public BookPageResponse() {
    }

    public List<BookResponse> getContent() {
        return content;
    }

    public void setContent(List<BookResponse> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
