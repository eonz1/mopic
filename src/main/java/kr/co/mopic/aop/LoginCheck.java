package kr.co.mopic.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //어디에 어노테이션을 적용할 지 타겟을 정한다.
public @interface LoginCheck {

}