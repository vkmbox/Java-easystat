package org.vkmbox.math.util;

import java.util.Random;

public class QuickSortDoubleArray {
  
  public static final Random RND = new Random();
  
  protected void swap( double[][] array, int posA, int posB ) 
  {
    double tmp[] = array[posA];
    array[posA] = array[posB];
    array[posB] = tmp;
  }
  
  public static int compare( double[] obj1, double[] obj2)
  {
    for ( int ii = 0; ii < obj1.length; ii++ )
      if (obj1[ii] < obj2[ii]) {
        return -1;
      }
      else if (obj1[ii] > obj2[ii]) {
        return 1;
      }
    return 0;
  }
  
  private int partition( double[][] array, int begin, int end ) 
  {
    int index = begin + RND.nextInt(end - begin + 1);
    double[] pivot = array[index];
    swap( array, index, end );
    for (int ii = index = begin; ii < end; ++ ii) {
      if ( compare(array[ii], pivot) <= 0 ) {
        swap(array, index++, ii);
      }
    }
    swap(array, index, end);
    return index;
  }
  
  private void qsort( double[][] array, int begin, int end )
  {
    if ( end > begin ) {
      int index = partition(array, begin, end );
      qsort(array, begin, index - 1 );
      qsort(array, index + 1,  end );
    }
  }
    
  public void qsort( double[][] array ) {
        qsort( array, 0, array.length - 1 );
  }
}
