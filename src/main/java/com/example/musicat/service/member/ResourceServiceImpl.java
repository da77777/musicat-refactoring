package com.example.musicat.service.member;

import com.example.musicat.domain.member.GradeResourceVO;
import com.example.musicat.domain.member.ResourceVO;
import com.example.musicat.repository.member.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public GradeResourceVO retrieveOneGradeResource(int gradeResourceNo) {
        return this.resourceDao.selectOneGradeResource(gradeResourceNo);
    }

    @Override
    public List<GradeResourceVO> retrieveGradeResourceList() {
        return this.resourceDao.selectGradeResourceList();
    }

    @Override
    public void registerGradeResource(GradeResourceVO gradeResourceVo) {
        this.resourceDao.insertGradeResource(gradeResourceVo);
    }

    @Override
    public Integer retrieveDuplicatedResource(String resourceName) {
        return this.resourceDao.selectDuplicatedResource(resourceName);
    }

    @Override
    public void modifyGradeResource(GradeResourceVO gradeResourceVo) {
        this.resourceDao.updateGradeResource(gradeResourceVo);
    }

    @Override
    public void removeGradeResource(int gradeResourceNo) {
        this.resourceDao.deleteGradeResource(gradeResourceNo);
    }

    @Override
    public List<ResourceVO> retrieveResourceList() {
        return this.resourceDao.selectResourceList();
    }
}
