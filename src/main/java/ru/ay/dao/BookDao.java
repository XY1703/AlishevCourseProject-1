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
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getById(int id) {
        return jdbcTemplate.query("select * from book where id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year) values (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where id=?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("update book set person_id=? where id=?", selectedPerson.getId(), id);
    }

    public void release(int id) {
        jdbcTemplate.update("update book set person_id=null where id=?", id);
    }

    public Optional<Person> getBookOwner(int id) {
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id " +
                "WHERE Book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public Optional<Book> getByName(String name) {
        return jdbcTemplate.query("select * from book where name=?",
                new Object[]{name}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }


}
