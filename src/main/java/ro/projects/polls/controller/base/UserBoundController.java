package ro.projects.polls.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.projects.polls.entity.User;
import ro.projects.polls.repository.UserRepository;

@Controller
public class UserBoundController {
    public final UserRepository userRepository;

    @Autowired
    public UserBoundController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("username")
    public String getLoggedUsername() {
        String user = null;

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != "anonymousUser") {
            user = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        }

        return user;
    }

    protected User getLoggedUser() {
        return userRepository.findByUsername(this.getLoggedUsername());
    }
}
