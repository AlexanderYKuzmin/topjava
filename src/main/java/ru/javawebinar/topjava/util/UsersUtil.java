package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User(null, "User-1", "user01@yandex.ru", "Password01", Role.USER),
            new User(null, "User-2", "user02@mail.ru", "Password02", Role.ADMIN)
    );
}
