package com.example.musicat.controller;

import com.example.musicat.domain.board.FileVO;
import com.example.musicat.domain.member.MemberVO;
import com.example.musicat.domain.member.ProfileVO;
import com.example.musicat.security.MemberAccount;
import com.example.musicat.service.member.MemberService;
import com.example.musicat.service.member.ProfileService;
import com.example.musicat.util.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.lang.reflect.Member;
import java.net.MalformedURLException;

@Controller
@Slf4j
public class ProfileController {
    @Autowired private ProfileService profileService;
    @Value("${file.profileDir}") private String profileFileDir;


    // 프로필 페이지 이동, session 정보를 가져와서 이동할 예정. 기능 구현을 위해서 임시 처리
    @GetMapping("/profile")
    public String chooseProfile(Model model) throws Exception{
        MemberVO member = HomeController.checkMemberNo();

        try {
            ProfileVO profile = profileService.retrieveProfile(member.getNo());
            log.info("----- sys : " + profile.getSystemFileName() + " ----- origin : " + profile.getOriginalFileName());
            model.addAttribute("member", member);
            model.addAttribute("profile", profile);
            return "view/member/profile";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 프로필 미리보기 출력
    @ResponseBody
    @GetMapping("/profileTempImage/{filename}")
    public Resource showProfileTempImage(@PathVariable String filename) throws MalformedURLException {
        log.info("프로필 미리보기 출력 : " + profileFileDir + filename);
        return new UrlResource("file:" + profileFileDir + filename);
    }

    // 프로필 이미지
    @ResponseBody
    @GetMapping("/profileImage/{no}")
    public Resource showProfileImage(@PathVariable int no) throws MalformedURLException {
        try{
            ProfileVO profile = profileService.retrieveProfile(no);
            log.info("----- profile image : " + profile);
            
            //조회 불가할 때 처리 필요
            
            String filename = profile.getSystemFileName();
            log.info("프로필 이미지 출력 : " + profileFileDir + filename);
            return new UrlResource("file:" + profileFileDir + filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 프로필 수정
    @PostMapping("/profile/{no}")
    public String alterProfile(@PathVariable int no, @RequestParam("importAttachFile") MultipartFile importAttachFile, @RequestParam("bio") String bio, @RequestParam("resetFlag") String flag, HttpServletRequest req) throws Exception {
        log.info("update 시작");
        log.info("flag : " + flag);
        log.info("null : " + importAttachFile.getOriginalFilename().isEmpty());
        log.info("mul : " + importAttachFile.getOriginalFilename());
        try {
            if(!importAttachFile.getOriginalFilename().isEmpty()){
                ProfileVO oldProfile = profileService.retrieveProfile(no);
                profileService.deleteProfilePhoto(oldProfile);
                ProfileVO newProfile = profileService.uploadProfilePhoto(importAttachFile);
                profileService.modifyProfile(no, newProfile);
            }
            else{
                if(flag.equals("true")) {
                    ProfileVO oldProfile = profileService.retrieveProfile(no);
                    profileService.deleteProfilePhoto(oldProfile);
                    ProfileVO newProfile = profileService.resetProfilePhoto();
                    profileService.modifyProfile(no, newProfile);
                }
            }
            profileService.modifyBio(no, bio);
            return "redirect:/myPage/Playlist/"+no;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
