package org.top.BuySellMVC.rdb;


import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.UserProfile;
import org.top.BuySellMVC.rdb.repository.UserProfileRepository;
import org.top.BuySellMVC.service.UserProfileService;

import java.util.Optional;

@Service
public class RdbUserProfileService implements UserProfileService {
    private final UserProfileRepository repository;

    public RdbUserProfileService(UserProfileRepository repository){
        this.repository = repository;
    }

    @Override
    public Iterable<UserProfile> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserProfile> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserProfile> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Optional<UserProfile> deleteUserProfile(Integer id) {
        Optional<UserProfile> deleted = findById(id);
        if (deleted.isPresent()){
           repository.deleteById(id);
        }
        return deleted;
    }

    @Override
    public Optional<UserProfile> updateUserProfile(UserProfile profile) {
        Optional<UserProfile> updated = findById(profile.getId());
        if (updated.isPresent()){
            updated = Optional.of(repository.save(profile));
        }
        return updated;
    }

    @Override
    public Optional<UserProfile> addUserProfile(UserProfile profile) {
        Optional<UserProfile> added = findByName(profile.getName());
        if (added.isEmpty()){
            added = Optional.of(repository.save(profile));
            return added;
        }
        return Optional.empty();
    }
}
