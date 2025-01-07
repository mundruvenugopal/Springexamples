package com.example.demo.CurdOperations;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class StudentRequestDTO {
	private Long id;
		@NotNull(message = "is required")
		@Size(min=1,max=10,message = "is required")
		private String firstName;
		@NotNull(message = "is required")
		@Size(min=1,max=10,message = "is required")
		private String lastName;
		@Email(message = "Please enter a valid email Id")
		@NotNull(message = "Email cannot be NULL")
		@Size(min=1,max=25,message = "Email cannot be NULL")
		private String email;
		@Pattern(regexp="^[0-9]{10}$",message="Phone number must be a 10-digit number")
		//@Digits(integer = 10, fraction = 0,message="Phone number must be a 10-digit number")
		private String phNo;
	 }






