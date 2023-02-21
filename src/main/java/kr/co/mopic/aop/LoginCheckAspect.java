package kr.co.mopic.aop;

import kr.co.mopic.dto.response.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Aspect
@Component
@SuppressWarnings("unchecked")
public class LoginCheckAspect {

    @Before("@annotation(kr.co.mopic.aop.LoginCheck) && @annotation(LoginCheck)")
    public void loginCheck(JoinPoint jp) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();

        verifyUserSession(session);
    }

    private void verifyUserSession(HttpSession session) {

        UserDTO userDTO = ((UserDTO) session.getAttribute("USER"));


        if (userDTO == null) {
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.") {};
        }
    }
}
