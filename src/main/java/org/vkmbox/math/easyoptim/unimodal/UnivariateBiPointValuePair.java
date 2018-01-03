package org.vkmbox.math.easyoptim.unimodal;

import java.io.Serializable;
import org.apache.commons.math4.exception.NumberIsTooLargeException;

/**
 *
 * @author VK
 */
public class UnivariateBiPointValuePair implements Serializable 
{
    /** Serializable version identifier. */
    private static final long serialVersionUID = 6413888396256744891L;
    /** Low endpoint. */
    private final double lo;
    /** Value of the objective function at the low endpoin. */
    private final double loVal;

    /** High endpoint. */
    private final double hi;
    /** Value of the objective function at the high endpoin. */
    private final double hiVal;
    
    /**
     * Build a point/objective function value pair.
     *
     * @param loPoint Low endpoint.
     * @param loValue Value of an objective function at the point
     * @param hiPoint High endpoint.
     * @param hiValue Value of an objective function at the point
     */
    public UnivariateBiPointValuePair
      (final double loPoint, final double loValue, final double hiPoint, final double hiValue) {

      if (loPoint >= hiPoint) 
          throw new NumberIsTooLargeException(loPoint, hiPoint, false);

      this.lo = loPoint;
      this.loVal = loValue;
      this.hi = hiPoint;
      this.hiVal = hiValue;
    }

    /**
     * Get the point.
     *
     * @return the low endpoint.
     */
    public double getLo() {
        return lo;
    }

    /**
     * Get the value of the objective function.
     *
     * @return the stored value of the objective function at the low endpoint.
     */
    public double getLoVal() {
        return loVal;
    }  

    /**
     * Get the point.
     *
     * @return the high endpoint.
     */
    public double getHi() {
        return hi;
    }

    /**
     * Get the value of the objective function.
     *
     * @return the stored value of the objective function at the high endpoin.
     */
    public double getHiVal() {
        return hiVal;
    }
    
    public String toString() {
      return String.format("Low endpoint:%f, value at point:%f, high endpoint:%f, value at point:%f"
        , getLo(), getLoVal(), getHi(), getHiVal());
    }
    
}
