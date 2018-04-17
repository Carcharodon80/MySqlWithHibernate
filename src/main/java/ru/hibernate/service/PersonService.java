package ru.hibernate.service;

import org.hibernate.Session;
import ru.hibernate.dao.Person;
import ru.hibernate.utils.HibernateSessionFactory;
import ru.hibernate.utils.ReaderService;

import javax.persistence.TypedQuery;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PersonService {

    public List<Person> getAllPerson() {
        List<Person> list;

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            //запрос на получение всех людей
            TypedQuery<Person> query = session.createQuery("FROM Person", Person.class);
            list = query.getResultList();
        }
        return list;
    }

    public void addPerson() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            //вводим с консоли нового человека (имя, фамилию и др.)
            Person newPerson = new Person();
            newPerson.setFirstName(ReaderService.inputString("Введите имя: "));
            newPerson.setLastName(ReaderService.inputString("Введите фамилию: "));
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                newPerson.setBirthDate(simpleDateFormat.parse(ReaderService.inputString("Введите дату рождения: ")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newPerson.setSalary(Integer.parseInt(ReaderService.inputString("Введите зарплату: ")));

            //добавляем нового человека
            session.beginTransaction();
            session.save(newPerson);
            session.getTransaction().commit();
        }
    }

    public void deletePerson() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            //вводим id человека, получаем его из базы и удаляем
            Integer idDeletedPerson = Integer.parseInt(ReaderService.inputString("Введите id удаляемого человека: "));
            Person deletedPerson = session.get(Person.class, idDeletedPerson);
            session.beginTransaction();
            session.delete(deletedPerson);
            session.getTransaction().commit();
        }
    }

    public void updatePerson() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {

            //вводим id человека и его новые данные (Enter - если не меняем)
            int idUpdatedPerson = Integer.parseInt(ReaderService.inputString("Введите id редактируемого человека: "));
            Person updatedPerson = session.get(Person.class, idUpdatedPerson);

            String newFirstName = ReaderService.inputString("Введите новое имя: ");
            if (newFirstName.equals("")) {
                newFirstName = updatedPerson.getFirstName();
            }

            String newLastName = ReaderService.inputString("Введите новую фамилию: ");
            if (newLastName.equals("")) {
                newLastName = updatedPerson.getLastName();
            }

            Date newBirthDate = null;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                String newBirthDateString = ReaderService.inputString("Введите дату рождения: ");
                if (newBirthDateString.equals("")) {
                    newBirthDate = updatedPerson.getBirthDate();
                } else {
                    newBirthDate = simpleDateFormat.parse(newBirthDateString);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(newBirthDate);

            int newSalary;
            String newSalaryString = ReaderService.inputString("Введите новую зарплату: ");
            if (newSalaryString.equals("")){
                newSalary = updatedPerson.getSalary();
            }
            else newSalary = Integer.parseInt(newSalaryString);

            //заносим новые данные в человека
            updatedPerson.setFirstName(newFirstName);
            updatedPerson.setLastName(newLastName);
            updatedPerson.setBirthDate(newBirthDate);
            updatedPerson.setSalary(newSalary);

            //ну и обновляем человека в базе
            session.beginTransaction();
            session.save(updatedPerson);
            session.getTransaction().commit();
        }
    }


}
