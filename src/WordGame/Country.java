package WordGame;

/**
 * Represents a country with its name, capital city, and an array of facts.
 */
public final class Country 
{
    /** The name of the country. */
    private final String name;

    /** The capital city of the country. */
    private final String capitalCityName;

    /** An array of three facts about the country. */
    private final String[] facts;

    /**
     * Constructs a Country object with the specified name, capital, and facts.
     * 
     * @param name            the name of the country
     * @param capitalCityName the capital city of the country
     * @param facts           an array of facts about the country (must be exactly 3)
     */
    public Country(final String name, final String capitalCityName, final String[] facts) 
    {
        if (facts == null || facts.length != 3) 
        {
            throw new IllegalArgumentException("Facts array must contain exactly 3 elements.");
        }
        this.name = name;
        this.capitalCityName = capitalCityName;
        this.facts = facts.clone();
    }

    /**
     * Gets the name of the country.
     *
     * @return the name of the country
     */
    public String getName() 
    {
        return name;
    }

    /**
     * Gets the capital city of the country.
     *
     * @return the capital city name
     */
    public String getCapitalCityName() 
    {
        return capitalCityName;
    }

    /**
     * Gets the array of facts about the country.
     *
     * @return a copy of the facts array
     */
    public String[] getFacts() 
    {
        return facts.clone();
    }
}
