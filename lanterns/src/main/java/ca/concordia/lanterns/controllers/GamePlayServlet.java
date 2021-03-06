package ca.concordia.lanterns.controllers;

import java.io.IOException;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.concordia.lanterns.services.impl.EndGameDetectService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.Player;

public class GamePlayServlet extends HttpServlet {

	private static final long serialVersionUID = 5002873556572170900L;
	public static final String EXCHANGE = "exchange";
	public static final String DEDICATION = "dedication";
	public static final String PLACE = "place";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String playerAction = request.getParameter("playerAction");
		String currentPlayer = request.getParameter("currentPlayer");
		Game game = (Game)session.getAttribute("game");
		if (EndGameDetectService.getInstance().isGameEnded(game)) {
			request.setAttribute("nextAction", "endGame");
			Set<Player> winners = EndGameDetectService.getInstance().getGameWinner(game);
			session.setAttribute("winners", winners.toArray(new Player[0]));
		} else {
			int currentPlayerIndex = validate(game, playerAction, currentPlayer, request, response);
			takeAction(game, playerAction, currentPlayerIndex, request);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
		dispatcher.forward(request, response);
	}
	
	private int validate(Game game, String playerAction, String currentPlayer, HttpServletRequest request, HttpServletResponse response) throws IOException {
		int currentPlayerIndex = -1;
		if ((currentPlayer != null) && (playerAction != null)) {
			try {
				currentPlayerIndex = Integer.parseInt(currentPlayer);
				if (currentPlayerIndex != game.getCurrentTurnPlayer()) {
					request.setAttribute("message", "Current player does not match game current player: currentPlayer='" + currentPlayer + "'");
					response.sendRedirect("/error");
				}
			} catch (NumberFormatException e) {
				request.setAttribute("message", "Invalid integer: currentPlayer='" + currentPlayer + "'");
				response.sendRedirect("/error");
			}
		} else {
			request.setAttribute("message", "Invalid parameters: playerAction='"+playerAction 
					+ "', currentPlayer='" + currentPlayer + "'");
			response.sendRedirect("/error");
		}
		return currentPlayerIndex;
	}
	
	private void takeAction(Game game, String playerAction, int currentPlayerIndex, HttpServletRequest request) {
		System.out.println("Taking Action '" + playerAction + "' for player '" + currentPlayerIndex 
				+ "' in game '" + game.getId() + "'"); // TODO - change to logger
		// FIXME - change for human player 
		switch (playerAction) {
		case EXCHANGE:
			// ActivePlayerService.getInstance().exchangeLanternCard(game, currentPlayerIndex, giveCard, receiveCard);
			game.getAiPlayers()[currentPlayerIndex].performExchange();
			request.setAttribute("nextAction", "dedication");
			break;
		case DEDICATION:
			game.getAiPlayers()[currentPlayerIndex].performDedication();
			request.setAttribute("nextAction", "place");
			break;
		case PLACE:
			if (game.getPlayers()[currentPlayerIndex].getTiles().size() != 0) {
	        	game.getAiPlayers()[currentPlayerIndex].performTilePlay();
	        }
			game.setCurrentTurnPlayer(game.getNextPlayer());
			request.setAttribute("nextAction", "exchange");
			break;
		default:
			break;
		}
	}
}
