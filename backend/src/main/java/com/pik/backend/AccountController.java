package com.pik.backend;

import com.pik.backend.service_impl.DefaultUserService;
import com.pik.ride2work.tables.pojos.User;
import com.pik.ride2work.tables.records.UserRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AccountController {
    private final DefaultUserService userService;

    AccountController(DefaultUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }
}
