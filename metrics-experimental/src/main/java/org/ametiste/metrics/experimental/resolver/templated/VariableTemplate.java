package org.ametiste.metrics.experimental.resolver.templated;

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

    public VariableTemplate bind(VariableBind variableBind) {

        final BindedVariable variable = variableBind.bindVariable();

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
