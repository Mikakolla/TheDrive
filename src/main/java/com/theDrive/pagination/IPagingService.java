package com.theDrive.pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface IPagingService {
    int getInitialPageSize();
    int getInitialPage();
    String getDefaultSortField();
    String getDefaultSortDir();

    HashMap<String, Object> preparePagingParams(int currentPage, int totalPages, long totalItems, String sortField, String sortDir);

    int validatePageNo(Optional<Integer> pageNo);

    List<Object> preparePagingNumbersToView(int totalPages, int currentPage);
}
