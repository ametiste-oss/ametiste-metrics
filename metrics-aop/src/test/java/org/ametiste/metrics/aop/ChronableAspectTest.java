package org.ametiste.metrics.aop;

import org.ametiste.metrics.MetricsService;
import org.ametiste.metrics.annotations.Chronable;
import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ChronableAspectTest {
    @Mock
    private ExpressionParser parser;

    @Mock
    private IdentifierResolver resolver;

    @Mock
    private MetricsService service;

    private ChronableAspect chronableAspect;

    @Mock
    private JoinPoint pjp;

    @Mock
    private Chronable chron;

    @Mock
    private Object returnedObject;

    @Mock
    private Expression exp;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        chronableAspect = new ChronableAspect(service, resolver, parser);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithNormalObjectWithEmptyChronableValue() {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("");
        when(chron.valueExpression()).thenReturn("");
        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, returnedObject, chron);

    }

    @Test
    public void testWithNormalObjectWithBadNameSuffixExpression() throws MetricExpressionParsingException {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("");
        when(chron.valueExpression()).thenReturn("eq");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class))).thenThrow(
                MetricExpressionParsingException.class);

        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, returnedObject, chron);
        verify(service, times(0)).createEvent(anyString(), anyInt());

    }

    @Test
    public void testWithNormalObjectWithBadFirstValue() throws MetricExpressionParsingException {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("");
        when(chron.valueExpression()).thenReturn("eq");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class)))
                .thenReturn("metric.name");

        when(parser.parseExpression(anyString())).thenThrow(
                MetricExpressionParsingException.class);
        chronableAspect.processTiming(pjp, returnedObject, chron);
        verify(service, times(0)).createEvent(anyString(), anyInt());

    }


    @Test
    public void testWithNormalObjectWithBadCondition() throws MetricExpressionParsingException {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("eq");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class)))
                .thenReturn("metric.name");
        when(parser.parseExpression(anyString())).thenThrow(
                MetricExpressionParsingException.class);
        when(exp.getValue(any(ParserContext.class), any(Class.class))).thenReturn(2L);
        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, returnedObject, chron);
        verify(service, times(0)).createEvent(anyString(), anyInt());

    }

    @Test
    public void testWithNormalObjectWithOneGoodValue() throws MetricExpressionParsingException {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("");
        when(chron.valueExpression()).thenReturn("eq");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class)))
                .thenReturn("metric.name");
        when(parser.parseExpression(anyString())).thenReturn(exp, exp);
        when(exp.getValue(any(ParserContext.class), any(Class.class))).thenReturn(true, 2);
        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, returnedObject, chron);
        verify(service, times(1)).createEvent("metric.name", 2);

    }

    @Test
    public void testWithNormalObjectWithValue() throws MetricExpressionParsingException {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("2");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class)))
                .thenReturn("metric.name");
        when(parser.parseExpression(anyString())).thenReturn(exp, exp);
        when(exp.getValue(any(ParserContext.class), any(Class.class))).thenReturn(true);
        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, returnedObject, chron);
        verify(service, times(1)).createEvent("metric.name", 2);

    }

    @Test
    public void testWithNormalObjectWithTwoGoodValueAndFalseCondition() throws MetricExpressionParsingException {

        doReturn(Chronable.NO_EXCEPTION.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("");
        when(chron.valueExpression()).thenReturn("eq");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class)))
                .thenReturn("metric.name");
        when(parser.parseExpression(anyString())).thenReturn(exp, exp);
        when(exp.getValue(any(ParserContext.class), any(Class.class))).thenReturn(false, 2);
        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, returnedObject, chron);
        verify(service, times(0)).createEvent("metric.name", 2);

    }

    @Test
    public void testWithNormalObjectWithException() throws MetricExpressionParsingException {

        doReturn(Exception.class).when(chron).exceptionClass();
        when(chron.value()).thenReturn("");
        when(chron.valueExpression()).thenReturn("eq");
        when(resolver.getTargetIdentifier(anyString(), anyString(), any(AspectContext.class)))
                .thenReturn("metric.name");
        when(parser.parseExpression(anyString())).thenReturn(exp, exp);
        when(exp.getValue(any(ParserContext.class), any(Class.class))).thenReturn(true, 2);
        // when(chron.exceptionClass()).thenReturn(value);
        chronableAspect.processTiming(pjp, new IllegalArgumentException(), chron);
        verify(service, times(1)).createEvent("metric.name", 2);

    }

}
