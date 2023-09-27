package com.reactdevops.timetracker.backend.service.services;

import com.reactdevops.timetracker.backend.web.dto.User;

/**
 * Interface for user actions
 * @author yegorchevardin
 * @version 0.0.1
 */
public interface UserService extends CreateReadDeleteService<User> {
    User findByUsername(String username);
}
