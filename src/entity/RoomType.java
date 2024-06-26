package entity;

public enum RoomType {
    SINGLE("Single Room"),
    DOUBLE("Double Room"),
    JUNIOR_SUITE("Junior Suite"),
    SUITE("Suite");

    private final String typeName;

    RoomType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
    @Override
    public String toString() {
        return typeName;
    }
}
