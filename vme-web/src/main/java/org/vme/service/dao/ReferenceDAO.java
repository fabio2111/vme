/**
 * The reference data service.
 * @author Fabrizio Sibeni
 *
 */

package org.vme.service.dao;


import java.util.List;




public interface ReferenceDAO  {
	/**
	 * Return all the defined reference classes.
	 * @return the existing concepts if any, an empty list otherwise
	 * @throws ReferenceServiceException
	 */
	public List<Class<?>> getConcepts() throws ReferenceServiceException;
	
	
	/**
	 * Return a concept via its acronym.
	 * @param acronym the concept acronym
	 * @return the requested concept if existing, null otherwise 
	 * @throws ReferenceServiceException
	 */
	public Class<?> getConcept(String acronym) throws ReferenceServiceException;

	/**
	 * Return a defined concept class via its name.
	 * @param id the concept class name
	 * @return the requested concept class if existing
	 * @throws ReferenceServiceException
	 */
	public Object getReference(Class<?> concept, Long id) throws ReferenceServiceException;
	
	/**
	 * Return a defined concept class via its name.
	 * @param id the concept class name
	 * @return the requested concept class if existing
	 * @throws ReferenceServiceException
	 */
	public List<?> getAllReferences(Class<?> concept) throws ReferenceServiceException;
	
	
	/**
	 * Return a defined concept class via its name.
	 * @param id the concept class name
	 * @return the requested concept class if existing
	 * @throws ReferenceServiceException
	 */
	public Object getReferenceByAcronym(Class<?> concept, String acronym) throws ReferenceServiceException;
	
}