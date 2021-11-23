package ru.erius.lab4.сity;

import ru.erius.lab3.livingbeing.people.Person;

public class Employee extends CityResident {

    protected int money;
    protected boolean isWorking = false;
    protected IWork work;

    public Employee(String name) {
        this(name, 0, null, null);
    }

    public Employee(String name, int age) {
        this(name, age, null, null);
    }

    public Employee(String name, int age, String speech) {
        this(name, age, speech, null);
    }

    public Employee(String name, IWork work) {
        this(name, 0, null, work);
    }

    public Employee(String name, int age, IWork work) {
        this(name, age, null, work);
    }

    public Employee(String name, int age, String speech, IWork work) {
        super(name, age, speech);
        this.work = work;
    }

    public Employee(Person person) {
        this(person.getName(), person.getAge(), person.getSpeech(), null);
    }

    public Employee(Person person, IWork work) {
        this(person.getName(), person.getAge(), person.getSpeech(), work);
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public IWork getWork() {
        return this.work;
    }

    public void setWork(IWork work) {
        this.work = work;
    }

    public void startWorking() throws IllegalEmployeeState {
        if (this.isWorking) throw new IllegalEmployeeState("Работник " + this.name + " уже работает");
        if (this.work == null) throw new IllegalEmployeeState("Работник " + this.name + " не может начать работу, т. к. её нет");
        System.out.println(this.name + " начал работать");
        this.isWorking = true;
        new Thread(() -> {
            while (this.isWorking)
                money += this.work.work(this);
        }).start();
    }

    public void stopWorking() throws IllegalEmployeeState {
        if (!this.isWorking) throw new IllegalEmployeeState("Работник " + this.name + " не работает");
        this.isWorking = false;
        this.work.stop(this);
    }

    public interface IWork {
        int work(Employee employee);
        void stop(Employee employee);
    }

    public static class IllegalEmployeeState extends Exception {

        public IllegalEmployeeState() {
        }

        public IllegalEmployeeState(String message) {
            super(message);
        }

        public IllegalEmployeeState(String message, Throwable cause) {
            super(message, cause);
        }

        public IllegalEmployeeState(Throwable cause) {
            super(cause);
        }
    }
}
