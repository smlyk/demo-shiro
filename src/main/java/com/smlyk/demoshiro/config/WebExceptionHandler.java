package com.smlyk.demoshiro.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: always
 * @Date: 2020/11/6 3:15 下午
 */
@ControllerAdvice
@Component
public class WebExceptionHandler {

    /**
     * shiro权限 异常
     */
    @ExceptionHandler({ UnauthorizedException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void processUnauthorizedException(HttpServletRequest request, HttpServletResponse response, UnauthorizedException e) {
        try {
            request.getRequestDispatcher("/403").forward(request, response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
