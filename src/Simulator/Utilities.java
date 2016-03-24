/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Simulator;

import org.uncommons.maths.random.JavaRNG;
import unbc.ca.distributed.distributions.BinomialDistribution;
import unbc.ca.distributed.distributions.ExponentialDistribution;
import unbc.ca.distributed.distributions.GaussianDistribution;
import unbc.ca.distributed.distributions.Generator;
import unbc.ca.distributed.distributions.PoissonDistribution;
import unbc.ca.distributed.distributions.UniformDistribution;

/**
 *
 * @author IDontKnow
 */
public class Utilities {
    
   public static Generator returnDistribution(String type, int mean, double variance) {
        Generator u;
        switch (type) {
            case "Binomial":
                u = new BinomialDistribution(mean, variance);
                u.setGenerator(new JavaRNG());
                return u;
            case "Poisson":
                u = new PoissonDistribution(mean);
                u.setGenerator(new JavaRNG());
                return u;
            case "Uniform":
                u = new UniformDistribution(mean, (int)variance);
                u.setGenerator(new JavaRNG());
                return u;
            case "Exponential":
                u = new ExponentialDistribution(mean);
                u.setGenerator(new JavaRNG());
                return u;
            case "Gaussian":
                u = new GaussianDistribution(mean, variance);
                u.setGenerator(new JavaRNG());
                return u;
        }
        return null;
    }
    
}
