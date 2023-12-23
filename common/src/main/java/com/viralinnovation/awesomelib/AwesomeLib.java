package com.viralinnovation.awesomelib;

import com.viralinnovation.awesomelib.context.ArchRegistrationContext;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.registry.BlockFamilies;

public class AwesomeLib {
	public static final String MOD_ID = "alib";

	public static void init() {
		
	}

	public static void asApiInit(String namespace, BlockFamilies.BlockFamiliesFactory familiesFactory) {
		RegistrationContext registrationContext = new ArchRegistrationContext(namespace, familiesFactory);
	}
}
