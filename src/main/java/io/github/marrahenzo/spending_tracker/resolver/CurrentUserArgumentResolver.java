package io.github.marrahenzo.spending_tracker.resolver;

import io.github.marrahenzo.spending_tracker.annotation.CurrentUser;
import io.github.marrahenzo.spending_tracker.exception.UnauthorizedException;
import io.github.marrahenzo.spending_tracker.model.User;
import io.github.marrahenzo.spending_tracker.util.Constants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        var session = request.getSession(false);
        var user = (session != null) ? session.getAttribute(Constants.SESSION_USER) : null;

        if (user == null) {
            throw new UnauthorizedException();
        }

        return user;
    }

}
