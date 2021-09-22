package ru.kpfu.itis.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kpfu.itis.form.UserForm;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repostories.UsersRepository;

public class UsersServicesImpl implements UsersService {

    private UsersRepository usersRepository;

    public UsersServicesImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public User register(UserForm userForm) {

        User user = new User();
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setLogin(userForm.getLogin());

        String passwordHash = new BCryptPasswordEncoder().encode(userForm.getPassword());

        user.setPasswordHash(passwordHash);

        return usersRepository.save(user);
    }
}
