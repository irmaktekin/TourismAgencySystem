package entity;

public enum HostelType {
    ULTRA(1,"Ultra All Inclusive"),
    INCLUSIVE(2,"All Inclusive"),
    BREAKFAST(3,"Room Breakfast"),
    HOSTEL(4,"Full Hostel"),
    BOARD(5,"Half Board"),
    BED(6,"Only Bed"),
    CREDIT(7,"Full Credit Alcohol Exclusive");

    private final String hostelType;
    private final int typeId;

    HostelType(int typeId,String hostelType){
        this.hostelType = hostelType;
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
    }
    public String getHostelTypeInfo(){
        return hostelType;
    }

    @Override
    public String toString() {
        return "HostelType{" +
                "hostelType='" + hostelType + '\'' +
                '}';
    }

}
