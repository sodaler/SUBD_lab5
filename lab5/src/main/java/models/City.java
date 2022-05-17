package models;


import javax.persistence.*;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private int city_id;
    @Column(name = "city_name")
    private String city_name;

    public City() {
    }

    public City(String city_name) {
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }
    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name() {
        return city_name;
    }

    @Override
    public String toString(){ return String.format("%-15d%s", city_id, city_name); }
}
