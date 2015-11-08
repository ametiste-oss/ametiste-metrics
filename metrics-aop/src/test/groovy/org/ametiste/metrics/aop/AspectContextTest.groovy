package org.ametiste.metrics.aop

import spock.lang.Specification

/**
 * Created by atlantis on 11/2/15.
 */
class AspectContextTest extends Specification {

    Object[] args = new Object(){}
    Object result = new Object()
    Object target = new Object()

    private AspectContext context = new AspectContext(args, target, result)

    def initialization() {
        when: "context is initialized with null args object"
            new AspectContext(null, target);
        then: "nothin happens, but exception should be"
            // will be exception after fixes
        when: "context is initialized with null target object"
            new AspectContext(args, null);
        then: "nothin happens, but exception should be"
        // will be exception after fixes
        when: "context is initialized with null args object"
            new AspectContext(null, target, result);
        then: "nothin happens, but exception should be"
        // will be exception after fixes
        when: "context is initialized with null target object"
            new AspectContext(args, null, result);
        then: "nothin happens, but exception should be"
        // will be exception after fixes
        when: "context is initialized with null target object"
            new AspectContext(args, target, null);
        then: "nothin happens, but exception should be"
        // will be exception after fixes

    }

    def getArgs() {
        when: "getting args"
            Object[] args = context.getArgs()
        then: "should be expected args"
           args == this.args
    }

    def getTarget() {
        when: "getting target"
            Object t = context.getTarget()
        then: "should return target"
            t == target
    }

    def getResult() {
        when: "getting result"
            Object r = context.getResult()
        then: "should return result"
            r == result

    }

    def getResultWithNoResultContext() {
        given: "aspect with no result set"
            AspectContext context = new AspectContext(args, target)
        when: "getting result"
            Object r = context.getResult()
        then: "should return null"
            r == null

    }
}
