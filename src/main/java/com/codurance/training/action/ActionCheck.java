package com.codurance.training.action;

import com.codurance.training.tasks.Application;

/**
 * Cette classe permet de dire qu'une t�che n'est plus � faire de la fa�on
 * suivante : check <id>
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionCheck implements Action {

	@Override
	public String actionMessage() {
		return "check";
	}

	/**
	 * M�thode permettant d'ex�cuter une instruction donn�e en ligne de commande
	 * et fait appel � la m�thode setDone de la classe Application avec le
	 * param�tre true pour mettre la t�che � l'�tat suivant : tache faite
	 * 
	 * @param commandLine
	 *            qui d�signe la commande entr�e
	 * @exception Exception
	 *                qui l�ve une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		Application.setDone(commandLine, true);
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