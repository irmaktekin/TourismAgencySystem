package entity;

public enum HotelStar {
    ONE_STAR(1),
    TWO_STAR(2),
    THREE_STAR(3),
    FOUR_STAR(4),
    FIVE_STAR(5);

    private final int hotelRate;
    HotelStar(int hotelRate){
        this.hotelRate = hotelRate;
    }
    public int getHotelRate(){
        return  hotelRate;
    }

    @Override
    public String toString() {
        return "HotelStar{" +
                "hotelRate=" + hotelRate +
                '}';
    }
}
