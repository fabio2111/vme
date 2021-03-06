/**
 * The reference data service.
 * @author Fabrizio Sibeni
 *
 */

package org.vme.dao;

import java.util.List;

import org.fao.fi.vme.domain.model.Authority;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.AcronymAwareReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConcept;
import org.gcube.application.rsg.support.compiler.bridge.interfaces.reference.ReferenceConceptProvider;

public interface ReferenceDAO extends ReferenceConceptProvider<Long> {
	/**
	 * Return all the defined reference classes.
	 * 
	 * @return the existing ReferenceConcepts if any, an empty list otherwise
	 * @throws ReferenceServiceException
	 */
	List<Class<? extends ReferenceConcept>> getConcepts() throws ReferenceServiceException;

	/**
	 * Return a ReferenceConcept via its acronym.
	 * 
	 * @param acronym
	 *            the ReferenceConcept acronym
	 * @return the requested ReferenceConcept if existing, null otherwise
	 * @throws ReferenceServiceException
	 */
	Class<? extends ReferenceConcept> getConcept(String acronym) throws ReferenceServiceException;

	/**
	 * Return a defined ReferenceConcept class via its name.
	 * 
	 * @param id
	 *            the ReferenceConcept class name
	 * @return the requested ReferenceConcept class if existing
	 * @throws ReferenceServiceException
	 */
	ReferenceConcept getReference(Class<? extends ReferenceConcept> concept, Long id) throws ReferenceServiceException;

	/**
	 * Return a defined ReferenceConcept class via its name.
	 * 
	 * @param id
	 *            the ReferenceConcept class name
	 * @return the requested ReferenceConcept class if existing
	 * @throws ReferenceServiceException
	 */
	AcronymAwareReferenceConcept getReferenceByAcronym(Class<? extends AcronymAwareReferenceConcept> concept,
			String acronym) throws ReferenceServiceException;

	List<Authority> getAllAuthorities();

}
