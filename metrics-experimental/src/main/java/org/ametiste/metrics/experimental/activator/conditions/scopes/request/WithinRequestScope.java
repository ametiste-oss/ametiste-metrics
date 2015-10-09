package org.ametiste.metrics.experimental.activator.conditions.scopes.request;

import org.ametiste.metrics.experimental.activator.ActivationCondition;

/**
 *
 * @since
 */
public class WithinRequestScope implements ActivationCondition {

    @Override
    public boolean checkActivation() {
        return RequestScopeDetector.isRequestScoped();
    }

}
