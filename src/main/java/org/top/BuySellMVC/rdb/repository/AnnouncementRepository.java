package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.Announcement;

import java.util.List;
import java.util.Set;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement,Integer> {
    List<Announcement> findAllByProfileId(Integer id);
    List<Announcement> findAllByCategoryId(Integer id);
}
