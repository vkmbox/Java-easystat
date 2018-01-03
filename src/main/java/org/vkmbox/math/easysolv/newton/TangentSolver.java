package org.vkmbox.math.easysolv.newton;

import java.util.function.Consumer;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.solvers.BaseAbstractUnivariateSolver;
import org.apache.commons.math4.analysis.solvers.UnivariateSolver;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.FastMath;

public class TangentSolver extends BaseAbstractUnivariateSolver<UnivariateFunction>
  implements UnivariateSolver {
  /** Default absolute accuracy. */
  private static final double DEFAULT_ABSOLUTE_ACCURACY = 1e-6;

  /**
   * Construct a solver.
   */
  public TangentSolver() {
      this(DEFAULT_ABSOLUTE_ACCURACY);
  }
  /**
   * Construct a solver.
   *
   * @param absoluteAccuracy Absolute accuracy.
   */
  public TangentSolver(double absoluteAccuracy) {
      super(absoluteAccuracy);
  }

  /**
   * Find a zero near the midpoint of {@code min} and {@code max}.
   *
   * @param maxEval Maximum number of evaluations.
   * @param f Function to solve.
   * @param min Lower bound for the interval.
   * @param max Upper bound for the interval.
   * @param startValue Start value to use.
   * @return the value where the function is zero.
   * @throws org.apache.commons.math4.exception.TooManyEvaluationsException
   * if the maximum evaluation count is exceeded.
   * @throws org.apache.commons.math4.exception.NumberIsTooLargeException
   * if {@code min >= max}.
   */
  @Override
  public double solve(int maxEval, final UnivariateFunction f,
                    final double min, final double max, double startValue)
    throws TooManyEvaluationsException {
    return super.solve(maxEval, f, min, max, startValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected double doSolve() throws TooManyEvaluationsException {
    final double startValue = getStartValue();
    final double absoluteAccuracy = getAbsoluteAccuracy();

    double x_2 = startValue; fireOnIterationEvent(x_2);
    double minObj = computeObjectiveValue(getMin());
    double maxObj = computeObjectiveValue(getMax());
    double x_1 = (getMin()*maxObj - getMax()*minObj)/(maxObj - minObj), x0;
    fireOnIterationEvent(x_1);
    double y_1 = computeObjectiveValue(x_1), y_2 = computeObjectiveValue(x_2);
    while (true) {
      x0 = x_1 - y_1*(x_1 - x_2)/(y_1-y_2);
      fireOnIterationEvent(x0);
      if (FastMath.abs(x0 - x_1) <= absoluteAccuracy) {
        return x0;
      }
      x_2 = x_1; y_2 = y_1; x_1 = x0; 
      y_1 = computeObjectiveValue(x_1);
    }
    /*while (true) {
      final DerivativeStructure y0 = computeObjectiveValueAndDerivative(x0);
      x1 = x0 - (y0.getValue() / y0.getPartialDerivative(1));
      if (FastMath.abs(x1 - x0) <= absoluteAccuracy) {
          return x1;
      }

      x0 = x1;
    }*/
  }
    
  private Consumer<Double> onIterationEvent;
  
  private void fireOnIterationEvent(double point) {
    if (onIterationEvent != null) onIterationEvent.accept(point);
  }

  public void setOnIterationEvent(Consumer<Double> handler) {
    onIterationEvent = handler;
  }
    
}