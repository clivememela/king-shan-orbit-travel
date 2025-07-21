package za.co.titandynamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * [speed: Integer; unit: String (mega miles/hour)]
 *
 * @author clivememela
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Velocity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private int speed; // Default unit is mega miles/hour.
    private String  unit;
}
