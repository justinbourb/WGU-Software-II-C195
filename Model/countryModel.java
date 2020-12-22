package Model;

/** This class stores the data from the database in a Java Object*/
public class countryModel {
    private String Country_ID;
    private String Country;

    /** This is the constructor for the country Model*/
    public countryModel(String country_ID, String country) {
        this.Country_ID = country_ID;
        this.Country = country;
    }

    public String getCountry_ID() {
        return Country_ID;
    }

    public void setCountry_ID(String country_ID) {
        Country_ID = country_ID;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }


}
