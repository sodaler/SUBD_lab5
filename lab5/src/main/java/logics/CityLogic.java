package logics;

import models.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class CityLogic {
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
        System.out.print("Введите название города:");
        String city_name = scanner.nextLine();
        try {
            City city = new City(city_name);
            session.save(city);
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
                List<City> cityList = session.createQuery("SELECT t FROM City t",
                        City.class).getResultList();
                System.out.println("Город");
                System.out.printf("%-15s%s\n","ID","Название");
                cityList.forEach(System.out::println);
            }
            default -> System.out.println("Неверный ввод");
        }

    }
    private void update(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID города:");
        int city_id = scanner.nextInt();
        System.out.println("Введите название города:");
        scanner.nextLine();
        String city_name = scanner.nextLine();
        try {
            City city = session.get(City.class, city_id);
            city.setCity_name(city_name);
            session.save(city);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void delete(Session session){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ID города:");
        int city_id = scanner.nextInt();
        try {
            City city = session.get(City.class, city_id);
            session.delete(city);
        }
        catch (Exception ex){
            System.out.println("Этой записи не существует");
        }
    }

    private void filter(Session session){
        System.out.println("\tПоля");
        System.out.println("1.Id города");
        System.out.println("2.Название города");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        List<City> cityList;
        switch (i) {
            case 1 -> {
                System.out.print("Введите ID города:");
                int city_id = scanner.nextInt();
                try {
                    cityList = session.createQuery("SELECT c FROM City c WHERE city_id =" + city_id,
                            City.class).getResultList();
                    System.out.println("Город");
                    System.out.printf("%-15s%s\n","ID города","Название");
                    cityList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            case 2 -> {
                System.out.print("Введите название:");
                scanner.nextLine();
                String city_name = scanner.next();
                try {
                    cityList = session.createQuery("SELECT c FROM City c WHERE city_name ='" + city_name + "'",
                            City.class).getResultList();
                    System.out.println("Город");
                    System.out.printf("%-15s%s\n","ID города","Название");
                    cityList.forEach(System.out::println);
                }
                catch (Exception ex){
                    System.out.println("Этой записи не существует");
                }
            }
            default -> System.out.println("Неверный ввод");
        }
    }
}
