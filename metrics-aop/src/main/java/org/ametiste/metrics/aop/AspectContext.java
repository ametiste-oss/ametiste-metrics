package org.ametiste.metrics.aop;

/**
 * Context for SpEL expressions from annotations, contains target method arguments, target object and
 * in some cases result of method execution
 *
 * @author ametiste
 * @since 0.1.0
 */
public class AspectContext {

    private final Object[] args;
    private final Object target;
    private final Object result;

    /**
     * Constructor for build context of methods with normal execution
     *
     * @param args          target method arguments array
     * @param target        target object
     * @param returnedValue result of target method execution
     */
    public AspectContext(Object[] args, Object target, Object returnedValue) {
        this.args = args;
        this.target = target;
        this.result = returnedValue;
    }

    /**
     * Constructor for build context of methods with abnormal execution, ended with exception
     *
     * @param args   target method arguments array
     * @param target target object
     */
    public AspectContext(Object[] args, Object target) {
        this.args = args;
        this.target = target;
        this.result = null;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getTarget() {
        return target;
    }

    public Object getResult() {
        return result;
    }

}
