package com.portfolio.syscomm.logging.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 매서드 호출 로깅을 위한 커스텀 어노테이션
 * 이 어노테이션이 붙은 메서드는 파라미터, 실행 시간, 반환 값 등이 자동으로 로깅 됨.
 */
@Target(ElementType.METHOD)         // 메서드에만 적용
@Retention(RetentionPolicy.RUNTIME) // 런타임 시에도 어노테이션 정보 참조.
public @interface Loggable {
}
