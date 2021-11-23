package ru.erius.lab4.сity;

import ru.erius.lab3.livingbeing.people.Person;

import java.util.*;

public class City {

    private String name;
    private final List<Apartment> apartments = new ArrayList<>();
    private final List<CityResident> residents = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Apartment> getApartments() {
        return this.apartments;
    }

    public Apartment getApartmentByNumber(int number) {
        return this.apartments.get(number - 1);
    }

    public List<CityResident> getResidents() {
        return this.residents;
    }

    public Apartment createApartment(int maxResidents, CityResident... residents) {
        Apartment apartment = new Apartment(maxResidents, residents);
        apartment.number = this.apartments.size() + 1;
        this.apartments.add(apartment);
        return apartment;
    }

    public Apartment createApartment(int maxResidents) {
        return this.createApartment(maxResidents, new CityResident[]{});
    }

    public void randomInteraction() {
        int i = new Random().nextInt(this.residents.size());
        int j = new Random().nextInt(this.residents.size());
        if (i == j) {
            System.out.println("Все сидят дома :(");
            return;
        }
        this.residents.get(i).interact(this.residents.get(j));
    }

    public class Apartment {
        private int number;
        private final int maxResidents;
        private final List<CityResident> residents = new ArrayList<>();

        private Apartment() {
            this(2, new CityResident[]{});
        }

        private Apartment(int maxResidents) {
            this(maxResidents, new CityResident[]{});
        }

        private Apartment(int maxResidents, CityResident... residents) {
            if (maxResidents < 1) throw new IllegalMaxResidentsException("Квартира должна вмещать как минимум 1 человека");
            this.maxResidents = maxResidents;
            this.residents.addAll(Arrays.asList(residents));
        }

        public void addResidents(Person... people) {
            if (people.length + this.residents.size() > this.maxResidents)
                throw new MaxResidentsExceededException("В квартире " + this.number + " города " + City.this.name + " не хватает места новым жителям");
            Arrays.stream(people).forEach(person -> {
                CityResident resident = new CityResident(person);
                this.residents.add(resident);
                resident.setApartment(this);
                System.out.println(resident.getName() + " теперь живет в квартире " + this.number + " в городе " + City.this.name);
            });
        }

        public int getNumber() {
            return number;
        }

        public int getMaxResidents() {
            return maxResidents;
        }

        public List<CityResident> getResidents() {
            return residents;
        }

        public City getLocation() {
            return City.this;
        }
    }

    public static class MaxResidentsExceededException extends RuntimeException {
        public MaxResidentsExceededException() {
        }

        public MaxResidentsExceededException(String message) {
            super(message);
        }

        public MaxResidentsExceededException(String message, Throwable cause) {
            super(message, cause);
        }

        public MaxResidentsExceededException(Throwable cause) {
            super(cause);
        }
    }

    public static class IllegalMaxResidentsException extends RuntimeException {
        public IllegalMaxResidentsException() {
        }

        public IllegalMaxResidentsException(String message) {
            super(message);
        }

        public IllegalMaxResidentsException(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalMaxResidentsException(Throwable cause) {
            super(cause);
        }
    }
}
