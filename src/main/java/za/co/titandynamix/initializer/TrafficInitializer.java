package za.co.titandynamix.initializer;

import za.co.titandynamix.enums.WeatherCondition;
import za.co.titandynamix.model.Orbit;
import za.co.titandynamix.model.Vehicle;
import za.co.titandynamix.model.Velocity;
import za.co.titandynamix.model.Weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DESCRIPTION - This class is responsible to initialize all data with hard coded values. Data has been initialized from the standard I/O in the problem specification.
 * <p>
 * It initializes following objects:
 * 	-	List of all possible weathers
 *  - 	List of available vehicles
 *  -  	List of routes/orbits, and its corresponding details.
 * <p>
 * Note: In real time application, these values will be retrieved from database or file system or through any third party web service.
 *
 * @author clivememela
 **/
public class TrafficInitializer {
    // Create an object of SingleObject
    private static final TrafficInitializer initializerInstance = new TrafficInitializer();


    // Get the only object available
    public static TrafficInitializer getInstance(){
        return initializerInstance;
    }

    /**
     * This method is responsible for
     * 	-	Populate all Weather objects with hard coded values from the standard I/O in the problem pdf.
     * <p>
     * Sunny - craters reduce by 10%. Car, bike and tuktuk can be used in this weather.
     * Rainy - craters increase by 20%. Car and tuktuk can be used in this weather.
     * Windy - no change to number of craters. All vehicles can be used in this weather.
     * <p>
     * In the list of vehicles, sequence should be maintained. 
     * As, if there is a tie in which vehicle to choose, use bike, auto/tuktuk, car in that order.
     *
     * @return - List of all possible weathers
     */
    public List<Weather> getAllWeatherDetails() {
        List<Weather> weathers = new ArrayList<>();
        weathers.add(new Weather(WeatherCondition.SUNNY, -10, Arrays.asList("Bike", "Tuktuk", "Car")));
        weathers.add(new Weather(WeatherCondition.RAINY, +20, Arrays.asList("Tuktuk", "Car")));
        weathers.add(new Weather(WeatherCondition.WINDY, +0, getAllVehicleNames()));
        return weathers;
    }

    /**
     * Returns all vehicle names. This is used while creating Weather object, where all types of vehicles can be used.
     *
     * @return - List of all vehicle names
     */
    public List<String> getAllVehicleNames() {
        return getAllVehicles().stream().map(Vehicle::getName).collect(Collectors.toList());
    }

    /**
     * This method is responsible for
     * 	-	Populate all Vehicle objects with hard coded values from the standard I/O in the problem pdf.
     * <p>
     * Bike - 10 mega miles/hour & takes 2 min to cross 1 crater
     * Tuktuk - 12 mm/hour & takes 1 min to cross 1 crater
     * Car - 20 mm/hour & takes 3 min to cross 1 crater
     * <p>
     * In the list of vehicles, sequence should be maintained. 
     * As, if there is a tie in which vehicle to choose, use bike, tuktuk, car in that order.
     *
     * @return - List of available vehicles
     */
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("Bike", new Velocity(10, "megamiles/hour"), 2));
        vehicles.add(new Vehicle("Tuktuk", new Velocity(12, "megamiles/hour"), 1));
        vehicles.add(new Vehicle("Car", new Velocity(20, "megamiles/hour"), 3));
        return vehicles;
    }

    /**
     * This method is responsible for
     * 	-	Populate all Orbit objects with hard coded values from the standard I/O in the problem pdf.
     * <p>
     * Orbit options: 
     * Orbit 1 - 18 mega miles & 20 craters to cross
     * Orbit 2 - 20 mega miles & 10 craters to cross
     * <p>
     * Note: 1.) Orbit's speed limit is initialized with -1. Valid value will be set through user's input.
     * 		 2.) Here source and destination, can't be interchanged. As road could be two ways. 
     * 		 	 i.e. A to B is not same with B to A.
     *
     * @return - List of routes/orbits along with its corresponding details.
     */
    public List<Orbit> getAllOrbits() {
        List<Orbit> orbits = new ArrayList<>();
        // Data has been initialized from the standard I/O in the problem pdf.
        orbits.add(new Orbit("Orbit1", "Silk Drob", "Hallitharam", 18, 20, new Velocity(-1, "megamiles/hour")));
        orbits.add(new Orbit("Orbit2", "Silk Drob", "Hallitharam", 20, 10, new Velocity(-1, "megamiles/hour")));

        return orbits;
    }
}
