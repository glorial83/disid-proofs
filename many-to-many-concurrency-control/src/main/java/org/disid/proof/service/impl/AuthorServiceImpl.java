package org.disid.proof.service.impl;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.disid.proof.domain.Author;
import org.disid.proof.domain.Book;
import org.disid.proof.service.api.AuthorService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * = AuthorServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = AuthorService.class)
public class AuthorServiceImpl implements AuthorService {
	
    /**
     * Method that assign some existing {@link Book}s to the provided {@link Author}. 
     * By default, during the update process, JPA only will update the version field of the 
     * child entity. However, to be able to manage the concurrency during the assignation of
     * some books to an author, is necessary to update the version field of the parent side to 
     * know if the parent has changed because it has new books assigned. 
     * 
     * @param author
     * @param books
     * @return Author
     */
    @Transactional
    public Author setBooks(Author author, Iterable<Long> books) {
        List<Book> items = getBookService().findAll(books);
        Set<Book> currents = author.getBooks();
        Set<Book> toRemove = new HashSet<Book>();
        for (Iterator<Book> iterator = currents.iterator(); iterator.hasNext();) {
            Book nextBook = iterator.next();
            if (items.contains(nextBook)) {
                items.remove(nextBook);
            } else {
                toRemove.add(nextBook);
            }
        }
        author.removeFromBooks(toRemove);
        author.addToBooks(items);
        // Force the version update of the parent side to know that the parent has changed 
        // because it has new books assigned
        author.setVersion(author.getVersion() + 1);
        return getAuthorRepository().save(author);
    }
}
