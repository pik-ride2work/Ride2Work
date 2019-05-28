package com.pik.backend.services;

import com.google.common.collect.Lists;
import com.pik.ride2work.tables.pojos.User;
import java.util.ArrayList;
import java.util.List;

public class NoCredUserView {

    private NoCredUserView() {
    }

    static User apply(User user) {
        User view = new User();
        view.setId(user.getId());
        view.setUsername(user.getUsername());
        view.setFirstName(user.getFirstName());
        view.setLastName(user.getLastName());
        view.setEmail(user.getEmail());
        return view;
    }

    static List<User> apply(List<User> users){
        ArrayList<User> result = Lists.newArrayList();
        for (User user : users) {
            result.add(apply(user));
        }
        return result;
    }
}
