package com.example.musicat.service.member;

import com.example.musicat.domain.board.FileVO;
import com.example.musicat.domain.member.ProfileVO;
import com.example.musicat.mapper.member.ProfileMapper;
import com.example.musicat.util.FileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service("profileService")
@Slf4j
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService{
    @Autowired private ProfileMapper profileMapper;
    @Autowired private FileManager fileManager;

//    private static final String initOriginImg = "basicImage.png"; // 기본 이미지 original name
//    private static final String initSysImg = "basicImage.png"; // 기본 이미지 system name

    private final String initOriginImg = fileManager.initOriginImage; // 기본 이미지 original name
    private final String initSysImg = fileManager.initSysImage; // 기본 이미지 system name

    // 회원가입 시 프로필 생성, 회원가입 쪽에 해당 서비스 붙일 것.
    @Override
    @Transactional
    public void addProfile(int no) throws Exception{
        String location = fileManager.profileFileDir + initSysImg;
        log.info("----- 프로필 파일 경로 location : " + location);
        File file = new File(location);
        long fileSize = file.length();
        ProfileVO profile = new ProfileVO(no, initOriginImg, initSysImg, fileSize);
        profileMapper.insertProfile(profile);
    }

    // 프로필 조회
    @Override
    public ProfileVO retrieveProfile(int no) throws Exception{
        return profileMapper.selectProfile(no);
    }

    // 프로필 수정
    @Transactional
    @Override
    public void modifyProfile(int no, ProfileVO profile) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("no", no);
        map.put("profile", profile);
        profileMapper.updateProfile(map);
    }

    // 프로필 수정 시 파일 로컬 저장
    @Override
    public ProfileVO uploadProfilePhoto(MultipartFile multipartFile) throws Exception {
        log.info("----- 프로필 수정 시 파일 업로드");
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String systemFileName = fileManager.createSystemFileName(originalFileName);
        Long fileSize = multipartFile.getSize();
        multipartFile.transferTo(new File(fileManager.profileFileDir + systemFileName));

        return new ProfileVO(originalFileName, systemFileName, fileSize);
    }

    // 프로필 이미지 업데이트 시 기존 프로필 이미지 삭제
    @Override
    public void deleteProfilePhoto(ProfileVO profile) throws Exception {
        File file = new File(fileManager.profileFileDir + profile.getSystemFileName());
        if(!(file.getName().equals(initSysImg))) {
            if(file.exists()) {
                log.info("존재");
                file.delete();
            }
            else{
                log.info("존재하지 않음");
            }
        }
    }

    // 기본 이미지로 변경
    @Override
    public ProfileVO resetProfilePhoto() throws Exception {
//        String extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//        String systemFileName = UUID.randomUUID().toString() + "." + extension;
//        String location = profileFileDir + "profile/" + originalFileName;

        log.info("----- 기본 이미지로 변경");
        String originalFileName = initOriginImg;
        String systemFileName = fileManager.createSystemFileName(originalFileName);
        String location = fileManager.profileFileDir + originalFileName;
        long fileSize = location.length();
        String newLocation = fileManager.profileFileDir + systemFileName;


        // 원본 이미지 복사 후 새로운 system이름으로 저장
        final int N = 1024;
        InputStream is = null;
        OutputStream os = null;
        File origin = new File(location);
        byte[] buffer = new byte[N];

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            is = new FileInputStream(origin);
            os = new FileOutputStream(newLocation);

            bis = new BufferedInputStream(is, N);
            bos = new BufferedOutputStream(os, N);

            int n = 0;
            while((n=bis.read(buffer)) != -1) {
                bos.write(buffer, 0, n);
            }

            os.flush();
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
                bis.close();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ProfileVO profile = new ProfileVO(originalFileName, systemFileName, fileSize);
        return profile;
    }

    // 프로필 자기소개 변경
    @Override
    @Transactional
    public void modifyBio(int no, String bio) throws Exception {
        Map<String, Object> map =  new HashMap<String,Object>();
        map.put("no", no);
        map.put("bio", bio);
        profileMapper.updateBio(map);
    }
}
