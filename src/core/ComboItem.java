package core;

public class ComboItem {
    private int key;
    private String value;
    public ComboItem(int key,String value){
        this.value=value;
        this.key = key;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
