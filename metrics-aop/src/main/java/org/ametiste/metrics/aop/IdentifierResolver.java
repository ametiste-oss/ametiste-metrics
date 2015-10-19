package org.ametiste.metrics.aop;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;


/**
 * Builds metric identifier from name and suffixExpression
 *
 * @author ametiste
 * @since 0.1.0
 */
public class IdentifierResolver {

    private final ExpressionParser parser;

    public IdentifierResolver(ExpressionParser parser) {
        this.parser = parser;
    }


    /**
     * Builds metric identifier from name and nameSuffixExpression, parsing expression with target context
     *
     * @param name                 metrics name, unchangeable part, can be empty, cant be null
     * @param nameSuffixExpression expression of suffix, can be applied as only identifier if name is empty
     * @param context              target object and method context to parse expression
     * @return metric identifier
     * @throws MetricExpressionParsingException
     */
    public String getTargetIdentifier(String name, String nameSuffixExpression, AspectContext context)
            throws MetricExpressionParsingException {

        // TODO refactor this... :O :E

        String suffix = "";

        if (!nameSuffixExpression.isEmpty()) {
            try {
                Expression exp = parser.parseExpression(nameSuffixExpression);
                suffix = exp.getValue(context, String.class);
            } catch (Exception e) {
                throw new MetricExpressionParsingException(e);
            }
        }

        if (name.isEmpty() && suffix.isEmpty()) {
            throw new MetricExpressionParsingException(
                    "metric name cant be empty, should be name or expression defining name");
        }
        if (name.isEmpty()) {
            return suffix;
        }
        if (suffix.isEmpty()) {
            return name;
        }
        return name + "." + suffix;
    }

}
