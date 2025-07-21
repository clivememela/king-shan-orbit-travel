package za.co.titandynamix;

import za.co.titandynamix.finder.TrafficFinder;
import za.co.titandynamix.model.Orbit;

import java.util.*;

/**
 * @author clivememela
 */
public class KingShanOrbitTravelApplication {

    public static void main(String[] args) {
       // SpringApplication.run(KingShanOrbitTravelApplication.class, args);

        // Create an instance of TrafficFinder, which will calculate optimum time.
        TrafficFinder trafficFinder = new TrafficFinder();

        // Create an instance of Scanner, to scan user inputs
        Scanner scanner = new Scanner(System.in);

        // Flag to continue the program in a loop
        boolean continueLoop;

        do {
            // Input for weather conditions
            System.out.println("Enter weather conditions (Sunny, Rainy, Windy): ");
            String weatherCondition = scanner.nextLine();

            /*
             * Input for source. For this problem, source and destination is always same.
             * i.e. as per the requirements:
             * Goal: To go from Silk Dorb to Hallitharam in the shortest time possible.
             *
             * Therefore, source is 'Silk Drob' and destination is 'Hallitharam'
             */
            String source = "Silk Drob";
            String destination = "Hallitharam";

            /*
             * Get all routes/orbit sequence between the source and destination.
             * Here each orbit-sequence contains only one orbit,
             * i.e. route/orbit between any source and destination.
             */
            List<List<Orbit>> availableOrbits = trafficFinder.getTrafficHelper().getOrbits(source, destination);

            // Get all available orbit names and arrange them in sequence. Will be used while taking inputs from user.
            Set<String> availableOrbitNames = new TreeSet<>();
            availableOrbits.stream().forEach(orbitSequence -> orbitSequence.stream()
                    .forEach(orbit -> availableOrbitNames.add(orbit.getName())));

            /*
             * This map is used to hold user inputs (orbit's speed limit) corresponding to orbit name.
             * Will be used while doing input validation.
             */
            Map<String, Integer> orbitSpeedLimitMap = new LinkedHashMap<>();
            for (String orbitName : availableOrbitNames) {
                System.out.println("Enter the  max traffic speed of " + orbitName + ": ");
                // Input for speed limit of the orbit
                String input = scanner.nextLine();

                // Parse input speed from string to integer. Put into the map, to be used in coming iteration
                orbitSpeedLimitMap.put(orbitName, trafficFinder.getTrafficHelper().parseOrbitSpeed(input));
            }

            // Call the trafficFinder method to find the optimal time and print the optimal time
            System.out.println("\nOptimal time:" + trafficFinder.calculateOptimumTime(weatherCondition, availableOrbits, orbitSpeedLimitMap));

            System.out.println("\n***************************************************************");
            // Ask user if they want to continue
            System.out.println("Do you want to continue? (yes/y or no/n) N.B not case sensitive");
            System.out.println("****************************************************************");
            String continueInput = scanner.nextLine();

            // Set continueLoop flag based on user input
            if (continueInput.trim(). equalsIgnoreCase("yes") || continueInput.trim().equalsIgnoreCase("y")
                    || continueInput.trim().isEmpty())
                continueLoop = true;
            else if (continueInput.trim().equalsIgnoreCase("no") || continueInput.trim().equalsIgnoreCase("n"))
                continueLoop = false;
            else {
                System.out.println("=== Invalid response entered : " + continueInput + " : please see below and try again");

                System.out.println("\n***************************************************************");
                // Ask user if they want to continue
                System.out.println("Do you want to continue? (yes/y or no/n) N.B not case sensitive");
                System.out.println("****************************************************************");
                continueInput = scanner.nextLine();

                continueLoop = true;
            }

        }while (continueLoop);

    }
}
