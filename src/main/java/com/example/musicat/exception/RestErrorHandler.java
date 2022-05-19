package com.example.musicat.exception;


import org.json.JSONObject;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        // your error handling here
        System.out.println("statusCode : " + response.getStatusCode());
        System.out.println("statusText : " + response.getStatusText());
        System.out.println("headers : "  + response.getHeaders());

        byte[] b = FileCopyUtils.copyToByteArray(response.getBody());
        String str = new String(b);
        System.out.println("body : " + str);

        JSONObject jsonObject = new JSONObject(str);
        System.out.println("message : " + jsonObject.get("message"));
        System.out.println("statusCode : " + jsonObject.get("statusCode"));

        throw new RuntimeException("test error");

    }
}
