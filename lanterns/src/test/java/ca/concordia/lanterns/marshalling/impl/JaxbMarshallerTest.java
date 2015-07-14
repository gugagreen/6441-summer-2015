package ca.concordia.lanterns.marshalling.impl;

import org.junit.Before;

import ca.concordia.lanterns.services.impl.DefaultSetupService;

public class JaxbMarshallerTest {

	private DefaultSetupService service;
	
	@Before
	public void setup() {
		this.service = new DefaultSetupService();
	}
}
