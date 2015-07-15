package ca.concordia.lanterns;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ca.concordia.lanterns.dao.impl.FileGameDaoTest;
import ca.concordia.lanterns.entities.DedicationTokenTest;
import ca.concordia.lanterns.entities.LakeTileTest;
import ca.concordia.lanterns.entities.LanternCardTest;
import ca.concordia.lanterns.entities.PlayerTest;
import ca.concordia.lanterns.entities.TileSideTest;
import ca.concordia.lanterns.readwrite.impl.JaxbGameMarshallerTest;
import ca.concordia.lanterns.services.impl.DefaultSetupServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({FileGameDaoTest.class,DedicationTokenTest.class, LakeTileTest.class, 
	LanternCardTest.class, PlayerTest.class, TileSideTest.class , JaxbGameMarshallerTest.class, DefaultSetupServiceTest.class})
public class TestSuite {
	
}