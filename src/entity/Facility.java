package entity;

public class Facility {
    private int facility_id;
    private String facility_name;

    public void setFacility_id(int facility_id) {
        this.facility_id = facility_id;
    }

    public void setFacility_name(String facility_name) {
        this.facility_name = facility_name;
    }

    public int getFacility_id() {
        return facility_id;
    }

    public String getFacility_name() {
        return facility_name;
    }

    public Facility() {
    }

    public Facility(int facilityId, String facilityName) {
        facility_id = facilityId;
        facility_name = facilityName;
    }
}
