package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    User getByUsername(String username);
}
