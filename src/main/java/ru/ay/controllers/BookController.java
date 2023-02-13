package ru.ay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ay.dao.BookDao;
import ru.ay.dao.PersonDao;
import ru.ay.models.Book;
import ru.ay.models.Person;
import ru.ay.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDao bookDao;
    private final PersonDao personDao;
    private final BookValidator validator;

    @Autowired
    public BookController(BookDao bookDao, PersonDao personDao, BookValidator validator) {
        this.bookDao = bookDao;
        this.personDao = personDao;
        this.validator = validator;
    }

    @GetMapping()
    public String getAll(Model model){
        model.addAttribute("books", bookDao.getAll());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id, @ModelAttribute Person person){
        model.addAttribute("book", bookDao.getById(id));

        Optional<Person> bookOwner = bookDao.getBookOwner(id);

        if(bookOwner.isPresent()) model.addAttribute("owner", bookOwner.get());
        else model.addAttribute("people", personDao.getAll());

        return "books/book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book){
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        validator.validate(book, bindingResult);

        if(bindingResult.hasErrors()) return "books/new";

        bookDao.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDao.getById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id){

        if(bindingResult.hasErrors()) return "books/edit";

        bookDao.update(id, book);
        return "redirect:/books";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDao.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDao.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookDao.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }
}
