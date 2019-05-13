package com.pik.backend.services;

import com.pik.ride2work.tables.pojos.User;

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
}
