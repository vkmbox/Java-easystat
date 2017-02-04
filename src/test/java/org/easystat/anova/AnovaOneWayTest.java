package org.easystat.anova;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link AnovaOneWay} class.
 */

public final class AnovaOneWayTest {
  
  private static final double TOLERANCE = 10E-10;
  private static final double FVALUE = 42.0/(68.0/15.0);

  /** example from https://en.wikipedia.org/wiki/One-way_analysis_of_variance */
  @Test
  public void testAnovaOneWay()
  {
    AnovaOneWay.AnovaOneResult result = 
      new AnovaOneWay().anovaOneResult
        ( new double[][]
            { {6,8,4,5,3,4}
            , {8,12,9,11,6,8}
            , {13,9,11,8,7,12}
            }
        );
        
    Assert.assertEquals("Between-group sum of squared differences", 84, result.getSb(), TOLERANCE);
    Assert.assertEquals("Degrees of freedom in numerator (between groups)", 2, result.getDfb(), TOLERANCE);
    Assert.assertEquals("Within-group sum of squares", 68, result.getSw(), TOLERANCE);
    Assert.assertEquals("Degrees of freedom in denominator (within groups)", 15, result.getDfw(), TOLERANCE);
    Assert.assertEquals("F-ratio value", FVALUE, result.getFValue(), TOLERANCE);
        
  }
  
}
