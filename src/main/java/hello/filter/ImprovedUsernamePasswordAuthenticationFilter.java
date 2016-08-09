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
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        final String queryString = request.getQueryString();

        if (!StringUtils.isEmpty(queryString)) {

            final String usernameParameter = getUsernameParameter();
            final String passwordParameter = getPasswordParameter();
            if (
                    queryString.contains(usernameParameter)
                            || queryString.contains(passwordParameter)
                    )
                throw new AuthenticationServiceException("Query parameters for login are a prohibit, use message body only!");
        }
        return super.attemptAuthentication(request, response);

    }

}
