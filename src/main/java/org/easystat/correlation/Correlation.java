package org.easystat.correlation;

import org.apache.commons.math4.stat.StatUtils;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public class Correlation {

  /**
   * Computes the correlation between the two arrays. 
   *
   * <p>Array lengths must match and the common length must be at least 2.</p>
   *
   * @param xArray first data array
   * @param yArray second data array
   * @return returns the correlation ratio for the two arrays
   * @throws  MathIllegalArgumentException if the arrays lengths do not match or
   * there is insufficient data
   */
  public double correlation(final double[] xArray, final double[] yArray) throws MathIllegalArgumentException 
  {
    int length = xArray.length;
    if ( length != yArray.length ) {
      throw new MathIllegalArgumentException( LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, length, yArray.length );
    } else if ( length < 2 ) {
      throw new MathIllegalArgumentException( LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, length, 2 );
    }
    
    double xMean = StatUtils.mean(xArray);
    double yMean = StatUtils.mean(yArray);
    double covSum = 0, varX = 0, varY = 0;
    for ( int ii = 0; ii < length; ii++ )
    {
      covSum += (xArray[ii] - xMean)*(yArray[ii] - yMean);
      varX += Math.pow((xArray[ii] - xMean), 2);
      varY += Math.pow((yArray[ii] - yMean), 2);
    }
    //System.out.print(covSum+";"+varX+";"+varY);
    return varX*varY == 0 ? Double.NaN : covSum/Math.pow(varX*varY, 0.5);
  }
  
}