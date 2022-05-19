package com.example.musicat.security;

import com.example.musicat.domain.member.MemberVO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    //인증 커스텀
    @Bean
    public AuthenticationProvider customAutheticationProvider() {
        return new CustomAutheticationProvider();
    }

    //ApplicationListener<SessionDestroyedEvent> ( SessionDestroyListener )를 사용하기 위한 Bean 등록
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher(){
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher(){
        });
    }

    //security 기본 필터 작동 전에 처리할 내용들
    @Bean
    public FilterSecurityInterceptor customFilterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager()); //AccessDecisionManager에 권한 검사 위임
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        return filterSecurityInterceptor;
    }

    //DB에서 리소스와 권한 정보 매칭
    @Bean
    public FilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
        return new UrlFilterInvocationSecurityMetadataSource();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 권한 계층 설정
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER > ROLE_ANONYMOUS");
        return roleHierarchy;
    }

    // 권한 계층 부여
    @Bean
    public AffirmativeBased accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();

        //RoleHierarchyVoter : 계층형 Role 지원
        RoleVoter roleVoter = new RoleHierarchyVoter(roleHierarchy());
        roleVoter.setRolePrefix("");

        decisionVoters.add(roleVoter);

        AffirmativeBased affirm = new AffirmativeBased(decisionVoters);
        affirm.setAllowIfAllAbstainDecisions(true);

        return affirm;
    }

    //인증에 대한 지원을 설정하는 메소드를 가지고 있음 ( customAutheticationProvider : 인증 커스텀 )
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAutheticationProvider());
    }

    //필요 없는 인증처리 방지를 위한 정적파일 ignoring 처리 : ignoring 처리하지 않으면 permitAll에 포함되므로 static에 있는 파일들도 인증처리를 거치게 됨
    //정적파일 뿐만 아니라 인증이 필요 없는 url은 ignoring 처리 해주는 것이 좋음
    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/static/**");
        //web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //권한 계층 설정 필터를 먼저 실행하도록
        http
                .addFilterBefore(customFilterSecurityInterceptor(), FilterSecurityInterceptor.class);

        http
                .csrf().disable();

//        http
//                .authorizeRequests()
//                .antMatchers("/user/**", "/ChangePwd/**", "/logout", "/articles/insert").authenticated() //
//                .antMatchers("/manager/**", "/members/**", "/boardManager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')" )
//                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .anyRequest().permitAll();

        http
                .formLogin()
                .loginPage("/musicatlogin")
                .loginProcessingUrl("/login")
                .usernameParameter("email") //view에서 들어오는 아이디 파라미터명 명시
                .passwordParameter("password") //view에서 들어오는 비밀번호 파라미터명 명시
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        //인증 성공 시 인증 결과를 담은 인증 객체를 파라미터로 받음 (인증 요청하지 않은 사용자의 정보는 HomController(/main)에서 처리해줌
                        MemberVO member = ((MemberAccount) authentication.getPrincipal()).getMemberVo();

                        //사용자 요청페이지 저장
                        RequestCache requestCache = new HttpSessionRequestCache();
                        SavedRequest savedRequest = requestCache.getRequest(request, response);

                        if(savedRequest == null) { //요청페이지가 없으면 보낼 url
                            response.sendRedirect("/main");
                        } else { //요청 페이지가 있으면 보낼 url
                            response.sendRedirect(savedRequest.getRedirectUrl());
                        }
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

                        //로그인 실패 예외 발생 시 처리
                        if (exception instanceof UsernameNotFoundException) { //DB에 일치하는 email이 없는 경우
                            request.setAttribute("loginFailMessage", "아이디 또는 비밀번호를 잘못 입력하였습니다.");
                            log.info(request.getAttribute("loginFailMessage").toString());
                        } else if (exception instanceof BadCredentialsException) { //비밀번호가 틀린 경우
                            request.setAttribute("loginFailMessage", "아이디 또는 비밀번호를 잘못 입력하였습니다.");
                            log.info(request.getAttribute("loginFailMessage").toString());
                        } else if (exception instanceof DisabledException) { //Ban 처리된 회원인 경우
                            request.setAttribute("loginFailMessage", "활동정지 된 계정입니다.");
                            log.info(request.getAttribute("loginFailMessage").toString());
                        } else if (exception instanceof AccountExpiredException) { //isMember 1인 경우
                            request.setAttribute("loginFailMessage", "탈퇴처리 된 계정입니다.");
                            log.info(request.getAttribute("loginFailMessage").toString());
                        } else if (exception instanceof SessionAuthenticationException) {
                            request.setAttribute("loginFailMessage", "생성 가능한 세션 개수를 초과하였습니다. 브라우저에서 로그아웃 후 다시 시도해주세요.");
                        }

                        RequestDispatcher dispatcher = request.getRequestDispatcher("/musicatlogin");
                        dispatcher.forward(request, response);
                    }
                });
        http
                .exceptionHandling().accessDeniedPage("/accessDenideGrade"); //403 에러 뜨면 이동할 페이지
        
        http
                .logout()
                .logoutUrl("/logout") //로그아웃 처리 url
                .invalidateHttpSession(true) //세션비우기
                .deleteCookies("JSESSIONID", "remember-me") //로그아웃 시 Tomcat이 발급한 세션 유지 쿠키, 자동 로그인을 위한 remember-me 쿠키 삭제(remeberme 사용 했을 때 필요)
                .logoutSuccessUrl("/musicatlogin"); //로그아웃 후 이동할 페이지

        http
                .sessionManagement() //세션 관리
                .sessionFixation().changeSessionId() //인증이 필요할 때마다 새로운 세션을 만들어 쿠키 조작을 방지
                .maximumSessions(3) //최대 세션 개수
                .expiredUrl("/expiredUrl") //session 만료 시 이동 페이지
                .maxSessionsPreventsLogin(true); //false : 이전에 로그인한 세션 만료, true : 나중에 로그인 시도하는 세션 생성 불가(로그인 불가)

        //RememberMeAuthenticationFilter가 작동하는 조건
        //1. authentication(인증이 성공한 사용자의 정보를 담은 인증객체)이 null인 경우(security context안에 authentication이 존재하지 않는 경우(즉, session이 끊겼거나 만료된 경우)),
        //2. form 인증 당시 remember Me 기능을 활성화하여 rememberMe 쿠키를 받은 경우에 작동
        //쿠키에 JSESSIONID와 remember-me 토큰이 저장됨. remember-me 체크하지 않을 경우 JSESSIONID삭제하면 다시 로그인해아하나, remember-me 쿠키를 사용할 경우 JSESSIONID 삭제해도 재인증 필요x
        http
                .rememberMe()
                .rememberMeParameter("remember-me") //form에서 rememberMe 기능 사용 여부를 체크할 때 받을 파라미터명과 동일해야 함
                .tokenValiditySeconds(3600) //rememberMe 토큰 만료 기간
                .alwaysRemember(false) //remember Me 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService); //rememberMe 인증 시 인증 계정 조회를 위해 필요
    }

}
