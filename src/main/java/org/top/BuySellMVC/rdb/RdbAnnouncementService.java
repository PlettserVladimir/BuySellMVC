package org.top.BuySellMVC.rdb;

import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Announcement;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.rdb.repository.AnnouncementRepository;
import org.top.BuySellMVC.service.AnnouncementService;
import org.top.BuySellMVC.service.ProfileService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RdbAnnouncementService implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final ProfileService profileService;

    public RdbAnnouncementService(AnnouncementRepository announcementRepository, ProfileService profileService){
        this.announcementRepository = announcementRepository;
        this.profileService = profileService;
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
           return Optional.of(announcementRepository.save(announcement));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Announcement> addAnnouncement(Announcement announcement) {
        announcement.setDataCreation(LocalDate.now());
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

    @Override
    public List<Announcement> findByProfileId(Integer id) {
        return announcementRepository.findAllByProfileId(id);
    }

    @Override
    public List<Announcement> findByCategoryId(Integer id) {
        return announcementRepository.findAllByCategoryId(id);
    }

    public boolean buy(Profile profile,Announcement announcement){
        if (profile.getWallet()> announcement.getPrice()){
            profile.setWallet(profile.getWallet()- announcement.getPrice());
            profileService.updateProfile(profile);
            delete(announcement.getId());
            return true;
        }
        return false;
    }

}
