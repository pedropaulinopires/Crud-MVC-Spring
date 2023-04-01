package com.pedro.spring.controller;

import com.pedro.spring.domain.Teacher;
import com.pedro.spring.enums.StatusTeacher;
import com.pedro.spring.request.TeacherRequest;
import com.pedro.spring.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/professores")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    private ModelAndView showMessageClient(String message,Boolean error){
        return new ModelAndView("redirect:/professores")
                .addObject("message",message)
                .addObject("error",error);
    }

    @GetMapping
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("teachers/index");
        if (teacherService.findAll().size() > 0) {
            mv.addObject("teachersList", teacherService.findAll());
            mv.addObject("listEmpty", Boolean.FALSE);
        } else {
            mv.addObject("listEmpty", Boolean.TRUE);
            mv.addObject("teachersList", teacherService.findAll());
        }

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView teacherNew() {
        ModelAndView mv = new ModelAndView("teachers/new");
        mv.addObject("statusOptions", StatusTeacher.values());
        return mv;
    }

    @PostMapping
    public ModelAndView saveTeacher(@Valid TeacherRequest teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView mv = new ModelAndView("teachers/new");
            mv.addObject("statusOptions", StatusTeacher.values());
            return mv;
        }
        Teacher teacherSave = teacherService.save(teacher);
        return showMessageClient("Professor cadastrado com sucesso!",Boolean.FALSE);
    }

    @GetMapping("/{id}")
    public ModelAndView search(@PathVariable Long id) {
        try {
            ModelAndView mv = new ModelAndView("teachers/search");
            mv.addObject("teacher", teacherService.findById(id));
            return mv;
        } catch (Exception e) {
            return showMessageClient("Id de professor inváido ou não existe!",Boolean.TRUE);
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, TeacherRequest teacherRequest) {
        try {
            ModelAndView mv = new ModelAndView("teachers/edit");
            Teacher teacher = teacherService.findById(id);
            mv.addObject("teacher", teacher);
            teacherRequest.fromTeacher(teacher);
            mv.addObject("statusOptions", StatusTeacher.values());
            mv.addObject("teacherId", id);
            return mv;
        } catch (Exception e) {
            return showMessageClient("Erro ao procurar professor, pois o id não existe! !",Boolean.TRUE);
        }


    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, @Valid TeacherRequest teacher, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //error
            ModelAndView mv = new ModelAndView("teachers/edit");
            mv.addObject("teacherId", id);
            mv.addObject("statusOptions", StatusTeacher.values());
            return mv;
        } else {
            //edit
            try {
                teacherService.replace(teacher.build(teacherService.findById(id), id));
                return showMessageClient("Professor editado com sucesso!",Boolean.FALSE);
            } catch (Exception e) {
                return showMessageClient("Erro ao editar professor, pois o id não existe!",Boolean.FALSE);
            }
        }
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        try {
            teacherService.deleteById(id);
            return showMessageClient("Professor deletado com sucesso!",Boolean.FALSE);
        } catch (Exception e) {
            return showMessageClient("Erro ao deletar professor!",Boolean.TRUE);
        }
    }

    @ModelAttribute(value = "teacherRequest")
    public TeacherRequest getTeacherRequest() {
        return new TeacherRequest();
    }

}
