package org.top.BuySellMVC.rdb;


import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.rdb.repository.ProfileRepository;
import org.top.BuySellMVC.service.ProfileService;

import java.util.Optional;

@Service
public class RdbProfileService implements ProfileService {
    private final ProfileRepository profileRepository;

    public RdbProfileService(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @Override
    public Iterable<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public Optional<Profile> findById(Integer id) {
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> findByName(String name) {
        return profileRepository.findByName(name);
    }

    @Override
    public Optional<Profile> deleteProfile(Integer id) {
        Optional<Profile> deleted = findById(id);
        if (deleted.isPresent()){
           profileRepository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Profile> updateProfile(Profile profile) {
        Optional<Profile> updated = findById(profile.getId());
        if (updated.isPresent()){
            updated = Optional.of(profileRepository.save(profile));
        }
        return updated;
    }

    @Override
    public Optional<Profile> addProfile(Profile profile) {
        Optional<Profile> added = findByName(profile.getName());
        if (added.isEmpty()){
            added = Optional.of(profileRepository.save(profile));
            return added;
        }
        return Optional.empty();
    }
    @Override
    public boolean replenishmentOfBalance(Profile profile,Integer summa){
        if (profileRepository.findById(profile.getId()).isPresent()) {
            profile.setWallet(profile.getWallet() + summa);
            profileRepository.save(profile);
            return true;
        }
        return false;
    }
}
