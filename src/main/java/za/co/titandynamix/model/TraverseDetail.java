package za.co.titandynamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author clivememela
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraverseDetail implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    // Contains traverse time for all the orbits. Default unit is minutes.
    private int traverseTime;

    // Contains sequence of routes/orbits between source and final destinations, to visit via other suburbs
    private List<Orbit> orbits;
    private Vehicle vehicle;

    @Override
    public String toString() {
        return "TraverseDetail" + ": {" +
                "traverseTime=" + traverseTime +
                ", orbits=" + orbits +
                ", vehicle=" + vehicle +
                "}";
    }
}
