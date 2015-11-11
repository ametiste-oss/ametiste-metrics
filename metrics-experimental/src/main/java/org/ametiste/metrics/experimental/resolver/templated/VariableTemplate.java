package org.ametiste.metrics.experimental.resolver.templated;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @since
 */
public class VariableTemplate {

    private String resolved;

    public VariableTemplate(String template) {
        this.resolved = template;
    }

    public static VariableTemplate eval(String template, List<VariableBind> binds) {
        final VariableTemplate variableTemplate = new VariableTemplate(template);
        binds.forEach(variableTemplate::bind);
        return variableTemplate;
    }

    public static VariableTemplate eval(String template, VariableBind... binds) {
        return eval(template, Arrays.asList(binds));
    }

    public static String evalResolved(String template, List<VariableBind> binds) {
        return eval(template, binds).resolved();
    }

    public static String evalResolved(String template, VariableBind... binds) {
        return evalResolved(template, Arrays.asList(binds));
    }

    public VariableTemplate bind(VariableBind variableBind) {

        final BindedVariable variable = variableBind.bindVariable();

        // TODO: rework, VariableBind is ugly now, I want to allocate resolving to VariableBind
        // so that I can have easy conditional resolving and so one
        this.resolved = resolved.replaceAll(
                "\\{" + variable.name + "\\}",
                variable.value
        );

        return this;
    }

    public String resolved() {
        return resolved;
    }

}
