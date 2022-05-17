package logics;

import models.Position;
import models.Worker;
import models.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class WorkerLogic {
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
        System.out.print("Введите ID города:");
        int city_id = scanner.nextInt();
        System.out.print("Введите ID должности:");
        int position_id = scanner.nextInt();
        System.out.print("Введите имя работника:");
        scanner.nextLine();
        String worker_name = scanner.nextLine();
        System.out.print("Введите возраст:");
        int age = scanner.nextInt();
        System.out.print("Введите пол:");
        scanner.nextLine();
        String male = scanner.next();
        System.out.print("Введите почту:");
        scanner.nextLine();
        String email = scanner.next();
        try {
            Worker worker = new Worker(session.get(City.class, city_id), session.get(Position.class, position_id),
                    worker_name, age, male, email);
            session.save(worker);
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
                List<Worker> workerList = session.createQuery("SELECT w FROM Worker w",
                        Worker.class).getResultList();
                System.out.println("Работник");
                System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                        "Название", "Возраст", "Пол", "Почта");
                workerList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID работника:");
        int worker_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.ID города");
        System.out.println("2.ID должности");
        System.out.println("3.Название");
        System.out.println("4.Возраст");
        System.out.println("5.Пол");
        System.out.println("6.Почта");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID города:");
                scanner.nextLine();
                int city_id = scanner.nextInt();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setCity(session.get(City.class, city_id));
                    session.save(worker);

                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите ID должности:");
                scanner.nextLine();
                int position_id = scanner.nextInt();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setPosition(session.get(Position.class, position_id));
                    session.save(worker);

                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите название:");
                scanner.nextLine();
                String worker_name = scanner.nextLine();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setWorker_name(worker_name);
                    session.save(worker);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите возраст:");
                scanner.nextLine();
                int age = scanner.nextInt();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setAge(age);
                    session.save(worker);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите пол:");
                scanner.nextLine();
                String male = scanner.nextLine();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setMale(male);
                    session.save(worker);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 6 -> {
                System.out.print("Введите почту:");
                scanner.nextLine();
                String email = scanner.nextLine();
                try {
                    Worker worker = session.get(Worker.class, worker_id);
                    worker.setEmail(email);
                    session.save(worker);
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
        System.out.print("Введите ID работника:");
        int worker_id = scanner.nextInt();
        try {
            Worker worker = session.get(Worker.class, worker_id);
            session.delete(worker);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.Id работника");
        System.out.println("2.Id города");
        System.out.println("3.Id должности");
        System.out.println("4.Название");
        System.out.println("5.Возраст");
        System.out.println("6.Пол");
        System.out.println("7.Почта");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Worker> workerList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID работника:");
                int worker_id = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT w FROM Worker w WHERE worker_id =" + worker_id,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите ID города:");
                int city_id = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE city_id =" + city_id,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите ID должности:");
                int position_id = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE position_id =" + position_id,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите название:");
                int worker_name = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE worker_name =" + worker_name,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 5 -> {
                System.out.print("Введите возраст:");
                int age = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE age =" + age,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 6 -> {
                System.out.print("Введите пол:");
                int male = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE male =" + male,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 7 -> {
                System.out.print("Введите почту:");
                int email = scanner.nextInt();
                try {
                    workerList = session.createQuery("SELECT p FROM Worker p WHERE email =" + email,
                            Worker.class).getResultList();
                    System.out.println("Работник");
                    System.out.printf("%-30s%-30s%-30s%-30s%-30s%-30s%-30s\n","ID","ID города","ID должности",
                            "Название", "Возраст", "Пол", "Почта");
                    workerList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
