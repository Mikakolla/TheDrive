package com.theDrive.pagination;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class PagingService implements IPagingService{

    @Value("${default.initialPageNumber}")
    private int initialPageNumber;

    @Value("${default.initialPageSize}")
    private int initialPageSize;

    @Value("${default.sortField}")
    private String defaultSortField;

    @Value("${default.sortDir}")
    private String defaultSortDir;

    @Override
    public int getInitialPageSize() {
        return initialPageSize;
    }

    @Override
    public int getInitialPage() {
        return initialPageNumber;
    }

    @Override
    public String getDefaultSortField() {
        return defaultSortField;
    }

    @Override
    public String getDefaultSortDir() {
        return defaultSortDir;
    }

    @Override
    public HashMap<String, Object> preparePagingParams(int currentPage, int totalPages, long totalItems,
                                                       String sortField, String sortDir) {


        HashMap<String, Object> data = new HashMap<>();

        data.put("currentPage", currentPage + 1);
        data.put("totalPages", totalPages);
        data.put("totalItems", totalItems);
        data.put("sortField", sortField);
        data.put("sortDir", sortDir);
        data.put("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        data.put("navPages", preparePagingNumbersToView(totalPages, currentPage));

        return data;
    }

    @Override
    public int validatePageNo(Optional<Integer> pageNo) {
        return pageNo.filter(p -> p >= 1)
                .map(p -> p - 1)
                .orElse(getInitialPage());
    }

    @Override
    public List<Object> preparePagingNumbersToView(int totalPages, int currentPage) {

        List<Object> navPages = new ArrayList<>();

        if (totalPages <= 9) {
            for (int i = 1; i <= totalPages; i++) {
                navPages.add(i);
            }
        } else {
            navPages.add(1);
            if (currentPage - 2 > 1) navPages.add("...");

            for (int i = -1; i <= 3; i++) {
                if (currentPage + i < 2) continue;
                else if (currentPage + i > totalPages - 1) continue;
                else navPages.add(currentPage + i);
            }

            if (currentPage + 4 < totalPages - 1) navPages.add("...");
            navPages.add(totalPages);
        }

        return navPages;
    }
}
