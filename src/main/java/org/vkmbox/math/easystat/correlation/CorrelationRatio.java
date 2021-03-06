package org.vkmbox.math.easystat.correlation;

//import java.util.*;
import org.vkmbox.math.util.QuickSortDouble;
import org.vkmbox.math.util.QuickSortDoubleArray;

import org.apache.commons.math4.stat.StatUtils;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public class CorrelationRatio {

  /**
   * Computes the correlation ratio between the two arrays. 
   * It is expected that in the first array are found duplicates.
   *
   * <p>Array lengths must match and the common length must be at least 2.</p>
   *
   * @param xArray first data array
   * @param yArray second data array
   * @return returns the correlation ratio for the two arrays
   * @throws  MathIllegalArgumentException if the arrays lengths do not match or
   * there is insufficient data
   */
  public double correlationRatio(final double[] xArray, final double[] yArray) throws MathIllegalArgumentException 
  {
    int length = xArray.length;
    if ( length != yArray.length ) {
      throw new MathIllegalArgumentException( LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, length, yArray.length );
    } else if ( length < 2 ) {
      throw new MathIllegalArgumentException( LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, length, 2 );
    }

    //toList(xArray);
    //toList(yArray);
    
    QuickSortDouble sorter = new QuickSortDouble() {
      protected void swapDouble( double[] array, int posA, int posB ) 
      {
        super.swapDouble( xArray, posA, posB );
        super.swapDouble( yArray, posA, posB );
      }
    };
    
    sorter.qsortDouble(xArray);
    
    //toList(xArray);
    //toList(yArray);
    
    double currX = xArray[0];
    int currLen = 0, currPos = 0;
    double yMean = StatUtils.mean(yArray);
    double regSum = 0d, varSum = 0d;
    //System.out.println("Mean:"+yMean);
    
    for ( int ii = 0; ii < length; ii++ )
    {
      varSum += Math.pow(( yMean - yArray[ii] ),2);
      if ( currX == xArray[ii] ) {
        currLen += 1;
      } else {
        //System.out.println(currPos + ";" + currLen + ";" + currX + ";" + StatUtils.mean(yArray, currPos, currLen));
        //System.out.println(currLen+";"+( yMean - StatUtils.mean(yArray, currPos, currLen) ));
        regSum += currLen*Math.pow(( yMean - StatUtils.mean(yArray, currPos, currLen) ),2);
        currX = xArray[ii]; currLen = 1; currPos = ii;
      }
    }
    //System.out.println(currPos + ";" + currLen + ";" + currX + ";" + StatUtils.mean(yArray, currPos, currLen));
    //System.out.println(currLen+";"+( yMean - StatUtils.mean(yArray, currPos, currLen) ));
    regSum += currLen*Math.pow(( yMean - StatUtils.mean(yArray, currPos, currLen) ),2);
    
    //System.out.print(regSum/length+";"+varSum/length+";"+regSum/varSum);
    return varSum == 0 ? Double.NaN : Math.pow(regSum/varSum, 0.5);
  }

  /**
   * Computes the correlation ratio between the two arrays. 
   * The first data set - a two-dimensional (an array of vectors)
   * It is expected that in the first array are found duplicates.
   *
   * <p>Array lengths must match and the common length must be at least 2.</p>
   *
   * @param xArray first data array
   * @param yArray second data array
   * @return returns the correlation ratio for the two arrays
   * @throws  MathIllegalArgumentException if the arrays lengths do not match or
   * there is insufficient data
   */
  public double correlationRatio(final double[][] xArray, final double[] yArray) throws MathIllegalArgumentException 
  {
    int length = xArray.length;
    if ( length != yArray.length ) {
      throw new MathIllegalArgumentException( LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE, length, yArray.length );
    } else if ( length < 2 ) {
      throw new MathIllegalArgumentException( LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, length, 2 );
    }
    QuickSortDoubleArray sorter = new QuickSortDoubleArray() {
      protected void swap( double[][] array, int posA, int posB ) 
      {
        super.swap( xArray, posA, posB );
        double tmp = yArray[posA];
        yArray[posA] = yArray[posB];
        yArray[posB] = tmp;
      }
    };
    
    sorter.qsort(xArray);
    
    double[] currX = xArray[0];
    int currLen = 0, currPos = 0;
    double yMean = StatUtils.mean(yArray);
    double regSum = 0d, varSum = 0d;
    
    for ( int ii = 0; ii < length; ii++ )
    {
      varSum += Math.pow(( yMean - yArray[ii] ),2);
      if ( sorter.compare(currX, xArray[ii]) == 0 ) {
        currLen += 1;
      } else {
        regSum += currLen*Math.pow(( yMean - StatUtils.mean(yArray, currPos, currLen) ),2);
        currX = xArray[ii]; currLen = 1; currPos = ii;
      }
    }
    regSum += currLen*Math.pow(( yMean - StatUtils.mean(yArray, currPos, currLen) ),2);
    return varSum == 0 ? Double.NaN : Math.pow(regSum/varSum, 0.5);
  }
  
  /*private List<Double> toList( double[] val )
  {
    List<Double> result = new ArrayList<>(val.length);
    for ( int ii = 0; ii < val.length; ii++ )
    {
      //System.out.print(val[ii]+";");
      val[ii] = new Double(val[ii]);
    }
    System.out.println("");
    return result;
  }*/
  
}