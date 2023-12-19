package org.top.BuySellMVC.service;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Profile;

import java.util.Optional;

@Service
public interface ProfileService {
    //Получить все записи
    Iterable<Profile> findAll();
    //получить запись по ИД
    Optional<Profile> findById(Integer id);
    //получить запись по имени
    Optional<Profile> findByName(String name);
    //удалить запись
    Optional<Profile> deleteProfile(Integer id);
    //редактировать запись
    Optional<Profile> updateProfile(Profile profile);
    //добавить запись
    Optional<Profile> addProfile(Profile profile);
    //пополнение кошелька
    boolean replenishmentOfBalance(Profile profile,Integer summa);
}
