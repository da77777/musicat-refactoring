package com.example.musicat.service.member;

import com.example.musicat.domain.member.GradeResourceVO;
import com.example.musicat.domain.member.ResourceVO;

import java.util.List;
import java.util.Map;

public interface ResourceService {
    GradeResourceVO retrieveOneGradeResource(int gradeResourceNo);

    //grde와 resource 목록 조회
    List<GradeResourceVO> retrieveGradeResourceList();

    //grade와 resource 추가
    void registerGradeResource(GradeResourceVO gradeResourceVo);

    //grade와 resource 중복 검사
    Integer retrieveDuplicatedResource(String resourceName);

    //grade와 resource 수정
    void modifyGradeResource(GradeResourceVO gradeResourceVo);

    //grade와 resource 삭제
    void removeGradeResource(int gradeResourceNo);

    //리소스 목록 조회
    List<ResourceVO> retrieveResourceList();
}
