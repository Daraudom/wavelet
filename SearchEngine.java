import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler{
    // Initialize a new array list to store the list of strings
    ArrayList<String> listofStrings = new ArrayList<>();

    @Override
    public String handleRequest(URI url) {
        // Home Page
        if (url.getPath().equals("/")) {
            return ("Welcome to the Search Engine Server.\n Begin by adding a list of strings or search for some!");

        } else {
            System.out.println("Path: " + url.getPath());
            // Adding Method
            if (url.getPath().contains("/add") && url.getQuery().contains("=")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s" )) {
                    listofStrings.add(parameters[1]);
                    return String.format("String: %s has been added to the database!\n" + "[ " + String.join(", ", listofStrings) + " ]", parameters[1]);
                } else {
                    return ("Invalid Query! Please type it as /add?s=<A String>!");
                }
            // Searching Method
            } else if (url.getPath().contains("/search") && url.getQuery().contains("=")){
                String[] queryParaStrings = url.getQuery().split("=");
                ArrayList<String> results = new ArrayList<String>();
                for (int i = 0; i < listofStrings.size(); i++) {
                    if (listofStrings.get(i).contains(queryParaStrings[1])){
                        results.add(listofStrings.get(i));
                    } 
                }
                return "Tinkering.... WOWZAAA here are they: " + String.join(", ", results);
            // Reseting the List
            } else if (url.getPath().contains("/reset")){
                ArrayList<String> newListofStrings = new ArrayList<>();
                listofStrings = newListofStrings;
                return "Database Reset. Waiting for new inputs...";
            }
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
