package com.codurance.training.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codurance.training.action.Action;
import com.codurance.training.action.ActionAdd;
import com.codurance.training.action.ActionCheck;
import com.codurance.training.action.ActionDeadline;
import com.codurance.training.action.ActionDelete;
import com.codurance.training.action.ActionError;
import com.codurance.training.action.ActionHelp;
import com.codurance.training.action.ActionToday;
import com.codurance.training.action.ActionUncheck;
import com.codurance.training.action.ActionView;

import static java.lang.System.lineSeparator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class ApplicationTest {
	public static final String PROMPT = "> ";
	private final PipedOutputStream inStream = new PipedOutputStream();
	private final PrintWriter inWriter = new PrintWriter(inStream, true);

	private final PipedInputStream outStream = new PipedInputStream();
	private final BufferedReader outReader = new BufferedReader(new InputStreamReader(outStream));

	private Thread applicationThread;
	
	private Long millis = System.currentTimeMillis();
	private Date today = new Date(millis);
	
	public ApplicationTest() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new PipedInputStream(inStream)));
		PrintWriter out = new PrintWriter(new PipedOutputStream(outStream), true);

		Application test = Application.getInstance();
		test.setIn(in);
		test.setOut(out);
		applicationThread = new Thread(test);
		
	}

	@Before
	public void start_the_application() {
		applicationThread.start();
	}

	@After
	public void kill_the_application() throws IOException, InterruptedException {
		if (!stillRunning()) {
			return;
		}

		Thread.sleep(1000);
		if (!stillRunning()) {
			return;
		}

		applicationThread.interrupt();
		throw new IllegalStateException("The application is still running.");
	}

	@Test(timeout = 1000)
	public void it_works() throws IOException {
		
		 // Cas d'erreurs basiques
		 execute("add projcdtr test");  
		 readLines("I don't know what the command \"projcdtr\" is","Type help to see the possible commands");
		 
		 execute("add tsfsdf test task1");  
		 readLines("I don't know what the command \"tsfsdf\" is","Type help to see the possible commands");

		 execute("deadlsdsqdine 1 10/10/2020");  
		 readLines("I don't know what the command \"deadlsdsqdine\" is","Type help to see the possible commands");
		 
		 execute("test");  
		 readLines("I don't know what the command \"test\" is","Type help to see the possible commands");
		 
		 execute("view by date");  
		 readLines("No date given","Please create a project");
		 
		 execute("view by deadline");  
		 readLines("No date given","Please create a project");
		
		 execute("view by project"); 
		 readLines("Please create a project");
		 
		 execute("vwxcw by date");  
		 readLines("I don't know what the command \"vwxcw\" is","Type help to see the possible commands");
		 
		 execute("view bybybyby date");  
		 readLines("I don't know what the command \"bybybyby\" is","Type help to see the possible commands");
		 
		 execute("vwxcw by deadline");  
		 readLines("I don't know what the command \"vwxcw\" is","Type help to see the possible commands");
		 
		 execute("view bybybyby deadline");  
		 readLines("I don't know what the command \"bybybyby\" is","Type help to see the possible commands");
		 
		 execute("vwxcw by project");  
		 readLines("I don't know what the command \"vwxcw\" is","Type help to see the possible commands");
		 
		 execute("view bybybyby project");  
		 readLines("I don't know what the command \"bybybyby\" is","Type help to see the possible commands");
		 
		 execute("view by sdfsdf");  
		 readLines("Parameter not found","Please create a project");

		 execute("check 1");  
		 readLines("Could not find a task with a ID of : 1","Please create a project");
		 
		 execute("uncheck 1");  
		 readLines("Could not find a task with a ID of : 1","Please create a project");
		 
		 execute("today");  
		 readLines("No date found","Please create a project");
		 
		 execute("help"); 
		 readLines("Commands:",
				   "  add project <project name>",
				   "  add task <project name> <task description>",
				   "  check <task ID>",
				   "  deadline <task id> <date>",
				   "  today",
				   "  view by date",
				   "  view by deadline",
				   "  view by project");
		 
		 // Cas d erreurs apres la creation d un projet		 
		 execute("add project test");
		 execute("add task test Testing");
		 
		 execute("deadline 1 10/10/2000");
		 readLines("Deadline : 10/10/2000 can't be inferior to today's date");
		 
		 execute("deadline 1 35/10/2040");
		 readLines("Date 35/10/2040 is not valid");
		 
		 execute("deadline 1 10/10/2020");

		 execute("view by date");
		 readLines("Undone TaskList : ","Project : test, Name : Testing, Id : 1, Creation date : "+getTodaysDate());

		 execute("view by deadline");
		 readLines("Undone TaskList : ","Project : test, Name : Testing, Id : 1, Deadline : 10/10/2020");
		 
		 execute("deadline 1 "+getTodaysDate());
		 
		 execute("today");
		 readLines("Project : test, Description : Testing, Id : 1");
		 
		 execute("view by project");
		 readLines("test",
				   "Undone TaskList : ",
                   "    [ ] 1: Testing",
                   "    Creation date : "+getTodaysDate(),
 				   "    Deadline : "+getTodaysDate());
		 
		 execute("add project secrets");
		 
		 execute("add project secrets");
		 readLines("Project secrets already exists");
		 
		 execute("add task secrets Eat more donuts.");
		 
		 execute("add task sfdf test");
		 readLines("Could not find a project with the name : sfdf");
		 
		 execute("add task secrets Destroy all humans.");
		 
		 execute("view by project"); 
		 readLines("test",
		     	   "Undone TaskList : ",
				   "    [ ] 1: Testing",
				   "    Creation date : "+getTodaysDate(),
				   "    Deadline : "+getTodaysDate(),
				   "secrets",
		     	   "Undone TaskList : ",
				   "    [ ] 2: Eat more donuts.",
				   "    Creation date : No date given",
				   "    Deadline : No date given",
				   "    [ ] 3: Destroy all humans.", 
				   "    Creation date : No date given",
				   "    Deadline : No date given");
		 
		 execute("add project training");
		 execute("add task training Four Elements of Simple Design");
		 execute("add task training SOLID");
		 execute("add task secrets SOLID");
		 execute("add task training Coupling and Cohesion");
		 execute("add task training Primitive Obsession");
		 execute("add task training Outside-In TDD");
		 execute("add task training Interaction-Driven Design");
		 
		 execute("check 2"); 
		 readLines("Id 2 removed from undone TaskLists");
		 execute("check 4"); 
		 readLines("Id 4 removed from undone TaskLists");
		 execute("check 6");
		 readLines("Id 6 removed from undone TaskLists");
		 execute("check 7");
		 readLines("Id 7 removed from undone TaskLists");
		 
		 execute("view by project"); 
		 
		 //Pour etre honnete, les tests a partir d ici passent 1 fois sur 10 pour une raison inconnue...
		 readLines("test",
		     	   "Undone TaskList : ",
				   "    [ ] 1: Testing",
				   "    Creation date : "+getTodaysDate(),
				   "    Deadline : "+getTodaysDate(),
				   
				   "secrets",
				   "Undone TaskList : ",
				   "    [ ] 3: Destroy all humans.",
				   "    Creation date : No date given",
				   "    Deadline : No date given",
				   "    [ ] 5: SOLID",
				   "    Creation date : No date given",
				   "    Deadline : No date given",
				   "Done TaskList : ",
				   "    [x] 2: Eat more donuts.",
				   "    Creation date : No date given",
				   "    Deadline : No date given",
				   
				   "training",
				   "Undone TaskList : ",
				   "    [ ] 5: SOLID",
				   "    Creation date : No date given",
			       "    Deadline : No date given",
			       "    [ ] 8: Outside-In TDD",
				   "    Creation date : No date given",
			       "    Deadline : No date given",
				   "    [ ] 9: Interaction-Driven Design",
				   "    Creation date : No date given",
			       "    Deadline : No date given",
				   "Done TaskList : ",
				   "    [x] 4: Four Elements of Simple Design",
				   "    Creation date : No date given",
				   "    Deadline : No date given",
				   "    [x] 6: Coupling and Cohesion",
				   "    Creation date : No date given",
				   "    Deadline : No date given",
			       "    [x] 7: Primitive Obsession",
				   "    Creation date : No date given",
				   "    Deadline : No date given"); 
		
		 execute("check 7");
		 readLines("Could not find a task with a ID of : 7 in Undone TasksList");

		 execute("uncheck 9");
		 readLines("Could not find a task with a ID of : 9 in Done TasksList");
		 
		 execute("quit");
	}

	private String getTodaysDate(){
		return Application.dateToString(today);
	}
	
	private void execute(String command) throws IOException {
		read(PROMPT);
		write(command);
	}

	private void read(String expectedOutput) throws IOException {
		int length = expectedOutput.length();
		char[] buffer = new char[length];
		outReader.read(buffer, 0, length);
		assertThat(String.valueOf(buffer), is(expectedOutput));
	}
	
	private void readLines(String... expectedOutput) throws IOException {
		for (String line : expectedOutput) {
			read(line + lineSeparator());
		}
	}

	private void write(String input) {
		inWriter.println(input);
	}

	private boolean stillRunning() {
		return applicationThread != null && applicationThread.isAlive();
	}
}
