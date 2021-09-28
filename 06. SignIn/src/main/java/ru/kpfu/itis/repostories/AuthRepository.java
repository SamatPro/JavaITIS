package ru.kpfu.itis.repostories;

import ru.kpfu.itis.models.Auth;

public interface AuthRepository extends CrudRepository<Auth> {
    Auth findByCookieValue(String cookieValue);
}
