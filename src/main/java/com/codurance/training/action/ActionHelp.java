package com.codurance.training.action;

import com.codurance.training.tasks.Application;

/**
 * Cette classe permet d'afficher les commmandes afin d'aider l'utilisateur via
 * la commande suivante : help
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionHelp implements Action {

	@Override
	public String actionMessage() {
		return "help";
	}

	/**
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * 
	 * @param commandLine
	 *            qui désigne la commande entrée
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
	}

	/**
	 * Méthode permettant d'exécuter une action sans paramètre
	 * 
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithoutParamater() throws Exception {
		Application.getOut().println("Commands:");
		Application.getOut().println("  add project <project name>");
		Application.getOut().println("  add task <project name> <task description>");
		Application.getOut().println("  check <task ID>");
		Application.getOut().println("  deadline <task id> <date>");
		Application.getOut().println("  today");
		Application.getOut().println("  view by date");
		Application.getOut().println("  view by deadline");
		Application.getOut().println("  view by project");
	}
}