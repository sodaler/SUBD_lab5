package models;

import javax.persistence.*;

@Entity
@Table(name = "worker")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worker_id")
    private int worker_id;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @Column(name = "worker_name")
    private String worker_name;
    @Column(name = "age")
    private int age;
    @Column(name = "male")
    private String male;
    @Column(name = "email")
    private String email;

    public Worker() {

    }

    public Worker(City city, Position position, String worker_name, int age, String male, String email) {
        this.city = city;
        this.position = position;
        this.worker_name = worker_name;
        this.age = age;
        this.male = male;
        this.email = email;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public String getWorker_name() {
        return worker_name;
    }
    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getMale() {
        return male;
    }
    public void setMale(String male) {
        this.male = male;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){ return String.format("%-30d%-30d%-30d%-30s%-30d%-30s%-30s",
            worker_id, city.getCity_id(),position.getPosition_id(), worker_name, age, male,
            email); }
}
