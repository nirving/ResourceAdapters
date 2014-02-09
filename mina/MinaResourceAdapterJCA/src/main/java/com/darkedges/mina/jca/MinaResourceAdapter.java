package com.darkedges.mina.jca;

import java.io.Serializable;

import javax.resource.ResourceException;
import javax.resource.cci.MessageListener;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaResourceAdapter implements ResourceAdapter, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2139083285383298992L;

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(MinaResourceAdapter.class);

	private WorkManager workManager;
	
	private MinaServer server;

	public MinaResourceAdapter() {
		logger.info("[Start] MinaResourceAdapter()");
	}

	public void start(BootstrapContext bootstrapContext)
			throws ResourceAdapterInternalException {
		logger.info("[Start] start()");
		workManager = bootstrapContext.getWorkManager();
	}

	public void stop() {
		logger.info("[stop] stop()");
		server.unbind();
	}

	public void endpointActivation(
			MessageEndpointFactory messageEndpointFactory,
			ActivationSpec activationSpec) throws ResourceException {
		logger.info("[start] endpointActivation");
		MessageEndpoint endPoint = messageEndpointFactory.createEndpoint(null);
		if (endPoint instanceof MessageListener) {
			server = new MinaServer(activationSpec, endPoint);
			server.bind();
		}
	}

	public void endpointDeactivation(
			MessageEndpointFactory messageEndpointFactory,
			ActivationSpec activationSpec) {
		logger.info("[start] endpointDeactivation");
		server.unbind();
	}

	public XAResource[] getXAResources(ActivationSpec[] activationSpecs)
			throws ResourceException {
		return new XAResource[0];
	}
}