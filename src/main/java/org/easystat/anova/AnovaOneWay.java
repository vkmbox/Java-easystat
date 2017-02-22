package org.easystat.anova;

import org.apache.commons.math4.stat.StatUtils;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public class AnovaOneWay {
  
  public static final class AnovaOneResult
  {
    /** sum in numerator (between groups sum of squared differences). */
    private final double sb;
    public double getSb() { return sb; }
    /** Degrees of freedom in numerator (between groups). */
    private final int dfb;
    public double getDfb() { return dfb; }
    /** sum in denominator (within groups sum of squares). */
    private final double sw;
    public double getSw() { return sw; }
    /** Degrees of freedom in denominator (within groups). */
    private final int dfw;
    public double getDfw() { return dfw; }
    
    public AnovaOneResult(double sb, int dfb, double sw, int dfw)
    {
      this.sb = sb;
      this.dfb = dfb;
      this.sw = sw;
      this.dfw = dfw;
    }
    
    /** Fisher value */
    public double getFValue()
    {
      return (sb/dfb)/(sw/dfw);
    }
    
  }
  
    /**
     * This method actually does the calculations (except P-value).
     *
     * @param categoryData <code>Array</code> of <code>double[]</code>
     * arrays each containing data for one category
     * @return computed AnovaOneResult
     * @throws MathIllegalArgumentException if the number of categories is less than 2 
     * or a contained array for category does not contain at least two values
     */
    public AnovaOneResult anovaOneResult(final double[][] categoryData)
        throws MathIllegalArgumentException
    {
      if ( categoryData.length < 2 ) 
        throw new MathIllegalArgumentException(LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED, categoryData.length, 2);
      // check if each category has enough data
      for ( int ii = 0; ii < categoryData.length; ii++ )
        if ( categoryData[ii].length < 2 )
          throw new MathIllegalArgumentException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, categoryData[ii].length, 2);
        
      double sb = 0, sw = 0, totsum = 0, totsumsq = 0;
      int dfb = categoryData.length - 1, totalnum = 0, dfw = 0;
      
      for ( int ii = 0; ii < categoryData.length; ii++ )
      {
        double meanw = StatUtils.mean(categoryData[ii]);
        totalnum += categoryData[ii].length;
        for ( int jj = 0; jj < categoryData[ii].length; jj++ )
        {
          //System.out.println(meanw + ":" +categoryData[ii][jj]);
          sw += Math.pow(meanw - categoryData[ii][jj], 2);
          totsum += categoryData[ii][jj];
          totsumsq += Math.pow(categoryData[ii][jj], 2);
        }
      }
      sb = totsumsq - ((totsum*totsum)/totalnum) - sw;
      dfw = totalnum - categoryData.length;
      
      return new AnovaOneResult(sb, dfb, sw, dfw);
      
    }
}