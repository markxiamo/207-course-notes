/**
 * A <em>concrete strategy</em>: public-transit directions.
 *
 * <p>Complete {@link #getDirections(String)} so it returns
 * {@code "Take transit to " + destination} (compare with {@link DrivingDirections}).
 */
public class TransitDirections implements DirectionGenerator {

  @Override
  public String getDirections(String destination) {
    return "Take transit to " + destination;
  }
}
