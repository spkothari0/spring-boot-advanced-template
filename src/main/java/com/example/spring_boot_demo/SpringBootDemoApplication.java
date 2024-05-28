package com.example.spring_boot_demo;

import com.example.spring_boot_demo.entity.StudentEntity;
import com.example.spring_boot_demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDemoApplication implements CommandLineRunner {

	@Autowired
	StudentRepository studentRepository;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		StudentEntity studentEntity = new StudentEntity("Shreyas", "Kothari", "spk.othari@gmail.com", 22);
		studentRepository.save(studentEntity);

		StudentEntity studentEntity1 = new StudentEntity("Amit", "Tomar", "amit.tomar@gmail.com", 23);
		studentRepository.save(studentEntity1);

		studentRepository.findByEmail("amit.tomar@gmail.com").forEach(x-> System.out.println(x));
	}
}
