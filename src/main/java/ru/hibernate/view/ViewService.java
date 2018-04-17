package ru.hibernate.view;

import ru.hibernate.dao.Person;

import java.util.List;

public class ViewService {
    public void showAllPersons(List<Person> listPersonal){
        System.out.println("---Persons---");
        System.out.println();

        for (Person personal : listPersonal){
            System.out.println(personal);
        }

    }
}
