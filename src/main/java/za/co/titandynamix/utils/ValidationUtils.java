package za.co.titandynamix.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author clivememela
 */
public class ValidationUtils {

    public static final String SPACE	= " ";
    public static final String EMPTY 	= "";

    public static final int INDEX_NOT_FOUND = -1;

    // Restrict instantiation
    private ValidationUtils() {
        super();
    }

    public static boolean isEmpty(Object object) {
        return object == null;
    }

    public static boolean isNotEmpty(Object object) {
        return object != null;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isBlank(String str) {
        return (str == null) || (str.trim().isEmpty());
    }

    public static boolean isNotBlank(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static <E> boolean isEmpty(Collection<E> collectionObj) {
        return collectionObj == null || collectionObj.isEmpty();
    }

    public static <E> boolean isNotEmpty(Collection<E> collectionObj) {
        return collectionObj != null && !collectionObj.isEmpty();
    }

    /**
     * Convert array inputs into a list, where inputs are array of objects
     *
     * @param inputs - User inputs as array of objects
     * @return - Converted input object list
     */
    public static List<Object> getList(Object... inputs) {
        return Arrays.asList(inputs);
    }

    /**
     * Convert array inputs into a list, where inputs are array of string
     *
     * @param inputs - User inputs as array of string
     * @return - Converted input list
     */
    public static List<String> getList(String... inputs) {
        return Arrays.asList(inputs);
    }
}
