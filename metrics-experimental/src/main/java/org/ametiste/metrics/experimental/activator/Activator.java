package org.ametiste.metrics.experimental.activator;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <p>
 *     Abstract activator class that defines common activation check.
 * </p>
 *
 * <p>
 *     TODO: controversial solution to have it as avstract class, would work only for some cases.
 * </p>
 *
 * @since
 */
public abstract class Activator {

    private final List<ActivationCondition> activationConditions;

    public Activator(List<ActivationCondition> activationConditions) {
        this.activationConditions = activationConditions;
    }

    protected boolean isActive() {
        return activationConditions
                .stream()
                .map(ActivationCondition::checkActivation)
                .allMatch(Boolean.TRUE::equals);
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
