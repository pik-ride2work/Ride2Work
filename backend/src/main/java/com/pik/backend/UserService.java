package com.pik.backend;

import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    @Transactional
    void create(Long id, String username, String password);

    User getByUsername(String username);
}
