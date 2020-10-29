package com.codurance.training.action;

import com.codurance.training.tasks.Application;

/**
 * Cette classe permet de dire qu'une t�che d�j� faite doit �tre � faire via la
 * commande suivante : uncheck <id>
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionUncheck implements Action {

	@Override
	public String actionMessage() {
		return "uncheck";
	}

	/**
	 * M�thode permettant d'ex�cuter une instruction donn�e en ligne de commande
	 * et fait appel � la m�thode setDone de la classe Application avec le
	 * param�tre true pour mettre la t�che � l'�tat suivant : tache � faire
	 * 
	 * @param commandLine
	 *            qui d�signe la commande entr�e
	 * @exception Exception
	 *                qui l�ve une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		Application.setDone(commandLine, false);
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