package com.pik.backend;

import com.pik.ride2work.tables.pojos.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    User create(User user);

    @Transactional
    User update(User user, Integer id);

    User getByUsername(String username);
}
