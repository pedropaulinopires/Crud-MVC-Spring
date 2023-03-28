package com.pedro.spring.request;

import com.pedro.spring.domain.Teacher;
import com.pedro.spring.enums.StatusTeacher;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequest {

    @NotEmpty(message = "O nome é obrigatório!")
    private String name;
    @NotNull(message = "O salário é obrigatório!")
    private BigDecimal wage;
    private StatusTeacher statusTeacher;

    public Teacher build(){
        return new Teacher().builder()
                .name(this.name)
                .wage(this.wage)
                .statusTeacher(this.statusTeacher)
                .build();
    }

    public Teacher build(Teacher teacher,Long id){
        return teacher.builder()
                .id(id)
                .name(this.name)
                .wage(this.wage)
                .statusTeacher(this.statusTeacher)
                .build();
    }

    public void fromTeacher(Teacher teacher){
        this.name = teacher.getName();
        this.wage = teacher.getWage();
        this.statusTeacher = teacher.getStatusTeacher();
    }
}
