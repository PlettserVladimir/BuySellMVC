package org.top.BuySellMVC.rdb;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.top.BuySellMVC.entity.Profile;
import org.top.BuySellMVC.entity.User;
import org.top.BuySellMVC.form.UserRegistrationsForm;
import org.top.BuySellMVC.rdb.repository.ProfileRepository;
import org.top.BuySellMVC.rdb.repository.UserRepository;
import org.top.BuySellMVC.service.UserService;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RdbUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public RdbUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean register(UserRegistrationsForm userRegistrationsForm) {
        if (userRepository.findByLogin(userRegistrationsForm.getLogin()).isPresent()){
            return false;
        }
        Profile profile = new Profile();
        profile.setName(userRegistrationsForm.getName());
        profile.setEmail(userRegistrationsForm.getEmail());
        profile.setRate(0.0);
        profile.setRegistrationDate(LocalDate.now());
        profile.setWallet(0.0);
        User user = new User();
        user.setLogin(userRegistrationsForm.getLogin());
        user.setPassword(passwordEncoder.encode(userRegistrationsForm.getPassword()));
        user.setRole(userRegistrationsForm.getRole());
        profile.setUser(user);
        user.setProfile(profile);
        userRepository.save(user);
        profileRepository.save(profile);
        return true;
    }

    @Override
    public Iterable<User> findUserByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }
}
