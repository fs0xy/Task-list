package com.codurance.training.action;

import com.codurance.training.tasks.Application;
import com.codurance.training.tasks.Project;
import com.codurance.training.tasks.Task;

/**
 * Cette classe permet d'ajouter des projets et des tâches de la façon suivante
 * : add project <name> ou add project <name1> <name2> ... <nameN> 
 * add task <project name> <task description>
 * 
 * IMPORTANT : Deux projets ne peuvent pas avoir le même nom mais deux tâches le
 * peuvent
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionAdd implements Action {

	// Compteur permettant de gerer le cas où plusieurs projets ont ete crees
	// sur une meme ligne
	private static int cpt = 0;
	private static long id = 1;
	private static boolean pass = false;

	@Override
	public String actionMessage() {
		return "add";
	}

	/**
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * et fait appel a différentes méthodes présentes dans cette classe et dans
	 * Application
	 * 
	 * @param commandLine
	 *            qui désigne la commande entrée en paramètre
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {

		// Recuperation du contenu de la commande et decoupage a chaque espace
		String[] subcommandRest = commandLine.split(" ", 2);

		// Verification du deuxieme argument
		String subcommand = subcommandRest[0];

		// Declaration d un booleen qui va permettre de detecter les commandes
		// inconnues
		boolean check = false;

		if (!subcommandRest[0].equals("project") && !subcommandRest[0].equals("task")) {
			check = true;
		}

		// Si la commande est bonne alors on entre dans la boucle
		if (!check) {

			// Si il s agit de l ajout d un projet
			if (subcommand.equals("project")) {

				// On incremente le compteur
				cpt++;

				// On decoupe tout ce qui suit le mot project
				String[] multiProjects = subcommandRest[1].split(" ");
				for (int i = 0; i < multiProjects.length; i++) {

					// Permet d ajouter plusieurs projets d un coup
					addProject(multiProjects[i]);
				}

				// Si il s agit de l ajout d une tache
			} else if (subcommand.equals("task")) {

				// On decoupe les differents parametres et on fait appel a la
				// methode addTask
				String[] projectTask = subcommandRest[1].split(" ", 2);
				addTask(projectTask[0], projectTask[1]);
			}
		}

		// Sinon on afffiche des messages d erreur
		if (check) {
			Action error = new ActionError();
			error.executeWithParamater(subcommand);
		}
	}

	/**
	 * Cette méthode permet d ajouter un projet
	 * 
	 * @param nameProject
	 *            qui désigne le nom du projet
	 */
	private void addProject(String nameProject) {

		// On retouve le booleen permettant de tester si un projet existe deja
		boolean find = false;

		// Parcours de la liste des projets
		for (Project p : Application.projectList) {

			// Si un projet possede le nom du projet en parametre alors le
			// booleen devient vrai
			if (p.getName().equals(nameProject)) {
				find = true;
			}
		}

		// Affichage du message d'erreur
		if (find) {
			Application.getOut().println("Project "+nameProject+" already exists");

			// Si tout est correct, on ajoute le projet dans la liste des
			// projets
		} else {
			Project project = new Project(nameProject);
			Application.projectList.add(project);
		}
	}

	/**
	 * Méthode permettant d'ajouter une tâche
	 * 
	 * @param projectName
	 *            qui correspond au nom du projet
	 * @param description
	 *            qui correspond à la description de la tâche
	 */
	private void addTask(String projectName, String description) {
		int compteur = 0;

		// Booleen permettant d afficher le message ci-dessous
		boolean find = false;

		// Dans le cas ou plusieurs projets ont ete ajoutes sur une meme ligne
		// et que
		// seul un projet possede des taches
		if (nbTask(projectName) == 0 && cpt == 1
				&& nbTaskOtherProject(Application.projectList.get(Application.projectList.size() - 1).getName()) && pass
				&& Application.projectList.size() == 1) {

			// parcours de la liste des projets
			for (Project p : Application.projectList) {

				// Si le projet existe deja alors find devient vrai
				if (p.getName().equals(projectName)) {
					find = true;
					compteur++;

					// Ajout automatique dans la liste des taches a faire
					p.getTasksUndone().add(new Task(Application.nextId(), description, false, null, null));
				}
			}
		}

		// Appel de la methode nbtask permettant de savoir si un projet contient
		// deja des taches
		// Le compteur ici nous permet de savoir si le projet a ete cree par
		// ajouts multiples. Si ce cas d'erreur n est pas gerer alors les taches
		// s ajoutent dans tous les projets crees en une seule ligne
		if (nbTask(projectName) == 0 && cpt == 1) {

			pass = true;

			// parcours de la liste des projets
			for (Project p : Application.projectList) {

				// Si le projet existe deja alors find devient vrai
				if (p.getName().equals(projectName)) {
					find = true;

					// Ajout automatique dans la liste des taches a faire
					p.getTasksUndone().add(new Task(Application.nextId(), description, false, null, null));
				}
			}
		} else {

			// Initialisation d un booleen et de l id a 0
			// once va permettre d optimiser la recherche d une tache deja
			// existante
			// En effet, si on trouve une tache en doublon alors va recuperer
			// son id et pas continuer a chercher une autre occurrence car les
			// id sont les memes

			if (compteur < 1) {
				boolean once = false;

				// Parcours de la liste des projets
				for (Project p : Application.projectList) {

					// Parcours de la liste des taches a faire
					for (Task task : p.getTasksUndone()) {

						// Si deux taches ont la meme description on recupere l
						// id de la premiere
						if (task.getDescription().equals(description) && !once) {
							id = task.getId();
							once = true;
						}
					}
				}

				// Parcours de la liste des projets
				for (Project p : Application.projectList) {

					// Si le projet existe deja alors find devient vrai
					if (p.getName().equals(projectName)) {
						find = true;

						// Si on a deja trouve un doublon alors on creer la
						// tache
						// avec le meme id sinon la creation se fait avec un id
						// different
						if (once) {
							p.getTasksUndone().add(new Task(id, description, false, null, null));
						} else {
							p.getTasksUndone().add(new Task(Application.nextId(), description, false, null, null));
						}
					}
				}
			}
		}
		// Si find est faux alors on est passe dans aucune boucle
		if (!find) {
			Application.getOut().println("Could not find a project with the name : "+projectName);
		}
	}

	/**
	 * Méthode permettant de connaître le nombre de taches affectées à un projet
	 * 
	 * @param projectName
	 *            qui est le nom du projet
	 * @return le nombre de tâches du projet
	 */
	private int nbTask(String projectName) {
		// Initialisation d un compteur qui sera le nombre de taches du projet
		// projectName
		int compteur = 0;

		// Parcours de la liste des projets
		for (Project p : Application.projectList) {

			// Si le projet existe
			if (p.getName().equals(projectName)) {

				// Si la taille la liste contient des taches alors on recupere
				// sa taille et opt devient vrai
				if (p.getTasksUndone().size() > 0) {
					compteur = p.getUndoneListeSize();
				}
			}
		}
		return compteur;
	}

	/**
	 * Méthode permettant de savoir si un autre projet ne contient pas de
	 * tâche(s)
	 * 
	 * @param projectName
	 *            qui est le nom du projet
	 * @return un booleen indiquant si oui ou non un autre projet contient au
	 *         moins une tâche
	 */
	private boolean nbTaskOtherProject(String projectName) {

		boolean check = false;

		// Parcours de la liste des projets
		for (Project p : Application.projectList) {

			// Si le projet existe
			if (p.getName().equals(projectName)) {

				if (nbTask(projectName) == 0) {
					check = true;
				}
			}
		}
		return check;
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