/** A rectangle with mutable width and height. */
public class Rectangle {
  private double width;
  private double height;

  /**
   * Creates a rectangle with the given dimensions.
   *
   * @param width the initial width
   * @param height the initial height
   */
  public Rectangle(double width, double height) {
    this.width = width;
    this.height = height;
  }

  /**
   * Returns the rectangle's area.
   *
   * @return width multiplied by height
   */
  public double area() {
    return width * height;
  }

  /**
   * Scales both dimensions of the rectangle.
   *
   * @param factor the multiplier applied to each dimension
   */
  public void scale(double factor) {
    width *= factor;
    height *= factor;
  }

  /**
   * Compares this rectangle's area with another rectangle's area.
   *
   * @param other the rectangle to compare with
   * @return true if this rectangle has a strictly larger area
   */
  public boolean isLargerThan(Rectangle other) {
    return area() > other.area();
  }
}
