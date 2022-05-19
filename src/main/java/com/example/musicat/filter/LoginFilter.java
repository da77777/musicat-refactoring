
//package com.example.musicat.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.util.PatternMatchUtils;
//
//import lombok.extern.java.Log;
//
//@Log
//public class LoginFilter implements Filter {
//    private static final String[] whitelist = {"/", "/petopialogin", "/login", "/logout", "/main", "/join1", "/join", "/findPWD", "/sendmail"};
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String requestURI = httpRequest.getRequestURI();
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        boolean isResource = (requestURI.startsWith("/css/") || requestURI.startsWith("/webjars/") || requestURI.startsWith("/images/") || requestURI.startsWith("/js/"));
//
//        try {
//            if (isLoginCheckPath(requestURI) && !isResource) {
//                HttpSession session = httpRequest.getSession(false);
//                if (session == null || session.getAttribute("loginUser") == null) {
//                    log.info("미인증 사용자 요청 {}" + requestURI);
//                    //로그인으로 redirect
//                    //httpResponse.sendRedirect("/?redirectURL=" + requestURI);
//                    httpResponse.sendRedirect("/petopialogin");
//                    return; //미인증 사용자는 다음으로 진행하지 않음
//                }
//            }
//            chain.doFilter(request, response); //다음 필터 진행
//        } catch (Exception e) {
//            throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함
//        } finally {
//            //log.info("인증 체크 필터 종료 {}" + requestURI);
//        }
//    }
//
//    /**
//     * 화이트 리스트의 경우 인증 체크X
//     */
//    private boolean isLoginCheckPath(String requestURI) {
//        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
//    }
//
//
//}
