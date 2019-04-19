package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends GenericService<User> {

    User getByUsername(String username);
}
