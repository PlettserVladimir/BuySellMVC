package org.top.BuySellMVC.rdb.repository;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Announcement;
import org.top.BuySellMVC.service.AnnouncementService;

import java.util.Optional;

@Service
public class RdbAnnouncementService implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public RdbAnnouncementService(AnnouncementRepository announcementRepository){
        this.announcementRepository = announcementRepository;
    }
    @Override
    public Iterable<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    @Override
    public Optional<Announcement> findById(Integer id) {
        return announcementRepository.findById(id);
    }

    @Override
    public Optional<Announcement> update(Announcement announcement) {
        Optional<Announcement> updated = findById(announcement.getId());
        if (updated.isPresent()){
            announcementRepository.save(announcement);
            return updated;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Announcement> addAnnouncement(Announcement announcement) {
        return Optional.of(announcementRepository.save(announcement));
    }

    @Override
    public Optional<Announcement> delete(Integer id) {
        Optional<Announcement> deleted = findById(id);
        if (deleted.isPresent()){
            announcementRepository.deleteById(id);
        }
        return deleted;
    }
}
