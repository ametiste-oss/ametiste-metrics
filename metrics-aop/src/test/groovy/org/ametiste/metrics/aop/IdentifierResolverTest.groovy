package org.ametiste.metrics.aop

import org.springframework.expression.Expression
import org.springframework.expression.ExpressionParser
import spock.lang.Specification

/**
 * Created by atlantis on 11/2/15.
 */
class IdentifierResolverTest extends Specification {

    AspectContext context = Mock()
    ExpressionParser parser = Mock()
    IdentifierResolver resolver = new IdentifierResolver(parser)


    def initialization() {
        when: "resolver is initialized with null parser"
            new IdentifierResolver(null)
        then: "exception should be thrown"
            //nothing happens now

    }

    def getTargetIdentifierWithEmptyNames() {
        when: "id is resolved with both empty name and name suffix"
            resolver.getTargetIdentifier("", "", context)
        then: "exception is thrown"
            thrown(MetricExpressionParsingException)
    }

    def getTargetIdentifierWithParserException() {
        when: "parser throws exception when parses name suffix"
            parser.parseExpression(_) >> { throw new IllegalArgumentException() }
            resolver.getTargetIdentifier("", "something", context)
        then: "exception is thrown"
            thrown(MetricExpressionParsingException)
    }

    def getTargetIdentifierWithEmptyName() {
        when: "id is resolved with empty name"
            Expression expression = Mock()
            parser.parseExpression(_) >> expression
            expression.getValue(context, String.class) >> "resolved"
            String resolved = resolver.getTargetIdentifier("", "something", context)
        then: "id is only suffix"
            "resolved" == resolved
    }

    def getTargetIdentifierWithEmptyNameExp() {
        when: "id is resolved with empty name suffix"
            String resolved = resolver.getTargetIdentifier("name", "", context)
        then: "id is only name"
            "name" == resolved
    }

    def getTargetIdentifierWithBoth() {
        when: "id is resolved with name and name suffix"
            Expression expression = Mock()
            parser.parseExpression(_) >> expression
            expression.getValue(context, String.class) >> "resolved"
            String resolved = resolver.getTargetIdentifier("name", "something", context)
        then: "name concatenated with resolved name suffix with point"
            "name.resolved" == resolved
    }
}
