package za.co.titandynamix.validator;

import za.co.titandynamix.enums.WeatherCondition;
import za.co.titandynamix.utils.ValidationUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to validate traffic conditions
 * DESCRIPTION - This is a validator class, injected in TrafficFinder and TrafficHelper.
 * <p>
 *   It performs following operations:
 *  	1.	Validate all input parameters
 *  	2.  For invalid input or any error, generate a meaningful error message.
 *
 * @author clivememela
 */
public class TrafficValidator {
    // Create an object of Singleton Object
    private static final TrafficValidator validatorInstance = new TrafficValidator();

    // Get the only object available
    public static TrafficValidator getInstance(){
        return validatorInstance;
    }

    /**
     * This method is responsible to validate input parameters. 
     * It performs following operations:
     * 	-Validation for user inputs (i.e. weather type and orbit's speed limit)
     *
     * @param weatherType - User input
     * @param orbitSpeedLimitMap - Map for user's input (speed limit) and corresponding orbit name
     *
     * @return - If something invalid, it will have invalid message
     */
    public String validateUserInputs(String weatherType, Map<String, Integer> orbitSpeedLimitMap) {

        StringBuilder invalidMessage = new StringBuilder();
        // Validate weather condition
        if (!WeatherCondition.contains(weatherType))
            invalidMessage.append("=== please enter a valid weather condition input :  ").append(weatherType).append(" : is invalid \n");

        // Validation for available orbits for the given source and destination.
        if (null == orbitSpeedLimitMap || orbitSpeedLimitMap.isEmpty())
            invalidMessage.append("No route/orbit found for the given source-destination.");
        else {
            List<String> invalidOrbitNames = orbitSpeedLimitMap.keySet().stream()
                    .filter(orbitName -> orbitSpeedLimitMap.get(orbitName) < 1).collect(Collectors.toList());
            if (ValidationUtils.isNotEmpty(invalidOrbitNames)) {
                invalidMessage.append("Invalid maximum traffic speed(s) for : ")
                        .append(invalidOrbitNames).append(". \n=== Only +ve integer(s) accepted, please enter valid input(s).");
            }
        }

        return invalidMessage.toString();
    }

}
