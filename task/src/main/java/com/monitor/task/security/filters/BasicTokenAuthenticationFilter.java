package com.monitor.task.security.filters;

import com.monitor.task.security.persistance.AuthTokenEntity;
import com.monitor.task.security.service.AuthTokenService;
import com.monitor.task.user.persistance.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.monitor.task.common.AppConst.AUTH_TOKEN_NAME;

public class BasicTokenAuthenticationFilter extends BasicAuthenticationFilter {
    private final AuthTokenService tokenService;

    public BasicTokenAuthenticationFilter(AuthenticationManager authenticationManager, AuthTokenService authTokenService) {
        super(authenticationManager);
        this.tokenService = authTokenService;
    }

    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              Authentication authResult) throws IOException {
        UserEntity user = (UserEntity) authResult.getPrincipal();

        tokenService.findByUser(user).forEach(tokenService::delete);

        AuthTokenEntity newToken = tokenService.create(user);

        request.getSession().setAttribute(AUTH_TOKEN_NAME, newToken.getId());
    }
}
