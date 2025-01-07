package com.example.demo.CurdOperations;
import org.springframework.stereotype.Component;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Getter
@Data
@Table(name="StudentsDetails")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Student {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@NotNull(message = "is required")
	@Size(min=1,max=10,message = "is required")
	private String firstName;
	@NotNull(message = "is required")
	@Size(min=1,max=10,message = "is required")
	private String lastName;
	@Email(message = "Please enter a valid email Id")
	@NotNull(message = "Email cannot be NULL")
	private String email;
	@Pattern(regexp="^[0-9]{10}$",message="Phone number must be a 10-digit number")
	//@Digits(integer = 10, fraction = 0,message="Phone number must be a 10-digit number")
	private String phNo;
	@Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phNo=" + phNo +
                '}';
    }
	}
