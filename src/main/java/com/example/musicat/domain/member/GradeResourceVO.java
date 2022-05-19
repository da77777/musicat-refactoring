package com.example.musicat.domain.member;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Alias("gradeResourceVo")
public class GradeResourceVO {
    private int gradeResourceNo;
    private int resourceNo;
    private String resourceName;
    private String resourceType;
    private int gradeNo;
    private String grade;
    private String gradeName;
}
