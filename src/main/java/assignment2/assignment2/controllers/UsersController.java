package assignment2.assignment2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import assignment2.assignment2.models.User;
import assignment2.assignment2.models.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
    
    @Autowired
    private UserRepository userRepo;
    
    // display all users
    @GetMapping("/users")
    public String getAllUsers(Model model){

        List<User> users = userRepo.findAll();
        
        model.addAttribute("users", users);
        return "users/showAll";
    }

    // go to add student page
    @GetMapping("/users/new")
    public String addPage() {
        return "users/add";
    }

    // add a student
    @PostMapping("/users/add")
    public String addUser(@RequestParam Map<String, String> user,HttpServletResponse response) {
        // checking for empty inputs
        if (user.get("name") == "" || user.get("weight") == "" 
            || user.get("height") == "" || user.get("hair_color") == ""
            || user.get("gpa") == "" || user.get("age") == "") 
            {
                return "redirect:/users";
            }
        
        String newName = user.get("name");
        int newWeight = Integer.parseInt(user.get("weight"));
        int newHeight = Integer.parseInt(user.get("height"));
        String newHair_color = user.get("hair_color");
        float newGpa = Float.parseFloat(user.get("gpa"));
        int newAge = Integer.parseInt(user.get("age"));
        
        // checking number bounds
        if (newWeight <= 0 || newHeight <= 0 || newGpa < 0.00 || newGpa > 4.33 || newAge < 18) {
            return "redirect:/users";
        }
        
        userRepo.save(new User(newName,newWeight,newHeight,newHair_color,newGpa,newAge));
        response.setStatus(201);
        return "redirect:/users";
    }

    // delete a student
    @GetMapping("/users/delete/{uid}")
    public String deleteStudent(@PathVariable Integer uid) {
        userRepo.deleteById(uid);
        return "redirect:/users";
    }
    
    // edit student attributes
    @GetMapping("/users/edit/{uid}")
    public ModelAndView editStudent(@PathVariable Integer uid) {
        ModelAndView editView = new ModelAndView("/users/edit");
        User user = userRepo.findById(uid).get();
        editView.addObject("user", user);

        return editView;
    }
    // save updated student information
    @PostMapping("/save")
    public String saveStudent(@ModelAttribute("user") User user) {
        
        // User student = userRepo.findById(uid).get();
        // student.setName(user.getName());
        // student.setHeight(user.getHeight());
        // student.setWeight(user.getWeight());
        // student.setHair_color(user.getHair_color());
        // student.setGpa(user.getGpa());
        // student.setAge(user.getAge());

        userRepo.save(user);
        return "redirect:/users";
    }


}
