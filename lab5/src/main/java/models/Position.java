package models;


import javax.persistence.*;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private int position_id;
    @Column(name = "position_name")
    private String position_name;
    @Column(name = "salary")
    private int salary;
    @Column(name = "work_time_month")
    private int work_time_month;

    public Position() {
    }

    public Position(String position_name, int salary, int work_time_month) {
        this.position_name = position_name;
        this.salary = salary;
        this.work_time_month = work_time_month;
    }

    public int getPosition_id() {
        return position_id;
    }
    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setWorkTimeMonth(int work_time_month) {
        this.work_time_month = work_time_month;
    }

    public int getWorkTimeMonth() {
        return work_time_month;
    }

    @Override
    public String toString(){ return String.format("%-30d%-30s%-30d%-30d ", position_id, position_name,
            salary, work_time_month); }
}
