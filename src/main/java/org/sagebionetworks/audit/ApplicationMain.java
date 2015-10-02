package org.sagebionetworks.audit;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Injector;

public class ApplicationMain {

	private static final Logger log = LogManager.getLogger(ApplicationMain.class);
	Injector injector;

	@Inject
	public ApplicationMain(Injector injector){
		this.injector = injector;
	}

	public static void main(String[] args) {
		
	}
}
