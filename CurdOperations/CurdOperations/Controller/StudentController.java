package com.example.demo.CurdOperations.Controller;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.CurdOperations.Entity.Student;
import com.example.demo.CurdOperations.Model.StudentRequestDTO;
import com.example.demo.CurdOperations.Repository.StudentRepository;
import com.example.demo.CurdOperations.Service.StudentService;

import jakarta.validation.Valid;

@Controller
public class StudentController {
    
    private static final Logger logger = Logger.getLogger(StudentController.class);
	@Autowired
	private StudentRepository studentrepository;
	  @Autowired
	    private StudentService studentService;
	
	@GetMapping("/page/showFormForAdd")
	public String showFormforAdd(StudentRequestDTO stduentrequestdto,Model themodel)
	{
		themodel.addAttribute("studentRequestDTO",stduentrequestdto);
		return "StudentForm";
	}
	
	   @PostMapping("/save")
	   public String saveStudent(@Valid StudentRequestDTO studentRequest, BindingResult bindingResult) {
		  
		   if (studentrepository.existsByEmail( studentRequest.getEmail())) {
				
  			   bindingResult.rejectValue("email", "error.stud", "Email ID already exists.");
  			}
  			
  			if (studentrepository.existsByFirstName( studentRequest.getFirstName())) {
  				bindingResult.rejectValue("firstName", "error.std", "FirstName already exists.");
  			}

  			if (studentrepository.existsByLastName(studentRequest.getLastName())) {
  				bindingResult.rejectValue("lastName", "error.std", "LastName already exists.");
  			}
  			
	       if (bindingResult.hasErrors()) {
	    	   logger.warn("Validation errors occurred while saving student: " + bindingResult.getFieldErrors());
	           return "StudentForm"; 
	       }
	      
	       studentService.saveStudent(studentRequest);
	       logger.info("Student saved successfully: " + studentRequest.getFirstName() + " " + studentRequest.getLastName());
	       return "redirect:/page/1"; 
	    
	   }
	
	  
 
	   
@GetMapping("/page/{pageNo}")
public String printStudentsList(@PathVariable(value = "pageNo") int pageNo, Model model)
{
	      // List<StudentResponseDTO> studentlist = studentService.getAllStudents();
	      // model.addAttribute("studentlist", studentlist);	      
	       int pageSize = 4;
		    Page <Student> page = studentService.findPaginated(pageNo, pageSize);
		  //  Page<Student> students =studentrepository.findAll(page);
		    List <Student> students = page.getContent();
		   // Pageable pageable = PageRequest.of(pageNo,pageSize );
		      // Page<Student> students =studentrepository.findAll(pageable);
		    model.addAttribute("currentPage", pageNo);
		    model.addAttribute("totalPages", page.getTotalPages());
		    model.addAttribute("totalItems", page.getTotalElements());
		   model.addAttribute("studentlist", students);
	       return "list"; 
	   
}




@GetMapping("/page/showFormForUpdate")
public String showFormForUpdate(@RequestParam("studentid")Long id ,Model themodel)
{
	Student thestudent=studentrepository.findById(id).get();
	themodel.addAttribute("student",thestudent);
	 StudentRequestDTO studentRequestDTO = StudentRequestDTO.builder()
			 .id(id)
			 .firstName(thestudent.getFirstName())
			 .lastName(thestudent.getLastName())
			 .email(thestudent.getEmail())
			 .phNo(thestudent.getPhNo())
			 .build();
		themodel.addAttribute("studentRequestDTO",studentRequestDTO);
	return "StudentFormUpdate";
}	


@PostMapping("saveUpdatedData")
public String saveStudentUpdatedData(@Valid StudentRequestDTO studentRequestDTO,BindingResult theBindingResult)
{

	if (theBindingResult.hasErrors()) {
		 logger.warn("Validation errors occurred while updating student: " + theBindingResult.getFieldErrors());
		 return "StudentFormUpdate";
		 }
		 else {
			 
			// System.out.println(studentRequestDTO.getId());
			 studentService.saveUdatedStudent(studentRequestDTO);
			 logger.info("Student updated successfully: " + studentRequestDTO.getFirstName() + " " + studentRequestDTO.getLastName());
				return "redirect:/page/1";	
		}
}



	
	@GetMapping("/page/delete")
	public String delete(@RequestParam("studentid")Long id)
	{
		studentService.deleteStudent(id);
		logger.debug("Student Deleted Successfully");
		return "redirect:/page/1";
	}	
	
	
}




