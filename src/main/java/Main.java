import ru.hibernate.dao.Person;
import ru.hibernate.service.PersonService;
import ru.hibernate.utils.ReaderService;
import ru.hibernate.utils.HibernateSessionFactory;
import ru.hibernate.view.ViewService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hibernate begin.");

        //открываем служебные приблуды
        PersonService personService = new PersonService();
        ViewService view = new ViewService();
        ReaderService.openReader();


        //получаем список людей
        List<Person> list = personService.getAllPerson();
        //и показываем всех
        view.showAllPersons(list);


        //добавляем человека
        personService.addPerson();
        //и показываем всех
        view.showAllPersons(personService.getAllPerson());


        personService.deletePerson();
        view.showAllPersons(personService.getAllPerson());


        personService.updatePerson();
        view.showAllPersons(personService.getAllPerson());

        //закрываем служебные приблуды
        HibernateSessionFactory.shutdownSessionFactory();
        ReaderService.closeReader();


    }
}
