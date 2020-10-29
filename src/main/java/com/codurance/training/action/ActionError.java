package com.codurance.training.action;

import com.codurance.training.tasks.Application;

/**
 * Cette classe permet d'afficher un message d'erreur lorsqu'une commande n'est
 * pas reconnue
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionError implements Action {

	@Override
	public String actionMessage() {
		return "error";
	}

	/**
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * et d'afficher le message d'erreur
	 * 
	 * @param commandLine
	 *            qui désigne la commande entrée
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		Application.getOut().println("I don't know what the command \""+commandLine+"\" is");
		Application.getOut().println("Type help to see the possible commands");
	}

	/**
	 * Méthode permettant d'exécuter une action sans paramètre
	 * 
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithoutParamater() throws Exception {
	}
}