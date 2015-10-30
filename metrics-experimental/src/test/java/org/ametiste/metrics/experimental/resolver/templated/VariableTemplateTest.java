package org.ametiste.metrics.experimental.resolver.templated;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 *
 * @since
 */
public class VariableTemplateTest {

    private final static String TEST_TEMPLATE = "{firstTestVar}.{secondTestVar}";

    private final static String firstTestVarName = "firstTestVar";
    private final static String firstTestVarValue = "TEST_VAR_1";

    private final static String secondTestVarName = "secondTestVar";
    private final static String secondTestVarValue = "TEST_VAR_2";

    private final String EXPECTED_EVAL_TEST = "TEST_VAR_1.TEST_VAR_2";

    private VariableTemplate variableTemplate;

    @Mock
    private VariableBind firstBind;

    @Mock
    private VariableBind secondBind;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        variableTemplate = new VariableTemplate(TEST_TEMPLATE);
    }

    @Test
    public void testEvalResolved() throws Exception {

        when(firstBind.bindVariable()).thenReturn(new BindedVariable(firstTestVarName, firstTestVarValue));
        when(secondBind.bindVariable()).thenReturn(new BindedVariable(secondTestVarName, secondTestVarValue));

        final String resolved = VariableTemplate.evalResolved(
                TEST_TEMPLATE,
                firstBind, secondBind
        );

        assertEquals(EXPECTED_EVAL_TEST, resolved);
    }

    @Test
    public void testEval() throws Exception {

        when(firstBind.bindVariable()).thenReturn(new BindedVariable(firstTestVarName, firstTestVarValue));
        when(secondBind.bindVariable()).thenReturn(new BindedVariable(secondTestVarName, secondTestVarValue));

        final String resolved = VariableTemplate.eval(
                TEST_TEMPLATE,
                firstBind, secondBind
        ).resolved();

        assertEquals(EXPECTED_EVAL_TEST, resolved);
    }

    @Test
    public void testMultipleBind() throws Exception {

        when(firstBind.bindVariable()).thenReturn(new BindedVariable(firstTestVarName, firstTestVarValue));
        when(secondBind.bindVariable()).thenReturn(new BindedVariable(secondTestVarName, secondTestVarValue));

        variableTemplate
                .bind(firstBind)
                .bind(secondBind);

        assertEquals(EXPECTED_EVAL_TEST, variableTemplate.resolved());
    }

}