import java.util.*;

public class Main {
    private static int currentUserId;
    private static int currentMemberId;
    private static boolean isAdmin;
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
	    System.out.println("[1] Create new user account");
	    System.out.println("[2] Login existing user");
	    System.out.println("[3] Login existing admin");
	    System.out.println("[Q] Quit");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		createUserMenu();
		break;
	    case "2":
		loginUserMenu();
		break;
	    case "3":
		loginAdminMenu();
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

    /****** USER/MEMBER LOGIN/MENU **********************************************************************************/
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
		isAdmin = false;
		userMenu();
		return;
	    } else {
		System.out.println("Oops, user exists already.");
	    }
	}
    }
    
    public static void loginAdminMenu() {
	while (true) {
	    System.out.print("Logging into Admin account.");
	    System.out.print("\r\nEmail: ");
	    String email = scanner.nextLine();
	    System.out.print("\r\nPassword: ");
	    String password = scanner.nextLine();
	    System.out.println(wipe);

	    int response = sqlProc.loginAdmin(email, password);

	    if (response > -1) {
		currentUserId = response;
		isAdmin = true;
		adminMenu();
		return;
	    } else {
		System.out.println("Oops, user exists already.");
	    }
	}
    }

    public static void userMenu() {
	currentMemberId = sqlProc.getMemberId(currentUserId);
	while (true) {
	    System.out.println("Welcome user!");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] Browse games");
	    System.out.println("[2] Browse consoles");
	    if (currentMemberId == -1) {
		 System.out.println("[3] Sign up for GameGo membership");
	    } else {
		 System.out.println("[3] Your GameGo Membership");
	    }
	    System.out.println("[4] View purchase history");
	    System.out.println("[Q] Log out");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		browseGamesMenu();
		break;
	    case "2":
		browseConsolesMenu();
		break;
	    case "3":
		if (currentMemberId == -1) {
		    createMemberMenu();
		} else {
		    memberMenu();
		}
		break;
	    case "4":
		viewMyTransactions();
		break;
	    case "q":
		System.out.println("You've recently logged out.");
		currentUserId = 0;
		isAdmin = false;
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }

    public static void memberMenu() {
	while (currentMemberId != -1) {
	    System.out.println("Membership Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] Check Points");
	    System.out.println("[2] Use Points");
	    System.out.println("[3] Check Rentals");
	    System.out.println("[4] Return Rentals");
	    System.out.println("[5] End Membership");
	    System.out.println("[Q] Go back");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		System.out.println(displaySqlProc.viewMemberPoints(currentMemberId));
		break;
	    case "2":
		membershipPrizeMenu();
		break;
	    case "3":
		System.out.println(currentMemberId);
		printItemList(displaySqlProc.viewMemberRentals(currentMemberId));
		break;
	    case "4":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    printItemList(displaySqlProc.viewMemberRentals(currentMemberId));
		    System.out.println("Enter the game ID to return the rental.");
		    System.out.println("Or, press [Q] to go back.");
		    String gidString = scanner.nextLine();
		    System.out.println(wipe);
		    if (gidString.toLowerCase().equals("q")) {
			break;
		    } else {
			int gidInt = Integer.parseInt(gidString);
			String result = sqlProc.returnGameRental(currentMemberId, gidInt);
			System.out.println(result);
		    }
		}
		break;
	    case "5":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    System.out.println("Are you sure you want to end your membership?");
		    System.out.println("[Y] Yes | [N] No");
		    System.out.println("Or, press [Q] to go back.");
		    String gidString = scanner.nextLine();
		    System.out.println(wipe);
		    if (!gidString.toLowerCase().equals("y")) {
			break;
		    } else {
			String result = sqlProc.endMembership(currentMemberId);
			System.out.println(result);
			currentMemberId = -1;
		    }
		}
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }

	}
    }
    
    public static void membershipPrizeMenu() {
	while (currentMemberId != -1) {
	    printItemList(displaySqlProc.viewPrizes());
	    
	    System.out.println("Membership Exclusive Prizes");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] Check Points");
	    System.out.println("[R] Redeem Prize");
	    System.out.println("[Q] Go back");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		System.out.println(displaySqlProc.viewMemberPoints(currentMemberId));
		break;
	    case "r":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    printItemList(displaySqlProc.viewPrizes());
		    System.out.println("Enter the prize ID to redeem the prize.");
		    System.out.println("Or, press [Q] to go back.");
		    String pidString = scanner.nextLine();
		    System.out.println(wipe);
		    if (pidString.toLowerCase().equals("q")) {
			break;
		    } else {
			int pidInt = Integer.parseInt(pidString);
			String result = sqlProc.redeemPrize(currentMemberId, pidInt);
			System.out.println(result);
		    }
		}
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }

	}
    }
    
    /****** GAME MENUES **********************************************************************************/
    public static void browseGamesMenu() {
	while (true) {
	    System.out.println("Browse Games Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] View game listing");
	    System.out.println("[2] Search for game");
	    System.out.println("[3] View games on sale");
	    System.out.println("[Q] Go back");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		viewGamesMenu();
		break;
	    case "2":
		searchGamesMenu();
		break;
	    case "3":
		ArrayList<String> gameList = displaySqlProc.viewGamesOnSale();
		printItemList(gameList);
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }

    public static void viewGamesMenu() {
	while (true) {
	    System.out.println("View Games Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("View games sorted by..");
	    System.out.println("[1]title, [2]author, [3]genre, [4]console, [5]rating, [6]price");
	    if (isAdmin == false) {
		System.out.println("[B] Buy game");
		System.out.println("[R] Rent game (GameGo Members Only)");
	    }
	    System.out.println("[Q] Go back");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);
	    ArrayList<String> gameList = new ArrayList<String>();

	    switch (choice.toLowerCase()) {
	    case "1":
		gameList = displaySqlProc.viewGames("title");
		printItemList(gameList);
		break;
	    case "2":
		gameList = displaySqlProc.viewGames("author");
		printItemList(gameList);
		break;
	    case "3":
		gameList = displaySqlProc.viewGames("genre");
		printItemList(gameList);
		break;
	    case "4":
		gameList = displaySqlProc.viewGames("console");
		printItemList(gameList);
		break;
	    case "5":
		gameList = displaySqlProc.viewGames("rating");
		printItemList(gameList);
		break;
	    case "6":
		gameList = displaySqlProc.viewGames("price");
		printItemList(gameList);
		break;
	    case "b":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    gameList = displaySqlProc.viewGames("title");
		    printItemList(gameList);
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
		}
		break;
	    case "r":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    if (currentMemberId == -1) {
			System.out.println("Error: renting is excluse to GameGo members");
		    } else {
			gameList = displaySqlProc.viewGames("title");
			printItemList(gameList);
			System.out.println("Enter the game's ID to rent the game.");
			System.out.println("Or, press [Q] to go back.");
			String gidRentString = scanner.nextLine();
			System.out.println(wipe);
			if (gidRentString.toLowerCase().equals("q")) {
			    break;
			} else {
		   	    int gidInt = Integer.parseInt(gidRentString);
		   	    String result = sqlProc.rentGame(currentMemberId, gidInt);
		   	    System.out.println(result);
			}
		    }
		}
		
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }
    
    public static void searchGamesMenu() {
	while (true) {
	    System.out.println("View Games Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("Search by..");
	    System.out.println("[1]title, [2]author, [3]genre, [4]console");
	    System.out.println("[5]rating (Less Than), [6]rating (Greater Than)");
	    System.out.println("[7]price (Less Than), [8]price (Greater Than)");
	    if (isAdmin == false) {
		System.out.println("[B] Buy game");
		System.out.println("[R] Rent game (GameGo Members Only)");
	    }
	    System.out.println("[Q] Go back");

	    System.out.println("Categorical search:");
	    String choice = scanner.nextLine();
	    System.out.println(wipe);
	    ArrayList<String> gameList = new ArrayList<String>();
	    
	    switch (choice.toLowerCase()) {
	    case "1":
		searchGamesByTitleMenu();
		break;
	    case "2":
		searchGamesByAuthorMenu();
		break;
	    case "3":
		searchGamesByGenreMenu();
		break;
	    case "4":
		searchGamesByConsoleMenu();
		break;
	    case "5":
		searchGamesByRatingMenu("LessThan");
		break;
	    case "6":
		searchGamesByRatingMenu("GreaterThan");
		break;
	    case "7":
		searchGamesByPriceMenu("LessThan");
		break;
	    case "8":
		searchGamesByPriceMenu("GreaterThan");
		break;
	    case "b":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    gameList = displaySqlProc.viewGames("title");
		    printItemList(gameList);
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
		}
		break;
	    case "r":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    if (currentMemberId == -1) {
			System.out.println("Error: renting is excluse to GameGo members");
		    } else {
			gameList = displaySqlProc.viewGames("title");
			printItemList(gameList);
			System.out.println("Enter the game's ID to rent the game.");
			System.out.println("Or, press [Q] to go back.");
			String gidRentString = scanner.nextLine();
			System.out.println(wipe);
			if (gidRentString.toLowerCase().equals("q")) {
			    break;
			} else {
		   	    int gidInt = Integer.parseInt(gidRentString);
		   	    String result = sqlProc.rentGame(currentMemberId, gidInt);
		   	    System.out.println(result);
			}
		    }
		}
		
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }
    
    public static void searchGamesByTitleMenu() {
	while (true) {
	    System.out.println("Enter the game's title or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		ArrayList<String> gameList = displaySqlProc.searchGamesString("title", input);
		printItemList(gameList);
	    }
	}
    }
    
    public static void searchGamesByAuthorMenu() {
	while (true) {
	    System.out.println("Enter the game's author or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		ArrayList<String> gameList = displaySqlProc.searchGamesString("author", input);
		printItemList(gameList);
	    }
	}
    }

    public static void searchGamesByGenreMenu() {
	while (true) {
	    System.out.println("Enter the game's genre or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		ArrayList<String> gameList = displaySqlProc.searchGamesString("genre", input);
		printItemList(gameList);
	    }
	}
    }
    public static void searchGamesByConsoleMenu() {
	while (true) {
	    System.out.println("Enter the game's console or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		ArrayList<String> gameList = displaySqlProc.searchGamesString("console", input);
		printItemList(gameList);
	    }
	}
    }
    public static void searchGamesByRatingMenu(String lessOrGreat) {
	while (true) {
	    System.out.println("Enter a rating of 1-5 or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		int inputInt = Integer.parseInt(input);
		ArrayList<String> gameList = displaySqlProc.searchGamesByRating(lessOrGreat, inputInt);
		printItemList(gameList);
	    }
	}
    }
    public static void searchGamesByPriceMenu(String lessOrGreat) {
	while (true) {
	    System.out.println("Enter a price or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		double inputDouble = Double.parseDouble(input);
		ArrayList<String> gameList = displaySqlProc.searchGamesByPrice(lessOrGreat, inputDouble);
		printItemList(gameList);
	    }
	}
    }
    
    /****** CONSOLE MENU **********************************************************************************/
    public static void browseConsolesMenu() {
	while (true) {
	    System.out.println("Browse Consoles Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] View all consoles by title");
	    System.out.println("[2] View all consoles by price");
	    if (isAdmin == false) {
		System.out.println("[B] Buy");
	    }
	    System.out.println("[Q] Go back");

	    String choice = scanner.nextLine();
	    System.out.println(wipe);
	    
	    ArrayList<String> consoleList = new ArrayList<String>();
	    
	    switch (choice.toLowerCase()) {
	    case "1":
		consoleList = displaySqlProc.viewConsoles("name");
		printItemList(consoleList);
		break;
	    case "2":
		consoleList = displaySqlProc.viewConsoles("price");
		printItemList(consoleList);
		break;
	    case "b":
		if (isAdmin == true) {
		    System.out.println("Invalid input, try again.");
		} else {
		    consoleList = displaySqlProc.viewConsoles("name");
		    printItemList(consoleList);
		    System.out.println("Enter the console's ID to purchase the game.");
		    System.out.println("Or, press [Q] to go back.");
		    String cidString = scanner.nextLine();
		    System.out.println(wipe);
		    if (cidString.toLowerCase().equals("q")) {
			break;
		    } else {
			int cidInt = Integer.parseInt(cidString);
			String result = sqlProc.buyConsole(currentUserId, cidInt);
			System.out.println(result);
		    }
		}
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }
    /****** GAMES/CONSOLE PRINTING HELPERS **********************************************************************************/
    public static void printItemList(ArrayList<String> itemList) {
	for (String listing : itemList) {
	    System.out.println(listing);
	}
    }
    
    public static void viewMyTransactions() {
	displaySqlProc.viewTransactionsById(currentUserId);
    }
    
    /****** ADMIN SPECIFIC MENUES **********************************************************************************/
    
    public static void adminMenu() {
	while (true) {
	    System.out.println("ADMIN Membership Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] Browse games\n[2] Browse consoles\n[3] Modify Inventory");
	    System.out.println("View\n[4]Memberships\n[5]Rentals\n[6]Sales\n[7]Transactions\n[8]Statistics");
	    System.out.println("[9] Promote user to admin");
	    System.out.println("[Q] Log out");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		browseGamesMenu();
		break;
	    case "2":
		browseConsolesMenu();
		break;
	    case "3":
	    modifyInventoryMenu();
		break;
	    case "4":
		admin_membershipsMenu();
		break;
	    case "5":
	    adminRentalMenu();
		break;
	    case "6":
	    adminSalesMenu();
		break;
	    case "7":
		admin_transactionsMenu();
		break;
	    case "8":
		admin_statisticsMenu();
		break;
	    case "9":
		ArrayList<String> list = displaySqlProc.admin_viewMemberships();
		printItemList(list);
		admin_addNewAdmin();
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }

	}
    }
    
    public static void admin_membershipsMenu() {
	while (true) {
	    System.out.println("ADMIN Memberships Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("[1] List all users and GameGo member ID (if signed up)");
	    System.out.println("[2] Search member by email");
	    System.out.println("[Q] Go back");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		ArrayList<String> list = displaySqlProc.admin_viewMemberships();
		printItemList(list);
		break;
	    case "2":
		helper_admin_searchMemberships();
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }

	}
    }
    
    public static void helper_admin_searchMemberships() {
	while (true) {
	    System.out.println("Enter an email or type !q to go back:");
	    String input = scanner.nextLine();
	    System.out.println(wipe);
	    
	    switch (input.toLowerCase()) {
	    case "!q":
		return;
	    default: 
		ArrayList<String> list = displaySqlProc.admin_searchMembershipsByEmail(input);
		printItemList(list);
	    }
	}
    }
    
    public static void admin_addNewAdmin() {
	while (true) {
	    System.out.print("Promoting user to admin.");
	    System.out.print("\r\nUser's UID: ");
	    String uid = scanner.nextLine();
	    System.out.print("\r\nUser's Email: ");
	    String email = scanner.nextLine();
	    System.out.println(wipe);
	    
	    int uidInt = Integer.parseInt(uid);

	    int response = sqlProc.admin_addNewAdmin(uidInt, email);

	    if (response > -1) {
		System.out.println("You've promoted user " + email + " to admin");
		return;
	    } else {
		System.out.println("Oops, something went wrong.");
		return;
	    }

	}
    }
    
    public static void admin_transactionsMenu() {
	while (true) {
	    System.out.println("ADMIN Transactions Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("-- List Transactions by...");
	    System.out.println("\t[1] tID [2] uID [3] gID [4] cID [5] Price [6] Date");
	    System.out.println("-- Search Transactions and Archive by...");
	    System.out.println("\t[7] tID [8] uID [9] gID [10] cID [11] Date");
	    System.out.println("[A] View Archive Transactions Menu");
	    System.out.println("[N] Net Gross of Transactions");
	    System.out.println("[Q] Go back");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		printItemList(displaySqlProc.sortTransactionsBy("TID"));
		break;
	    case "2":
		printItemList(displaySqlProc.sortTransactionsBy("UID"));
		break;
	    case "3":
		printItemList(displaySqlProc.sortTransactionsBy("GID"));
		break;
	    case "4":
		printItemList(displaySqlProc.sortTransactionsBy("CID"));
		break;
	    case "5":
		printItemList(displaySqlProc.sortTransactionsBy("PRICE"));
		break;
	    case "6":
		printItemList(displaySqlProc.sortTransactionsBy("DATE"));
		break;
	    case "7":
		System.out.println("Enter tID\nOr Q to Cancel");
		choice = scanner.nextLine();
		if(choice.toLowerCase().equals("q"))
		    break;
		else{
		    printItemList(displaySqlProc.searchAllTransactionsBy("TID", Integer.parseInt(choice)));
		}
		break;
	    case "8":
		System.out.println("Enter uID\nOr Q to Cancel");
		choice = scanner.nextLine();
		if(choice.toLowerCase().equals("q"))
		    break;
		else{
		    printItemList(displaySqlProc.searchAllTransactionsBy("UID", Integer.parseInt(choice)));
		}
		break;
	    case "9":
		System.out.println("Enter gID\nOr Q to Cancel");
		choice = scanner.nextLine();
		if(choice.toLowerCase().equals("q"))
		    break;
		else{
		    printItemList(displaySqlProc.searchAllTransactionsBy("GID", Integer.parseInt(choice)));
		}
		break;
	    case "10":
		System.out.println("Enter cID\nOr Q to Cancel");
		choice = scanner.nextLine();
		if(choice.toLowerCase().equals("q"))
		    break;
		else{
		    printItemList(displaySqlProc.searchAllTransactionsBy("CID", Integer.parseInt(choice)));
		}
		break;
	    case "11":
		System.out.println("Enter first date (in YYYY-MM-DD format)\nOr Q to Cancel");
		String choice1 = scanner.nextLine();
		if(choice1.toLowerCase().equals("q"))
		    break;
		System.out.println("Enter second date (in YYYY-MM-DD format)\nOr Q to Cancel");
		String choice2 = scanner.nextLine();
		if(choice2.toLowerCase().equals("q"))
		    break;
		printItemList(displaySqlProc.searchAllTransactionsByDate(choice1, choice2));
	   	break;
	    case "a":
		admin_archiveTransactionsMenu();
		break;
	    case "n":
		System.out.println(sqlProc.sumOfTransactions());
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }

	}
    }
    
    public static void admin_archiveTransactionsMenu() {
	while (true) {
	    System.out.println("ADMIN Archive Transactions Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("-- List Archive Transactions by...");
	    System.out.println("\t[1] tID [2] uID [3] gID [4] cID [5] Price [6] Date");
	    System.out.println("[A] Archive All Current Transactions Menu");
	    System.out.println("[B] Archive Current Transactions by Date Range");
	    System.out.println("[Q] Go back");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    switch (choice.toLowerCase()) {
	    case "1":
		printItemList(displaySqlProc.sortArchiveTransactionsBy("TID"));
		break;
	    case "2":
		printItemList(displaySqlProc.sortArchiveTransactionsBy("UID"));
		break;
	    case "3":
		printItemList(displaySqlProc.sortArchiveTransactionsBy("GID"));
		break;
	    case "4":
		printItemList(displaySqlProc.sortArchiveTransactionsBy("CID"));
		break;
	    case "5":
		printItemList(displaySqlProc.sortArchiveTransactionsBy("PRICE"));
		break;
	    case "6":
		printItemList(displaySqlProc.sortArchiveTransactionsBy("DATE"));
		break;
	    case "a":
		System.out.println("Are you sure you want to archive all current transactions?");
		System.out.println("[Y] Yes | [N] No");
		System.out.println("Or, press [Q] to go back.");
		choice = scanner.nextLine();
		System.out.println(wipe);
		if (!choice.toLowerCase().equals("y")) 
			break;
		System.out.println(sqlProc.archiveAllTransactions());
		break;
	    case "b":
		System.out.println("Enter first date (in YYYY-MM-DD format)\nOr [Q] to Cancel");
		String choice1 = scanner.nextLine();
		if(choice1.toLowerCase().equals("q"))
		    break;
		System.out.println("Enter second date (in YYYY-MM-DD format)\nOr [Q] to Cancel");
		String choice2 = scanner.nextLine();
		if(choice2.toLowerCase().equals("q"))
		    break;
		System.out.println("Are you sure you want to archive the transactions?");
		System.out.println("[Y] Yes | [N] No");
		System.out.println("Or, press [Q] to go back.");
		choice = scanner.nextLine();
		System.out.println(wipe);
		if (!choice.toLowerCase().equals("y")) 
			break;
		System.out.println(sqlProc.archiveTransactionsByTwoDates(choice1, choice2));
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }
	}
    }
    
    public static void admin_statisticsMenu() {
	while (true) {
	    System.out.println("ADMIN Statistics Menu");
	    System.out.println("Enter a key value to proceed:");
	    System.out.println("Count games grouped by..");
	    System.out.println("[1]author, [2]genre, [3]console, [4]rating");
	    System.out.println("List games with above average rating sorted by..");
	    System.out.println("[5]genre, [6]console");
	    System.out.println("[7] Sum of revenue between two dates");
	    System.out.println("[Q] Go back");
	    
	    String choice = scanner.nextLine();
	    System.out.println(wipe);

	    ArrayList<String> list = new ArrayList<String>();
	    switch (choice.toLowerCase()) {
	    case "1":
		list = displaySqlProc.countGames("author");
		printItemList(list);
		break;
	    case "2":
		list = displaySqlProc.countGames("genre");
		printItemList(list);
		break;
	    case "3":
		list = displaySqlProc.countGames("console");
		printItemList(list);
		break;
	    case "4":
		list = displaySqlProc.countGames("rating");
		printItemList(list);
		break;
	    case "5":
		list = displaySqlProc.ratingGreaterThanAvg("genre");
		printItemList(list);
		break;
	    case "6":
		list = displaySqlProc.ratingGreaterThanAvg("console");
		printItemList(list);
		break;
	    case "7":
		System.out.print("Enter a date in YYYY-MM-DD format");
		System.out.print("\r\nTransactions after this date: ");
		String date1 = scanner.nextLine();
		System.out.print("\r\nTransactions before this date: ");
		String date2 = scanner.nextLine();
		System.out.println(wipe);
		
		displaySqlProc.sumOfTransactionsByTwoDates(date1, date2);
		break;
	    case "q":
		return;
	    default:
		System.out.println("Invalid input, try again.");
	    }

	}
    }
    
    /******admin rental view**********/
    public static void adminRentalMenu(){
    	while(true){
    		System.out.println("ADMIN Rentals Menu");
    	    System.out.println("Enter a key value to proceed:");
    	    System.out.println("[1] List all Rentals");
    	    System.out.println("[2] Search rentals by email");
    	    //System.out.println("[3] Search rentals by date");
    	    System.out.println("[3] Search rentals by gid");
    	    System.out.println("[4] List all overdue rentals with names");
    	    System.out.println("[Q] Cancel");
    	    
    	    String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	ArrayList<String> rentalsList = displaySqlProc.viewRentals();
    			printItemList(rentalsList);
    			break;
    	    case "2":
    	    	searchRentalsByEmailMenu();
    	    	break;
    	    case "3":
    	    	searchRentalsByGidMenu();
    	    	break;
    	    case "4":
    	    	ArrayList<String> rentalsList2 = displaySqlProc.viewOverdueRentals();
    			printItemList(rentalsList2);
    			break;
    	    case "q":
    			return;
    		    default:
    			System.out.println("Invalid input, try again.");
    		    }
    	    }
    	}

    public static void searchRentalsByEmailMenu(){
    	System.out.println("Enter the renter's email to continue or type !q to go back:");
    	String input = scanner.nextLine();
    	System.out.println(wipe);
    	
    	switch(input.toLowerCase()){
    	case "!q":
    		return;
    		default:
    			ArrayList<String> rentalsList = displaySqlProc.searchRentalsString("email", input);
        		printItemList(rentalsList);
    	}
    }

    
   public static void searchRentalsByGidMenu(){
    	System.out.println("Enter the rental's game id or type !q to go back:");
    	String input = scanner.nextLine();
    	int gidInt = Integer.parseInt(input);
    	System.out.println(wipe);
    	
    	switch(input.toLowerCase()){
    	case "!q":
    		return;
    		default:
    			ArrayList<String> rentalsList = displaySqlProc.searchRentalsByGid("gid", gidInt);
        		printItemList(rentalsList);
    	}
    }
/******admin sales view********/
public static void adminSalesMenu(){
    	while(true){
    		System.out.println("ADMIN Rentals Menu");
    	    System.out.println("Enter a key value to proceed:");
    	    System.out.println("[1] List all games on sale by title");
    	    System.out.println("[2] Add a game on sale");
    	    System.out.println("[3] Update game on sale");
    	    System.out.println("[4] Remove game on sale");
    	    System.out.println("[Q] Cancel");
    	    
    	    String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	ArrayList<String> gameList = displaySqlProc.viewGamesOnSaleByTitle();
    			printItemList(gameList);
    			break;
    	    case "2":
    	    	ArrayList<String> list = displaySqlProc.viewGamesOnSaleByTitle();
    			printItemList(list);
    			admin_addNewSale();
    	    	break;
    	    case "3":
    	    	ArrayList<String> list2 = displaySqlProc.viewGamesOnSaleByTitle();
    			printItemList(list2);
    	    	admin_updateSaleMenu();
    	    	break;
    	    case "4":
    	    	ArrayList<String> rentalsList = displaySqlProc.viewGamesOnSaleByTitle();
    			printItemList(rentalsList);
    			admin_removeSale();
    			break;
    	    case "q":
    			return;
    		    default:
    			System.out.println("Invalid input, try again.");
    		    }
    	    }
    	}


public static void admin_addNewSale() {
    	while (true) {
    	    System.out.print("Adding a new sale.");
    	    System.out.print("\r\nGame's GID: ");
    	    String gid = scanner.nextLine();
    	    System.out.print("\r\nDiscount: ");
    	    String discount = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    int gidInt = Integer.parseInt(gid);
    	    double discountDub = Double.parseDouble(discount);
    	    String response = sqlProc.admin_addNewSale(gidInt, discountDub);
    	    
    	    System.out.println(response);
    	    return;
    	}
}


public static void admin_removeSale(){
    	while(true){
    		System.out.print("Removing a sale.");
    	    System.out.print("\r\nGame's GID: ");
    	    String gid = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    int gidInt = Integer.parseInt(gid);
    	    String response = sqlProc.admin_removeSale(gidInt);
    	    
    	    System.out.println(response);
    	    return;
    	}
    }

public static void admin_updateSaleMenu(){
    	while(true){
    		System.out.println("Update Sales Menu");
    	    System.out.print("\r\nGame's GID: ");
    	    String gid = scanner.nextLine();
    		
    		System.out.println("Enter a key value to proceed:");
    	    System.out.println("Choose an attribute to update");
    	    System.out.println("[1]gid, [2]discount, [3]originalPrice, [Q]Go back");
    	    String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	admin_updateSaleGid(gid);
    	    	return;
    	    case "2":
    	    	admin_updateSaleDiscount(gid);
    	    	return;
    	    case "3":
    	    	admin_updateSaleOriginalPrice(gid);
    	    	return;
    	    case "q":
    	    	return;
    	    	default:
    	    		System.out.println("Invalid input, try again.");
    	    }
    	}
    }
    
    public static void admin_updateSaleGid(String gid){
    	while(true){
    		System.out.println("Enter the game's new gid or type !q to go back:");
    	    String input = scanner.nextLine();
    	    int newGid = Integer.parseInt(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.admin_updateSaleGid(gidInt, newGid);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void admin_updateSaleDiscount(String gid){
    	while(true){
    		System.out.println("Enter the game's new discount or type !q to go back:");
    	    String input = scanner.nextLine();
    	    double newDiscount = Double.parseDouble(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.admin_updateSaleDiscount(gidInt, newDiscount);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void admin_updateSaleOriginalPrice(String gid){
    	while(true){
    		System.out.println("Enter the game's new originalPrice or type !q to go back:");
    	    String input = scanner.nextLine();
    	    double newOriginalPrice = Double.parseDouble(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.admin_updateSaleOriginalPrice(gidInt, newOriginalPrice);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    /*******modify inventory******/
    public static void modifyInventoryMenu(){
    	while(true){
    		System.out.println("Modify Inventory Menu");
    		System.out.println("Enter a key value to proceed:");
    		System.out.println("[1] Modify game listing, [2] Modify console listing, [Q] Go back");
    		String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	modifyGameListing();
    	    	break;
    	    case "2":
    	    	modifyConsoleListing();
    	    	break;
    	    case "q":
    	    	return;
    	    	default:
    	    		System.out.println("Invalid input, try again.");
    	    }
    	}
    }
    
    public static void modifyGameListing(){
    	while(true){
    		System.out.println("Modify Game Menu");
    		System.out.println("Enter a key value to proceed:");
    		System.out.println("[1] Insert a new game, [2] Update existing game, [3] Delete existing game, [Q] Go  back");
    		String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	inventoryInsertGame();
    	    	break;
    	    case "2":
    	    	ArrayList<String> gameList = new ArrayList<String>();
    	    	gameList = displaySqlProc.viewGames("title");
    			printItemList(gameList);
    	    	inventoryUpdateGame();
    	    	break;
    	    case "3":
    	    	ArrayList<String> gameList2 = new ArrayList<String>();
    	    	gameList2 = displaySqlProc.viewGames("title");
    			printItemList(gameList2);
    	    	inventoryDeleteGame();
    	    	break;
    	    case "q":
    	    	return;
    	    	default:
    	    		System.out.println("Invalid input, try again.");
    	    }
    	}
    }
    
    public static void inventoryInsertGame(){
 	    System.out.println("Enter the new game's title: ");
 	    String title = scanner.nextLine();
 	    System.out.println("Enter the new game's author: ");
	    String author = scanner.nextLine();
	    System.out.println("Enter the new game's genre: ");
	    String genre = scanner.nextLine();
	    System.out.println("Enter the new game's console type: ");
 	    String console_type = scanner.nextLine();
 	    System.out.println("Enter the new game's rating: ");
 	    String rating = scanner.nextLine();
 	    System.out.println("Enter the new game's price: ");
	    String price = scanner.nextLine();
	    System.out.println("Enter the new game's stock: ");
	    String stock = scanner.nextLine();
 	    System.out.println(wipe);
 	    
 	    int ratingInt = Integer.parseInt(rating);
 	    double priceDub = Double.parseDouble(price);
 	    int stockInt = Integer.parseInt(stock);

	    double response = sqlProc.inventoryInsertGame(title, author, genre, console_type, ratingInt, priceDub, stockInt);
	    
	    if(response > -1){
	    	System.out.println("You've successfully added " + title + " to the game listings.");
	    	return;
	    } else {
	    	System.out.println("Oops, something went wrong.");
	    	return;
	    }
    }
    
    public static void inventoryDeleteGame(){
    	System.out.println("Enter the game's gid you wish to remove: ");
 	    String gid = scanner.nextLine();
 	    System.out.println("Enter that game's title: ");
 	    String title = scanner.nextLine();
 	    System.out.println(wipe);
 	    
 	    int gidInt = Integer.parseInt(gid);
	    String response = sqlProc.inventoryDeleteGame(gidInt, title);
	    System.out.println(response);
    }
    
    public static void inventoryUpdateGame(){
    	while(true){
    		System.out.println("Update Games Menu");
    	    System.out.println("Game's GID: ");
    	    String gid = scanner.nextLine();
    		
    		System.out.println("Enter a key value to proceed:");
    	    System.out.println("Choose an attribute to update");
    	    System.out.println("[1] Title, [2] Author, [3] Genre, [4] Console type");
    	    System.out.println("[5] Rating, [6] Price, [7] Stock");
    	    System.out.println("[Q] Go back");
    	    String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	updateGameTitle(gid);
    	    	return;
    	    case "2":
    	    	updateGameAuthor(gid);
    	    	return;
    	    case "3":
    	    	updateGameGenre(gid);
    	    	return;
    	    case "4":
    	    	updateGameConsoleType(gid);
    	    	return;
    	    case "5":
    	    	updateGameRating(gid);
    	    	return;
    	    case "6":
    	    	updateGamePrice(gid);
    	    	return;
    	    case "7":
    	    	updateGameStock(gid);
    	    	return;
    	    case "q":
    	    	return;
    	    	default:
    	    		System.out.println("Invalid input, try again.");
    	    }
    	}
    }
    
    public static void modifyConsoleListing(){
    	while(true){
    		System.out.println("Modify Console Menu");
    		System.out.println("Enter a key value to proceed:");
    		System.out.println("[1] Insert a new console, [2] Update existing console, [3] Delete existing console, [Q] Go  back");
    		String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	inventoryInsertConsole();
    	    	return;
    	    case "2":
    	    	inventoryUpdateConsole();
    	    	return;
    	    case "3":
    	    	inventoryDeleteConsole();
    	    	return;
    	    case "q":
    	    	return;
    	    	default:
    	    		System.out.println("Invalid input, try again.");
    	    }
    	}
    }
    
    public static void inventoryInsertConsole(){
 	    System.out.println("Enter the new console's name: ");
 	    String name = scanner.nextLine();
 	    System.out.println("Enter the new console's price: ");
	    String price = scanner.nextLine();
	    System.out.println("Enter the new console's stock: ");
	    String stock = scanner.nextLine();
 	    System.out.println(wipe);
 	    
 	    double priceDub = Double.parseDouble(price);
 	    int stockInt = Integer.parseInt(stock);

	    double response = sqlProc.inventoryInsertConsole(name, priceDub, stockInt);
	    
	    if(response > -1){
	    	System.out.println("You've successfully added " + name + " to the console listings.");
	    	return;
	    } else {
	    	System.out.println("Oops, something went wrong.");
	    	return;
	    }
    }
    
    public static void inventoryDeleteConsole(){
 	    System.out.println("Enter the console's name you wish to remove: ");
 	    String name = scanner.nextLine();
 	    System.out.println(wipe);

	    String response = sqlProc.inventoryDeleteConsole(name);
	    System.out.println(response);
    }
    
    public static void inventoryUpdateConsole(){
    	while(true){
    		System.out.println("Update Consoles Menu");
    	    System.out.println("Console's name: ");
    	    String name = scanner.nextLine();
    		
    		System.out.println("Enter a key value to proceed:");
    	    System.out.println("Choose an attribute to update");
    	    System.out.println("[1] Name, [2] Price, [3] Stock");
    	    System.out.println("[Q] Go back");
    	    String choice = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(choice.toLowerCase()){
    	    case "1":
    	    	updateConsoleName(name);
    	    	return;
    	    case "2":
    	    	updateConsolePrice(name);
    	    	return;
    	    case "3":
    	    	updateConsoleStock(name);
    	    	return;
    	    case "q":
    	    	return;
    	    	default:
    	    		System.out.println("Invalid input, try again.");
    	    }
    	}
    }
    
    
    public static void updateGameTitle(String gid){
    	while(true){
    		System.out.println("Type the game's new title or type !q to go back:");
    	    String input = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGameTitle(gidInt, input);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateGameAuthor(String gid){
    	while(true){
    		System.out.println("Type the game's new author or type !q to go back:");
    	    String input = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGameAuthor(gidInt, input);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateGameGenre(String gid){
    	while(true){
    		System.out.println("Type the game's new genre or type !q to go back:");
    	    String input = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGameGenre(gidInt, input);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateGameConsoleType(String gid){
    	while(true){
    		System.out.println("Type the game's new console type or type !q to go back:");
    	    String input = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGameConsoleType(gidInt, input);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateGameRating(String gid){
    	while(true){
    		System.out.println("Type the game's new rating or type !q to go back:");
    	    String input = scanner.nextLine();
    	    int newRating = Integer.parseInt(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGameRating(gidInt, newRating);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateGamePrice(String gid){
    	while(true){
    		System.out.println("Type the game's new price or type !q to go back:");
    	    String input = scanner.nextLine();
    	    double newPrice = Double.parseDouble(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGamePrice(gidInt, newPrice);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateGameStock(String gid){
    	while(true){
    		System.out.println("Type the game's new stock or type !q to go back:");
    	    String input = scanner.nextLine();
    	    int newStock = Integer.parseInt(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	int gidInt = Integer.parseInt(gid);
    	    	String response = sqlProc.updateGameStock(gidInt, newStock);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    
    public static void updateConsoleName(String name){
    	while(true){
    		System.out.println("Type the console's new name or type !q to go back:");
    	    String input = scanner.nextLine();
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	String response = sqlProc.updateConsoleName(name, input);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateConsolePrice(String name){
    	while(true){
    		System.out.println("Type the console's new price or type !q to go back:");
    	    String input = scanner.nextLine();
    	    double newPrice = Double.parseDouble(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	String response = sqlProc.updateConsolePrice(name, newPrice);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
    
    public static void updateConsoleStock(String name){
    	while(true){
    		System.out.println("Type the console's new stock or type !q to go back:");
    	    String input = scanner.nextLine();
    	    int newStock = Integer.parseInt(input);
    	    System.out.println(wipe);
    	    
    	    switch(input.toLowerCase()){
    	    case "!q":
    	    	return;
    	    default:
    	    	String response = sqlProc.updateConsoleStock(name, newStock);
    	    	System.out.println(response);
    	    	return;
    	    }
    	}
    }
}