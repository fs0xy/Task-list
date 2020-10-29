package com.codurance.training.action;

import com.codurance.training.tasks.Application;
import com.codurance.training.tasks.Project;
import com.codurance.training.tasks.Task;

/**
 * Cette classe permet d'afficher les tâches selon differents critères que sont
 * la date et la deadline via les commandes suivantes : 
 * view by date 
 * view by deadline
 * 
 * Ces deux commandes ne vont afficher que la liste des tâches a faire
 * 
 * La commande : view by project remplace la commande show
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionView implements Action {

	@Override
	public String actionMessage() {
		return "add";
	}

	/**
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * et fait appel a différentes méthodes de cette classe et de la classe Application
	 * 
	 * @param commandLine
	 *            qui désigne la commande entrée
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		// Initialisation d un compteur permettant d afficher le message
		// ci-dessous
		int cpt = 1;

		// Recuperation du contenu de la commande et decoupage a chaque espace
		String[] subcommandRest = commandLine.split(" ", 2);

		// Verification du deuxieme argument
		String subcommand = subcommandRest[0];

		// Si la apres 'view' se trouve 'by' on continue
		if (subcommand.equals("by")) {

			// On recupere ce qui suit le 'by'
			String subcommand2 = subcommandRest[1];

			// Si Il s agit de view by date
			if (subcommand2.equals("date")) {

				cpt = 0;

				// Booleen permettant d afficher une seule fois certains
				// messages, d eviter des repetitions
				boolean display = false;

				// Parcours de la liste des projets
				for (Project project : Application.projectList) {

					// Parcours de la liste des taches
					for (Task task : project.getTasksUndone()) {

						// Si la liste des taches a faire est superieure a 0
						if (project.getTasksUndone().size() > 0) {

							// Si une date de creation a ete mise
							if (!task.getDateCreation().equals("No date given")) {
								if (!display) {
									display = true;

									// On affiche un message
									Application.getOut().println("Undone TaskList : ");
								}

								// Affichage des differentes informations
								Application.getOut()
										.println("Project : " + project.getName() + ", Name : " + task.getDescription()
												+ ", Id : " + task.getId() + ", Creation date : "
												+ task.getDateCreation());
								cpt+=2;
							}
						}
					}
				}
			}

			// Si il s agit de view by deadline
			if (subcommand2.equals("deadline")) {

				cpt = 0;

				// Meme initialisation du boolean
				boolean display = false;

				// Parcours de la liste des projets
				for (Project project : Application.projectList) {

					// SI la liste des taches n'est pas vide
					if (project.getTasksUndone().size() > 0) {

						// Parcours la liste des taches a faire
						for (Task task : project.getTasksUndone()) {
							if (!task.getDateLimite().equals("No date given")) {
								if (!display) {
									display = true;

									// Affichage d un message
									Application.getOut().println("Undone TaskList : ");
								}

								// Affichage des informations
								Application.getOut()
										.println("Project : " + project.getName() + ", Name : " + task.getDescription()
												+ ", Id : " + task.getId() + ", Deadline : " + task.getDateLimite());
								cpt += 2;
							}
						}
					}
				}
			}

			if (cpt == 0) {
				Application.getOut().println("No date given");
			}

			// Si il s agit de view by project
			if (subcommand2.equals("project")) {

				cpt = 0;

				// Parcours de la liste des projets
				for (Project project : Application.projectList) {

					// Affichage du nom des projets
					Application.getOut().println(project.getName());

					// Si la liste des taches a faire n est pas vide
					if (project.getTasksUndone().size() > 0) {

						// Affichage d un message
						Application.getOut().println("Undone TaskList : ");

						// Parcours de la liste des taches a faire
						for (Task task : project.getTasksUndone()) {

							// Affichage des differentes informations
							Application.getOut().println("    ["+(task.isDone() ? 'x' : ' ')+"] "+task.getId()+": "+task.getDescription());
							Application.getOut().println("    Creation date : "+task.getDateCreation());
							Application.getOut().println("    Deadline : "+task.getDateLimite());
							
							cpt += 2;
						}
					}

					// Si la liste des taches achevees n est pas vide
					if (project.getTasksDone().size() > 0) {

						// Affichage d un message
						Application.getOut().println("Done TaskList : ");
						for (Task task : project.getTasksDone()) {

							// Affichage des informations
							Application.getOut().println("    ["+(task.isDone() ? 'x' : ' ')+"] "+task.getId()+": "+task.getDescription());
							Application.getOut().println("    Creation date : "+task.getDateCreation());
							Application.getOut().println("    Deadline : "+task.getDateLimite());
							cpt += 2;
						}
					}
				}
			}
			
			// Cela signifie qu on est pas rentre dans les boucles
			if (cpt == 1) {
				Application.getOut().println("Parameter not found");
			}

			// Toujours avoir creer au moins un projet
			if (Application.projectList.size() == 0) {
				Application.getOut().println("Please create a project");
			}
		} else {
			Action error = new ActionError();
			error.executeWithParamater(subcommand);
		}
	}

	@Override
	public void executeWithoutParamater() throws Exception {
	}
}