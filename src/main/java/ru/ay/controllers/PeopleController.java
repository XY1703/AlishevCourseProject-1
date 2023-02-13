package ru.ay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ay.dao.PersonDao;
import ru.ay.models.Person;
import ru.ay.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDao personDao;
    private final PersonValidator validator;

    @Autowired
    public PeopleController(PersonDao personDao, PersonValidator validator) {
        this.personDao = personDao;
        this.validator = validator;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("people", personDao.getAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getById(id));
        model.addAttribute("books", personDao.getAllBookByPersonId(id));
        return "people/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        validator.validate(person, bindingResult);

        if(bindingResult.hasErrors()) return "people/new";

        personDao.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDao.getById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult, @PathVariable("id") int id){

        if(bindingResult.hasErrors()) return "people/edit";

        personDao.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.delete(id);
        return "redirect:/people";
    }

}
