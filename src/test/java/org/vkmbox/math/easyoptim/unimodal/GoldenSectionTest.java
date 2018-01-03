package org.vkmbox.math.easyoptim.unimodal;

import org.apache.commons.math4.optim.MaxEval;
import org.apache.commons.math4.optim.MaxIter;
import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.univariate.SearchInterval;
import org.apache.commons.math4.optim.univariate.UnivariateObjectiveFunction;
import org.junit.Assert;
import org.junit.Test;

public class GoldenSectionTest {
  
  private static final double TOLERANCE = 0.01;
  private static final double FVALUE = 0;
  
  @Test
  public void testGoldenSection(){
    GoldenSection gold = new GoldenSection();

    OptimizationData[] optData = 
      { new SearchInterval(-2, 3)
      , new IndeterminationInterval(0.01)
      , new UnivariateObjectiveFunction(x-> Math.pow((x-1), 4))
      //, new UnivariateObjectiveFunction(x-> Math.pow((x-1), 2)*Math.sin(x))
      , MaxEval.unlimited()
      , MaxIter.unlimited()
      };
    
    UnivariateBiPointValuePair result = gold.optimize(optData);
    Assert.assertTrue("Lower bound less than upper", result.getHi()-result.getLo() >= 0);
    Assert.assertTrue("Lower bound more than 0,99", result.getLo()> 1 - TOLERANCE);
    Assert.assertTrue("Upper bound less than 0,11", result.getHi()< 1 + TOLERANCE);
    Assert.assertTrue("Indetermination interval less than 0,01", result.getHi()-result.getLo()< TOLERANCE);
    
  }  
  
}