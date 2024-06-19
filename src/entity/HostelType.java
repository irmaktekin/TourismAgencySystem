package entity;

public enum HostelType {
    ULTRA("Ultra All Inclusive"),
    INCLUSIVE("All Inclusive"),
    BREAKFAST("Room Breakfast"),
    HOSTEL("Full Hostel"),
    BOARD("Half Board"),
    BED("Only Bed"),
    CREDIT("Full Credit Alcohol Exclusive");

    private final String hostelType;
    HostelType(String hostelType){
        this.hostelType = hostelType;
    }

    public String getHostelTypeInfo(){
        return this.name();
    }
    @Override
    public String toString() {
        return "HostelType{" +
                "hostelType='" + hostelType + '\'' +
                '}';
    }
}
