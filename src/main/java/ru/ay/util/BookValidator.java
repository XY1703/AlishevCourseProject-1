package ru.ay.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.ay.dao.BookDao;
import ru.ay.models.Book;

@Component
public class BookValidator implements Validator {

    private final BookDao bookDao;

    @Autowired
    public BookValidator(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if(bookDao.getByName(book.getName()).isPresent()){
            errors.rejectValue("name", "", "Такая книга уже существует");
        }
    }
}
