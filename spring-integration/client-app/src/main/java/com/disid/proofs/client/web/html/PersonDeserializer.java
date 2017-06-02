package com.disid.proofs.client.web.html;
import com.disid.proofs.client.domain.Person;
import com.disid.proofs.client.service.api.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = PersonDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Person.class)
public class PersonDeserializer extends JsonObjectDeserializer<Person> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private PersonService personService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param personService
     * @param conversionService
     */
    @Autowired
    public PersonDeserializer(@Lazy PersonService personService, ConversionService conversionService) {
        this.personService = personService;
        this.conversionService = conversionService;
    }
}
