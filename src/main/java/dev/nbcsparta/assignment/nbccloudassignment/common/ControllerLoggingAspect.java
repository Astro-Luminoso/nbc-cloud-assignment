package dev.nbcsparta.assignment.nbccloudassignment.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = getCurrentRequest();

        String httpMethod = request != null ? request.getMethod() : "UNKNOWN";
        String requestUri = request != null ? request.getRequestURI() : "UNKNOWN";


        log.info(
                "[API - LOG] {}: {}",
                httpMethod,
                requestUri
        );

        try {
            return joinPoint.proceed();
        } catch (Throwable e) {

            log.error(
                    "[API - ERROR] {} {}",
                    httpMethod,
                    requestUri,
                    e
            );

            throw e;
        }
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            return null;
        }

        return attributes.getRequest();
    }
}