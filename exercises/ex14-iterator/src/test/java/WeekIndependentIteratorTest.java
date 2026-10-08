import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import org.junit.jupiter.api.Test;

class WeekIndependentIteratorTest {
  @Test
  void iteratorsHaveIndependentPositionsAndHasNextDoesNotAdvance() {
    Week week = new Week();
    Iterator<String> first = week.iterator();
    Iterator<String> second = week.iterator();
    assertTrue(first.hasNext());
    assertTrue(first.hasNext());
    assertEquals("Sunday", first.next());
    assertEquals("Monday", first.next());
    assertEquals("Sunday", second.next());
    assertEquals("Sunday", week.iterator().next());
  }
}
