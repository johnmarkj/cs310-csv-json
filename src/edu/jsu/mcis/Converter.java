package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and other whitespace
        have been added for clarity).  Note the curly braces, square brackets, and double-quotes!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        //Creates a StringBuilder for json
        StringBuilder json = new StringBuilder();
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> csv = reader.readAll();
            Iterator<String[]> iterator = csv.iterator();
            
            JSONObject jsonObject = new JSONObject();
            
            // INSERT YOUR CODE HERE
            
            JSONArray rowHeaders = new JSONArray();
            JSONArray colHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            String[] rows;
            
            //First array from CSV column data; add elements to the colHeader array
            colHeaders.addAll(Arrays.asList(iterator.next()));
            
            //Loop through the data from the rows in CSV
            
            while(iterator.hasNext()){
                //Container for rows
                
                JSONArray row = new JSONArray();
                
                //Get the next array from CSV
                
                rows = iterator.next();
                
                //Get the first element from CSV and and to rowHeader
                
                rowHeaders.add(rows[0]);
                
                //Fill out the rest of the rows
                
                for(int i = 1; i < rows.length; i++){
                    row.add(rows[i]);
                }
                
                //add row to data
                
                data.add(row);
            }
            
            //************* Construct JSON String ****************
            
            // Add the row and column headers
            
            json.append("{\n   \"colHeaders\": ").append(colHeaders.toString());
            json.append("{\n   \"rowHeaders\": ").append(rowHeaders.toString()).append(", \n");
            
            //Split data
            
            rows = data.toString().split("],");
            
            //Add data to the rows
            
            json.append("  \"data\": ");
            
            for(int i = 0; i < rows.length; i++){
                String dat = rows[i];
                dat = dat.replace("\"","");       //delete the double quotes
                dat = dat.replace("]]", "]");     //replace last square brackets
                
                json.append(dat);     //append the rows
                
                //if last row, close the row and begin a new row
                
                if((i % rows.length) != (rows.length - 1)){
                    json.append("], \n        ");
                }
            }
            //close JSON String
            
            json.append("\n    ]\n}");
        }
        
        catch(IOException e) { return e.toString(); }
        
        //return results.trim();
        return json.toString();
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
        }
        
        catch(ParseException e) { return e.toString(); }
        
        return results.trim();
        
    }
	
}