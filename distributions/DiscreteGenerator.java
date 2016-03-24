/* DiscreteGenerator.java */

package distributions;
/**
 * This interface defines the functionality that should be provided by random number generators based
 * on discrete distributions.
 */
public interface DiscreteGenerator extends Generator {

  /**
   * Sample the random number generator.
   * @return The sample
   */
  public long sample();
}
