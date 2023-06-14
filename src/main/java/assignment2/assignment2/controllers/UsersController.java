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
import org.springframework.web.bind.annotation.RequestParam;

import assignment2.assignment2.models.User;
import assignment2.assignment2.models.UserRepository;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
    
    @Autowired
    private UserRepository studentRepo;
    
    // display all students
    @GetMapping("/students")
    public String getAllStudents(Model model){

        List<User> students = studentRepo.findAll();
        
        model.addAttribute("users", students);
        return "students/studentsDisplay";
    }

    // go to rectangles page
    @GetMapping("/students/rectangles")
    public String rectanglesPage(Model model) {
        List<User> students = studentRepo.findAll();
        
        model.addAttribute("users", students);
        return "students/rectanglesDisplay";
    }

    // go to add student page
    @GetMapping("/students/new")
    public String addPage() {
        return "students/add";
    }

    // add a student
    @PostMapping("/students/add")
    public String addUser(@RequestParam Map<String, String> user,HttpServletResponse response) {
        // checking for empty inputs
        if (user.get("name") == "" || user.get("weight") == "" 
            || user.get("height") == "" || user.get("hair_color") == ""
            || user.get("gpa") == "" || user.get("age") == "") 
            {
                return "students/add";
            }
        
        String newName = user.get("name");
        int newWeight = Integer.parseInt(user.get("weight"));
        int newHeight = Integer.parseInt(user.get("height"));
        String newHair_color = user.get("hair_color");
        float newGpa = Float.parseFloat(user.get("gpa"));
        int newAge = Integer.parseInt(user.get("age"));
        
        // checking number bounds
        if (newWeight < 70 || newWeight > 300 || newHeight < 100 || newHeight > 215 || newGpa < 0.00 || newGpa > 4.33 || newAge < 16 || newAge > 100) {
            return "students/add";
        }
        
        studentRepo.save(new User(newName,newWeight,newHeight,newHair_color,newGpa,newAge));
        response.setStatus(201);
        return "redirect:/students";
    }

    // delete a student from table
    @GetMapping("/students/delete/{uid}")
    public String deleteStudent(@PathVariable Integer uid) {
        studentRepo.deleteById(uid);
        return "redirect:/students";
    }

    // delete student rectangle
    @GetMapping("/students/deleteRectangle/{uid}")
    public String deleteStudentRectangle(@PathVariable Integer uid) {
        studentRepo.deleteById(uid);
        return "redirect:/students/rectangles";
    }
    
    // edit student attributes
    @GetMapping("/students/{uid}")
	public String editStudentForm(@PathVariable Integer uid, Model model) {
		model.addAttribute("user", studentRepo.findById(uid).get());
		return "students/edit";
	}

    // save updated student information
    @PostMapping("/students/{uid}")
	public String updateStudent(@PathVariable Integer uid, @ModelAttribute("user") User user) {
	
		User student = studentRepo.findById(uid).get();
        student.setName(user.getName());
        student.setHeight(user.getHeight());
        student.setWeight(user.getWeight());
        student.setHair_color(user.getHair_color());
        student.setGpa(user.getGpa());
        student.setAge(user.getAge());
		
		studentRepo.save(student);
		return "redirect:/students";		
	}
}
