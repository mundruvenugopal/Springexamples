package com.example.demo.CurdOperations.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor 
public class StudentResponseDTO {
	 private Long id; 
	 private String firstName;
	 private String lastName; 
	 private String email; 
	 private String phNo;
	 private String studentphoto;
	 //private String studentphoto; // Base64 encoded string

	// private Byte[] studentphoto;
}


