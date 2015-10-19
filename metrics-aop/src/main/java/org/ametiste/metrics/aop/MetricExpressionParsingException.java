package org.ametiste.metrics.aop;

/**
 * Points to errors during expression parsing exception
 *
 * @author ametiste
 * @since 0.1.0
 */
public class MetricExpressionParsingException extends Exception {

    public MetricExpressionParsingException(Exception e) {
        super(e);
    }

    public MetricExpressionParsingException(String message, Exception e) {
        super(message, e);
    }

    public MetricExpressionParsingException(String msg) {
        super(msg);
    }

}
