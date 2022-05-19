package com.example.musicat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class MusicatRestExceptionHandler {

    //@ExceptionHandler({HttpClientErrorException.class, HttpServerErrorException.class, UnknownHttpStatusCodeException.class})
    @ExceptionHandler(HttpClientErrorException.class)
    public ModelAndView handleHttpClientErrorException(HttpClientErrorException e) {

        log.info("localize Message : " + e.getLocalizedMessage());
        log.info("Status Text : " + e.getStatusText());
        log.info("Status Code : " + e.getStatusCode());
        log.info("ResponseBody as String : " + e.getResponseBodyAsString());

        /*
        // json 형태로도 리턴 가능
        Map<String, String> test = new HashMap<String, String>();
        test.put("attr1", "value1");
        test.put("attr2", "value2");
        return test;
        */

        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", e.getStatusCode().value());
        mv.addObject("errorMsg", checkHttpStatusCode(e.getStatusCode().value()));
        mv.setViewName("view/etc/httpError");
        return mv;
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public Map<String, String> handleHttpServerErrorException(HttpServerErrorException e) {
        Map<String, String> map = new HashMap<String, String>();

        log.error("HttpServerErrorException : ", e);
        map.put("Success", Integer.toString(0));
        map.put("errorCode", Integer.toString(e.getStatusCode().value()));
        map.put("errorMsg", checkHttpStatusCode(e.getStatusCode().value()));

        return map;
    }

    @ExceptionHandler(UnknownHttpStatusCodeException.class)
    public ModelAndView handleUnknownHttpStatusCodeException(UnknownHttpStatusCodeException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorCode", "UnknownError");
        mv.addObject("errorMsg", e.getMessage());
        mv.setViewName("view/etc/httpError");
        return mv;
    }
    private String checkHttpStatusCode(int statusCode) {

        String message = null;

        switch (statusCode) {
            case 400:
                message = "잘못된 요청입니다.";
                break;
            case 500:
                message = "요청을 처리하지 못했습니다.";
                break;
            default:
                message = "에러가 발생했습니다.";
        }

        return message;
    }
}
