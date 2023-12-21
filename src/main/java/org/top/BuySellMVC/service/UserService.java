package org.top.BuySellMVC.service;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.User;
import org.top.BuySellMVC.form.UserRegistrationsForm;

import java.util.Optional;

@Service
public interface UserService {
    //1. Регистрация пользователя
    boolean register(UserRegistrationsForm userRegistrationsForm);
    //2. Вывод всех пользователей по роли
    Iterable<User> findUserByRole(String role);
    //3. Поиск по логину
    Optional<User> findUserByLogin(String login);
    //4. Поиск пользователя по ИД
    Optional<User> findUserById(Integer id);
}
