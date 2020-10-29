package com.codurance.training.action;

import com.codurance.training.tasks.Project;
import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.Application;

/**
 * Cette classe permet d'afficher les tâches dont la date butoire est la date du
 * jour via la commande suivante : today
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionToday implements Action {

	@Override
	public String actionMessage() {
		return "today";
	}

	/**
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * 
	 * @param commandLine
	 *            qui désigne la commande entrée
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
	}

	/**
	 * Méthode permettant d'exécuter une action sans paramètre
	 * 
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithoutParamater() throws Exception {
		// Intialisation d un compteur perttant d afficher le message ci-dessous
		int cpt = 0;

		// Parcours de la liste des projets
		for (Project project : Application.projectList) {

			// Parcours de la liste des taches a faire (Il n y a pas d interet a
			// parcourir la liste des taches deja faites)
			for (Task task : project.getTasksUndone()) {

				// Si la date limite correspond a la date du jour et que les
				// dates ne correspondent pas a 'No date given' alors on affiche
				// differentes informations
				if (task.getDateLimite().equals(task.getDateCreation())
						&& !task.getDateCreation().equals("No date given")
						&& !task.getDateLimite().equals("No date given")) {
					Application.getOut().println("Project : " + project.getName() + ", Description : "
							+ task.getDescription() + ", Id : " + task.getId());

					// On incremente le compteur
					cpt++;
				}
			}
		}

		// Cela signifie qu on est pas passe par les boucles
		if (cpt == 0) {
			Application.getOut().println("No date found");
		}

		// Il faut creer un projet avant quoi que ce soit
		if (Application.projectList.size() == 0) {
			Application.getOut().println("Please create a project");
		}
	}
}