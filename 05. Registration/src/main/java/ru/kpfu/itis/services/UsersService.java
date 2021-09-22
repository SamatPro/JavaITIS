package ru.kpfu.itis.services;

import ru.kpfu.itis.form.UserForm;
import ru.kpfu.itis.models.User;

public interface UsersService {
    User register(UserForm userForm);
}
