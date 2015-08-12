package ca.concordia.lanterns.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.concordia.lanternsentities.Game;

public class GameServlet extends HttpServlet {

	private static final long serialVersionUID = 5002873556572170900L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String playerAction = request.getParameter("playerAction");
		System.out.println("playerAction: " + playerAction);
		String currentPlayer = request.getParameter("currentPlayer");
		System.out.println("currentPlayer: " + currentPlayer);
		
		Game game = (Game)session.getAttribute("game");
		game.setCurrentTurnPlayer(game.getNextPlayer());
		
		session.setAttribute("game", game);
		System.out.println(">> ping " + System.currentTimeMillis());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
		dispatcher.forward(request, response);
	}
}
