package org.top.BuySellMVC.startup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.top.BuySellMVC.form.UserRegistrationsForm;
import org.top.BuySellMVC.service.UserService;

@Service
public class StartUp {
    private final UserService userService;

    public StartUp(UserService userService) {
        this.userService = userService;
    }
    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup(){
        if (userService.findUserByRole("ADMIN").iterator().hasNext()){
            System.out.println("Admin is exists");
            return;
        }
        UserRegistrationsForm admin = new UserRegistrationsForm();
        admin.setName("admin");
        admin.setRole("ADMIN");
        admin.setLogin("admin");
        admin.setEmail("-");
        admin.setPassword("admin");
        if (userService.register(admin)){
            System.out.println("Created default administration");
        }else {
            System.out.println("Can't create default administration");
        }
    }
}
