package fi.solita.clamav;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author ashutoshmimani
 */
@Component
public class AuthKeyFilter extends OncePerRequestFilter
{

    @Value("${apikey}")
    private String apikey;

    private static final String API_KEY_HEADER = "X-Api-Key";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
    {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (request.getRequestURI().equals("/")
                || (!StringUtils.isEmpty(apiKey) && apiKey.equals(apikey)))
        {
            chain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED_401);
    }
}
