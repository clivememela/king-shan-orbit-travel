package za.co.titandynamix.helper;

import za.co.titandynamix.initializer.TrafficInitializer;
import za.co.titandynamix.model.Orbit;
import za.co.titandynamix.model.TraverseDetail;
import za.co.titandynamix.model.Vehicle;
import za.co.titandynamix.model.Weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper class for managing traffic.
 * <p>
 * DESCRIPTION - This is a helper class injected into TrafficFinder.
 * <p>
 * It performs following operations:
 * 	1.	Get all suitable vehicle names for the weather and get their corresponding Vehicle objects.
 * 	2.	Calculate optimum traverse time, for each route and vehicle and populate list of TraverseDetail objects.
 * 	3.	Compare these traverse times and find out the optimized one.
 *
 * @author clivememela
 */
public class TrafficHelper {
    // Create an object of Singleton Object
    private static final TrafficHelper trafficHelperInstance = new TrafficHelper();

    private static final int MINUTES_PER_HOUR = 60;

    // Get the only object available for TrafficInitializer
    private static final TrafficInitializer initializer = TrafficInitializer.getInstance();


    // Get the only object available
    public static TrafficHelper getInstance(){
        return trafficHelperInstance;
    }

    /**
     * Parse input speed from string to integer.
     *
     * @param speedLimit - User input
     * @return Integer value. If input is valid return actual entered value, else -1 
     */
    public int parseOrbitSpeed(String speedLimit) {
        try {
            return Integer.parseInt(speedLimit);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Find weather by weather type, from all available weathers.
     *
     * @param weatherType - User input
     * @return - Matched Weather.
     */
    public Weather getWeatherByType(String weatherType) {
        return initializer.getAllWeatherDetails().parallelStream()
                .filter(weather -> weatherType.equalsIgnoreCase(weather.getWeatherCondition().toString()))
                .findAny().orElse(null);
    }

    /**
     * Get vehicle objects corresponding to vehicle names.
     *
     * @param vehicleNames - suitable vehicle names for the weather
     * @return - List of Vehicle objects
     */
    public List<Vehicle> getSuitableVehicles(List<String> vehicleNames) {
        return initializer.getAllVehicles().stream()
                .filter(vehicle -> vehicleNames.contains(vehicle.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Find out all available sequence of routes/orbits between any source and destination.
     * Here each orbit-sequence contains only one orbit, i.e. orbit between any source and destination.
     * This source and destination is coming through user-input.
     * <p>
     * Note: Here source and destination, can't be interchanged. As road could be two ways. 
     *
     * @param source - User input
     * @param destination - User input
     * @return - all possible orbit/route - sequence between any source and destination.
     */
    public List<List<Orbit>> getOrbits(String source, String destination) {
        return initializer.getAllOrbits().stream()
                .filter(orbit -> orbit.getSource().equalsIgnoreCase(source)
                        && orbit.getDestination().equalsIgnoreCase(destination))
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }

    /**
     * This method is responsible for:
     * 		-  	Iterates over vehicles and available orbit-sequences.
     * 		- 	Populate TraverseDetail object with the traverse time, orbit-sequence and vehicle.
     * 		-	Add all populated TraverseDetail objects into a list and return it back.
     *
     * @param weather - User input
     * @param vehicles - Suitable vehicles for input weather
     * @param availableOrbitSequences - Available orbits/routes sequences, to traverse multiple destinations
     *
     * @return List of TraverseDetailForMultiSuburbs objects for these vehicles and orbit-sequences
     */
    public List<TraverseDetail> getTraverseDetails(Weather weather,
                                                   List<Vehicle> vehicles, List<List<Orbit>> availableOrbitSequences) {

        List<TraverseDetail> traverseDetails = new ArrayList<>();
        vehicles.stream()
                .forEach(vehicle -> availableOrbitSequences.stream()
                        .forEach(orbitSequence -> {
                            // Populate TraverseDetailForMultiSuburbs object with the traverse time, orbit-sequence and vehicle.
                            TraverseDetail traverseDetail = new TraverseDetail();

                            // Get optimized time to traverse an orbit with a vehicle, 
                            traverseDetail.setTraverseTime(calculateOptimizedTraverseTime(weather, vehicle, orbitSequence));
                            traverseDetail.setOrbits(orbitSequence);
                            traverseDetail.setVehicle(vehicle);

                            // Add all populated TraverseDetailForMultiSuburbs objects into a list
                            traverseDetails.add(traverseDetail);
                            //System.out.println(traverseDetail);
                        })
                );
        return traverseDetails;
    }

    /**
     * This method is responsible for:
     * 		-	Identify actual number of craters based on weather.
     * 		-	Calculate optimum traverse time for any orbit and vehicle combination.
     * <p>
     * Calculation steps:
     *  	1. 	Get total distance for all the orbits in the sequence.
     *  	2.	Calculate maximum speed from orbit's speed limit and vehicle's maximum speed.
     *  	3. 	Calculate traverse time = distance/applicable speed + crater cross time * number of actual craters.
     * <p>
     *  Assumption: Unit of speed limit of orbit-sequence and vehicle's speed should be same. Default unit is mega miles/hour.
     *
     * @param weather - Weather object
     * @param vehicle - Vehicle object
     * @param orbits - Orbit objects
     *
     * @return Optimized traverse time for orbit-sequence with a vehicle
     */
    private static Integer calculateOptimizedTraverseTime(Weather weather, Vehicle vehicle, List<Orbit> orbits) {

        // Get distance of the orbit. Default unit is mega miles.
        int distance = orbits.stream()
                .mapToInt(Orbit::getDistance).sum();

        // Get number of craters from orbit-sequence. 
        int numberOfCratersFromOrbitSequence = orbits.stream()
                .mapToInt(Orbit::getNumberOfCraters).sum();

        // Get minimum orbit's speed limit among those sequence. Default unit is mega miles/hour.
        int orbitSpeedLimit = orbits.stream()
                .mapToInt(orbit -> orbit.getVelocityLimit().getSpeed())
                .min().getAsInt();

        // Get vehicle's maximum speed. Default unit is mega miles/hour.
        int vehicleMaxSpeed = vehicle.getVelocity().getSpeed();

        /*
         * A vehicle cannot travel faster than the traffic speed limit of an orbit-sequence.
         * Calculate maximum speed, which is applicable for the orbit-sequence with that vehicle.
         */
        int applicableMaxSpeed = Math.min(orbitSpeedLimit, vehicleMaxSpeed);

        // Apply change rate on given craters and get actual applicable crater's number.
        int actualNumberOfCraters = (int) Math.round(numberOfCratersFromOrbitSequence * (100 + weather.getCraterChangePercentage()) / 100.00);

        /*
         * Return optimized traverse time for an orbit-sequence with a vehicle
         */
        return ((distance * MINUTES_PER_HOUR) / applicableMaxSpeed)
                + (actualNumberOfCraters * vehicle.getTimeToCrossCrater());
    }

    /**
     * Compare TraverseDetail objects and find out the optimized one, based on traverse time.
     *
     * @param traverseDetails - List of all populated TraverseDetail objects
     * @return - Optimum TraverseDetail object among the list
     */
    public TraverseDetail findOptimumTraverseDetail(List<TraverseDetail> traverseDetails) {
        TraverseDetail optimumTraverseDetail = null;
        int minimumTime = Integer.MAX_VALUE;
        for (TraverseDetail traverseDetail : traverseDetails) {
            int traverseTime = traverseDetail.getTraverseTime();
            if (traverseTime < minimumTime) {
                minimumTime = traverseTime;

                // No new memory allocation is happening, it's just a change of reference
                optimumTraverseDetail = traverseDetail;
            }

        }
        return optimumTraverseDetail;
    }

    /**
     * This method is responsible to generate output message from optimized TraverseDetail object 
     *
     * @param optimumTraverseDetail - Optimized TraverseDetail object
     * @return - Output message from OptimumTraverseDetail object
     */
    public String generateOutputMessage(TraverseDetail optimumTraverseDetail) {
        StringBuilder output = new StringBuilder();
        output.append("Vehicle ").append(optimumTraverseDetail.getVehicle().getName());
        optimumTraverseDetail.getOrbits().stream()
                        .forEach(orbit -> output.append(" on ").append(orbit.getName()));

        return output.toString();
    }
}

