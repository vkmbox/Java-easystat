package org.easystat.util;

import java.util.Random;

public class QuickSortDouble {
  
  public static final Random RND = new Random();
  
  protected void swapDouble( double[] array, int posA, int posB ) 
  {
    double tmp = array[posA];
    array[posA] = array[posB];
    array[posB] = tmp;
  }
  
  private int partitionDouble( double[] array, int begin, int end ) 
  {
    int index = begin + RND.nextInt(end - begin + 1);
    double pivot = array[index];
    swapDouble( array, index, end );
    for (int ii = index = begin; ii < end; ++ ii) {
      if ( array[ii] <= pivot ) {
        swapDouble(array, index++, ii);
      }
    }
    swapDouble(array, index, end);
    return index;
  }
  
  private void qsortDouble( double[] array, int begin, int end )
  {
    if ( end > begin ) {
      int index = partitionDouble(array, begin, end );
      qsortDouble(array, begin, index - 1 );
      qsortDouble(array, index + 1,  end );
    }
  }
    
  public void qsortDouble( double[] array ) {
        qsortDouble( array, 0, array.length - 1 );
  }
}
