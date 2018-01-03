package org.vkmbox.math.easyoptim.unimodal;

import org.apache.commons.math4.optim.BaseOptimizer;
//import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.function.Consumer;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
//import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.univariate.SearchInterval;
import org.apache.commons.math4.optim.univariate.UnivariateObjectiveFunction;
//import org.apache.commons.math4.optim.univariate.UnivariateOptimizer;
//import org.apache.commons.math4.optim.univariate.UnivariatePointValuePair;

public class GoldenSection extends BaseOptimizer<UnivariateBiPointValuePair> {

    /** Objective function. */
    private UnivariateFunction function;
    /** Initial guess. */
    private double start;
    /** Lower bound. */
    private double min;
    /** Upper bound. */
    private double max;
  
  private static final double TAU = (1.0+sqrt(5))/2.0;
  
  public GoldenSection() {
    super( new GsConvergenceChecker());
  }

  /**
   * {@inheritDoc}
   *
   * @param optData Optimization data. In addition to those documented in
   * {@link BaseOptimizer#parseOptimizationData(OptimizationData[])
   * BaseOptimizer}, this method will register the following data:
   * <ul>
   *  <li>{@link GoalType}</li>
   *  <li>{@link SearchInterval}</li>
   *  <li>{@link UnivariateObjectiveFunction}</li>
   * </ul>
   * @return {@inheritDoc}
   * @throws TooManyEvaluationsException if the maximal number of
   * evaluations is exceeded.
   */
  @Override
  public UnivariateBiPointValuePair optimize(OptimizationData... optData)
    throws TooManyEvaluationsException {
    // Perform computation.
    return super.optimize(optData);
  }

  /**
   * Scans the list of (required and optional) optimization data that
   * characterize the problem.
   *
   * @param optData Optimization data.
   * The following data will be looked for:
   * <ul>
   *  <li>{@link SearchInterval}</li>
   *  <li>{@link IndeterminationInterval}</li>
   *  <li>{@link UnivariateObjectiveFunction}</li>
   * </ul>
   */
  @Override
  protected void parseOptimizationData(OptimizationData... optData) {
    // Allow base class to register its own data.
    super.parseOptimizationData(optData);

    // The existing values (as set by the previous call) are reused if
    // not provided in the argument list.
    for (OptimizationData data : optData) {
      if (data instanceof SearchInterval) {
        final SearchInterval interval = (SearchInterval) data;
        min = interval.getMin();
        max = interval.getMax();
        start = interval.getStartValue();
        continue;
      }
      if (data instanceof UnivariateObjectiveFunction) {
        function = ((UnivariateObjectiveFunction) data).getObjectiveFunction();
        continue;
      }
      if (data instanceof IndeterminationInterval) {
        double val = ((IndeterminationInterval)data).getValue();
        ((GsConvergenceChecker)this.getConvergenceChecker()).setIndeterminationInterval(val);
        continue;
      }
    }
  }
  
  /**
   * @return the initial guess.
   */
  public double getStartValue() {
      return start;
  }
  /**
   * @return the lower bounds.
   */
  public double getMin() {
      return min;
  }
  /**
   * @return the upper bounds.
   */
  public double getMax() {
      return max;
  }

  /**
   * Computes the objective function value.
   * This method <em>must</em> be called by subclasses to enforce the
   * evaluation counter limit.
   *
   * @param x Point at which the objective function must be evaluated.
   * @return the objective function value at the specified point.
   * @throws TooManyEvaluationsException if the maximal number of
   * evaluations is exceeded.
   */
  protected double computeObjectiveValue(double x) {
      super.incrementEvaluationCount();
      return function.value(x);
  }  
  
  @Override
  protected UnivariateBiPointValuePair doOptimize()
  {
    checkParameters();
    UnivariateBiPointValuePair previous = null;
    UnivariateBiPointValuePair current = 
      new UnivariateBiPointValuePair(getMin(), computeObjectiveValue(getMin()), getMax(), computeObjectiveValue(getMax()));
    double x2 = current.getLo()+ (current.getHi()-current.getLo())/TAU;
    double x1 = current.getLo()+ current.getHi()-x2;
    double f1 = computeObjectiveValue(x1);
    double f2 = computeObjectiveValue(x2);
    while (true) {
      fireOnIterationEvent(current);
      if ( getConvergenceChecker().converged(getIterations(), previous == null?current:previous, current) == true ) 
        return current;
      previous = current;
      if (f1 < f2) {
        current = new UnivariateBiPointValuePair(previous.getLo(), previous.getLoVal(), x2, f2);
        x2 = x1; f2 = f1;
        x1 = current.getLo()+ current.getHi()-x2;
        f1 = computeObjectiveValue(x1);
      } else {
        current = new UnivariateBiPointValuePair(x1, f1, previous.getHi(), previous.getHiVal());
        x1 = x2; f1 = f2;
        x2 = current.getLo()+ current.getHi()-x1;
        f2 = computeObjectiveValue(x2);
      }
      incrementIterationCount();
    }
  }

  private void checkParameters()
  {
    if (function == null)
      throw new IllegalArgumentException("Target function not initialized");
  }
  
  private Consumer<UnivariateBiPointValuePair> onIterationEvent;
  
  private void fireOnIterationEvent(UnivariateBiPointValuePair pair) {
    if (onIterationEvent != null) onIterationEvent.accept(pair);
  }

  public void setOnIterationEvent(Consumer<UnivariateBiPointValuePair> handler) {
    onIterationEvent = handler;
  }
  
  static class GsConvergenceChecker implements ConvergenceChecker<UnivariateBiPointValuePair>
  {
    
    private double indeterminationInterval;
    //private final GoldenSection OWNER;

    public GsConvergenceChecker()
    {
      //OWNER = owner;
      /*if ( indetermInterval < 0 ) 
        throw new IllegalArgumentException("Indetermination can not be negative");
      INDETERMINATION_INTERVAL = indetermInterval;*/
    }

    private double getIndeterminationInterval() {
      return indeterminationInterval;
    }
    
    private void setIndeterminationInterval(double indeterminationInterval) {
      this.indeterminationInterval = indeterminationInterval;
    }
    
    public boolean converged(int iteration, UnivariateBiPointValuePair previous, UnivariateBiPointValuePair current)
    {
      return current.getHi()-current.getLo() < getIndeterminationInterval();
    }

  }
  
}