package ca.concordia.lanterns.services.impl;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanterns.services.PlayerService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

public class ActivePlayerService implements PlayerService {

	@Override
	public void exchangeLanternCard(Game game, int id, Colour giveCard,
			Colour receiveCard) throws GameRuleViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeDedication(Game game, int id,
			DedicationType dedicationType, Colour[] color)
			throws GameRuleViolationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void placeLakeTile(Game game, int id, int playerTileIndex,
			int lakeTileIndex, int lakeTileSideIndex, int playerTileSideIndex)
			throws GameRuleViolationException {
		// TODO Auto-generated method stub

	}

}
