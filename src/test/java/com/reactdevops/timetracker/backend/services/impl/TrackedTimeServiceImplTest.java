package com.reactdevops.timetracker.backend.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.TimeTrackerCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.TrackedTimeEntity;
import com.reactdevops.timetracker.backend.service.convertors.impl.TrackedTimeEntityDtoObjectConvertor;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.services.impl.TrackedTimeServiceImpl;
import com.reactdevops.timetracker.backend.web.dto.TrackedTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class TrackedTimeServiceImplTest {

    @InjectMocks
    private TrackedTimeServiceImpl trackedTimeService;

    @Mock
    private TimeTrackerCRUDDAO timeEntityCreateReadDeleteDAO;

    @Mock
    private TrackedTimeEntityDtoObjectConvertor timeConvertor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void serviceShouldCreate() throws SQLException {
        TrackedTime trackedTime = new TrackedTime(1L, "Description", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), null);
        TrackedTimeEntity entity = new TrackedTimeEntity(1L, "Description", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), null);

        when(timeConvertor.dtoToEntity(trackedTime)).thenReturn(entity);
        doNothing().when(timeEntityCreateReadDeleteDAO).create(entity);

        assertDoesNotThrow(() -> trackedTimeService.create(trackedTime));
    }

    @Test
    public void serviceShouldRead() throws SQLException {
        Long id = 1L;
        TrackedTimeEntity entity = new TrackedTimeEntity(1L, "Description", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), null);
        TrackedTime trackedTime = new TrackedTime(1L, "Description", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), null);

        when(timeEntityCreateReadDeleteDAO.read(id)).thenReturn(Optional.of(entity));
        when(timeConvertor.entityToDto(entity)).thenReturn(trackedTime);

        assertEquals(trackedTime, trackedTimeService.read(id));
    }

    @Test
    public void serviceShouldReadAll() throws SQLException {
        List<TrackedTimeEntity> entities = new ArrayList<>();
        List<TrackedTime> trackedTimes = new ArrayList<>();

        when(timeEntityCreateReadDeleteDAO.readAll()).thenReturn(entities);
        when(timeConvertor.listEntityToListDto(entities)).thenReturn(trackedTimes);

        assertEquals(trackedTimes, trackedTimeService.readAll());
    }

    @Test
    public void serviceShouldDeleteById() {
        Long id = 1L;
        assertDoesNotThrow(() -> trackedTimeService.deleteById(id));
    }

    /*@Test
    public void serviceShouldReadAllByUserId() throws SQLException {
        Long userId = 1L;
        List<TrackedTimeEntity> entities = new ArrayList<>();
        List<TrackedTime> trackedTimes = new ArrayList<>();

        when(timeEntityCreateReadDeleteDAO.readAllByUserId(userId)).thenReturn(entities);
        when(timeConvertor.listEntityToListDto(entities)).thenReturn(trackedTimes);

        assertEquals(trackedTimes, trackedTimeService.readAllByUserId(userId));
    }*/

    @Test
    public void testExceptionHandling() throws SQLException {
        Long id = 1L;
        when(timeEntityCreateReadDeleteDAO.read(id)).thenThrow(new SQLException("Test Exception"));
        assertThrows(CustomWebException.class, () -> trackedTimeService.read(id));
    }
}