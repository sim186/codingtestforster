package forster.movieparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

//Gson library from google to manage in a simple way the json format
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/*
 * This class contains the parser to parse the JSON file and grouping it.
 * The interesting element is stored like this
 * Movie
 * 	- id
 * 	- year
 * 	- fsk level
 * 		- SFK16
 * Movie
 *  - id
 * 	- year
 * 	- fsk level
 * 		- SFK18
 * Movie
 * 	- id
 * 	- year
 * 	- fsk level
 * 		- FSF0
 * etc...
 * 
 * So we need to group for the element contained in the fsk level
 * */
public class MovieParser {
	
	//This method parse the JSON file and create the new JSON file as requested, 
	//it can throw exception on JSON and on the file creation
	public void ParseJsonMovie(String FilePath) throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		
		//Instantiate a new gson object in order to read the input file
		Gson gson = new Gson();
		//This statement creates a list based on the MovieData class fields
        List<MovieData> ListfromMovie = gson.fromJson(new BufferedReader( //create a BufferReader for the JSON
        										new FileReader("json/assets.json")), //Read the input JSON
        											new TypeToken<List<MovieData>>(){}.getType()); //use typetoken to store the type of the objects
        
        //Group the map using the groupingBy collector
        Map<String, List<MovieData>> groupedMap = ListfromMovie.stream().collect(Collectors.
        														//get the first element SFK from the JSON array Fsk_level_list_facet  
        														 groupingBy(post -> post.getFsk_level_list_facet()[0]));
        

        //iterate over the map in order to delete the Fsk string array since
        //in the output only id, year and title are necessary
        for(Entry<String, List<MovieData>> e : groupedMap.entrySet()){
        	   for(MovieData e1 : e.getValue())		  
        		  e1.setFsk_level_list_facet(null);
        	}
        
        Gson gsonout = new Gson(); //create a gson element to write the result in the output
        String jsonStr = gsonout.toJson(groupedMap); //create a json string from the map using the function ToJson
        try {
        	   //write converted json data to a file named "filtered-movie-by-sfk.json"
        	   FileWriter writer = new FileWriter("json/filtered-movie-by-sfk.json");
        	   writer.write(jsonStr);
        	   writer.close();
        	  
        	  } catch (IOException e) {
        	   e.printStackTrace();
        	  }
	}
    
	public static void main(String args[] ) throws Exception {

		String FilePath = "json/assets.json"; //input JSON file name
		MovieParser mp = new MovieParser();
		mp.ParseJsonMovie(FilePath); //call the parser

	}   

}
