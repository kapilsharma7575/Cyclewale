package com.example.springjpa.Controllers;

import com.example.springjpa.Entitites.User;
import com.example.springjpa.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class General {

    @Autowired
    UserRepository userRepo;

    @GetMapping("/")
    public String landingPage()
    {
        return "WelcomePage";
    }

    @GetMapping("/login")
    public String userlogin()
    {
        return "user_login";
    }

    @GetMapping("/adminlogin")
    public String adminlogin()
    {
        return "admin_login";
    }


    @GetMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("user", new User());
        return "user_signup";
    }

    @PostMapping("/process_register")
    public String processRegister(User user)
    {
        if(userRepo.findByEmail(user.getEmail()).orElse(null) != null)
            return "redirect:/signup?error";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "user_login";
    }

    @GetMapping("/login_success")
    public String loginSuccess(HttpServletRequest request)
    {
        if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/user/home";
        }

        return "redirect:/admin/home";
    }


}
