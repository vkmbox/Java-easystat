package org.easystat.correlation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link CorrelationRatio} class.
 */

public final class CorrelationRatioTest {
  
  private static final double TOLERANCE = 10E-4;
  private static final double CORRRATIO = 0.8386;

  /** example from https://en.wikipedia.org/wiki/Correlation_ratio */
  @Test
  public void testCorrelationRatio()
  {
    double result = 
      new CorrelationRatio().correlationRatio
        ( new double[]{3, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 3 }
        , new double[]{85, 40, 45, 70, 29, 15, 21, 20, 30, 42, 65, 95, 80, 70, 73 }
        );
    Assert.assertEquals("correlation ratio", CORRRATIO, result, TOLERANCE);
  }
  
  @Test
  public void testCorrelationRatioArray()
  {
    double result = 
      new CorrelationRatio().correlationRatio
        ( new double[][]{{3, 0}, {2, 0}, {1, 0}, {1, 0}, {1, 0}, {1, 0}, {1, 0}, {2, 0}, {2, 0}, {2, 0}, {3, 0}, {3, 0}, {3, 0}, {3, 0}, {3, 0} }
        , new double[]{85, 40, 45, 70, 29, 15, 21, 20, 30, 42, 65, 95, 80, 70, 73 }
        );
    Assert.assertEquals("correlation ratio", CORRRATIO, result, TOLERANCE);
  }
}
