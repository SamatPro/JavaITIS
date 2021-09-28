package ru.kpfu.itis.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.form.LoginForm;
import ru.kpfu.itis.form.UserForm;
import ru.kpfu.itis.models.User;
import ru.kpfu.itis.repostories.AuthRepository;
import ru.kpfu.itis.repostories.UsersRepository;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class UsersServicesImpl implements UsersService {

    private UsersRepository usersRepository;
    private AuthRepository authRepository;
    private PasswordEncoder passwordEncoder;

    public UsersServicesImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public UsersServicesImpl(UsersRepository usersRepository, AuthRepository authRepository) {
        this.usersRepository = usersRepository;
        this.authRepository = authRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(UserForm userForm) {

        User user = new User();
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setLogin(userForm.getLogin());

        String passwordHash = passwordEncoder.encode(userForm.getPassword());

        user.setPasswordHash(passwordHash);

        return usersRepository.save(user);
    }

    @Override
    public Cookie signIn(LoginForm loginForm) {

        User user = usersRepository.findByLogin(loginForm.getLogin());

        if (user != null) {
            if (passwordEncoder.matches(loginForm.getPassword(), user.getPasswordHash())) {
                System.out.println("Вход выполнен!");
                String cookieValue = UUID.randomUUID().toString();
                System.out.println(cookieValue);
                Cookie cookie = new Cookie("auth", cookieValue);
                cookie.setMaxAge(10 * 60 * 60);
                return cookie;
            } else {
                System.out.println("Вход не выполнен!");
            }
        }

        return null;
    }
}
