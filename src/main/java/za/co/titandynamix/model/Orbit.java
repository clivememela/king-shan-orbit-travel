package za.co.titandynamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * [source: String; number of craters: Integer; distance (mega mile): Integer; traffic speed limit: Velocity]
 *
 * @author clivememela
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orbit implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String source;
    private String destination;

    private int distance; // Default unit is mega miles.
    private int numberOfCraters;

    /**
     * public string Name { get; }
     *     public int Distance { get; }
     *     public int Craters { get; }
     *     public int TrafficSpeed { get; }
     */

    private Velocity velocityLimit; //A vehicle cannot travel faster than the traffic speed for an orbit.

    @Override
    public String toString() {

        return "Orbit" + ": {" +
                "orbitName=" + name +
                ", source=" + source +
                ", destination=" + destination +
                ", distance=" + distance +
                ", numberOfCraters=" + numberOfCraters +
                ", velocityLimit=" + velocityLimit +
                "}";
    }
}
