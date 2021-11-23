package ru.erius.lab4.сity;

import ru.erius.lab3.livingbeing.LivingBeing;
import ru.erius.lab3.livingbeing.people.Person;

public class CityResident extends Person {

    private City.Apartment apartment = null;

    public CityResident(String name) {
        super(name);
    }

    public CityResident(LivingBeing livingBeing) {
        super(livingBeing);
    }

    public CityResident(String name, int age) {
        super(name, age);
    }

    public CityResident(String name, int age, String speech) {
        super(name, age, speech);
    }

    public CityResident(Person person) {
        super(person.getName(), person.getAge(), person.getSpeech());
    }

    public City.Apartment getApartment() {
        return this.apartment;
    }

    public void setApartment(City.Apartment apartment) {
        this.apartment = apartment;
    }

    public City getCity() {
        return this.apartment.getLocation();
    }

    public void interact(CityResident resident) {
        class Interaction implements Runnable {
            public void run() {
                System.out.println(CityResident.this.name + " встретил " + resident.name + " во время прогулки");
                CityResident.this.speak();
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                resident.speak();
            }
        }
        new Thread(new Interaction()).start();
    }

    @Override
    public String toString() {
        return "CityResident{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", speech='" + speech + '\'' +
                '}';
    }
}
