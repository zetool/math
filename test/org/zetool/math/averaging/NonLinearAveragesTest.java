package org.zetool.math.averaging;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.jmock.AbstractExpectations.returnValue;
import static org.junit.Assert.assertThat;
import java.util.function.DoubleFunction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 *
 * @author Jan-Philipp Kappmeier
 */
public class NonLinearAveragesTest {
    private final Mockery context = new Mockery();
    
    @Test
    public void weightFunctionAverage() {
        DoubleFunction<Double> df = context.mock(DoubleFunction.class);
        
        context.checking(new Expectations() {
            {
                allowing(df).apply(with(0.2));
                will(returnValue(0.5));
            }
        });
        assertThat( NonLinearAverages.generalAverage(df, 0.2, 2, 4), is(closeTo(3.0, 10e-7)));        
    }
    
    @Test
    public void standardLogisticWeighting() {
        final double leftBoundary = NonLinearAverages.logisticAverage(0, 2, 4);
        assertThat(leftBoundary, is(closeTo(2, 0.005)));
        assertThat(leftBoundary, is(greaterThan(2.0)));
        
        final double middle = NonLinearAverages.logisticAverage(0.5, 2, 4);
        assertThat(middle, is(closeTo(3, 10e-7)));
        
        final double rightBoundary = NonLinearAverages.logisticAverage(1, 2, 4);
        assertThat(rightBoundary, is(closeTo(4, 0.005)));
        assertThat(rightBoundary, is(lessThan(4.0)));
    }    
}
