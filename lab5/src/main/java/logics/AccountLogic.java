package logics;

import models.Account;
import models.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AccountLogic {
    public void work(SessionFactory sessionFactory){
        System.out.println("1.Создать");
        System.out.println("2.Прочитать");
        System.out.println("3.Обновить");
        System.out.println("4.Удалить");
        System.out.println("5.Вернуться в меню");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch (i) {
            case 1 -> create(session);
            case 2 -> read(session);
            case 3 -> update(session);
            case 4 -> delete(session);
            case 5 -> {
                session.close();
                return;
            }
            default -> System.out.println("Неверный ввод");
        }
        session.getTransaction().commit();
    }

    private void create(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID работника:");
        int worker_id = scanner.nextInt();
        System.out.print("Введите логин сотрудника:");
        scanner.nextLine();
        String account_login = scanner.next();
        System.out.print("Введите пароль сотрудника:");
        scanner.nextLine();
        String password = scanner.next();
        System.out.print("Введите время регистрации сотрудника:");
        scanner.nextLine();
        Date registration_date = java.sql.Date.valueOf(scanner.next());
        try {
            Account account = new Account(session.get(Worker.class, worker_id), account_login, password, registration_date);
            session.save(account);
        }
        catch (Exception ex){
            System.out.println("Ошибка создания записи");
        }
    }

    private void read(Session session){
        System.out.println("1.С фильтром");
        System.out.println("2.Без фильтра");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i){
            case 1-> filter(session);
            case 2-> {
                List<Account> accountList = session.createQuery("SELECT f FROM Accountuser f",
                        Account.class).getResultList();
                System.out.println("Аккаунт");
                System.out.printf("%-30s%-30s%-30s%-30s\n","ID работника","логин",
                        "пароль", "время регистрации");
                accountList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID аккаунта:");
        int account_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.ID работника");
        System.out.println("2.Логин");
        System.out.println("3.Пароль");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID работника:");
                scanner.nextLine();
                int worker_id = scanner.nextInt();
                try {
                    Account account = session.get(Account.class, account_id);
                    account.setWorker(session.get(Worker.class, worker_id));
                    session.save(account);
                    session.getTransaction().commit();
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите логин сотрудника:");
                scanner.nextLine();
                String account_login = scanner.nextLine();
                try {
                    Account account = session.get(Account.class, account_id);
                    account.setAccount_login(account_login);
                    session.save(account);
                    session.getTransaction().commit();
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите пароль сотрудника:");
                scanner.nextLine();
                String password = scanner.nextLine();
                try {
                    Account account = session.get(Account.class, account_id);
                    account.setPassword(password);
                    session.save(account);
                    session.getTransaction().commit();
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }

    }

    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID аккаунта:");
        int account_id = scanner.nextInt();
        try {
            Account account = session.get(Account.class, account_id);
            session.delete(account);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.Id работника");
        System.out.println("2.Логин");
        System.out.println("3.Пароль");
        System.out.println("4.Дата регистрации");
        System.out.println("5.Дата регистрации");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Account> accountList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID аккаунта:");
                int account_id = scanner.nextInt();
                try {
                    accountList = session.createQuery("SELECT f FROM accountuser f WHERE account_id =" + account_id,
                            Account.class).getResultList();
                    System.out.println("Аккаунт");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID работника","логин",
                            "пароль", "Дата регистрации");
                    accountList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите ID работника:");
                int worker_id = scanner.nextInt();
                try {
                    accountList = session.createQuery("SELECT f FROM Accountuser f WHERE worker_id =" + worker_id,
                            Account.class).getResultList();
                    System.out.println("Аккаунт");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID работника","логин",
                            "пароль", "дата регистрации");
                    accountList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите логин:");
                scanner.nextLine();
                String account_login = scanner.next();
                try {
                    accountList = session.createQuery("SELECT f FROM Accountuser f WHERE account_login =" + account_login,
                            Account.class).getResultList();
                    System.out.println("Аккаунт");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","логин",
                            "пароль", "дата регистрации");
                    accountList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите пароль:");
                scanner.nextLine();
                String password = scanner.next();
                try {
                    accountList = session.createQuery("SELECT f FROM Accountuser f WHERE password =" + password,
                            Account.class).getResultList();
                    System.out.println("Аккаунт");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","логин",
                            "пароль", "дата регистрации");
                    accountList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите дату регистрации:");
                scanner.nextLine();
                Date registration_date = java.sql.Date.valueOf(scanner.next());
                try {
                    accountList = session.createQuery("SELECT f FROM Accountuser f WHERE registration_date =" + registration_date,
                            Account.class).getResultList();
                    System.out.println("Аккаунт");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","логин",
                            "пароль", "дата регистрации");
                    accountList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
