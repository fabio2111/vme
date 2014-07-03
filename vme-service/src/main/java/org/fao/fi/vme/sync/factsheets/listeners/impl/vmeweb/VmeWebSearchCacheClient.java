package org.fao.fi.vme.sync.factsheets.listeners.impl.vmeweb;

import java.io.IOException;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;

/**
 * VmeWebSearchCacheClient in order to clear the cache, when needed.
 * 
 * 
 * @author Erik van Ingen
 * 
 */

@Dependent
public class VmeWebSearchCacheClient {

	public static String RESOURCE = "/webservice/cache-delete/";

	public static final String MESSAGE = "VME_CACHE_DELETED_SUCCESS";

	protected static final Logger LOG = LoggerFactory.getLogger(VmeWebSearchCacheClient.class);
	private static final String errorMessage = "Did not manage to clear the cach in vme-web search properly";

	@Inject
	VmeWebServer vmeWebServer;

	/**
	 * launches a asynchronous request in order to have the cache cleared.
	 */
	public void process(@Observes VmeModelChange vmeModelChange) {
		final AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
		try {
			String get = vmeWebServer.getServer() + RESOURCE;
			asyncHttpClient.prepareGet(get).execute(new AsyncCompletionHandler<Response>() {
				@Override
				public Response onCompleted(Response response) throws Exception {
					if (!response.getResponseBody().equals(MESSAGE)) {
						LOG.error("This message was expected but not received: " + MESSAGE);
					}
					LOG.info("CacheDeleteHandler received this message after a cache delete request was launched to vme-web search : "
							+ response.getResponseBody());
					asyncHttpClient.close();
					return response;
				}

				@Override
				public void onThrowable(Throwable t) {
					LOG.error("Something wrong happened. ", t);
				}
			});

			LOG.debug("VmeWebSearchCacheClient clear request launched!");
		} catch (IOException e) {
			LOG.error(errorMessage, e);
		}
	}

}
