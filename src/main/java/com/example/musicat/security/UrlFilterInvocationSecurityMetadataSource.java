package com.example.musicat.security;

import com.example.musicat.domain.member.GradeResourceVO;
import com.example.musicat.mapper.member.ResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ResourceMapper resourceMapper;

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap = new LinkedHashMap<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        //DB에서 url과 권한 정보 가져오기
        List<GradeResourceVO> gradeResourceList = resourceMapper.selectGradeResourceList();

        //url에 권한 리스트 매칭
        for(GradeResourceVO gradeResource : gradeResourceList) {
            requestMap.put(new AntPathRequestMatcher(gradeResource.getResourceName()),
                    Arrays.asList(new SecurityConfig(gradeResource.getGrade())));
        }

        if(requestMap != null) {
            for(Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : requestMap.entrySet()) {
                RequestMatcher matcher = entry.getKey();
                if(matcher.matches(request)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    @Override
    //모든 권한 리스트 가져오기
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return requestMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}