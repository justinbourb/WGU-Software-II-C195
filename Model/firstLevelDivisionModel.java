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

    public firstLevelDivisionModel(String division_ID, String division, String COUNTRY_ID) {
        this.Division_ID = division_ID;
        this.Division = division;
        this.COUNTRY_ID = COUNTRY_ID;
    }

    public String getDivision_ID() {
        return Division_ID;
    }

    public void setDivision_ID(String division_ID) {
        Division_ID = division_ID;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getCOUNTRY_ID() {
        return COUNTRY_ID;
    }

    public void setCOUNTRY_ID(String COUNTRY_ID) {
        this.COUNTRY_ID = COUNTRY_ID;
    }
}


