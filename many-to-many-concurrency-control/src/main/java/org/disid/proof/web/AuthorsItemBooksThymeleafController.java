package org.disid.proof.web;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.disid.proof.domain.Author;
import org.disid.proof.domain.Book;
import org.springframework.roo.addon.web.mvc.controller.annotations.ControllerType;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooController;
import org.springframework.roo.addon.web.mvc.controller.annotations.RooDetail;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleaf;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * = AuthorsItemBooksThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Author.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "books")
@RooThymeleaf
public class AuthorsItemBooksThymeleafController {
	
    /**
     * Method that assign some existing books to an existing Author. Also, it checks the 
     * version field of the parent side to be able to manage concurrency if some other user
     * has updated the parent record before or if some other user has include new books
     * before.
     * 
     * @param author
     * @param books
     * @param version
     * @param concurrency
     * @param model
     * @return ModelAndView
     */
    @PostMapping(name = "create")
    public ModelAndView create(@ModelAttribute Author author, @RequestParam(value = "booksIds", required = false) List<Long> books, 
    		@RequestParam("parentVersion") Integer version, 
    		@RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl, Model model) {
    	
        // Remove empty values
    	if(books != null){
    		for (Iterator<Long> iterator = books.iterator(); iterator.hasNext();) {
    			if (iterator.next() == null) {
    				iterator.remove();
    			}
    		}
    	}
    	
        // Concurrency control
        if(version != author.getVersion() && StringUtils.isEmpty(concurrencyControl)){
            populateForm(model);
            // Obtain the selected books and include them in the author that will be 
            // included in the view
            if(books != null){
            	author.setBooks(new HashSet<Book>(getBookService().findAll(books)));
            }else{
            	author.setBooks(new HashSet<Book>());
            }
            // Reset the version to prevent update
            author.setVersion(version);
            // Include the updated author in the model
            model.addAttribute("author", author);
            model.addAttribute("concurrency", true);
            return new ModelAndView("authors/books/create");
        } else if(version != author.getVersion() && "discard".equals(concurrencyControl)){
            populateForm(model);
            // Provide the original author from the Database
            model.addAttribute("author", author);
            model.addAttribute("concurrency", false);
            return new ModelAndView("authors/books/create");
        }
        getAuthorService().setBooks(author,books);
        return new ModelAndView("redirect:" + getCollectionLink().to(AuthorsCollectionThymeleafLinkFactory.LIST).toUriString());
    }
    
    public ModelAndView create(@ModelAttribute Author author, @RequestParam("booksIds") List<Long> books, Model model) {
        throw new UnsupportedOperationException("This method has been updated and now is "
        		+ "necessary to send the version and the concurrencyControl parameters.");
    }
}
