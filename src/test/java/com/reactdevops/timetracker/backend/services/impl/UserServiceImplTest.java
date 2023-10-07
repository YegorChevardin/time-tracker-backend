package com.reactdevops.timetracker.backend.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.UserCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.convertors.DtoEntityObjectConvertor;
import com.reactdevops.timetracker.backend.service.exceptions.CustomWebException;
import com.reactdevops.timetracker.backend.service.exceptions.ObjectExistsException;
import com.reactdevops.timetracker.backend.service.exceptions.ObjectNotFoundException;
import com.reactdevops.timetracker.backend.service.handler.HashHandler;
import com.reactdevops.timetracker.backend.service.services.impl.UserServiceImpl;
import com.reactdevops.timetracker.backend.web.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserCRUDDAO userCRUDDAO;

    @Mock
    private DtoEntityObjectConvertor<UserEntity, User> convertor;

    @Mock
    private HashHandler hashHandler;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser_Success() throws SQLException {
        User user = new User(null, "testuser", "testpassword");
        UserEntity userEntity = new UserEntity(null, "testuser", "hashedPassword");
        when(userCRUDDAO.findByUsername("testuser")).thenReturn(Optional.empty());
        when(hashHandler.hash("testpassword")).thenReturn("hashedPassword");
        when(convertor.dtoToEntity(user)).thenReturn(userEntity);

        assertDoesNotThrow(() -> userService.create(user));

        verify(userCRUDDAO, times(1)).findByUsername("testuser");
        verify(hashHandler, times(1)).hash("testpassword");
        verify(userCRUDDAO, times(1)).create(userEntity);
    }

    @Test
    void testCreateUser_UserAlreadyExists() throws SQLException {
        User user = new User(null, "testuser", "testpassword");
        when(userCRUDDAO.findByUsername("testuser")).thenReturn(Optional.of(new UserEntity(1L, "testuser", "hashedPassword")));
        assertThrows(ObjectExistsException.class, () -> userService.create(user));
        verify(userCRUDDAO, times(1)).findByUsername("testuser");
        verify(hashHandler, never()).hash(anyString());
        verify(userCRUDDAO, never()).create(any(UserEntity.class));
    }

    @Test
    void testCreateUser_SQLException() throws SQLException {
        User user = new User(null, "testuser", "testpassword");
        when(userCRUDDAO.findByUsername("testuser")).thenReturn(Optional.empty());
        when(hashHandler.hash("testpassword")).thenReturn("hashedPassword");
        when(convertor.dtoToEntity(user)).thenReturn(new UserEntity(null, "testuser", "hashedPassword"));
        doThrow(SQLException.class).when(userCRUDDAO).create(any(UserEntity.class));
        assertThrows(CustomWebException.class, () -> userService.create(user));
        verify(userCRUDDAO, times(1)).findByUsername("testuser");
        verify(hashHandler, times(1)).hash("testpassword");
        verify(userCRUDDAO, times(1)).create(any(UserEntity.class));
    }
}
