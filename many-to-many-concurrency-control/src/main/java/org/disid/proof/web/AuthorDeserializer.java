package org.disid.proof.web;
import org.disid.proof.domain.Author;
import org.disid.proof.service.api.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = AuthorDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Author.class)
public class AuthorDeserializer extends JsonObjectDeserializer<Author> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private AuthorService authorService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param authorService
     * @param conversionService
     */
    @Autowired
    public AuthorDeserializer(@Lazy AuthorService authorService, ConversionService conversionService) {
        this.authorService = authorService;
        this.conversionService = conversionService;
    }
}
