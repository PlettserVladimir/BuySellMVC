package org.top.BuySellMVC.service;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.UserProfile;

import java.util.Optional;

@Service
public interface UserProfileService {
    //Получить все записи
    Iterable<UserProfile> findAll();
    //получить запись по ИД
    Optional<UserProfile> findById(Integer id);
    //получить запись по имени
    Optional<UserProfile> findByName(String name);
    //удалить запись
    Optional<UserProfile> deleteUserProfile(Integer id);
    //редактировать запись
    Optional<UserProfile> updateUserProfile(UserProfile profile);
    //добавить запись
    Optional<UserProfile> addUserProfile(UserProfile profile);
}
