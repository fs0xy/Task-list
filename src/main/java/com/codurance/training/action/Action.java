package com.codurance.training.action;

/**
 * Cette classe permet de cr�er des actions
 * 
 * @author Thomas Reynaud
 *
 */
public interface Action {

	public String actionMessage();

	/**
	 * Cette m�thode permet d'ex�cuter une action suivant le param�tre donn�
	 *
	 * @param commandLine
	 *            qui est la commande entr�e
	 * @exception Exception
	 *                qui va lever une exception
	 */
	public void executeWithParamater(String commandLine) throws Exception;

	/**
	 * Cette m�thode permet d'ex�cuter une action sans param�tre
	 *
	 * @exception Exception
	 *                qui va lever une exception
	 */
	public void executeWithoutParamater() throws Exception;
}
