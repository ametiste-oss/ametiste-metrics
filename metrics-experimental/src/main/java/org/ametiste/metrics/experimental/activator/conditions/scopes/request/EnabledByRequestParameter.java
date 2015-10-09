package org.ametiste.metrics.experimental.activator.conditions.scopes.request;

import org.ametiste.metrics.experimental.activator.ActivationCondition;

/**
 *
 * @since
 */
public class EnabledByRequestParameter implements ActivationCondition {

    private final String parameterName;

    public EnabledByRequestParameter(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public boolean checkActivation() {
        return RequestScopeDetector.isEnabledForRequest(parameterName);
    }

}
