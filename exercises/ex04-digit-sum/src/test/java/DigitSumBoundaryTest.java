import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DigitSumBoundaryTest {
  @Test
  void handlesEntireIntRange() {
    assertEquals(47, DigitSum.digitSum(Integer.MIN_VALUE));
    assertEquals(46, DigitSum.digitSum(Integer.MAX_VALUE));
  }
}
