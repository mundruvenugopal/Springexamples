package com.example.demo.CurdOperations;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
@Component
@Controller
public class StudentController {
	
	@Autowired
	private StudentRepository studentrepository;

	
	@RequestMapping("showFormForAdd")
	public String showFormforAdd(Model themodel,Student student)
	{
		themodel.addAttribute("student",student);
	
		return "StudentForm";
	}
	
	@RequestMapping("save")
	public String saveStudentData(@Valid Student thestudent,BindingResult theBindingResult)
	{
		if (studentrepository.existsByEmail(thestudent.getEmail())) {
			
			theBindingResult.rejectValue("email", "error.stud", "Email ID already exists.");
		}
		
if (studentrepository.existsByFirstName(thestudent.getFirstName())) {
    theBindingResult.rejectValue("firstName", "error.std", "FirstName already exists.");
}

if (studentrepository.existsByLastName(thestudent.getLastName())) {
theBindingResult.rejectValue("lastName", "error.std", "LastName already exists.");
}
   // theBindingResult.rejectValue("lastName", "error.std", errorMessage);
//	theBindingResult.rejectValue("FirstName", "error.std", "Length of FirstName and LastName should be >5.");
//		System.out.println("Length of FirstName and LastName should be >5.");
//	
	//}
		if (theBindingResult.hasErrors()) {
			 return "StudentForm";
			 }
			 else {
				 studentrepository.save(thestudent);
					return "redirect:list";
				
			}
	}
	
		
@RequestMapping("list")
public String printStudentsList(Model themodel)
{
	List<Student>studentlist=studentrepository.findAll();
	//System.out.println("studentlist="+studentlist);
	themodel.addAttribute("studentlist", studentlist);
	return "list";
}
	
	
	
	
	
	@RequestMapping("showFormForUpdate")
	public String showFormForUpdate(@RequestParam("studentid")Long id ,Model themodel)
	{
		Optional<Student>thestudent=studentrepository.findById(id);
		themodel.addAttribute("student",thestudent);
		return "StudentFormUpdate";
	}	
	@RequestMapping("saveUpdatedData")
	public String saveStudentUpdatedData(@Valid Student thestudent,BindingResult theBindingResult)
	{
		if (theBindingResult.hasErrors()) {
			 return "StudentFormUpdate";
			 }
			 else {
				 studentrepository.save(thestudent);
					return "redirect:list";
				
			}
	}
	
	
	
	
	
	@RequestMapping("delete")
	public String delete(@RequestParam("studentid")Long id)
	{
	studentrepository.deleteById(id);
		
		return "redirect:list";
	}	
}