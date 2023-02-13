package ru.ay.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ay.models.Book;
import ru.ay.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAll() {
        return jdbcTemplate.query("select * from person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getById(int id) {
        return jdbcTemplate.query("select * from person where id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, birthyear) values (?, ?)", person.getName(), person.getBirthYear());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?, birthyear=? where id=?", updatedPerson.getName(),
                updatedPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Book> getAllBookByPersonId(int id) {
        return jdbcTemplate.query("select * from book where person_id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Person> getPersonByName(String name) {
        return jdbcTemplate.query("select * from person where name=?",
                new Object[]{name}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}
