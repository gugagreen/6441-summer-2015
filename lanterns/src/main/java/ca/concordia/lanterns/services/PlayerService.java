package ca.concordia.lanterns.services;

import ca.concordia.lanterns.exception.GameRuleViolationException;
import ca.concordia.lanternsentities.*;
import ca.concordia.lanternsentities.enums.Colour;
import ca.concordia.lanternsentities.enums.DedicationType;

/**
 * A set of services available to the players. An implementation of this interface must be based on the
 * agreed rules of the game.
 *
 * @author parth
 * @version 1.0
 */
public interface PlayerService {

    /**
     * Exchanges one <b>Lantern Card</b> from the {@link Player} stock of Lantern cards with
     * one <b>Lantern card</b> from the {@link Game} stock of <b>Lantern cards</b> according to the rules
     * of the game.
     *
     * @param game        - the {@link Game} in the context of which the service is demanded
     * @param id          - the id of the player who wish to make an exchange
     * @param giveCard    - the {@link Colour} of the lantern card that the player wish to return back to the game
     * @param receiveCard - the {@link Colour} of the lantern card that the player wish to obtain from the game
     * @throws GameRuleViolationException - when the rule for exchanging lantern cards is violated.
     */
    public void exchangeLanternCard(Game game, int id, Colour giveCard, Colour receiveCard) throws GameRuleViolationException;


    /**
     * A {@link Player} dedicates/returns a set of <b>Lantern Cards</b> from its stock to the
     * {@link Game} and obtains a {@link DedicationToken}. The number on the Dedication Token
     * indicates the amount of honor a player earns from the dedication.
     * <p/>
     * <p>The set of <b>Lantern Cards</b> needed to be dedicated for a Dedication Token depends
     * on the rules of the game.
     *
     * @param game           - the {@link Game} which the player is playing.
     * @param id             - the id of the player who wish to make a dedication
     * @param dedicationType - the {@link DedicationType} the describes the type of
     *                       dedication the player wishes to make.
     * @param colours          - indicates the {@link Colour} of the lantern cards involved in the dedication.
     * @throws GameRuleViolationException - when the rule for making a dedication is violated.
     */
    public void makeDedication(Game game, int id, DedicationType dedicationType, Colour[] colours)
            throws GameRuleViolationException;


    /**
     * A {@link Player} places a {@link LakeTile} into the Lake of the {@link Game}.
     * <p>All the players receives <b>Lantern Cards</b> depending on the rules of the game.
     *
     * @param game                - the {@link Game} which contains the concerned Lake for putting the Lake Tile
     * @param id                  - the id of the player who places the Lake Tile
     * @param playerTileIndex     - the index of the Lake Tile the player wishes to place from the list
     *                            of Lake Tiles available to the player.
     * @param lakeTileId       - the id of the Lake Tile adjacent to which the player wishes to
     *                            place the new Tile. This id must be from the list of Lake Tile already in the lake.
     * @param lakeTileSideIndex   - the index of the {@link TileSide} of Lake tile indexed by
     *                            lakeTileIndex. The lake tile indexed by playerTileIndex will be placed adjacent to this Tile
     *                            Side of Lake Tile.
     * @param playerTileSideIndex - the index of the {@link TileSide} of Lake tile indexed by
     *                            playerTileIndex. The lake tile indexed by lakeTileIndex will be adjacent to this Tile
     *                            Side of Lake Tile.
     * @throws GameRuleViolationException - when the rule for placing a tile is violated.
     */
    public void placeLakeTile(Game game, int id, int playerTileIndex, String lakeTileId,
                              int lakeTileSideIndex, int playerTileSideIndex) throws GameRuleViolationException;

}
