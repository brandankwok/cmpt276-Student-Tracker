package assignment2.assignment2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import assignment2.assignment2.models.User;
import assignment2.assignment2.models.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
    
    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/users")
    public String getAllUsers(Model model){

        List<User> users = userRepo.findAll();
        
        model.addAttribute("users", users);
        return "users/showAll";
    }

    @PostMapping("/users")
    public String addUser(@RequestParam Map<String, String> newUser,HttpServletResponse response) {
        if (newUser.get("name") == "" || newUser.get("weight") == "" 
            || newUser.get("height") == "" || newUser.get("hair_color") == ""
            || newUser.get("gpa") == "" || newUser.get("age") == "") 
            {
                return "redirect:/users";
            }
        
        String newName = newUser.get("name");
        int newWeight = Integer.parseInt(newUser.get("weight"));
        int newHeight = Integer.parseInt(newUser.get("height"));
        String newHair_color = newUser.get("hair_color");
        float newGpa = Float.parseFloat(newUser.get("gpa"));
        int NewAge = Integer.parseInt(newUser.get("age"));

        if (newWeight <= 0 || newHeight <= 0 || newGpa < 0.00 || newGpa > 4.33) {
            return "redirect:/users";
        }
        
        userRepo.save(new User(newName,newWeight,newHeight,newHair_color,newGpa,NewAge));
        response.setStatus(201);
        System.out.println("ADD User");
        return "redirect:/users";
    }
    
    @GetMapping("/users/new")
    public String addPage() {
        return "users/add";
    }    
}
