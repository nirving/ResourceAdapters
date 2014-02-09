package com.darkedges.commonj;

import java.util.concurrent.Executor;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import commonj.work.WorkManager;

public class CommonJExecutor implements Executor {

	private static Logger logger = (Logger) LoggerFactory
			.getLogger(CommonJExecutor.class);

	private static WorkManager workManager;
	private boolean isDaemon;

	public CommonJExecutor(String jndiName) {
		isDaemon = false;
		try {
			InitialContext ic = new InitialContext();
			Object tempObj = ic.lookup(jndiName);
			if (tempObj != null)
				workManager = (WorkManager) tempObj;
		} catch (NamingException e) {
			logger.error("CommonJExecutor", e);
		}
	}

	public CommonJExecutor(String jndiName,boolean isDaemon) {
		this.isDaemon = false;
		try {
			InitialContext ic = new InitialContext();
			Object tempObj = ic.lookup(jndiName);
			if (tempObj != null)
				workManager = (WorkManager) tempObj;
			this.isDaemon = isDaemon;
		} catch (NamingException e) {
			logger.error("CommonJExecutor", e);
		}
	}

	public void execute(Runnable r) {
		try {
			CommonJWorkImpl commonjWork = new CommonJWorkImpl(r);
			commonjWork.setIsDaemon(isDaemon);
			workManager.schedule(commonjWork, new CommonJWorkListenerImpl());
		} catch (Exception we) {
			throw new RuntimeException(we.getLocalizedMessage(), we);
		}
	}
}
