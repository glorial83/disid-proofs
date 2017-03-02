package org.disid.proof.web;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.disid.proof.domain.Book;
import org.disid.proof.domain.Editorial;
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
 * = EditorialsItemBooksThymeleafController
 *
 * TODO Auto-generated class documentation
 *
 */
@RooController(entity = Editorial.class, type = ControllerType.DETAIL)
@RooDetail(relationField = "books")
@RooThymeleaf
public class EditorialsItemBooksThymeleafController {
	
    /**
     * Method that assign some existing books to an existing Editorial. Also, it checks the 
     * version field of the parent side to be able to manage concurrency if some other user
     * has updated the parent record before or if some other user has include new books
     * before.
     * 
     * @param editorial
     * @param books
     * @param version
     * @param concurrency
     * @param model
     * @return ModelAndView
     */
    @PostMapping(name = "create")
    public ModelAndView create(@ModelAttribute Editorial editorial, @RequestParam("booksIds") List<Long> books, 
    		@RequestParam("parentVersion") Integer version, 
    		@RequestParam(value = "concurrency", required = false, defaultValue = "") String concurrencyControl,
    		Model model) {
        // Remove empty values
    	if(books != null){
	        for (Iterator<Long> iterator = books.iterator(); iterator.hasNext();) {
	            if (iterator.next() == null) {
	                iterator.remove();
	            }
	        }
    	}
    	
    	// Concurrency control
        if(version != editorial.getVersion() && StringUtils.isEmpty(concurrencyControl)){
            populateForm(model);
            // Obtain the selected books and include them in the editorial that will be 
            // included in the view
            if(books != null){
            	editorial.setBooks(new HashSet<Book>(getBookService().findAll(books)));
            }else{
            	editorial.setBooks(new HashSet<Book>());
            }
            // Reset the version to prevent update
            editorial.setVersion(version);
            // Include the updated editorial in the model
            model.addAttribute("editorial", editorial);
            model.addAttribute("concurrency", true);
            return new ModelAndView("editorials/books/create");
        } else if(version != editorial.getVersion() && "discard".equals(concurrencyControl)){
            populateForm(model);
            // Provide the original editorial from the Database
            model.addAttribute("editorial", editorial);
            model.addAttribute("concurrency", false);
            return new ModelAndView("editorials/books/create");
        }
    	
        getEditorialService().setBooks(editorial,books);
        return new ModelAndView("redirect:" + getCollectionLink().to(EditorialsCollectionThymeleafLinkFactory.LIST).toUriString());
    }
    
    public ModelAndView create(@ModelAttribute Editorial editorial, @RequestParam("booksIds") List<Long> books, Model model) {
    	throw new UnsupportedOperationException("This method has been updated and now is "
        		+ "necessary to send the version and the concurrencyControl parameters.");
    }
}
