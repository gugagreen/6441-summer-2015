package ca.concordia.lanterns;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ca.concordia.lanterns.entities.DedicationTokenTest;
import ca.concordia.lanterns.marshalling.impl.JaxbMarshallerTest;
import ca.concordia.lanterns.services.impl.DefaultSetupServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({DedicationTokenTest.class, JaxbMarshallerTest.class, DefaultSetupServiceTest.class})
public class TestSuite {
	
}