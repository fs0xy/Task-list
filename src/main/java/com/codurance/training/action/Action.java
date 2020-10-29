package com.codurance.training.action;

/**
 * Cette classe permet de créer des actions
 * 
 * @author Thomas Reynaud
 *
 */
public interface Action {

	public String actionMessage();

	/**
	 * Cette méthode permet d'exécuter une action suivant le paramètre donné
	 *
	 * @param commandLine
	 *            qui est la commande entrée
	 * @exception Exception
	 *                qui va lever une exception
	 */
	public void executeWithParamater(String commandLine) throws Exception;

	/**
	 * Cette méthode permet d'exécuter une action sans paramètre
	 *
	 * @exception Exception
	 *                qui va lever une exception
	 */
	public void executeWithoutParamater() throws Exception;
}
