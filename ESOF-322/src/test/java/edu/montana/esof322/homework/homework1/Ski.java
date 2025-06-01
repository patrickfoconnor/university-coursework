package edu.montana.esof322.homework.homework1;

public class Ski {

    private int length;
    private String name;
    private String brand;

    private Ski(int length, String name, String brand) {
        this.length = length;
        this.name = name;
        this.brand = brand;
    }

    public int getLength() {
        return length;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public static SkiBuilder builder() {
        return new SkiBuilder();
    }

    public static class SkiBuilder {
        private int length;
        private String name;
        private String brand;

        public SkiBuilder withLength(int length) {
            this.length = length;
            return this;
        }

        public SkiBuilder withName(String name) {
        this.name = name;
        return this;
        }

        public SkiBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Ski build(){
            return new Ski(length, name, brand);
        }
    }


}
