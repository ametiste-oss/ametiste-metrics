package org.ametiste.metrics.experimental.resolver.templated;

/**
 *
 * @since
 */
public class CorePrefixVariable implements VariableBind {

    private final String corePrefixValue;

    public CorePrefixVariable(String corePrefixValue) {
        this.corePrefixValue = corePrefixValue;
    }

    @Override
    public BindedVariable bindVariable() {
        return new BindedVariable("corePrefix", corePrefixValue);
    }

}
