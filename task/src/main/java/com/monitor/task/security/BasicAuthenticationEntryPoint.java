package com.monitor.task.security;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.monitor.task.common.AppConst.RESPONSE_MSG_KEY;

@Component
@RequiredArgsConstructor
public class BasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final Gson gson;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        // Construct response on error
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.addHeader("Content-Type", "application/json");

        httpServletResponse.addHeader("Access-Control-Allow-Headers", "session");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "HEAD,GET,PUT,POST,DELETE,PATCH");
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");

        HashMap<String,String> response = new HashMap<>();
        response.put(RESPONSE_MSG_KEY, "Invalid credentials!");
        httpServletResponse.getWriter().println(gson.toJson(response));
    }
}
