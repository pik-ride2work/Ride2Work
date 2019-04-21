package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.User;
import java.util.concurrent.Future;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

  Future<User> create(User user);

  Future<User> update(User user);

  Future<User> getByUsername(String username);
}
