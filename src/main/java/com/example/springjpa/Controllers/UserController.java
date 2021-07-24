package com.example.springjpa.Controllers;

import com.example.springjpa.Entitites.Cycle;
import com.example.springjpa.Entitites.Journey;
import com.example.springjpa.Entitites.Stands;
import com.example.springjpa.Entitites.User;
import com.example.springjpa.Repositories.CycleRepository;
import com.example.springjpa.Repositories.JourneyRepository;
import com.example.springjpa.Repositories.StandsRepository;
import com.example.springjpa.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    CycleRepository cycleRepo;

    @Autowired
    JourneyRepository jrnRepo;

    @Autowired
    StandsRepository standsRepo;

    @ResponseBody
    @GetMapping("/user")
    public String user()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepo.findByEmail(email).orElse(null);
        if(user == null) return "hehe";
        return "hello, " + user.getName();
    }

    @GetMapping("/user/home")
    public String userHome(Principal principal, Model model)
    {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        Journey journey = jrnRepo.findByUserId(user.getId()).orElse(null);
        model.addAttribute("currentUser", user);
        model.addAttribute("journey", journey);
        return "user_home";
    }

    @ResponseBody
    @PostMapping("/user/start_journey")
    public boolean startJourney(@RequestBody Journey journey, Principal principal)
    {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if (journey != null && user != null)
        {
            Cycle cycle = cycleRepo.findByCycleNo(journey.getCycleId()).orElse(null);

            cycle.setCycleStand(journey.getDestination());
            cycle.setTaken(true);
            user.setActive(true);
            journey.setUserId(user.getId());

            userRepo.save(user);
            cycleRepo.save(cycle);
            jrnRepo.save(journey);

            return true;
        }
        return false;
    }

    @ResponseBody
    @GetMapping("/user/stop_journey")
    public boolean stopJourney(Principal principal)
    {
        User user = userRepo.findByEmail(principal.getName()).orElse(null);
        if(user != null)
        {
            Journey journey = jrnRepo.findByUserId(user.getId()).orElse(null);
            Cycle cycle = cycleRepo.findByCycleNo(journey.getCycleId()).orElse(null);

            user.setActive(false);
            cycle.setTaken(false);
            jrnRepo.delete(journey);

            return true;
        }

        return false;
    }

    @ResponseBody
    @GetMapping("/user/check_availability/{stand}")
    public Long checkAvailability(@PathVariable("stand") String stand)
    {
        List<Cycle> availCycles = cycleRepo.findByCycleStand(stand);
        if (!availCycles.isEmpty())
        {
            for(Cycle current: availCycles)
            {
                if(current.isTaken() == false)
                    return  current.getCycleNo();
            }
        }
        return -1l;
    }

    @ResponseBody
    @GetMapping("/user/getStandCoords/{standName}")
    public double[] getStandCoords(@PathVariable("standName") String standName)
    {
        Stands stand =  standsRepo.findByName((String) standName).orElse(null);

        if (stand != null)
        {
            return stand.getCoords();
        }

        return null;
    }

    @ResponseBody
    @GetMapping("/user/getStands")
    public List<Stands> getStands()
    {
        return standsRepo.findAll();
    }


}
