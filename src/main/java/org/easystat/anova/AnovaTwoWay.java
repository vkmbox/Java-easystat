package org.easystat.anova;

import org.apache.commons.math4.stat.StatUtils;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MultiDimensionMismatchException;

public class AnovaTwoWay {
  
  public static final class AnovaTwoResult
  {
    /** sum in numerator for A-factor (between groups sum of squared differences). */
    private final double ssA;
    public double getSsA() { return ssA; }
    /** Degrees of freedom in numerator for A-factor (between groups). */
    private final int dfA;
    public double getDfA() { return dfA; }

    /** sum in numerator for B-factor (between groups sum of squared differences). */
    private final double ssB;
    public double getSsB() { return ssB; }
    /** Degrees of freedom in numerator for B-factor (between groups). */
    private final int dfB;
    public double getDfB() { return dfB; }
    
    /** sum in denominator (within groups sum of squares). */
    private final double sw;
    public double getSw() { return sw; }
    /** Degrees of freedom in denominator (within groups). */
    //private final int dfw;
    public double getDfw() { return dfA*dfB; }
    
    public AnovaTwoResult(double ssA, int dfA, double ssB, int dfB, double sw)
    {
      this.ssA = ssA;
      this.dfA = dfA;
      this.ssB = ssB;
      this.dfB = dfB;
      this.sw = sw;
    }
    
    /** Fisher value for A-factor*/
    public double getFValueA()
    {
      return (ssA/dfA)/(sw/getDfw());
    }

    /** Fisher value for B-factor*/
    public double getFValueB()
    {
      return (ssB/dfB)/(sw/getDfw());
    }
    
  }
  
    /**
     * This method actually does the calculations (except P-value).
     *
     * @param fData <code>double[][]</code> array
     * each cell containing data for categories combination
     * fData[k,*] - A-factor data of k-category, fData[*,s] - B-factor data of s-category
     * @return computed AnovaTwoResult
     * @throws MathIllegalArgumentException if the number of categories for A- or B-factor is less than 2 
     * or array is not rectangular
     */
    public AnovaTwoResult anovaTwoResult(final double[][] fData)
        throws MathIllegalArgumentException
    {
      // check A-category has enough data
      if ( fData.length < 2 ) 
        throw new MathIllegalArgumentException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, fData.length, 2);
      // check B-category has enough data
      int alen =  fData[0].length;
      if ( alen < 2 ) 
        throw new MathIllegalArgumentException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, alen, 2);
      
      for ( int ii = 0; ii < fData.length; ii++ )
        if ( fData[ii].length != alen )
          throw new MultiDimensionMismatchException(new Integer[]{fData.length, fData[ii].length}, new Integer[]{fData.length, alen});
        
      //double sA = 0, sB = 0, sw = 0, totsum = 0, totsumsq = 0;
      //int dfb = categoryData.length - 1, totalnum = 0, dfw = 0;
      double mean0 = 0;
      double[] meanA = new double[fData.length], meanB = new double[alen];
      
      for ( int ii = 0; ii < fData.length; ii++ )
      {
        meanA[ii] = StatUtils.mean(fData[ii]);
        for ( int jj = 0; jj < alen; jj++ )
        {
          mean0 += fData[ii][jj];
          meanB[jj] += fData[ii][jj];
        }
      }
      mean0 = mean0/(fData.length*alen);
      //System.out.println("mean:"+mean0);
      for ( int jj = 0; jj < alen; jj++ )
      {  
        meanB[jj] = meanB[jj]/fData.length; 
        //System.out.println("mean-"+jj+":"+meanB[jj]);
      }

      double ssA = 0, ssB = 0, totsumsq = 0; //, totsum = 0, sw = 0
      
        /*for ( int jj = 0; jj < alen; jj++ )
            for ( int ii = 0; ii < fData.length; ii++ )
              System.out.println("AA="+Math.pow(fData[ii][jj] - mean0, 2));*/

      //Summation
      for ( int ii = 0; ii < fData.length; ii++ )
      {
        ssA +=  alen * Math.pow(mean0 - meanA[ii], 2);
        for ( int jj = 0; jj < alen; jj++ )
        {
          if ( ii == 0 )
          {
            ssB += fData.length * Math.pow(mean0 - meanB[jj], 2);
            //System.out.println("ssB="+ssB);
          }
          totsumsq += Math.pow(fData[ii][jj] - mean0, 2); // - meanA[ii]- meanB[jj] + mean0
        }
      }
      //System.out.println("totsumsq="+totsumsq);
      return new AnovaTwoResult(ssA, fData.length-1, ssB, alen - 1, totsumsq - ssA - ssB );
    }
}
