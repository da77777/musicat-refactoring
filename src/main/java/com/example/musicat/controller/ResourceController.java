package com.example.musicat.controller;

import com.example.musicat.domain.member.GradeResourceVO;
import com.example.musicat.domain.member.GradeVO;
import com.example.musicat.service.member.GradeService;
import com.example.musicat.service.member.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private GradeService gradeService;

    //리소스 + 권한 목록 조회
    @GetMapping("/resourceManager")
    public String resourceManager(Model model) {
        List<GradeResourceVO> gradeResourceList = this.resourceService.retrieveGradeResourceList();
        model.addAttribute("gradeResourceList", gradeResourceList);

        model.addAttribute("managerContent", "/view/security/resourceManager");

        return "view/home/viewManagerTemplate";
    }

    @ResponseBody
    @PostMapping("/selectListAddResource")
    public Map<String, Object> selectListAddResource() {
        Map<String, Object> map = new HashMap<>();

        ArrayList<GradeVO> gradeList = this.gradeService.retrieveGradeList();
        map.put("gradeList", gradeList);

        return map;
    }

    //resource + grade 추가
    @ResponseBody
    @PostMapping("/writeResource")
    public Map<String, Object> writeResource(@ModelAttribute("gradeResourceVo") GradeResourceVO gradeResourceVo) {

        Map<String, Object> map = new HashMap<>();

        Integer duplicatedResource = resourceService.retrieveDuplicatedResource(gradeResourceVo.getResourceName());
        if(duplicatedResource != null) {
            map.put("duplicated", 1);
        } else {
            map.put("duplicated", 0);
            gradeResourceVo.setResourceName(gradeResourceVo.getResourceName());
            gradeResourceVo.setResourceType(gradeResourceVo.getResourceType());
            gradeResourceVo.setGradeNo(gradeResourceVo.getGradeNo());
            this.resourceService.registerGradeResource(gradeResourceVo);
        }

        List<GradeResourceVO> gradeResourceList = this.resourceService.retrieveGradeResourceList();
        map.put("gradeResourceList", gradeResourceList);

        return map;
    }

    //수정 페이지
    @ResponseBody
    @PostMapping("/selectListModifyResource")
    public Map<String, Object> selectListModifyResource(
            @RequestParam("gradeResourceNo") int gradeResourceNo) {
        Map<String, Object> map = new HashMap<>();

        ArrayList<GradeVO> gradeList = this.gradeService.retrieveGradeList();
        map.put("gradeList", gradeList);

        GradeResourceVO oneGradeResource = this.resourceService.retrieveOneGradeResource(gradeResourceNo);
        map.put("oneGradeResource", oneGradeResource);

        return map;
    }

    @ResponseBody
    @PostMapping("/modifyResource")
    public Map<String, Object> modifyResource( @ModelAttribute("gradeResourceVo") GradeResourceVO gradeResourceVo) {
        Map<String, Object> map = new HashMap<>();

        Integer duplicatedResource = resourceService.retrieveDuplicatedResource(gradeResourceVo.getResourceName());
        if(duplicatedResource == null) { //중복x
            map.put("duplicated", 0);
            this.resourceService.modifyGradeResource(gradeResourceVo);
        } else { //중복o
            if (duplicatedResource == gradeResourceVo.getResourceNo()) {
                map.put("duplicated", 0);
                this.resourceService.modifyGradeResource(gradeResourceVo);
            } else {
                map.put("duplicated", 1);
            }
        }
        gradeResourceVo = this.resourceService.retrieveOneGradeResource(gradeResourceVo.getGradeResourceNo());
        map.put("gradeResourceVo", gradeResourceVo);
        return map;
    }

    //resource + grade 삭제
    @ResponseBody
    @PostMapping("/deleteResource")
    public Map<String, Object> deleteResource(@ModelAttribute("gradeResourceVo") GradeResourceVO gradeResourceVo) {

        this.resourceService.removeGradeResource(gradeResourceVo.getGradeResourceNo());

        Map<String, Object> map = new HashMap<>();
        List<GradeResourceVO> gradeResourceList = this.resourceService.retrieveGradeResourceList();
        map.put("gradeResourceList", gradeResourceList);

        return map;
    }
}
