import models.Account;
import models.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Scanner;

public class Requests {
    public void work(SessionFactory sessionFactory){
        System.out.println("1.Первый запрос");
        System.out.println("2.Второй запрос");
        System.out.println("3.Третий запрос");
        System.out.println("4.Вернуться в меню");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        Session session;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        switch (i) {
            case 1 -> firstRequest(session);
            case 2 -> secondRequest(session);
            case 3 -> thirdRequest(session);
            case 4 -> {
                session.close();
                return;
            }
            default -> System.out.println("Неверный ввод");
        }
        session.getTransaction().commit();
    }

    private void firstRequest(Session session) {
        List<Account> accounts = session.createQuery("SELECT cp FROM Account cp",
                Account.class).getResultList();
        System.out.printf("%-25s%-20s\n", "логин сотрудника", "Дата");
        for (Account account : accounts)
            System.out.printf("%-25s%-20tF\n",account.getWorker().getWorker_name(),
                    account.getRegistration_date());
    }


    private void secondRequest(Session session){
        List<Worker> workerList = session.createQuery("SELECT w FROM Worker w", Worker.class).getResultList();
        System.out.printf("%-25s%-25s\n", "Название", "Возраст");
        for (Worker worker : workerList)
            if (worker.getAge()>18 && worker.getAge()<40)
            System.out.printf("%-25s%-25d \n",worker.getWorker_name(),worker.getAge());
    }

    private void thirdRequest(Session session){
        List<Account> accountList = session.createQuery("SELECT a FROM AccountUser a", Account.class).getResultList();
        System.out.printf("%-25s%-25s \n", "Поставка", "Дата");
        for (Account account : accountList)
            if(account.getRegistration_date().after(java.sql.Date.valueOf("2022-05-05")))
                System.out.printf("%-25s%-25tF \n",account.getAccount_id(),account.getRegistration_date());
    }

}
