package project.demo.security.jwt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String exception = (String)request.getAttribute("exception");

        //response.sendError(HttpStatus.UNAUTHORIZED.value(), exception);

        if(exception == null){
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "is null");
        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals("MalformedJwtException")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "MalformedJwtException");
        }
        //토큰 만료된 경우
        else if(exception.equals("ExpiredJwtException")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "ExpiredJwtException");
        }


    }
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
}
