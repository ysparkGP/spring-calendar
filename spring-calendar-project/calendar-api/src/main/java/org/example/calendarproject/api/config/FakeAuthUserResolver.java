package org.example.calendarproject.api.config;


import org.example.calendarproject.api.dto.AuthUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 귀찮게 세션 설정하지 않고 dev 환경일 때는 url 파라미터로 넘긴 값을 사용
 */
public class FakeAuthUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return AuthUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String userIdStr = webRequest.getParameter("userId");
        if(userIdStr == null){
            return new AuthUserResolver().resolveArgument(parameter, mavContainer, webRequest, binderFactory);
        }

        return AuthUser.of(Long.parseLong(userIdStr));
    }
}
