package com.codurance.training.action;

import java.util.Iterator;
import com.codurance.training.tasks.Project;
import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.Application;

/**
 * Cette classe permet de supprimer une t�che en faisant delete <id>
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionDelete implements Action {

	@Override
	public String actionMessage() {
		return "today";
	}

	/**
	 * M�thode permettant d'ex�cuter une instruction donn�e en ligne de commande
	 * Elle r�cup�re un id, le convertit en entier et proc�de � sa suppression
	 * 
	 * @param commandLine
	 *            qui d�signe la commande entr�e
	 * @exception Exception
	 *                qui l�ve une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {
		// Recuperation du contenu de la commande et decoupage a chaque espace
		String[] subcommandRest = commandLine.split(" ", 2);

		// Booleen permettant de verifier si l id est bien un entier
		boolean isInt = true;

		isInt = Application.isIdCorrect(subcommandRest[0]);

		if (isInt) {
			// Conversion de la chaine id en entier
			int id = Integer.parseInt(subcommandRest[0]);

			// Initialisation d'un compteur pour n afficher qu'une seule fois le
			// message ci-dessous
			int cpt = 0;

			// Parcours de la liste des projets
			for (Project project : Application.projectList) {

				// Parcours de la liste des taches a faire
				Iterator<Task> i = project.getTasksUndone().iterator();
				while (i.hasNext()) {
					Task tsk = i.next();

					// Si l id correspond alors on la supprime
					if (tsk.getId() == id) {
						i.remove();
					} else {
						cpt++;
					}
				}

				// Parcours de la liste des taches faites
				Iterator<Task> j = project.getTasksDone().iterator();
				while (j.hasNext()) {
					Task tsk = j.next();

					// Si l id correspond alors on la supprime
					if (tsk.getId() == id) {
						j.remove();
					} else {
						cpt++;
					}
				}
			}
			// Si le compteur vaut 0 cela signifie qu on est pas rentre dans les
			// boucles
			if (cpt > 0) {
				Application.getOut().println("id "+id+" removed from TaskLists");
			}
			Application.errorPrint(cpt, id);
		}
	}

	/**
	 * M�thode permettant d'ex�cuter une action sans param�tre
	 * 
	 * @exception Exception
	 *                qui l�ve une exception
	 */
	@Override
	public void executeWithoutParamater() throws Exception {
	}
}