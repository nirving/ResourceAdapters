package com.blu.jca.client;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.resource.ResourceException;
import javax.resource.cci.Record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blu.jca.bean.FileMessageBean;
import com.blu.jca.file.FileMessage;
import com.blu.jca.file.FileMessageListener;

@MessageDriven(messageListenerInterface = FileMessageListener.class, activationConfig = {
		@ActivationConfigProperty(propertyName = "filePath", propertyValue = "D:\\JcaFolder"),
		@ActivationConfigProperty(propertyName = "fileExt", propertyValue = "*.*"),
		@ActivationConfigProperty(propertyName = "pollingInterval", propertyValue = "10") })
/**
 * Resource adapter name during deployment by web
 * */
public class FileResourceAdapterClientMDBBean implements FileMessageListener {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(FileResourceAdapterClientMDBBean.class);

	public FileResourceAdapterClientMDBBean() {
		logger.info("[start] FileResourceAdapterClientMDBBean");
	}

	public Record onMessage(Record record) throws ResourceException {
		return record;
	}

	public void onMessage(FileMessage message) {
		logger.info("[start]:File client MDB fileMessage OnMessage");
		FileMessageBean fBean = message.getFileMesssageBean();

		logger.info("\t[Record Name]:" + fBean.getRecordName());
		logger.info("\t[Description:]:"
				+ fBean.getRecordShortDescription());
		logger.info("\t[File Name:]:" + fBean.getFileName());
		logger.info("\t[File Data:]:" + fBean.getData());
		logger.info("[End:] On Message");
	}
}