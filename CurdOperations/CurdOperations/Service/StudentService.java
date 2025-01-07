package com.example.demo.CurdOperations.Service;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.CurdOperations.Entity.Student;
import com.example.demo.CurdOperations.Model.StudentRequestDTO;
import com.example.demo.CurdOperations.Model.StudentResponseDTO;
import com.example.demo.CurdOperations.Repository.StudentRepository;
@Service
public class StudentService {
	 @Autowired
	    private StudentRepository studentRepository; 
	 private static final Logger logger = Logger.getLogger(StudentService.class);
	 public void saveStudent(StudentRequestDTO studentRequest) {
	
		 Student student = Student.builder()
					.firstName(studentRequest.getFirstName())
					.lastName(studentRequest.getLastName())
					.email(studentRequest.getEmail())
					.phNo(studentRequest.getPhNo())
					.build();
		 logger.debug("Saving student: " + student);
		 studentRepository.save(student);
		 logger.info("Saved new student: " + student.getFirstName() + " " + student.getLastName());
	   }
	 
	 public void saveUdatedStudent(StudentRequestDTO studentRequestDTO) {
		 Student student = new Student();
		    student.setId(studentRequestDTO.getId()); 
		    student.setFirstName(studentRequestDTO.getFirstName());
		    student.setLastName(studentRequestDTO.getLastName());
		    student.setEmail(studentRequestDTO.getEmail());
		    student.setPhNo(studentRequestDTO.getPhNo());

//		 Student student = Student.builder()
//				 .id(studentRequest.getId())
//					.firstName(studentRequest.getFirstName())
//					.lastName(studentRequest.getLastName())
//					.email(studentRequest.getEmail())
//					.phNo(studentRequest.getPhNo())
//					.build();
		 studentRepository.save(student);  
	   }

	 
	    public List<StudentResponseDTO> getAllStudents() {
	    	List<Student> students = studentRepository.findAll();
	        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();

	        for (Student student : students) {
	            StudentResponseDTO dto = StudentResponseDTO.builder()
	            		.id(student.getId())
						.firstName(student.getFirstName())
						.lastName(student.getLastName())
						.email(student.getEmail())
						.phNo(student.getPhNo())
						.build();
	            studentResponseDTOs.add(dto);
	    }
	        return studentResponseDTOs;
	    }

	
	    public Student updateStudent(StudentRequestDTO studentRequest) {
	        return studentRepository.findById(studentRequest.getId()).map(existingStudent -> {
	            existingStudent.setFirstName(studentRequest.getFirstName());
	            existingStudent.setLastName(studentRequest.getLastName());
	            existingStudent.setEmail(studentRequest.getEmail());
	            existingStudent.setPhNo(studentRequest.getPhNo());
	          return  studentRepository.save(existingStudent);
	        }).orElse(null); 
	    }
	    public void deleteStudent(Long id) {
	        studentRepository.deleteById(id);
	    }
	
	    public Page<Student>findPaginated(int pageNo,int pagesize){
		   Pageable pageable=PageRequest.of(pageNo-1, pagesize);
		   return this.studentRepository.findAll(pageable);
	   }
	}

