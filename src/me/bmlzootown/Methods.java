package me.bmlzootown;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Methods extends Info{
	
	public static String getUser(String username) {
		StringBuilder stuffz = new StringBuilder();
		URL url;
		
		try {
			String output;
			String a = "https://api.mojang.com/users/profiles/minecraft/" + username;
			url = new URL(a);
			URLConnection conn = url.openConnection();
			InputStreamReader inputReader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(inputReader);
			
			while ((output = br.readLine()) != null) {
				stuffz.append(output);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stuffz.toString();
	}
	
	public static String getUserData(String UUID) throws JSONException {
		StringBuilder stuffz = new StringBuilder();
		URL url;
		
		try {
			String output;
			String a = "https://sessionserver.mojang.com/session/minecraft/profile/" + UUID;
			url = new URL(a);
			URLConnection conn = url.openConnection();
			InputStreamReader inputReader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(inputReader);
			
			while ((output = br.readLine()) != null) {
				stuffz.append(output);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSONObject obj = new JSONObject(stuffz.toString());
		String data = obj.getString("properties");
		data = data.replace("[", "");
		data = data.replace("]", "");
		JSONObject obj2 = new JSONObject(data);
		String data2 = obj2.getString("value");
		
		String information = decode(data2);
		JSONObject skin = new JSONObject(information);
		String skin2 = skin.getString("textures");
		
		return skin2;
	}
	
	public static JSONArray getNames(String UUID) throws JSONException {
		StringBuilder names = new StringBuilder();
		URL url;
		
		try {
			String output;
			String a = "https://api.mojang.com/user/profiles/" + UUID + "/names";
			url = new URL(a);
			URLConnection conn = url.openConnection();
			InputStreamReader inputReader = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(inputReader);
			
			while ((output = br.readLine()) != null) {
				names.append(output);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String namez = names.toString();
		JSONArray array = new JSONArray(namez);
		
		return array;
	}
	
	public static ArrayList<String> nombre(JSONArray array) throws JSONException {
		ArrayList<String> nombre = new ArrayList<String>();
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject namez = new JSONObject(array.getString(i).toString());
			nombre.add(i, namez.getString("name"));
		}
		
		return nombre;
	}
	
	public static ArrayList<String> change(JSONArray array) throws JSONException {
		ArrayList<String> change = new ArrayList<String>();
		
		for (int i = 0; i < array.length(); i++) {
			JSONObject stuff = new JSONObject(array.getString(i).toString());
			if (stuff.has("changedToAt")) {
				change.add(i, "Changed at: " + stuff.getString("changedToAt"));
			} else {
				change.add(i, "Original");
			}
		}
		
		return change;
	}
	
	public static String getUUID(String info) throws Exception {
		String UUID;
		JSONObject obj = new JSONObject(info);
		if (obj.has("id")) {
			UUID = obj.getString("id");
		} else {
			UUID = null;
		}
		return UUID;
	}
	
	public static String getMigration(String info) throws Exception {
		String output;
		JSONObject obj = new JSONObject(info);
		if (obj.has("legacy")) {
			output = "false";
		} else {
			output = "true";
		}
		return output;
	}
	
	public static String getPremium(String info) throws Exception {
		String output;
		JSONObject obj = new JSONObject(info);
		if (obj.has("demo")) {
			output = "false";
		} else {
			output = "true";
		}
		return output;
	}
	
	public static String decode(String base64) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(base64);
		String value = new String(decodedByteArray);
		return value;
	}
	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
	
	public static String getDate() {
		DateFormat dfm = new SimpleDateFormat("yyyy_MM_dd");
		Date date = new Date();
		String today = dfm.format(date);
		
		return today;
	}
	
	public static ArrayList<String> convertTime(ArrayList<String> unixTime) {
		ArrayList<String> datez = new ArrayList<String>();
		for (int i = 0; i < unixTime.size(); i++) {
			if (unixTime.get(i) != "Original") {
				long l = Long.parseLong(unixTime.get(i).substring(12));
				Date date = new Date(l);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ss HH:mm:ss z");
				datez.add(sdf.format(date));
			} else {
				datez.add(unixTime.get(i));
			}
		}
	
		return datez;
	}
	
	public static void getSkin(String userData, String username) throws JSONException, IOException {
		JSONObject skin = new JSONObject(userData);
		if (skin.has("SKIN")) {
			String skinz = skin.getString("SKIN");
			JSONObject skin2 = new JSONObject(skinz);
			String url = skin2.getString("url");
			File dir = new File("skins/");
			if (!dir.exists()) {
				dir.mkdir();
			}
			saveImage(url, "skins/" + username + "-" + getDate() + ".skin.png");
		}
	}
	
	public static void getCape(String userData, String username) throws JSONException, IOException {
		JSONObject cape = new JSONObject(userData);
		if (cape.has("CAPE")) {
			String capez = cape.getString("CAPE");
			JSONObject cape2 = new JSONObject(capez);
			String url = cape2.getString("url");
			File dir = new File("capes/");
			if (!dir.exists()) {
				dir.mkdir();
			}
			saveImage(url, "capes/" + username + "-" + getDate() + ".cape.png");
		}
	}
	
	public static void clearScreen() throws IOException {
		String OS = System.getProperty("os.name");
		if (OS.contains("Windows")) {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		} else {
			Runtime.getRuntime().exec("clear");
		}
	}
	
	public static void pressAnyKeyToContinue()
	 { 
	        System.out.println("Press any key to continue...");
	        try
	        {
	            System.in.read();
	        }  
	        catch(Exception e)
	        {}  
	 }
}
