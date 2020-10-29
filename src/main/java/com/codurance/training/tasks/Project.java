package com.codurance.training.tasks;

import java.util.LinkedList;
import java.util.List;

/**
 * Cette classe permet de cr�er un objet Project qui repr�sente un projet
 * 
 * @author Thomas Reynaud
 *
 */
public class Project {
	private String name;
	private List<Task> myTasksDone;
	private List<Task> myTasksUndone;

	/**
	 * Constructeur prenant en param�tre le nom d'un projet et initialisant deux
	 * listes, les t�ches � faire et celles d�j� faites
	 * 
	 * @param nameProject
	 */
	public Project(String nameProject) {
		this.name = nameProject;
		myTasksDone = new LinkedList<Task>();
		myTasksUndone = new LinkedList<Task>();
	}

	/**
	 * M�thode permettant de conna�tre le nom du projet
	 * 
	 * @return le nom du projet
	 */
	public String getName() {
		return name;
	}

	/**
	 * M�thode permettant de visualiser les t�ches achev�es
	 * 
	 * @return la liste des t�ches faites
	 */
	public List<Task> getTasksDone() {
		return myTasksDone;
	}

	/**
	 * M�thode permettant de visualiser les t�ches � faire
	 * 
	 * @return la liste des t�ches � faire
	 */
	public List<Task> getTasksUndone() {
		return myTasksUndone;
	}
	
	/**
	 * M�thode permettant de savoir si un id est dans une liste de t�che achev�es
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
	 * M�thode permettant de savoir si un id est dans une liste de t�che non achev�es
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
	 * M�thode permettant de r�cup�rer la taille de la liste des t�ches � faire
	 * @return un entier
	 */
	public int getUndoneListeSize(){
		return this.myTasksUndone.size();
	}
}