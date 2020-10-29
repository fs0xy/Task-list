package com.codurance.training.tasks;

import java.util.Date;

/**
 * Cette classe permet de créer un objet Task qui représente une tâche
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
	 * Constructeur prenant en paramètre un id, une description, un état :
	 * 'done' et une date de creation avec sa deadline
	 * 
	 * @param id : id d'une tâche
	 * @param description : sa description
	 * @param done : son état
	 * @param dateCrea : date de création
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
	 * Méthode permettant de récupérer l'id de la tâche
	 * 
	 * @return un id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Méthode permettant de retourner la description de la tâche
	 * 
	 * @return une description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Méthode permettant de savoir si une tâche est finie ou non
	 * 
	 * @return un booleen
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * Méthode permettant de récupérer la date de creation au format String
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
	 * Méthode permettant de récupérer la deadline au format String
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
	 * Méthode permettant de changer l'état d'une tâche
	 * 
	 * @paramun done
	 */
	public void setDone(boolean done) {
		this.done = done;
	}

	/**
	 * Méthode permettant de fixer une date à la date du jour
	 * 
	 * @param dateCrea
	 */
	public void setDateCreation(Date dateCrea) {
		this.dateCreation = dateCrea;
	}

	/**
	 * Méthode permettant de fixer une deadline
	 * 
	 * @param dateLim
	 */
	public void setDateLimite(Date dateLim) {
		this.dateLimite = dateLim;
	}
}