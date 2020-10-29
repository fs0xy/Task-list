package com.codurance.training.tasks;

import java.util.Date;

/**
 * Cette classe permet de cr�er un objet Task qui repr�sente une t�che
 * 
 * @author Thomas Reynaud
 *
 */
public final class Task {
	private final long id;
	private final String description;
	private boolean done;
	private Date dateCreation;
	private Date dateLimite;

	/**
	 * Constructeur prenant en param�tre un id, une description, un �tat :
	 * 'done' et une date de creation avec sa deadline
	 * 
	 * @param id : id d'une t�che
	 * @param description : sa description
	 * @param done : son �tat
	 * @param dateCrea : date de cr�ation
	 * @param dateLim : date limite
	 */
	public Task(long id, String description, boolean done, Date dateCrea, Date dateLim) {
		this.id = id;
		this.description = description;
		this.done = done;
		this.dateCreation = dateCrea;
		this.dateLimite = dateLim;
	}

	/**
	 * M�thode permettant de r�cup�rer l'id de la t�che
	 * 
	 * @return un id
	 */
	public long getId() {
		return id;
	}

	/**
	 * M�thode permettant de retourner la description de la t�che
	 * 
	 * @return une description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * M�thode permettant de savoir si une t�che est finie ou non
	 * 
	 * @return un booleen
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * M�thode permettant de r�cup�rer la date de creation au format String
	 * 
	 * @return la date de creation
	 */
	public String getDateCreation() {
		String value = "";
		if (dateCreation == null) {
			value = "No date given";
			return value;
		} else {
			return Application.dateToString(dateCreation);
		}

	}

	/**
	 * M�thode permettant de r�cup�rer la deadline au format String
	 * 
	 * @return la deadline
	 */
	public String getDateLimite() {
		String value = "";
		if (dateLimite == null) {
			value = "No date given";
			return value;
		} else {
			return Application.dateToString(dateLimite);
		}
	}

	/**
	 * M�thode permettant de changer l'�tat d'une t�che
	 * 
	 * @paramun done
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * M�thode permettant de fixer une date � la date du jour
	 * 
	 * @param dateCrea
	 */
	public void setDateCreation(Date dateCrea) {
		this.dateCreation = dateCrea;
	}

	/**
	 * M�thode permettant de fixer une deadline
	 * 
	 * @param dateLim
	 */
	public void setDateLimite(Date dateLim) {
		this.dateLimite = dateLim;
	}
}