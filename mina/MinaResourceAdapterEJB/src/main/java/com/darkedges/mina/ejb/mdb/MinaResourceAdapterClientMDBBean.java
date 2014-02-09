package com.darkedges.mina.ejb.mdb;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.annotation.Resources;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.resource.ResourceException;
import javax.resource.cci.Record;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.darkedges.min.api.MinaMessageListener;

@MessageDriven(messageListenerInterface = MinaMessageListener.class, activationConfig = { @ActivationConfigProperty(propertyName = "port", propertyValue = "8801"), })
@Resources({ @Resource(name = "wm/minaWorkManager", type = commonj.work.WorkManager.class) })
/**
 * Resource adapter name during deployment by web
 * */
public class MinaResourceAdapterClientMDBBean implements MinaMessageListener {
	private static Logger logger = (Logger) LoggerFactory
			.getLogger(MinaResourceAdapterClientMDBBean.class);

	public MinaResourceAdapterClientMDBBean() {
		logger.info("[start] FileResourceAdapterClientMDBBean");
	}

	public Record onMessage(Record record) throws ResourceException {
		return record;
	}

	public void onMessage(String message) {
		logger.info("[start]:File client MDB fileMessage OnMessage");
		logger.info("\t[message:]:" + message);
		logger.info("[End:] On Message");
	}
}