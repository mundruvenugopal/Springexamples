
package com.example.demo.CurdOperations;


import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long>{
	
	boolean existsByEmail(String email);

	boolean existsByFirstName(String FirstName);
	boolean existsByLastName(String LastName);

}


