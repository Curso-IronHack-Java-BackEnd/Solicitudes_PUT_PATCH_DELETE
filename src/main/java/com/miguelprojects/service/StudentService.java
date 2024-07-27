package com.miguelprojects.service;

import com.miguelprojects.DTOs.StudentDTO;
import com.miguelprojects.DTOs.PartialStudentDTO;
import com.miguelprojects.model.Student;
import com.miguelprojects.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student createStudent (Student studentRequest){

        // Se haria asi cuando mandemos la info con DTOs
//        public StudentDTO createStudent (StudentDTO studentRequest)
//        Student newStudent = new Student();
//        newStudent.setName(studentRequest.getName());
//        newStudent.setAge(studentRequest.getAge());
//        return studentRepository.save(newStudent);

        return studentRepository.save(studentRequest);
    }

    public void updateStudent (int idStudent, StudentDTO studentRequest){
        Student student = studentRepository.findById(idStudent).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        student.setName(studentRequest.getName());
        student.setAge(studentRequest.getAge());
        studentRepository.save(student);

//        if (studentRequest.getName() != null) {
//            student.setName(studentRequest.getName());
//        }
//
//        if (studentRequest.getAge() != null) {
//            student.setAge(studentRequest.getAge());
//        }

//        studentRepository.save(student);
    }

    public void patchStudent (int idStudent, PartialStudentDTO studentRequest){
        Student student = studentRepository.findById(idStudent).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

//        student.setName(studentRequest.getName());
//        student.setAge(studentRequest.getAge());
//        studentRepository.save(student);

        if (studentRequest.getName() != null) {
            student.setName(studentRequest.getName());
        }

        if (studentRequest.getAge() != null) {
            student.setAge(studentRequest.getAge());
        }

        studentRepository.save(student);
    }

    public void deleteStudent (int idStudent){
        studentRepository.deleteById(idStudent);
    }


    public List<Student> findStudentsByNameAndAge (String name, Optional<Integer> age){

        if (name.isEmpty() && age.isEmpty()) {
            return studentRepository.findAll();
        } else if (!name.isEmpty() && age.isEmpty()) {
            return studentRepository.findByNameContaining(name);
        } else if (name.isEmpty() && !age.isEmpty()) {
            return studentRepository.findByAge(age.get());
        } else {
            return studentRepository.findByNameAndAge(name, age.get());
        }

    }
}
