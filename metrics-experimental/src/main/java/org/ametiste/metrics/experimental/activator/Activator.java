package org.ametiste.metrics.experimental.activator;

import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 * Abstract activator class that defines common activation check.
 * </p>
 * <p>
 * <p>
 * TODO: controversial solution to have it as avstract class, would work only for some cases.
 * </p>
 */
public abstract class Activator {

    private final List<ActivationCondition> activationConditions;

    public Activator(List<ActivationCondition> activationConditions) {
        this.activationConditions = activationConditions;
    }

    protected final boolean isActive() {
        return activationConditions
                .stream()
                .map(ActivationCondition::checkActivation)
                .allMatch(Boolean.TRUE::equals);
    }

    protected final void invokeIfActive(Runnable call) {
        if (isActive()) {
            call.run();
        }
    }

    protected final <T> T invokeIfActiveAndReturn(Supplier<T> sup, T elseReturn) {
        return isActive() ? sup.get() : elseReturn;
    }

}
