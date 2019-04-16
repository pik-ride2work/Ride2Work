package com.pik.backend;

import static com.pik.ride2work.Tables.USER;

import com.pik.ride2work.Tables;
import com.pik.ride2work.tables.daos.UserDao;
import com.pik.ride2work.tables.pojos.User;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
   //do skonfigurowania - autowired

   AccountController(){

   }

   @GetMapping("/users/{username}")
   public User getUserByUsername(@PathVariable String username){
    return null;
   }
}
