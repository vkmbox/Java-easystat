package org.easystat.correlation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link Correlation} class.
 */

public final class CorrelationTest {
  
  private static final double TOLERANCE = 10E-2;
  private static final double CORR = -0.73;

  @Test
  public void testCorrelation()
  {
    double result = 
      new Correlation().correlation
        ( new double[]{1,4,0,5,-3,3,-5,-1,2,-2 }
        , new double[]{-4,-5,4,-1,4,0,5,1,2,7 }
        );
    Assert.assertEquals("correlation", CORR, result, TOLERANCE);
  }
}
