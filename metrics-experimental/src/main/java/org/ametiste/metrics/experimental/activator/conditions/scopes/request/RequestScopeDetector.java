package org.ametiste.metrics.experimental.activator.conditions.scopes.request;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * <p>
 *     Helper functions to check <i>Spring Web MVC</i> webrequest-scope context.
 * </p>
 *
 * @since 0.2.0
 */
public final class RequestScopeDetector {

    /**
     * <p>
     *     Check that the operation is called out of the request-scope.
     * </p>
     *
     * @return <b>false</b> if called within the request-scope, <b>true</b> if not.
     */
    public static boolean isNotRequestScoped() {
        return !isRequestScoped();
    }

    /**
    * <p>
    *     Check that the operation is called within the request-scope.
    * </p>
     *
     * @return <b>true</b> if called within the request-scope, <b>false</b> if not.
    */
    public static boolean isRequestScoped() {
        // NOTE: ugly, but easiest known way to check is it request scope or not
        try {
            return extractHttpServletRequest() != null;
        } catch (IllegalStateException e) {
            // NOTE: if there is no request scope, RequestContextHolder.currentRequestAttributes() will
            // throw IllegalStateException
            return false;
        }
    }

    /**
     * <p>
     *     Check that the request parameter containes variable with the given name. And
     *     the variable has value of <i>'true'</i>.
     * </p>
     *
     * @return <b>true</b> if called within the request-scope, <b>false</b> if not.
     */
    public static boolean isTrueForRequest(String parameterName) {

        if (isNotRequestScoped()) {
            return false;
        }

        try {
            return extractRequestParameter(parameterName)
                    .orElse(Boolean.TRUE.toString())
                    .equals(Boolean.FALSE.toString());
        } catch (IllegalStateException e) {
            // NOTE: if there is no request scope, RequestContextHolder.currentRequestAttributes() will
            // throw IllegalStateException
            return false;
        }
    }

    /**
     * <p>
     *     Extracts a parameter with the given name from the request bound to current context.
     * </p>
     * <p>
     *     <b>WARNING</b>
     *
     *     This operation is unsafe cos <i>Spring Framework</i> does
     *     not provide any interface to check scope, please do exceptions handeling.
     * </p>
     *
     * @param parameterName name of parameter to be extracted
     * @return optional value of parameter with the given name
     * @throws IllegalStateException in case where operation called out of request-scope
     */
    public static Optional<String> extractRequestParameter(String parameterName) {
        return Optional.ofNullable(extractHttpServletRequest().getParameter(parameterName));
    }

    /**
     * <p>
     *     Extracts {@code HttServletRequest} from the request bound to current context.
     * </p>
     *
     * <p>
     *     <b>WARNING</b>
     *
     *     This operation is unsafe cos <i>Spring Framework</i> does
     *     not provide any interface to check scope, please do exceptions handeling.
     * </p>
     *
     * @return {@code HttServletRequest} bound to the current request contex, if any
     * @throws IllegalStateException in case where operation called out of request-scope
     */
    private static HttpServletRequest extractHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

}
