package org.top.BuySellMVC.service;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Announcement;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface AnnouncementService {

    //1. Получить все объявления
    Iterable<Announcement> findAll();
    //2. Получение по ИД
    Optional<Announcement> findById(Integer id);
    //3. Редактирование
    Optional<Announcement> update(Announcement announcement);
    //4. Добавление объявления
    Optional<Announcement> addAnnouncement(Announcement announcement);
    //5. Удалить объявление
    Optional<Announcement> delete(Integer id);
    //6. Получить по ИД профиля
    List<Announcement> findByProfileId(Integer id);
    //7. Получение по ИД категории
    List<Announcement> findByCategoryId(Integer id);
}
