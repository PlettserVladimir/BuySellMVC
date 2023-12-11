package org.top.BuySellMVC.rdb.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.top.BuySellMVC.entity.Announcement;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement,Integer> {
}
