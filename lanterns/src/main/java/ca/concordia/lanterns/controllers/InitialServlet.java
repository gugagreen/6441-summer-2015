package ca.concordia.lanterns.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.concordia.lanterns.services.impl.DefaultSetupService;
import ca.concordia.lanternsentities.Game;
import ca.concordia.lanternsentities.enums.AIType;

public class InitialServlet extends HttpServlet {

	private static final long serialVersionUID = 5002873556572170900L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AIType[] ais = AIType.values();
		HttpSession session = request.getSession();
		session.setAttribute("ais", ais);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("startGame.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] players = getPlayersFromForm(request);
		AIType[] aiTypes = getAIsFromForm(request);
		Game game = DefaultSetupService.getInstance().createGame(players, aiTypes);

		HttpSession session = request.getSession();
		session.setAttribute("game", game);
		RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
		dispatcher.forward(request, response);
	}
	
	private String[] getPlayersFromForm(HttpServletRequest request) {
		ArrayList<String> playersList = new ArrayList<String>();
		for (int i = 0; i < 4; i++) {
			String p = request.getParameter("p" + (1+i));
			if (notEmpty(p)) {
				playersList.add(p);
			}
		}
		return playersList.toArray(new String[playersList.size()]);
	}
	
	private AIType[] getAIsFromForm(HttpServletRequest request) {
		ArrayList<AIType> aiList = new ArrayList<AIType>();
		for (int i = 0; i < 4; i++) {
			String cbx = request.getParameter("ai_cbx_" + (1+i));
			if (notEmpty(cbx)) {
				AIType selected = AIType.getAIType(cbx); 
				if (selected != null) {
					aiList.add(selected);
				}
			}
		}
		return aiList.toArray(new AIType[aiList.size()]);
	}
	
	private boolean notEmpty(String s) {
		return s != null && s.length() > 0;
	}
}
