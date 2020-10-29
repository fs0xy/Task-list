package com.codurance.training.tasks;

import java.util.LinkedList;
import java.util.List;

/**
 * Cette classe permet de créer un objet Project qui représente un projet
 * 
 * @author Thomas Reynaud
 *
 */
public class Project {
	private String name;
	private List<Task> myTasksDone;
	private List<Task> myTasksUndone;

	/**
	 * Constructeur prenant en paramètre le nom d'un projet et initialisant deux
	 * listes, les tâches à faire et celles déjà faites
	 * 
	 * @param nameProject
	 */
	public Project(String nameProject) {
		this.name = nameProject;
		myTasksDone = new LinkedList<Task>();
		myTasksUndone = new LinkedList<Task>();
	}

	/**
	 * Méthode permettant de connaître le nom du projet
	 * 
	 * @return le nom du projet
	 */
	public String getName() {
		return name;
	}

	/**
	 * Méthode permettant de visualiser les tâches achevées
	 * 
	 * @return la liste des tâches faites
	 */
	public List<Task> getTasksDone() {
		return myTasksDone;
	}

	/**
	 * Méthode permettant de visualiser les tâches à faire
	 * 
	 * @return la liste des tâches à faire
	 */
	public List<Task> getTasksUndone() {
		return myTasksUndone;
	}
	
	/**
	 * Méthode permettant de savoir si un id est dans une liste de tâche achevées
	 * @param id
	 * @return un booleen
	 */
	public boolean isIdInDoneList(long id){
		boolean check = false;
		for(Task tsk : myTasksDone){
			if(tsk.getId() == id){
				check = true;
			}
		}
		return check;
	}
	
	/**
	 * Méthode permettant de savoir si un id est dans une liste de tâche non achevées
	 * @param id
	 * @return un booleen
	 */
	public boolean isIdInUndoneList(long id){
		boolean check = false;
		for(Task tsk : myTasksUndone){
			if(tsk.getId() == id){
				check = true;
			}
		}
		return check;
	}
	
	/**
	 * Méthode permettant de récupérer la taille de la liste des tâches à faire
	 * @return un entier
	 */
	public int getUndoneListeSize(){
		return this.myTasksUndone.size();
	}
}