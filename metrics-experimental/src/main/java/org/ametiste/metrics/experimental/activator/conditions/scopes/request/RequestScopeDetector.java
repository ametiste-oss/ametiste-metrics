package org.ametiste.metrics.experimental.activator.conditions.scopes.request;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 *
 * @since
 */
public final class RequestScopeDetector {

    public static boolean isNotRequestScoped() {
        return !isRequestScoped();
    }

    public static boolean isRequestScoped() {
        // NOTE: ugly, but easiest known way to check is it request scope or not
        try {
            return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest() != null;
        } catch (IllegalStateException e) {
            // NOTE: if there is no request scope, RequestContextHolder.currentRequestAttributes() will
            // throw IllegalStateException
            return false;
        }
    }

    public static boolean isEnabledForRequest(String parameterName) {

        if (isNotRequestScoped()) {
            return false;
        }

        try {
            return Optional.of(((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest().getParameter(parameterName))
                    .orElse("false")
                    .equals("true");
        } catch (IllegalStateException e) {
            // NOTE: if there is no request scope, RequestContextHolder.currentRequestAttributes() will
            // throw IllegalStateException
            return false;
        }

    }

}
