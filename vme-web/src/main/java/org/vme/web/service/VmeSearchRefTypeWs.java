package org.vme.web.service;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fao.fi.vme.batch.reference.ReferenceDataHardcodedBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vme.service.SearchServiceInterface;
import org.vme.service.search.ReferencesRequest;
import org.vme.service.search.ServiceResponse;

/**
 * 
 * @author Fabrizio Sibeni, Erik van Ingen, Roberto Empiri
 * 
 */
@Path("/references/{concept}/{lang}/")
@Singleton
public class VmeSearchRefTypeWs {

	protected static final Logger LOG = LoggerFactory.getLogger(VmeSearchRefTypeWs.class);

	@Inject
	private ReferenceDataHardcodedBatch batch;

	@Inject
	private SearchServiceInterface service;

	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response find(@PathParam("concept") String concept, @PathParam("lang") String lang) {
		try {
			ReferencesRequest refRequest = new ReferencesRequest(UUID.randomUUID());
			refRequest.setConcept(concept);
			ServiceResponse<?> result;
			result = service.invoke(refRequest);
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WebApplicationException(e, Response.Status.NOT_FOUND);
		}

	}

	@Path("/batch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public synchronized Response find() {
		try {
			batch.run();
			return Response.status(200).entity("ReferenceDataHardcodedBatch run successfully").build();
		} catch (Exception t) {
			LOG.error(t.getMessage());
			throw new WebApplicationException(t, Response.Status.NOT_FOUND);
		}
	}

}
