package za.co.titandynamix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.titandynamix.enums.WeatherCondition;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * [weather condition: enum; list of possible vehicle names: List<String>; change rate (+/- XX%): Integer;]
 *
 * @author clivememela
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private WeatherCondition weatherCondition;
    // It will be either +ve (if increase) or -ve (if reduce) percentage
    private int craterChangePercentage;
    private List<String> suitableVehicleNames;

    @Override
    public String toString() {
        return "Weather" + ": {" +
                "weatherType=" + weatherCondition +
                ", craterChangeRate=" + craterChangePercentage +
                ", suitableVehicleNames=" + suitableVehicleNames +
                "}";
    }
}
