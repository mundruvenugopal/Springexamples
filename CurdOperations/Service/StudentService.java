package com.example.demo.CurdOperations.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
	 public void saveStudent(StudentRequestDTO studentRequest) throws IOException {
		        String fileName = UUID.randomUUID().toString() + "_" + studentRequest.getStudentphoto().getOriginalFilename();
		        Path uploadDir = Paths.get("uploads/");
		        if (!Files.exists(uploadDir)) {
		            Files.createDirectories(uploadDir);
		        }
		        Path filePath = uploadDir.resolve(fileName);
		        Files.copy(studentRequest.getStudentphoto().getInputStream(), filePath);
		 Student student = Student.builder()
					.firstName(studentRequest.getFirstName())
					.lastName(studentRequest.getLastName())
					.email(studentRequest.getEmail())
					.phNo(studentRequest.getPhNo())
					 .studentphoto(fileName)
					.build();
		 logger.debug("Saving student: " + student);
		 studentRepository.save(student);
		 logger.info("Saved new student: " + student.getFirstName() + " " + student.getLastName());
	   }
	 public void saveUdatedStudent(StudentRequestDTO studentRequestDTO) throws IOException {
		 Student student = studentRepository.findById(studentRequestDTO.getId()).orElseThrow(() -> new RuntimeException("Student not found"));
		 if (studentRequestDTO.getStudentphoto() != null && !studentRequestDTO.getStudentphoto().isEmpty()) {
		        String fileName = UUID.randomUUID().toString() + "_" + studentRequestDTO.getStudentphoto().getOriginalFilename();
		        Path uploadDir = Paths.get("uploads/");
		        if (!Files.exists(uploadDir)) {
		            Files.createDirectories(uploadDir);
		        }
		        Path filePath = uploadDir.resolve(fileName);
		        Files.copy(studentRequestDTO.getStudentphoto().getInputStream(), filePath);
		        student.setStudentphoto(fileName);
		 }
		// Student student = new Student();
		    student.setId(studentRequestDTO.getId()); 
		    student.setFirstName(studentRequestDTO.getFirstName());
		    student.setLastName(studentRequestDTO.getLastName());
		    student.setEmail(studentRequestDTO.getEmail());
		    student.setPhNo(studentRequestDTO.getPhNo());    
		 studentRepository.save(student);  
	   }
	    public List<StudentResponseDTO> getAllStudents() {
	    	List<Student> students = studentRepository.findAll();
	        List<StudentResponseDTO> studentResponseDTOs = new ArrayList<>();
	        for (Student student : students) {
	        	 String photoUrl = student.getStudentphoto() != null
	                     ? "/uploads/" + Paths.get(student.getStudentphoto()).getFileName()
	                     : null;
	            StudentResponseDTO dto = StudentResponseDTO.builder()
	            		.id(student.getId())
						.firstName(student.getFirstName())
						.lastName(student.getLastName())
						.email(student.getEmail())
						.phNo(student.getPhNo())
						.studentphoto(photoUrl)
						.build();
	            studentResponseDTOs.add(dto);
	    }
	        return studentResponseDTOs;
	    }
	    public void deleteStudent(Long id) {
	        studentRepository.deleteById(id);
	    }

	    public Page<Student> findPaginated(int pageNo, int pageSize) {
	        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
	        return this.studentRepository.findAll(pageable);
	    }
	}