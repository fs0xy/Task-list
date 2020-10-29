package com.codurance.training.action;

import com.codurance.training.tasks.Application;

/**
 * Cette classe permet de dire qu'une tâche n'est plus à faire de la façon
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
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * et fait appel à la méthode setDone de la classe Application avec le
	 * paramètre true pour mettre la tâche à l'état suivant : tache faite
	 * 
	 * @param commandLine
	 *            qui désigne la commande entrée
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		Application.setDone(commandLine, true);
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