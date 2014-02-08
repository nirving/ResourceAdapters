package com.blu.jca.file;

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

public class FileResourceAdapter implements ResourceAdapter, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2139083285383298992L;

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FileResourceAdapter.class);

	private WorkManager workManager;

	public FileResourceAdapter() {
		logger.info("[Start] FileResourceAdapter()");
	}

	public void start(BootstrapContext bootstrapContext)
			throws ResourceAdapterInternalException {
		logger.info("[Start] start()");
		workManager = bootstrapContext.getWorkManager();
	}

	public void stop() {
		logger.info("[stop] stop()");
	}

	public void endpointActivation(
			MessageEndpointFactory messageEndpointFactory,
			ActivationSpec activationSpec) throws ResourceException {
		logger.info("[start] endpointActivation");
		MessageEndpoint endPoint = messageEndpointFactory.createEndpoint(null);
		if (endPoint instanceof MessageListener) {
			FileWork fileWork = new FileWork(activationSpec, endPoint);
			workManager.scheduleWork(fileWork);
		}
	}

	public void endpointDeactivation(
			MessageEndpointFactory messageEndpointFactory,
			ActivationSpec activationSpec) {
		logger.info("[start] endpointDeactivation");
	}

	public XAResource[] getXAResources(ActivationSpec[] activationSpecs)
			throws ResourceException {
		return new XAResource[0];
	}
}