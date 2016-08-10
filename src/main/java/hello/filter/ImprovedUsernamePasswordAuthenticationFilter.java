package hello.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImprovedUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    @Override
    protected String obtainUsername(HttpServletRequest request) {
        final String usernameParameter = getUsernameParameter();
        validateQueryParameter(request, usernameParameter);
        return super.obtainUsername(request);
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        final String passwordParameter = getPasswordParameter();
        validateQueryParameter(request, passwordParameter);
        return super.obtainPassword(request);
    }

    private void validateQueryParameter(HttpServletRequest request, String usernameParameter) {
        final String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            if (queryString.contains(usernameParameter))
                throw new AuthenticationServiceException("Query parameters for login are a prohibit, use message body only!");

        }
    }


}
