package za.co.titandynamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * [weather type: enum; list of possible vehicle names: List<String>; change rate (+/- XX%): Integer;]
 *
 * @author clivememela
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private Velocity velocity;

    /**
     * Time to cross a cracker (default unit is in minutes)
     */
    private int timeToCrossCrater;
}