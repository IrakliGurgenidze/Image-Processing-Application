package controller_tests;
import org.junit.Test;

import java.util.Arrays;

public class CompressCommandTest {

  /*
   This method "transforms" a 1-dimensional sequence of values via the
   Haar Wavelet Transform. We are assuming that padding has already taken
   place, and that length is a power of 2.
  */
  public double[] transform(double[] sequence) {

    //number of elements in sequence
    int n = sequence.length;

    //containers for average and difference elements
    double[] result = new double[n];
    int midwayIndex = n / 2;

    //for each consecutive pair of numbers, calculate avg and diff
    for (int i = 0; i < n; i += 2) {
      result[i/2] = (sequence[i] + sequence[i + 1]) / Math.sqrt(2);
      result[midwayIndex + i/2] = (sequence[i] - sequence[i + 1]) / Math.sqrt(2);
    }

    return result;
  }

  /*
    This method "inverts" a 1-dimensional sequence of values, reverting the
    transformation applied by its sister function.
   */
  public double[] invert(double[] sequence) {

    //number of elements in sequence
    int n = sequence.length;

    //containers for average and difference elements
    double[] result = new double[n];
    int midwayIndex = n / 2;

    //invert sequence transformation
    for (int i = 0; i < midwayIndex; i++) {
      double a = sequence[i];
      double b = sequence[midwayIndex + i];
      result[i*2] = (a + b) / Math.sqrt(2);
      result[i*2 + 1] = (a - b) / Math.sqrt(2);
    }

    return result;
  }

  @Test
  public void testTransformAndInverse() {
    double[] init = {5, 3, 2, 4, 2, 1, 0, 3};

    double[] initTransform1 = transform(init);
    System.out.println(Arrays.toString(initTransform1));

    double[] reverseTransform2 = invert(initTransform1);
    System.out.println(Arrays.toString(reverseTransform2));

  }
}
