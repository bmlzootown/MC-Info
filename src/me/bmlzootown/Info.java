package me.bmlzootown;

import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;

public class Info {
	
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("Player Information Query by bmlzootown");
		System.out.println("- Uses Official Mojang API");
		System.out.println("- Output includes UUID, premium/migration status, name history, skin, and cape.");
		System.out.println("** Requests may be limited by Mojang's API");
		System.out.println("** In other words... A 429 Error is all on Mojang! =D ");
		System.out.println("** Just restart the application and wait a bit longer between requests.");
		System.out.println(" ");
		
		while (true) {
			System.out.println("Enter player username:");
			System.out.println(" ");
			String user = in.nextLine();
			//in.close();
			String info = Methods.getUser(user);
			if (info.contains("{")) {
				String UUID = Methods.getUUID(info);
				String Migrated = Methods.getMigration(info);
				String Premium = Methods.getPremium(info);
				String userData = Methods.getUserData(UUID);
				JSONArray getNames = Methods.getNames(UUID);
				ArrayList<String> nombre = Methods.nombre(getNames);
				ArrayList<String> change = Methods.change(getNames);
				ArrayList<String> datez = Methods.convertTime(change);
				Methods.getSkin(userData, user);
				Methods.getCape(userData, user);
				
				System.out.println(" ");
				System.out.println("Name: " + user);
				System.out.println("UUID: " + UUID);
				System.out.println("Premium: " + Premium);
				System.out.println("Migrated: " + Migrated);
				System.out.println("Skin/Cape saved to current directory.");
				System.out.println("--Past Names-- ");
				for (int i = 0; i < getNames.length(); i ++) {
					System.out.println( nombre.get(i) + " - " + datez.get(i) );
				}
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("Type 'exit' to quit or 'new' to search another username.");
				System.out.println(" ");
				String action = in.nextLine();
				//in.close();
				if (action.equalsIgnoreCase("exit")) {
					in.close();
					break;
				} else if (action.equalsIgnoreCase("new")) {
					Methods.clearScreen();
					System.out.println(" ");
					System.out.println(" ");
					System.out.println(" ");
					System.out.println(" ");
					continue;
				} else {
					Methods.clearScreen();
					System.out.println(" ");
					System.out.println(" ");
					System.out.println("You know... Trying to break me isn't nice. >_>");
					System.out.println(" ");
					System.out.println(" ");
					continue;
				}
			} else {
				System.out.println(" ");
				System.out.println(" ");
				System.out.println("** The user you are looking for does not exist. **");
				System.out.println("Type 'exit' to quit or 'new' to search another username.");
				System.out.println(" ");
				String action = in.nextLine();
				//in.close();
				if (action.equalsIgnoreCase("exit")) {
					in.close();
					break;
				} else if (action.equalsIgnoreCase("new")) {
					Methods.clearScreen();
					System.out.println(" ");
					System.out.println(" ");
					System.out.println(" ");
					System.out.println(" ");
					continue;
				} else {
					Methods.clearScreen();
					System.out.println(" ");
					System.out.println(" ");
					System.out.println("You know... Trying to break me isn't nice. >_>");
					System.out.println(" ");
					System.out.println(" ");
					continue;
				}
			}

	   } 
	}

}
