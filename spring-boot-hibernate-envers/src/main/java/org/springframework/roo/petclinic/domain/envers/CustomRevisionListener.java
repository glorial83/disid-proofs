package org.springframework.roo.petclinic.domain.envers;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * = Listener to extends the revisionLog feature
 *
 * This class sets the new attributes defined in the CustomRevision class. In
 * this case, only the username that applies the change will be setted.
 * 
 * If you want to set more attributes, you should extend the CustomRevision
 * class and after that, you should extend this class to set the new provided
 * attributes.
 * 
 */
public class CustomRevisionListener implements RevisionListener {

	/**
	 * Sets the authenticated username that had applied the changes.
	 *
	 * @param revisionEntity
	 */
	public void newRevision(Object revisionEntity) {
		CustomRevision revision = (CustomRevision) revisionEntity;

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			revision.setUsername(authentication.getName());
		}
	}
}