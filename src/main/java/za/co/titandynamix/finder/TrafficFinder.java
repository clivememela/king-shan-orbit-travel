package za.co.titandynamix.finder;

import lombok.Getter;
import za.co.titandynamix.helper.TrafficHelper;
import za.co.titandynamix.model.Orbit;
import za.co.titandynamix.model.TraverseDetail;
import za.co.titandynamix.model.Vehicle;
import za.co.titandynamix.model.Weather;
import za.co.titandynamix.utils.ValidationUtils;
import za.co.titandynamix.validator.TrafficValidator;

import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION - This class is solution for problem: Traffic finder
 * <p>
 * It contains solutions for following problems:
 * Goal: Find best route from Silk Dorb to Hallitharam in the shortest possible time.
 */
@Getter
public class TrafficFinder {
    // Get the single only object available for TrafficHelper
    private static final TrafficHelper trafficHelper = TrafficHelper.getInstance();

    // Get the only object available for TrafficValidator (Singleton)
    private static final TrafficValidator trafficValidator = new TrafficValidator();

    /**
     * This method is responsible to calculate optimum time to reach the destination from the source.
     * <p>
     * To do this it performs following operations:
     *  1.	Get weather by weather-type.
     *  2. 	Based on weather type entered , get all suitable vehicle names and get their corresponding Vehicle objects.
     *  3. 	Get the orbits and update them with user inputs (i.e. speed limit)
     * 	4.	Based on weather type we can identify actual number of craters for each available orbit.
     * 	5.	Find out optimum traverse time for each orbit/route and vehicle combination.
     * 	6.	Compare these times and find out the optimized one.
     *
     * @param weatherType - User input
     * @param orbits - Updated list of orbit sequences with user's input (speed limit of orbits)
     * @param orbitSpeedLimitMap - This map is used to hold user inputs (orbit's speed limit) corresponding to orbit name.
     *
     * @return - Success or failure message after doing calculation for optimum traverse time as a String.
     */
    public String calculateOptimumTime(String weatherType,
                                       List<List<Orbit>> orbits, Map<String, Integer> orbitSpeedLimitMap) {

        // Validate input parameters. If something invalid, it will have invalid message
        String invalidMessage = trafficValidator.validateUserInputs(weatherType, orbitSpeedLimitMap);

        // If all inputs are valid, invalidMessage will be empty. Else if user inputs are not valid, return the invalid message
        if (ValidationUtils.isNotBlank(invalidMessage))
            return "\nValidation failed : \n" + invalidMessage;

        // Get weather by weather-type
        Weather weather = trafficHelper.getWeatherByType(weatherType);

        // Get all suitable vehicle names for the selected weather and get their corresponding Vehicle objects
        List<Vehicle> vehicles = trafficHelper.getSuitableVehicles(weather.getSuitableVehicleNames());

        // Iterate all available orbit-sequences and set its max speed limit, which came as input.
        orbits.stream()
                .forEach(orbitSequence -> orbitSequence.stream()
                        // Update the orbit-sequence with the orbit's speed limit
                        .forEach(orbit -> orbit.getVelocityLimit().setSpeed(orbitSpeedLimitMap.get(orbit.getName())))
                );

        /*
         * Populate TraverseDetail object with the traverse time, orbit and vehicle.
         * Get list of all populated TraverseDetail objects.
         */
        List<TraverseDetail> traverseDetails = trafficHelper.getTraverseDetails(weather, vehicles, orbits);

        // Find the optimum TraverseDetail object from list of traverseDetails
        TraverseDetail optimumTraverseDetail = trafficHelper.findOptimumTraverseDetail(traverseDetails);

        // Check optimum TraverseDetail object
        if (null == optimumTraverseDetail)
            return "System Error: Unable to find shortest possible time";
        else
            // Generate and return output message from optimized TraverseDetail object
            return trafficHelper.generateOutputMessage(optimumTraverseDetail);

    }

    /**
     * Getter method for TrafficHelper.
     *
     * @return TrafficHelper instance
     */
    public TrafficHelper getTrafficHelper() {
        return trafficHelper;
    }
}
