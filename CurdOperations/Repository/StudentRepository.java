
package com.example.demo.CurdOperations.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.CurdOperations.Entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long>{
	
	boolean existsByEmail(String email);

	boolean existsByFirstName(String FirstName);
	boolean existsByLastName(String LastName);

}


