package com.example.springjpa.Controllers;

import com.example.springjpa.Entitites.Cycle;
import com.example.springjpa.Entitites.Stands;
import com.example.springjpa.Entitites.User;
import com.example.springjpa.Repositories.CycleRepository;
import com.example.springjpa.Repositories.JourneyRepository;
import com.example.springjpa.Repositories.StandsRepository;
import com.example.springjpa.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    StandsRepository standsRepo;

    @Autowired
    CycleRepository cycleRepo;

    @GetMapping("/admin/home")
    public String adminhome(Model model, Principal principal)
    {
        User admin = userRepo.findByEmail(principal.getName()).orElse(null);

        List<Stands> listStands = standsRepo.findAll();
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listStands", listStands);
        model.addAttribute("newStand", new Stands());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("adminName", admin.getName());
        return "admin_home";
    }

    @GetMapping("/admin/delete_stand")
    public String deleteStand(@RequestParam("standName") String standName)
    {
        Stands stand = standsRepo.findByName(standName).orElse(null);
        if (stand != null)
        {
            List<Cycle> cycle =cycleRepo.findByCycleStand(standName);
            if(cycle.isEmpty()) standsRepo.delete(stand);
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/admin/delete_user")
    public String deleteUser(@RequestParam("user_id") long id)
    {
        User user = userRepo.findById(id).orElse(null);

        if (user != null)
            userRepo.delete(user);

        return "redirect:/admin/home";
    }

    @PostMapping("/admin/addStand")
    public String addStand(Stands newStand)
    {
        if (newStand != null)
        {
            Stands stand = standsRepo.findByName(newStand.getName()).orElse(null);
            if (stand == null)
                standsRepo.save(newStand);
        }
        return "redirect:/admin/home";
    }


}
