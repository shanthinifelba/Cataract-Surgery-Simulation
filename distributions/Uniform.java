package distributions;

/**
 * A random number generator based on the Uniform distribution.
 * @version     1.0, 13 May 2002
 * @author      Costas Simatos
 */

public class Uniform implements ContinuousGenerator {
  private RandomP source;
  private double mag, min;
  private String name;

  /**
   * Constructor with which <code>Sim_system</code> is allowed to set the random number
   * generator's seed
   * @param name The name to be associated with this instance
   * @param min  The minimum value this instance should generate
   * @param max  The maximum value this instance should generate
   */
  public Uniform(String name, double min, double max) {
    if (min >= max) {
      throw new Error("Sim_uniform_obj: The maximum must be greater than the minimum.");
    }
    source = new RandomP("Internal PRNG");
    this.mag = max - min;
    this.min = min;
    this.name = name;
  }

  /**
   * The constructor with which a specific seed is set for the random
   * number generator
   * @param name The name to be associated with this instance
   * @param min  The minimum value this instance should generate
   * @param max  The maximum value this instance should generate
   * @param seed The initial seed for the generator, two instances with
   *             the same seed will generate the same sequence of numbers
   */
  public Uniform(String name, double min, double max, long seed) {
    if (min >= max) {
      throw new Error("Sim_uniform_obj: The maximum must be greater than the minimum.");
    }
    source = new RandomP("Internal PRNG", seed);
    this.mag = max - min;
    this.min = min;
    this.name = name;
  }

  /**
   * Generate a new random number.
   * @return The next random number in the sequence
   */
    @Override
  public double sample() {
    return mag * source.sample() + min;
  }

  // Used by other distributions that rely on the Uniform distribution
  static double sample(RandomP source, double min, double max) {
    return (max-min) * source.sample() + min;
  }

  /**
   * Set the random number generator's seed.
   * @param seed The new seed for the generator
   */
  public void set_seed(long seed) {
    source.set_seed(seed);
  }

  /**
   * Get the random number generator's seed.
   * @return The generator's seed
   */
  public long get_seed() {
    return source.get_seed();
  }

  /**
   * Get the random number generator's name.
   * @return The generator's name
   */
  public String get_name() {
    return name;
  }

}
