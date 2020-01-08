package com.monitor.task.security.controller;

import com.monitor.task.common.AppConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/", produces = "application/json")
@Slf4j
public class SecurityController {
    @Autowired
    @Qualifier("springSecurityFilterChain")
    private Filter springSecurityFilterChain;

    @PostConstruct
    public void inPostConstruct() {
        log.info("FILTERS");
        FilterChainProxy filterChainProxy = (FilterChainProxy) springSecurityFilterChain;
        List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
        list.stream()
                .flatMap(chain -> chain.getFilters().stream())
                .forEach(filter -> log.info(filter.getClass() + " "));
        log.info("!FILTERS");
    }

    @PostMapping("/authorize")
    public Map<String, Object> auth(HttpSession session) {
        return new HashMap<String,Object>() {{
            put(AppConst.AUTH_TOKEN_NAME, session.getAttribute(AppConst.AUTH_TOKEN_NAME));
        }};
    }
}
