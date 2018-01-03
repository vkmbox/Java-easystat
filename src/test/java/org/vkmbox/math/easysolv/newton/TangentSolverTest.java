package org.vkmbox.math.easysolv.newton;

import org.junit.Assert;
import org.junit.Test;

public class TangentSolverTest {
  private static final double TOLERANCE = 0.01;
  
  @Test
  public void testGoldenSection(){
    TangentSolver solv = new TangentSolver(0.001);
    //solv.setOnIterationEvent(xx -> System.out.println(xx));
    double solve = solv.solve(100, x-> Math.pow((x-1), 3), -2, 3, 3);
    Assert.assertTrue("Absolute error less than 0.01", Math.abs(1-solve) < TOLERANCE);
  }  
}