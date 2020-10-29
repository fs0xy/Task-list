package com.codurance.training.tasks;

/**
 * Cette classe permet de faire appel � toutes les actions et de faire marcher le programme avec 
 * son main et ses diff�rentes m�thodes
 * 
 * @author Thomas Reynaud
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.codurance.training.action.ActionToday;
import com.codurance.training.action.ActionUncheck;
import com.codurance.training.action.ActionView;
import com.codurance.training.action.Action;
import com.codurance.training.action.ActionAdd;
import com.codurance.training.action.ActionCheck;
import com.codurance.training.action.ActionDeadline;
import com.codurance.training.action.ActionDelete;
import com.codurance.training.action.ActionError;
import com.codurance.training.action.ActionHelp;

public final class Application implements Runnable {

	// 'quit' permet de quitter le programme
	private static final String QUIT = "quit";

	// Liste contenant la liste des projets
	public static List<Project> projectList = new LinkedList<Project>();

	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static PrintWriter out = new PrintWriter(System.out);

	// Grace au singleton, permet de ne creer qu une seule instance de la classe
	private static Application uniqueInstance;

	// Va permettre de generer un id different grace au static
	private static long lastId = 0;

	/**
	 * Constructeur priv� prenant deux parametres permettant de lire et �crire
	 * 
	 * @param reader
	 *            qui permet de lire
	 * @param writer
	 *            qui permet d'�crire
	 */
	private Application(BufferedReader reader, PrintWriter writer) {
		Application.in = reader;
		Application.out = writer;
	}

	/**
	 * M�thode permettant de cr�er une seule instance du constructeur
	 * 
	 * @return une instance de Application
	 */
	public static synchronized Application getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Application(in, out);
		}
		return uniqueInstance;
	}

	/**
	 * M�thode permettant de lancer l'application et de r�cup�rer les commandes
	 */
	public void run() {
		while (true) {
			out.print("> ");
			out.flush();
			String command;
			try {
				command = in.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			if (command.equals(QUIT)) {
				break;
			}
			try {
				execute(command);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * M�thode permettant de r�cup�rer le contenu d'une commande et de faire
	 * appel aux bons Actions
	 * 
	 * @param commandLine
	 *            qui d�signe la commande
	 * @throws Exception
	 *             qui l�ve une exception
	 */
	private void execute(String commandLine) throws Exception {
		String[] commandRest = commandLine.split(" ", 2);
		String command = commandRest[0];
		switch (command) {
		case "add":
			Action add = new ActionAdd();
			add.executeWithParamater(commandRest[1]);
			break;
		case "check":
			Action check = new ActionCheck();
			check.executeWithParamater(commandRest[1]);
			break;
		case "uncheck":
			Action uncheck = new ActionUncheck();
			uncheck.executeWithParamater(commandRest[1]);
			break;
		case "deadline":
			Action deadline = new ActionDeadline();
			deadline.executeWithParamater(commandRest[1]);
			break;
		case "today":
			Action today = new ActionToday();
			today.executeWithoutParamater();
			break;
		case "delete":
			Action delete = new ActionDelete();
			delete.executeWithParamater(commandRest[1]);
			break;
		case "view":
			Action view = new ActionView();
			view.executeWithParamater(commandRest[1]);
			break;
		case "help":
			Action help = new ActionHelp();
			help.executeWithoutParamater();
			break;
		default:
			Action error = new ActionError();
			error.executeWithParamater(command);
			break;
		}
	}

	/**
	 * M�thode permettant de changer l'�tat d'une t�che
	 * 
	 * @param idString
	 *            qui d�signe la chaine de l'id
	 * @param done
	 *            qui correspond � l'�tat
	 * @throws Exception
	 *             qui l�ve une exception
	 */
	public static void setDone(String idString, boolean done) throws Exception {

		// Booleen permettant de verifier si l id est bien un entier
		boolean isInt = true;
		boolean findIdDone = false, findIdUndone = false;

		int cptundone = 0, cptdone = 0;

		isInt = Application.isIdCorrect(idString);

		if (isInt) {
			int id = Integer.parseInt(idString);

			// Si done est vrai alors il s agit de check
			if (done) {

				// Parcours de la liste des projets
				for (Project project : Application.projectList) {
					findIdDone = project.isIdInDoneList(id);

					// Parcours de la liste des taches
					Iterator<Task> i = project.getTasksUndone().iterator();
					while (i.hasNext()) {
						Task tsk = i.next();

						// Si l id correspond alors on la supprime et on l
						// ajoute
						// dans la liste des taches faites
						if (tsk.getId() == id) {
							findIdUndone = true;
							tsk.setDone(done);
							project.getTasksDone().add(tsk);
							i.remove();
							cptundone++;
						}
					}
				}
				if (cptundone > 0) {
					out.println("Id "+id+" removed from undone TaskLists");
				}
				if (findIdDone && !findIdUndone) {
					out.println("Could not find a task with a ID of : "+id+" in Undone TasksList");
				}
			}

			// Sinon il s agit de uncheck
			else {
				// Parcours de la liste des projets
				for (Project project : Application.projectList) {

					findIdUndone = project.isIdInUndoneList(id);

					// Parcours de la liste des taches
					Iterator<Task> i = project.getTasksDone().iterator();
					while (i.hasNext()) {
						Task tsk = i.next();

						// Si l id correspond alors on la supprime
						if (tsk.getId() == id) {
							tsk.setDone(done);
							findIdDone = true;
							// On l ajoute dans la liste des taches a faire
							project.getTasksUndone().add(tsk);
							i.remove();
							cptdone++;
						}
					}
				}
				if (cptdone > 0) {
					out.println("Id "+id+" removed from done TaskLists");
				}
				// Cela signifie qu on est pas passe dans les boucles
				if (!findIdDone && findIdUndone) {
					out.println("Could not find a task with a ID of : "+id+" in Done TasksList");
				}
			}
			if (!findIdDone && !findIdUndone) {
				 
				out.println("Could not find a task with a ID of : "+id);
			}
			// D abord creer un projet
			if (Application.projectList.size() == 0) {
				out.println("Please create a project");
			}
		}
	}

	/**
	 * M�thode centralisant les messages d'erreurs
	 * 
	 * @param compteur
	 *            qui d�signe un compteur
	 * @param id
	 *            qui d�signe l'id d'une t�che
	 */
	public static void errorPrint(int compteur, int id) {
		// Cela signifie qu on est pas passe dans les boucles
		if (compteur == 0) {
			 
			Application.getOut().println("Could not find a task with a ID of : "+id);
		}

		// D abord creer un projet
		if (Application.projectList.size() == 0) {
			Application.getOut().println("Please create a project");
		}
	}

	/**
	 * M�thode permettant de changer le param�tre 'in'
	 * 
	 * @param in
	 */
	public static void setIn(BufferedReader in) {
		Application.in = in;
	}

	/**
	 * M�thode permettant de changer le param�tre 'out'
	 * 
	 * @param out
	 */
	public static void setOut(PrintWriter out) {
		Application.out = out;
	}

	/**
	 * M�thode permettant de retourner le param�tre 'in'
	 * 
	 * @return in
	 */
	public static BufferedReader getIn() {
		return in;
	}

	/**
	 * M�thode permettant de retourner le param�tre 'out'
	 * 
	 * @return out
	 */
	public static PrintWriter getOut() {
		return out;
	}

	/**
	 * M�thode permettant de cr�er un id
	 * 
	 * @return un id
	 */
	public static long nextId() {
		return ++lastId;
	}

	/**
	 * M�thode permettant de convertir une date en string
	 * 
	 * @return un string
	 */
	public static String dateToString(Date myDate) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(myDate);
	}

	/**
	 * M�thode permettant de v�rifier que l'id est un entier
	 * 
	 * @param id
	 *            � fourir
	 * @return un booleen
	 */
	public static boolean isIdCorrect(String id) {
		int idCheck = 0;
		boolean check = true;
		try {
			idCheck = Integer.parseInt(id);
		} catch (NumberFormatException e) {
			check = false;
			Application.getOut().println("Id "+id+" is not an integer");
		}
		return check;
	}

	public static void main(String[] args) throws Exception {
		// Lancement de l application
		Application.getInstance().run();
	}
}