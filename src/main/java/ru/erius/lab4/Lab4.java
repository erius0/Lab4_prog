package ru.erius.lab4;

import ru.erius.lab3.livingbeing.people.Person;
import ru.erius.lab4.сity.City;
import ru.erius.lab4.сity.Employee;

import java.util.Random;

public class Lab4 {
    public final static City CITY = new City("Цветочный город");
    public final static Person[] PEOPLE = new Person[]{
            new Person("Пончик", 20, "Какие вкусные пироги!"),
            new Person("Сиропчик", 20, "Я выпил столько газированной воды!"),
            new Person("Знайка", 20, "Какой чудесный мост!"),
            new Person("Авоська", 20, "Я убирал урожай с малышками"),
            new Person("Торопыжка", 20, "Я тоже"),
            new Person("Винтик", 20, "Шофер Бублик - самый лучший"),
            new Person("Шпунтик", 20, "А вот у Шурупчика - все на кнопках!"),
            new Person("Пулька", 20, "Какой же замечательный врач Медуница!"),
            new Person("Шурупчик", 20),
            new Person("Бублик", 20),
            new Person("Медуница", 20)
    };

    static {
        City.Apartment apartment1 = CITY.createApartment(5);
        City.Apartment apartment2 = CITY.createApartment(5);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) apartment1.addResidents(PEOPLE[i]);
            else apartment2.addResidents(PEOPLE[i]);
        }
    }

    public static void main(String[] args) {
        Lab4.checkedExceptions();
        Lab4.uncheckedExceptions();
    }

    // Метод, который показывает пример обработки checked исключения IllegalEmployeeState
    public static void checkedExceptions() {
        Employee employee = new Employee("Бублик", 20);
        employee.setWork(new Employee.IWork() {
            int shifts = 0;
            final int salary = new Random().nextInt(10_000) + 1, maxShifts = new Random().nextInt(3) + 1;

            @Override
            public int work(Employee emp) {
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(emp.getName() + " отработал смену и заработал " + salary);
                shifts++;
                if (shifts >= maxShifts) {
                    try {
                        emp.stopWorking();
                    } catch (Employee.IllegalEmployeeState e) {
                        e.printStackTrace();
                    }
                }
                return salary;
            }

            @Override
            public void stop(Employee emp) {
                System.out.println(emp.getName() + " закончил работать. Баланс: " + emp.getMoney());
            }
        });
        try {
            employee.startWorking();
        } catch (Employee.IllegalEmployeeState e) {
            e.printStackTrace();
        }
        try {
            employee.startWorking();
        } catch (Employee.IllegalEmployeeState e) {
            System.out.println("Исключение IllegalEmployeeState обработано");
        }
    }

    // Метод, который показывает пример обработки unchecked исключений
    // MaxResidentsExceededException, IllegalMaxResidentsException и PersonSpeechException
    public static void uncheckedExceptions() {
        Person person1 = new Person("Чарли", 24);
        Person person2 = new Person("Влад", 7);
        try {
            person1.speak();
        } catch (Person.PersonSpeechException e) {
            System.out.println("Исключение PersonSpeechException обработано");
        }

        City.Apartment apartment;
        try {
            apartment = CITY.createApartment(-1);
        } catch (City.IllegalMaxResidentsException e) {
            System.out.println("Исключение IllegalMaxResidentsException обработано");
            apartment = CITY.createApartment(1);
        }

        apartment.addResidents(person1);
        try {
            apartment.addResidents(person2);
        } catch (City.MaxResidentsExceededException e) {
            System.out.println("Исключение MaxResidentsExceededException обработано");
        }
    }
}
