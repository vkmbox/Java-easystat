package org.easystat.anova;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link AnovaOneWay} class.
 */

public final class AnovaTwoWayTest {
  
  private static final double TOLERANCE = 10E-10;
  private static final double FVALUEA = 117;
  private static final double FVALUEB = 2.5;

  /** MSTU Example 8.5 */
  @Test
  public void testAnovaTwoWay()
  {
    AnovaTwoWay.AnovaTwoResult result = 
      new AnovaTwoWay().anovaTwoResult
        ( new double[][]{{4,3,6,7}, {18,19,18,13}, {26,25,24,21}, {38,35,28,31}, {44,43,39,38}} );
        
    Assert.assertEquals("Between-group sum of squared differences for A-factor", 3120, result.getSsA(), TOLERANCE);
    Assert.assertEquals("Degrees of freedom in numerator for A-factor (between groups)", 4, result.getDfA(), TOLERANCE);
    Assert.assertEquals("Between-group sum of squared differences for B-factor", 50, result.getSsB(), TOLERANCE);
    Assert.assertEquals("Degrees of freedom in numerator for B-factor (between groups)", 3, result.getDfB(), TOLERANCE);
    
    Assert.assertEquals("Within-group sum of squares", 80, result.getSw(), TOLERANCE);
    Assert.assertEquals("Degrees of freedom in denominator (within groups)", 12, result.getDfw(), TOLERANCE);
    Assert.assertEquals("F-ratio value for A-factor", FVALUEA, result.getFValueA(), TOLERANCE);
    Assert.assertEquals("F-ratio value for B-factor", FVALUEB, result.getFValueB(), TOLERANCE);
  }
  
}
