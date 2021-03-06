package li.l1t.sic.rest;

import li.l1t.sic.model.GuestUser;
import li.l1t.sic.model.dto.PersonDto;
import li.l1t.sic.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.dropwizard.DropwizardMetricServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller providing an API for getting information about people and modifying it.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@RestController
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private DropwizardMetricServices metricServices;


    @RequestMapping("/api/person/list")
    public List<PersonDto> personList() {
        metricServices.increment("controllers.person.listAccess");
        return StreamSupport.stream(personService.getAllPeople().spliterator(), false)
                .map(personService::toDto)
                .collect(Collectors.toList());
    }

    @RequestMapping("/api/person/by/id/{id}")
    public PersonDto singlePerson(@PathVariable("id") int id) {
        return personService.toDto(personService.getById(id));
    }

    @RequestMapping(value = "/api/person/new", method = RequestMethod.POST)
    public PersonDto newPerson(@RequestBody PersonDto personDto, Principal user) {
        GuestUser.validateNotGuest(user);
        return personService.toDto(personService.newPerson(personDto));
    }
}
