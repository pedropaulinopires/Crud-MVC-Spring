package com.pedro.spring.service;

import com.pedro.spring.domain.Teacher;
import com.pedro.spring.repository.TeacherRepository;
import com.pedro.spring.request.TeacherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public List<Teacher> findAll(){return teacherRepository.findAll();} //find all teachers

    public Teacher findById(Long id){return teacherRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Teacher not found by id!")
    );} //find teacher by id

    public void deleteById(Long id){findById(id);teacherRepository.deleteById(id);} //delete teacher by id

    public Teacher save(TeacherRequest teacher){
        return teacherRepository.save(teacher.build());
    } // save teacher on database

    public void replace(Teacher teacher){
        findById(teacher.getId());
        teacherRepository.save( teacher);
    }
}
