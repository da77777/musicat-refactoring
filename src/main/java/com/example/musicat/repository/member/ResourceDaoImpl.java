package com.example.musicat.repository.member;

import com.example.musicat.domain.member.GradeResourceVO;
import com.example.musicat.domain.member.ResourceVO;
import com.example.musicat.mapper.member.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Repository("resourceDao")
public class ResourceDaoImpl implements ResourceDao{

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public GradeResourceVO selectOneGradeResource(int gradeResourceNo) {
        return this.resourceMapper.selectOneGradeResource(gradeResourceNo);
    }

    @Override
    public List<GradeResourceVO> selectGradeResourceList() {
        return this.resourceMapper.selectGradeResourceList();
    }

    @Override
    public void insertGradeResource(GradeResourceVO gradeResourceVo) {
        this.resourceMapper.insertGradeResource(gradeResourceVo);
    }

    @Override
    public Integer selectDuplicatedResource(String resourceName) {
        return this.resourceMapper.selectDuplicatedResource(resourceName);
    }

    @Override
    public void updateGradeResource(GradeResourceVO gradeResourceVo) {
        this.resourceMapper.updateGradeResource(gradeResourceVo);
    }

    @Override
    public void deleteGradeResource(int gradeResourceNo) {
        this.resourceMapper.deleteGradeResource(gradeResourceNo);
    }

    @Override
    public List<ResourceVO> selectResourceList() {
        return this.resourceMapper.selectResourceList();
    }
}
