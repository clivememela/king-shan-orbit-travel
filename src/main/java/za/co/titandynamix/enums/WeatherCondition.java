package za.co.titandynamix.enums;

import za.co.titandynamix.exception.BusinessRuleException;

import java.util.Arrays;

/**
 * [weather condition: enum; list of possible vehicle names: List<String>; change rate (+)]
 *
 * @author clivememela
 */
public enum WeatherCondition {
    /**
     * The singleton instance for weather type: Sunny.
     * This has the numeric value of {@code 0}.
     */
    SUNNY("Sunny"),

    /**
     * The singleton instance for weather type: Rainy.
     * This has the numeric value of {@code 1}.
     */
    RAINY("Rainy"),

    /**
     * The singleton instance for weather type: Windy.
     * This has the numeric value of {@code 2}.
     */
    WINDY("Windy");

    private static final WeatherCondition[] ENUMS = WeatherCondition.values();

    /**
     * Obtains an instance of {@code WeatherCondition} from an {@code int} value.
     *
     * @param WeatherCondition  the weather type to represent, from 0 (Sunday) to 6 (Saturday)
     * @return the day-of-week singleton, not null
     * @throws BusinessRuleException, if the day-of-week is invalid
     */
    public static WeatherCondition of(int WeatherCondition) throws BusinessRuleException {
        if (WeatherCondition < 0) {
            throw new BusinessRuleException("Invalid value for WeatherCondition: " + WeatherCondition);
        }
        return ENUMS[WeatherCondition];
    }

    public static boolean contains(String weatherCondition) {
        return Arrays.stream(ENUMS	)
                .anyMatch(WeatherCondition -> WeatherCondition.toString().equalsIgnoreCase(weatherCondition));
    }

    private final String type;

    WeatherCondition(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
