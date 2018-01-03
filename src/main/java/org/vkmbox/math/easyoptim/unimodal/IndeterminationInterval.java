package org.vkmbox.math.easyoptim.unimodal;

import org.apache.commons.math4.optim.OptimizationData;

public class IndeterminationInterval implements OptimizationData {
  
  /** Value. */
  private final double value;

  /**
   * @param value parameter value.
   */
  public IndeterminationInterval(double value) {
      this.value = value;
  }

  /**
   * Gets the lower bound.
   *
   * @return the lower bound.
   */
  public double getValue() {
      return value;
  }
}