package org.app1.controllers;


import org.app1.models.Person;
import org.app1.services.PeopleService;
import org.app1.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

//    private final PersonDAO personDAO;
    private final PeopleService peopleService;

    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    //запрос на получение страницы со списком всех людей
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.getAllPeople());
        return "people/index";
    }

    //запрос на получение страницы с определенным человеком
    @GetMapping("/{id}")
    public String personPage(@PathVariable int id, Model model) {
        Optional<Person> person = peopleService.getPersonById(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("books", peopleService.getBooksByPerson(id));
            return "people/person";
        }
        else {
            return "redirect:/people";
        }
    }

    //запрос на получение страницы добавления человека
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new-person";
    }

    //запрос на добавление человека
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new-person";

        peopleService.save(person);
        return "redirect:/people";
    }

    //запрос на получение страницы изменения человека
    @GetMapping("/{id}/edit")
    public String editPersonPage(Model model, @PathVariable int id) {
        Optional<Person> selectedPerson = peopleService.getPersonById(id);
        if (selectedPerson.isPresent()) {
            model.addAttribute("person", selectedPerson.get());
            return "/people/edit-person";
        }
        return "redirect:/people";
    }

    //запрос на редактирование человека
    @PatchMapping("/{id}")
    public String edit(@PathVariable int id, @ModelAttribute("person") @Valid Person updatedPerson,
                       BindingResult bindingResult) {
        personValidator.validate(updatedPerson, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit-person";

        peopleService.updateById(updatedPerson, id);
        return "redirect:/people/" + id;
    }

    //запрос на удаление человека
    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
        peopleService.deleteById(id);
        return "redirect:/people";
    }
}
