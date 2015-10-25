package org.ametiste.metrics.experimental.activator;

import java.util.List;

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

    protected final void invokeIfActive(Runnable sup) {
        if (isActive()) {
            sup.run();
        }
    }


//    protected void invokeIfActivated(Function<T, String> supplier, String elseReturn) {
//
//        if (isActive) {
//            return supplier.apply();
//        } else {
//            return elseReturn;
//        }
//    }

}
