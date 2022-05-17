package models;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "accountuser")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int account_id;
    @Column(name = "account_login")
    private String account_login;
    @Column(name = "password")
    private String password;
    @Column(name = "registration_date")
    private Date registration_date;
    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public Account() {
    }

    public Account(Worker worker, String account_login, String password, Date registration_date) {
        this.worker = worker;
        this.account_login = account_login;
        this.password = password;
        this.registration_date = registration_date;
    }


    public String getAccount_login() {
        return account_login;
    }

    public void setAccount_login(String account_login) {
        this.account_login = account_login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getAccount_id() {
        return account_id;
    }

    @Override
    public String toString(){ return String.format("%-15d%s%-15s%-15tF%-15d", account_id, account_login, password, registration_date, worker.getWorker_id()); }
}
