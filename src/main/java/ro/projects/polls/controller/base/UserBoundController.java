package ro.projects.polls.controller.base;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import ro.projects.polls.entity.User;

@Controller
public class UserBoundController {
    @ModelAttribute("username")
    public String getLoggedUsername() {
        String user = null;

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != "anonymousUser") {
            user = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        }

        return user;
    }

    public User getLoggedUser() {
        User user = null;

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != "anonymousUser") {
            user = (User) authentication.getPrincipal();
        }

        return user;
    }
}
