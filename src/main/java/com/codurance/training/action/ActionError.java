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
	 * M�thode permettant d'ex�cuter une instruction donn�e en ligne de commande
	 * et d'afficher le message d'erreur
	 * 
	 * @param commandLine
	 *            qui d�signe la commande entr�e
	 * @exception Exception
	 *                qui l�ve une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		Application.getOut().println("I don't know what the command \""+commandLine+"\" is");
		Application.getOut().println("Type help to see the possible commands");
	}

	/**
	 * M�thode permettant d'ex�cuter une action sans param�tre
	 * 
	 * @exception Exception
	 *                qui l�ve une exception
	 */
	@Override
	public void executeWithoutParamater() throws Exception {
	}
}