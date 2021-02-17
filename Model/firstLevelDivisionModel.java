package Model;

/** This class stores the data from the database in a Java Object*/
public class firstLevelDivisionModel {
    private String Division_ID;
    private String Division;
    private String Create_Date;
    private String Created_By;
    private String Last_Update;
    private String Last_Updated_By;
    private String COUNTRY_ID;

    /** This is the constructor for the first level division Model
     *
     * @param division_ID id
     * @param division division
     * @param COUNTRY_ID country id
     */
    public firstLevelDivisionModel(String division_ID, String division, String COUNTRY_ID) {
        this.Division_ID = division_ID;
        this.Division = division;
        this.COUNTRY_ID = COUNTRY_ID;
    }

    /**This is an auto-generated getter or setter function
     *
     * @return id
     */
    public String getDivision_ID() {
        return Division_ID;
    }

    /**This is an auto-generated getter or setter function
     *
     * @param division_ID the id
     */
    public void setDivision_ID(String division_ID) {
        Division_ID = division_ID;
    }

    /**This is an auto-generated getter or setter function
     *
     * @return the division
     */
    public String getDivision() {
        return Division;
    }

    /**This is an auto-generated getter or setter function
     *
     * @param division the division
     */
    public void setDivision(String division) {
        Division = division;
    }

    /**This is an auto-generated getter or setter function
     *
     * @return country id
     */
    public String getCOUNTRY_ID() {
        return COUNTRY_ID;
    }

    /**This is an auto-generated getter or setter function
     *
     * @param COUNTRY_ID an id
     */
    public void setCOUNTRY_ID(String COUNTRY_ID) {
        this.COUNTRY_ID = COUNTRY_ID;
    }
}


