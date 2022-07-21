package logics;

import models.Position;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class PositionLogic {
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
        System.out.print("Введите название должности:");
        String position_name = scanner.nextLine();
        System.out.print("Введите ЗП сотрудника:");
        int salary = scanner.nextInt();
        System.out.print("Введите Рабочее время сотрудника:");
        int work_time_month = scanner.nextInt();
        try {
            Position position = new Position(position_name, salary, work_time_month);
            session.save(position);
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
                List<Position> positionList = session.createQuery("SELECT p FROM Position p",
                        Position.class).getResultList();
                System.out.println("Должность");
                System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название",
                        "ЗП", "Время работы");
                positionList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID должности:");
        int position_id = scanner.nextInt();
        System.out.println("Что вы хотите обновить?");
        System.out.println("1.Название должности");
        System.out.println("2.ЗП");
        System.out.println("3.Время работы");
        System.out.print(">");
        int i = scanner.nextInt();
        switch (i) {
            case 1 -> {
                System.out.print("Введите название должности:");
                scanner.nextLine();
                String position_name = scanner.nextLine();
                try {
                    Position position = session.get(Position.class, position_id);
                    position.setPosition_name(position_name);
                    session.save(position);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите ЗП:");
                scanner.nextLine();
                int salary = scanner.nextInt();
                try {
                    Position position = session.get(Position.class, position_id);
                    position.setSalary(salary);
                    session.save(position);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите Время работы:");
                scanner.nextLine();
                int work_time_month = scanner.nextInt();
                try {
                    Position position = session.get(Position.class, position_id);
                    position.setWorkTimeMonth(work_time_month);
                    session.save(position);
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
        System.out.print("Введите ID должности:");
        int position_id = scanner.nextInt();
        try {
            Position position = session.get(Position.class, position_id);
            session.delete(position);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.Id должности");
        System.out.println("2.Название");
        System.out.println("3.ЗП");
        System.out.println("4.Время работы");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<Position> positionList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID должности:");
                int position_id = scanner.nextInt();
                try {
                    positionList = session.createQuery("SELECT p FROM Position p WHERE position_id =" + position_id,
                            Position.class).getResultList();
                    System.out.println("Должность");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название",
                            "ЗП", "Время работы");
                    positionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите название:");
                String position_name = scanner.nextLine();
                try {
                    positionList = session.createQuery("SELECT p FROM Position p WHERE position_name =" + position_name,
                            Position.class).getResultList();
                    System.out.println("Должность");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название",
                            "ЗП", "Время работы");
                    positionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 3 -> {
                System.out.print("Введите ЗП:");
                int salary = scanner.nextInt();
                try {
                    positionList = session.createQuery("SELECT p FROM Position p WHERE salary =" + salary,
                            Position.class).getResultList();
                    System.out.println("Должность");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название",
                            "ЗП", "Время работы");
                    positionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 4 -> {
                System.out.print("Введите Время работы:");
                int work_time_month = scanner.nextInt();
                try {
                    positionList = session.createQuery("SELECT p FROM Position p WHERE work_time_month =" + work_time_month,
                            Position.class).getResultList();
                    System.out.println("Должность");
                    System.out.printf("%-30s%-30s%-30s%-30s\n","ID","Название",
                            "ЗП", "Время работы");
                    positionList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
