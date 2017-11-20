import java.util.*;

public class Main {
    private static int currentUserId;
    private static SqlProc sqlProc;
    private static DisplaySqlProc displaySqlProc;
    private static Scanner scanner;
    private static final String wipe = "\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n";

    public static void main(String[] argv) {
	sqlProc = new SqlProc();
	displaySqlProc = new DisplaySqlProc();
	scanner = new Scanner(System.in);
	while (true) {
	    System.out.println("Welcome to GameGo!");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[C] Create new user account");
	    System.out.println("[L] Login existing user");
	    System.out.println("[Q] Quit");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "c":
		createUserMenu();
		break;
	    case "l":
		loginUserMenu();
		break;
	    case "q":
		System.out.println("Goodbyes from GameGo!");
		scanner.close();
		System.exit(0);
		break;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }

    public static void createUserMenu() {
	while (true) {
	    System.out.print("Creating basic GameGo account.");
	    System.out.print("\r\nName: ");
	    String name = scanner.nextLine();
	    System.out.print("\r\nAge: ");
	    String ageString = scanner.nextLine();
	    System.out.print("\r\nEmail: ");
	    String email = scanner.nextLine();
	    System.out.print("\r\nPassword: ");
	    String password = scanner.nextLine();
	    System.out.println(wipe);

	    int ageInt = Integer.parseInt(ageString);
	    int response = sqlProc.createUser(name, ageInt, email, password);

	    if (response > -1) {
		currentUserId = response;
		userMenu();
		return;
	    } else {
		System.out.println("Oops, user exists already.");
	    }

	}
    }

    public static void loginUserMenu() {
	while (true) {
	    System.out.print("Logging into GameGo account.");
	    System.out.print("\r\nEmail: ");
	    String email = scanner.nextLine();
	    System.out.print("\r\nPassword: ");
	    String password = scanner.nextLine();
	    System.out.println(wipe);

	    int response = sqlProc.loginUser(email, password);

	    if (response > -1) {
		currentUserId = response;
		userMenu();
		return;
	    } else {
		System.out.println("Oops, user exists already.");
	    }
	}
    }

    public static void userMenu() {
	while (true) {
	    System.out.println("Welcome user!");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[M] Become a member");
	    System.out.println("[G] View games");
	    System.out.println("[C] View consoles");
	    System.out.println("[Q] Log out");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "m":
		createMemberMenu();
		break;
	    case "g":
		viewGamesMenu();
		break;
	    case "q":
		System.out.println("You've recently logged out.");
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }

    public static void createMemberMenu() {
	while (true) {
	    System.out.println("Joining GameGo Membership, please confirm your credentials.");
	    System.out.print("\r\nEmail: ");
	    String email = scanner.nextLine();
	    System.out.print("\r\nPassword: ");
	    String password = scanner.nextLine();
	    System.out.println(wipe);

	    int response = sqlProc.createMember(email, password);

	    if (response > -1) {
		System.out.println("You've successfully joined GameGo membership.");
		return;
	    } else {
		System.out.println("Oops, incorrect credentials when trying to become member.");
		return;
	    }

	}
    }

    public static void viewGamesMenu() {
	while (true) {
	    System.out.println("View Games Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[T] View games sorted by title");
	    System.out.println("[A] View games sorted by author");
	    System.out.println("[G] View games sorted by genre");
	    System.out.println("[C] View games sorted by console");
	    System.out.println("[R] View games sorted by rating");
	    System.out.println("[P] View games sorted by price");
	    System.out.println("[B] Buy game");
	    System.out.println("[Q] Go back");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);
	    ArrayList<String> gameList = new ArrayList<String>();

	    switch (choice.toLowerCase()) {
	    case "t":
		gameList = displaySqlProc.viewGames("title");
		break;
	    case "a":
		gameList = displaySqlProc.viewGames("author");
		break;
	    case "g":
		gameList = displaySqlProc.viewGames("genre");
		break;
	    case "c":
		gameList = displaySqlProc.viewGames("console");
		break;
	    case "r":
		gameList = displaySqlProc.viewGames("rating");
		break;
	    case "p":
		gameList = displaySqlProc.viewGames("price");
		break;
	    case "b":
		System.out.println("Enter the game's ID to purchase the game.");
		System.out.println("Or, press [Q] to go back.");
		String gidString = scanner.nextLine();
		System.out.println(wipe);
		if (gidString.toLowerCase().equals("q")) {
		    break;
		} else {
		    int gidInt = Integer.parseInt(gidString);
		    String result = sqlProc.buyGame(currentUserId, gidInt);
		    System.out.println(result);
		}
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	    printGameList(gameList);
	}
    }

    public static void printGameList(ArrayList<String> gameList) {
	for (String listing : gameList) {
	    System.out.println(listing);
	}
    }
}