package com.example.musicat.service.member;

import com.example.musicat.domain.member.ProfileVO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;

public interface ProfileService {
    public ProfileVO uploadProfilePhoto(MultipartFile multipartFile) throws Exception;
    public ProfileVO resetProfilePhoto() throws Exception;
    public void modifyProfile(int no, ProfileVO profile) throws Exception;
    public ProfileVO retrieveProfile(int no) throws Exception;
    public void addProfile(int no) throws Exception;
    public void deleteProfilePhoto(ProfileVO profile) throws Exception;
    public void modifyBio(int no, String bio) throws Exception;
}
