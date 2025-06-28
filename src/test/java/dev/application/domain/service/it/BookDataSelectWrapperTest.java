package dev.application.domain.service.it;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.application.db.BookDataSelectWrapper;
import dev.application.db.SqlMainLogic;
import dev.application.entity.BookDataSelectEntity;

@ExtendWith(MockitoExtension.class)
public class BookDataSelectWrapperTest {

    @Mock
    private SqlMainLogic select;

    @InjectMocks
    private BookDataSelectWrapper bookDataSelectService;

    @Test
    void testSelectResultMultipleList_NormalCase() throws Exception {
    	List<List<String>> mockSelectData1 = new ArrayList<>();
    	mockSelectData1.add(Arrays.asList("1", "1001", "A", "25:10'", "1", "TeamA", "1", "2", "TeamB", "2"));

        List<List<String>> mockSelectData2 = new ArrayList<>();
        mockSelectData2.add(Arrays.asList("1", "1001", "A", "25:20", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "11", "3", "2", "4", "4", "7", "4", "6", "5", "3"));
        mockSelectData2.add(Arrays.asList("2", "1002", "B", "45:10", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "4", "4", "7", "5", "6", "7", "3"));
        mockSelectData2.add(Arrays.asList("3", "1003", "B", "74:30", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "7", "4", "7", "5", "6", "7", "3"));
        when(select.executeSelect(any(), anyString(), any(String[].class), any(), any(), any())).thenReturn(mockSelectData1).thenReturn(mockSelectData2);

        List<BookDataSelectEntity> result = bookDataSelectService.executeSelect(1, null, false);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("25'", result.get(0).getTimes());
        assertEquals("45:10", result.get(1).getTimes());
    }

    @Test
    void testSelectResultMultipleList_EmptyResult() throws Exception {
    	List<List<String>> mockSelectData1 = new ArrayList<>();
    	mockSelectData1.add(Arrays.asList("1", "1001", "A", "25'", "1", "TeamA", "1", "2", "TeamB", "2"));

        List<List<String>> mockSelectData2 = new ArrayList<>();
        mockSelectData2.add(Arrays.asList("1", "1001", "A", "25'", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "11", "3", "2", "4", "4", "7", "4", "6", "5", "3"));
        mockSelectData2.add(Arrays.asList("2", "1002", "B", "45+'", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "4", "4", "7", "5", "6", "7", "3"));
        mockSelectData2.add(Arrays.asList("3", "1003", "B", "79'", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "7", "4", "7", "5", "6", "7", "3"));
        when(select.executeSelect(any(), anyString(), any(String[].class), any(), any(), any())).thenReturn(mockSelectData1).thenReturn(mockSelectData2);

        List<BookDataSelectEntity> result = bookDataSelectService.executeSelect(1, null, false);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("25'", result.get(0).getTimes());
        assertEquals("45:10", result.get(1).getTimes());
    }

    @Test
    void testSelectResultMultipleList_ExceptionThrown() throws Exception {
    	List<List<String>> mockSelectData1 = new ArrayList<>();
    	mockSelectData1.add(Arrays.asList("1", "1001", "A", "25'", "1", "TeamA", "1", "2", "TeamB", "2"));

        List<List<String>> mockSelectData2 = new ArrayList<>();
        mockSelectData2.add(Arrays.asList("1", "1001", "A", "31:34", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "11", "3", "2", "4", "4", "7", "4", "6", "5", "3"));
        mockSelectData2.add(Arrays.asList("2", "1002", "B", "45+8", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "4", "4", "7", "5", "6", "7", "3"));
        mockSelectData2.add(Arrays.asList("3", "1003", "B", "45+9", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "7", "4", "7", "6", "6", "7", "3"));
        mockSelectData2.add(Arrays.asList("4", "1004", "B", "ハーフタイム", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "7", "4", "7", "6", "6", "7", "3"));
        mockSelectData2.add(Arrays.asList("5", "1005", "B", "75:12", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "7", "4", "7", "6", "6", "7", "3"));
        mockSelectData2.add(Arrays.asList("6", "1006", "B", "82:34", "1", "TeamA", "1", "2", "TeamB", "2", "0.35", "0.24", "45%", "39%", "14", "3", "2", "7", "4", "7", "6", "6", "7", "6"));
        when(select.executeSelect(any(), anyString(), any(String[].class), any(), any(), any())).thenReturn(mockSelectData1).thenReturn(mockSelectData2);

        List<BookDataSelectEntity> result = bookDataSelectService.executeSelect(1, null, false);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("25'", result.get(0).getTimes());
        assertEquals("45:10", result.get(1).getTimes());
    }

}