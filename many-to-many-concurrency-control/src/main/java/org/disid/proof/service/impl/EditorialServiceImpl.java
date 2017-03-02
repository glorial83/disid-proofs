package org.disid.proof.service.impl;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.disid.proof.domain.Author;
import org.disid.proof.domain.Book;
import org.disid.proof.domain.Editorial;
import org.disid.proof.service.api.EditorialService;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.transaction.annotation.Transactional;

/**
 * = EditorialServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = EditorialService.class)
public class EditorialServiceImpl implements EditorialService {
	
    /**
     * Method that assign some existing {@link Book}s to the provided {@link Editorial}. 
     * By default, during the update process, JPA only will update the version field of the 
     * child entity. However, to be able to manage the concurrency during the assignation of
     * some books to an editorial, is necessary to update the version field of the parent side to 
     * know if the parent has changed because it has new books assigned. 
     * 
     * @param editorial
     * @param books
     * @return Editorial
     */
    @Transactional
    public Editorial setBooks(Editorial editorial, Iterable<Long> books) {
        List<Book> items = getBookService().findAll(books);
        Set<Book> currents = editorial.getBooks();
        Set<Book> toRemove = new HashSet<Book>();
        for (Iterator<Book> iterator = currents.iterator(); iterator.hasNext();) {
            Book nextBook = iterator.next();
            if (items.contains(nextBook)) {
                items.remove(nextBook);
            } else {
                toRemove.add(nextBook);
            }
        }
        editorial.removeFromBooks(toRemove);
        editorial.addToBooks(items);
        // Force the version update of the parent side to know that the parent has changed 
        // because it has new books assigned
        editorial.setVersion(editorial.getVersion() + 1);
        return getEditorialRepository().save(editorial);
    }
}
