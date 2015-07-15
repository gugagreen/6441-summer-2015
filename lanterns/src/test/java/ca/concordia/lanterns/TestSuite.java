package ca.concordia.lanterns;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ca.concordia.lanterns.entities.DedicationTokenTest;
import ca.concordia.lanterns.entities.LakeTileTest;
import ca.concordia.lanterns.readwrite.impl.JaxbGameMarshallerTest;
import ca.concordia.lanterns.services.impl.DefaultSetupServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({DedicationTokenTest.class, LakeTileTest.class, JaxbGameMarshallerTest.class, DefaultSetupServiceTest.class})
public class TestSuite {
	
}