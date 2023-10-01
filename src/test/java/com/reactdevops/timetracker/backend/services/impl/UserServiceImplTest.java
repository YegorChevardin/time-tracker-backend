package com.reactdevops.timetracker.backend.services.impl;

import com.reactdevops.timetracker.backend.repository.dao.UserCRUDDAO;
import com.reactdevops.timetracker.backend.repository.entities.UserEntity;
import com.reactdevops.timetracker.backend.service.convertors.DtoEntityObjectConvertor;
import com.reactdevops.timetracker.backend.service.handler.HashHandler;
import com.reactdevops.timetracker.backend.web.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class UserServiceImplTest {

    @InjectMocks
    private UserCRUDDAO userEntityCreateReadUpdateDeleteDAO;

    @InjectMocks
    private DtoEntityObjectConvertor<UserEntity, User> userDtoEntityObjectConvertor;

    @InjectMocks
    private HashHandler passwordHash;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void serviceShouldCreate(){

    }
}
