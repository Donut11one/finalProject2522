package WordGame;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents the world containing a collection of countries.
 */
public final class World 
{
    /** A map of country names to their corresponding Country objects. */
    private final Map<String, Country> countries;

    /**
     * Constructs an empty World object.
     */
    public World() 
    {
        countries = new HashMap<>();
    }

    /**
     * Adds a country to the world.
     *
     * @param country the country to add
     */
    public void addCountry(final Country country) 
    {
        countries.put(country.getName(), country);
    }

    /**
     * Retrieves a country by name.
     *
     * @param name the name of the country
     * @return the Country object, or null if not found
     */
    public Country getCountryByName(final String name) 
    {
        return countries.get(name);
    }

    /**
     * Gets a random country from the world.
     *
     * @return a randomly selected Country object
     */
    public Country getRandomCountry() 
    {
        final Object[] values = countries.values().toArray();
        final int randomIndex = new Random().nextInt(values.length);
        return (Country) values[randomIndex];
    }

    /**
     * Prints all country names in the world.
     */
    public void printAllCountries() 
    {
        if (countries.isEmpty()) {
            System.out.println("No countries available.");
            return;
        }

        System.out.println("Countries in the world:");
        for (String countryName : countries.keySet()) 
        {
            System.out.println(countryName);
        }
    }
}
