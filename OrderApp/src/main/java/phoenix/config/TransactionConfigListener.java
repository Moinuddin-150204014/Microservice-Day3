package phoenix.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConfigListener implements ApplicationListener<EnvironmentChangeEvent> {

	private final Logger logger = LoggerFactory.getLogger(TransactionConfigListener.class);
	@Autowired
	private TransactionConfiguration transactionConfiguration;

	@Override
	public void onApplicationEvent(EnvironmentChangeEvent event) {
		logger.info("application refresh success, current transaction status is : {}",
				transactionConfiguration.getApplytransaction());
	}
}
