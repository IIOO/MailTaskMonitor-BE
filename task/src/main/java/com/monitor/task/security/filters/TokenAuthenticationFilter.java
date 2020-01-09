package com.monitor.task.security.filters;

import com.monitor.task.common.AppConst;
import com.monitor.task.security.persistance.AuthTokenEntity;
import com.monitor.task.security.service.AuthTokenService;
import com.monitor.task.user.persistance.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class TokenAuthenticationFilter implements Filter {
    private final AuthTokenService tokenService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String tokenId = request.getHeader(AppConst.AUTH_TOKEN_NAME);

        if (Objects.nonNull(tokenId)) {
            AuthTokenEntity authToken = this.tokenService.findById(Long.parseLong(tokenId));

            if (Objects.nonNull(authToken)) {
                if (tokenService.isTokenExpired(authToken)) {
                    this.tokenService.delete(authToken);
                } else {
                    final UserEntity principal = authToken.getUser();
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    this.tokenService.update(authToken);
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
