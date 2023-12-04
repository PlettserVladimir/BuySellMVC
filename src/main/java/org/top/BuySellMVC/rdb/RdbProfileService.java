package org.top.BuySellMVC.rdb;


import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.rdb.repository.ProfileRepository;
import org.top.BuySellMVC.service.ProfileService;

import java.util.Optional;

@Service
public class RdbProfileService implements ProfileService {
    private final ProfileRepository repository;

    public RdbProfileService(ProfileRepository repository){
        this.repository = repository;
    }

    @Override
    public Iterable<Profile> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Profile> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Profile> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<Profile> deleteProfile(Integer id) {
        Optional<Profile> deleted = findById(id);
        if (deleted.isPresent()){
           repository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<Profile> updateProfile(Profile profile) {
        Optional<Profile> updated = findById(profile.getId());
        if (updated.isPresent()){
            updated = Optional.of(repository.save(profile));
        }
        return updated;
    }

    @Override
    public Optional<Profile> addProfile(Profile profile) {
        Optional<Profile> added = findByName(profile.getName());
        if (added.isEmpty()){
            added = Optional.of(repository.save(profile));
            return added;
        }
        return Optional.empty();
    }
}
