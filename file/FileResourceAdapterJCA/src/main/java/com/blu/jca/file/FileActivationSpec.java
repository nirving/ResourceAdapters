package com.blu.jca.file;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.ResourceException;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileActivationSpec implements Serializable, ActivationSpec {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5488424877539069751L;

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FileActivationSpec.class);

	private ResourceAdapter resourceAdapter;
	private String filePath;
	private String fileExt;
	private int pollingInterval;

	public FileActivationSpec() {
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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public int getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}
}