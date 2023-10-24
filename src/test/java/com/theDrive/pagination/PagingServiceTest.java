package com.theDrive.pagination;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PagingServiceTest {

    @Mock
    private PagingService pagingService;

    private int initialPageNumber = 5;
    private int initialPageSize = 5;
    private String defaultSortField = "id";
    private String defaultSortDir = "asc";

    @Test
    void getInitialPageSize() {

        //given
        Mockito.when(pagingService.getInitialPageSize()).thenReturn(initialPageSize);
        //when
        int result = pagingService.getInitialPageSize();
        //then
        Assertions.assertEquals(initialPageSize, result);
    }

    @Test
    void getInitialPage() {
        //given
        Mockito.when(pagingService.getInitialPage()).thenReturn(initialPageNumber);
        //when
        int result = pagingService.getInitialPage();
        //then
        Assertions.assertEquals(initialPageNumber, result);
    }

    @Test
    void getDefaultSortField() {
        //given
        Mockito.when(pagingService.getDefaultSortField()).thenReturn(defaultSortField);
        //when
        String result = pagingService.getDefaultSortField();
        //then
        Assertions.assertEquals(defaultSortField, result);
    }

    @Test
    void getDefaultSortDir() {
        //given
        Mockito.when(pagingService.getDefaultSortDir()).thenReturn(defaultSortDir);
        //when
        String result = pagingService.getDefaultSortDir();
        //then
        Assertions.assertEquals(defaultSortDir, result);
    }
}