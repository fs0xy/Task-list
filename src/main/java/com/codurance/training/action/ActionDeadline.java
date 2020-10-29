package com.codurance.training.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.codurance.training.tasks.Project;
import com.codurance.training.tasks.Task;
import com.codurance.training.tasks.Application;

/**
 * Cette classe permet de fixer une deadline grace à la commande suivante :
 * deadline <id> <date souhaitée>
 * 
 * IMPORTANT : Il n'est pas possible de mettre une deadline sur une tâche déjà
 * achevée car la logique veut que seules les tâches à faire disposent d'une
 * date butoire.
 * 
 * Veuillez donner une date au format jour/mois/annee
 * 
 * @author Thomas Reynaud
 *
 */
public class ActionDeadline implements Action {

	@Override
	public String actionMessage() {
		return "deadline";
	}

	/**
	 * Méthode permettant d'exécuter une instruction donnée en ligne de commande
	 * Elle récupère une chaîne représentant un id avec une date et les
	 * converties dans leurs formats respectifs
	 * 
	 * @param commandLine
	 *            qui désigne la commande
	 * @exception Exception
	 *                qui lève une exception
	 */
	@Override
	public void executeWithParamater(String commandLine) throws Exception {

		Long millis = System.currentTimeMillis();
		Date today = new Date(millis);

		// compteur qui va permettre d afficher une seule fois le message d
		// erreur y correspondant
		int cpt = 0;

		// Variable permettant de ne pas parcourir la liste des taches si l
		// id est incorrect
		boolean check = false;

		// Booleen permettant de verifier si l id est bien un entier
		boolean isInt = true;

		// Recuperation du contenu de la commande et decoupage a chaque espace
		String[] subcommandRest = commandLine.split(" ", 2);

		isInt = Application.isIdCorrect(subcommandRest[0]);

		if (isInt) {
			// Conversion de la chaine id en entier
			int id = Integer.parseInt(subcommandRest[0]);

			// Debut des verifications de la date au format francais
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			// Permet d etre plus precis sur les verifications
			sdf.setLenient(false);

			Date endDate = new Date();

			try {
				endDate = sdf.parse(subcommandRest[1]);
			} catch (ParseException e) {
				Application.getOut().println("Date "+subcommandRest[1]+" is not valid");
			}

			// Verifions que la deadline ne se situe pas avant la date d aujourd
			// hui
			if (endDate.compareTo(today) > 0
					|| Application.dateToString(today).compareTo(Application.dateToString(endDate)) == 0) {
				// Parcours de la liste des projets
				for (Project project : Application.projectList) {

					// On cherche a savoir si l id correspond a une tache
					// achevee ou pas
					if (project.isIdInDoneList(id)) {
						check = true;
						Application.getOut().println("id "+id+" is a done task");
						Application.getOut().println("Deadline can only be applied to an undone task");
					}

					// Si la tache n est pas finie
					if (!check) {
						// Parcours de la liste des taches a faire
						for (Task task : project.getTasksUndone()) {

							// Si l id correspond alors on affecte la date du
							// today
							// comme date de creation et la date donnee en
							// parametre comme date de fin
							if (task.getId() == id) {
								cpt++;
								task.setDateCreation(today);
								task.setDateLimite(endDate);
							}
						}
					}
				}
				Application.errorPrint(cpt, id);
			} else {
				// Appel de la methode pour convertir notre date en string
				Application.getOut().println(
						"Deadline : " + Application.dateToString(endDate) + " can't be inferior to today's date");
			}
		}
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