package com.blu.jca.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.resource.spi.ActivationSpec;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.work.Work;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blu.jca.bean.FileMessageBean;

public class FileWork implements Work {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FileWork.class);

	private FileActivationSpec activationSpec;
	private FileMessageListener messageListener;
	private boolean released;

	public FileWork(ActivationSpec activationSpec,
			MessageEndpoint messageEndpoint) {
		logger.info("[start] FileWork()");
		this.activationSpec = (FileActivationSpec) activationSpec;
		this.messageListener = (FileMessageListener) messageEndpoint;
		this.released = false;
	}

	public void release() {
		this.released = true;
	}

	public boolean isReleased() {
		return released;
	}

	public void run() {
		logger.info("[start] run()");
		int pollingInterval = activationSpec.getPollingInterval();
		// set polling interval in ms
		if (pollingInterval < 5) {
			pollingInterval = 5000;
		} else {
			pollingInterval *= 1000;
		}
		logger.info("[Set Polling interval in ms:]" + pollingInterval);
		while (!isReleased()) {
			// start read file from directory
			try {
				logger.info("[Start reading from folder]");
				File folder = new File(activationSpec.getFilePath());
				if (folder.exists() && folder.isDirectory()) {
					File[] files = folder.listFiles();
					// process file
					for (File file : files) {
						FileMessageBean fBean = new FileMessageBean();
						fBean.setRecordName("Jca Standard file record");
						fBean.setFileName("[fileName]:" + file.getName());
						fBean.setRecordShortDescription("JCA file record which wrappes the file data");
						FileMessage message = new FileMessage();
						message.setFileMesssageBean(fBean);
						logger.info("Read file data to wrap");
						FileInputStream fis = null;
						BufferedReader bf = null;
						try {
							fis = new FileInputStream(file);
							bf = new BufferedReader(new InputStreamReader(fis));
							String data;
							StringBuffer sbuffer = new StringBuffer();
							while ((data = bf.readLine()) != null) {
								sbuffer.append(data);
							}
							fBean.setData(sbuffer.toString());
							messageListener.onMessage(message);
							Thread.currentThread();
							// sleep
							Thread.sleep(pollingInterval);
						} catch (FileNotFoundException e) {
							logger.error(e.getMessage(), e);
							throw new RuntimeException("FileNotFoundException:"
									+ e.getMessage());
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
							throw new RuntimeException("Error on reading file:"
									+ e.getMessage());
						} finally {
							try {
								bf.close();
								fis.close();
							} catch (IOException e) {
								logger.error(e.getMessage(), e);
								e.printStackTrace();
								throw new RuntimeException(
										"Error on reading file:"
												+ e.getMessage());
							}
						}
					}
				}
			} catch (InterruptedException e) {
				logger.info("[Error on Run]" + e.getMessage());
				throw new RuntimeException("error in Run" + e.getMessage());
			}
		}
	}
}
