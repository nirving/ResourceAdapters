package com.darkedges.mina.jca;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.ResourceException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinaActivationSpec implements Serializable, ActivationSpec {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4703714282121110512L;

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(MinaActivationSpec.class);

	private ResourceAdapter resourceAdapter;
	private int port;

	public MinaActivationSpec() {
		logger.info("[Start] FileActivationSpec");
	}

	public void validate() throws InvalidPropertyException {
		logger.info("[start] validation");
	}

	public ResourceAdapter getResourceAdapter() {
		return resourceAdapter;
	}

	public void setResourceAdapter(ResourceAdapter resourceAdapter)
			throws ResourceException {
		this.resourceAdapter = resourceAdapter;
	}


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}