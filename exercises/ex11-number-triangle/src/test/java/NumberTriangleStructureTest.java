import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class NumberTriangleStructureTest {
  @TempDir Path directory;

  @Test
  void neighboringParentsShareTheirChildObject() throws Exception {
    Path input = directory.resolve("triangle.txt");
    Files.writeString(input, "1\n2 3\n4 5 6\n");
    NumberTriangle top = NumberTriangle.loadTriangle(input.toString());
    Field left = NumberTriangle.class.getDeclaredField("left");
    Field right = NumberTriangle.class.getDeclaredField("right");
    left.setAccessible(true);
    right.setAccessible(true);
    Object shared = right.get(left.get(top));
    assertSame(shared, left.get(right.get(top)));
    ((NumberTriangle) shared).setLeft(new NumberTriangle(99));
    assertEquals(99, top.retrieve("lrl"));
    assertEquals(99, top.retrieve("rll"));
  }

  @Test
  void singleRowIsALeafAndWhitespaceIsAccepted() throws Exception {
    Path input = directory.resolve("single.txt");
    Files.writeString(input, "  -8  \n");
    NumberTriangle top = NumberTriangle.loadTriangle(input.toString());
    assertEquals(-8, top.retrieve(""));
    assertTrue(top.isLeaf());
  }
}
