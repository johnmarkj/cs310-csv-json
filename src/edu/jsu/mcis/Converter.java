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
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            JSONObject jsonObject = new JSONObject();
            
            // INSERT YOUR CODE HERE
            
            JSONArray rHeaders = new JSONArray();
            JSONArray cHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            String[] cols;
            cols = iterator.next();
            for(String field : cols){
                cHeaders.add(field);
            }
            jsonObject.put("colHeaders", cHeaders);
             
        }
        
        catch(IOException e) {
            System.err.println(e.toString());
        } 
        
        //return results.trim();
        return json.toString();
    }
    
    public static String jsonToCsv(String jsonString) {
        
        //String results = "";
        StringWriter writer = new StringWriter();
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jobject = (JSONObject)parser.parse(jsonString);
            JSONArray row = (JSONArray) jobject.get("rowHeaders");
            JSONArray col = (JSONArray) jobject.get("colHeaders");
            JSONArray data = (JSONArray) jobject.get("data");
            
            //String array for OpenCSV
            
            String[] csvCol = new String[col.size()];
            String[] csvRow = new String[row.size()];
            String[] csvData = new String[data.size()];
            String[] rowData;
            
            //Copy the column headers
            
            for(int i = 0; i < col.size(); i++){
                csvCol[i] = col.get(i) + "";
            }
            
            //Copy the row headers and the row data
            
            for(int i = 0; i < row.size(); i++){
                csvRow[i] = row.get(i) + "";
                csvData[i] = data.get(i) + "";
            }
            
            
            //StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',' , '"' , "\n" );
            csvWriter.writeNext(csvCol);
            
            //Write column headers
            
            for(int i = 0; i < csvData.length; i++){
                //Replace square brackets from the row
                csvData[i] = csvData[i].replace("[","");
                csvData[i] = csvData[i].replace("]","");
            
            
                //Split data into elements
            
                String[] elements = csvData[i].split(",");
                
                //Create a container for row data
                
                rowData = new String[elements.length + 1];
                
                //Copy the row header into the first element of rowData
                
                rowData[0] = csvRow[i];
                
                //Copy the rest of rowData
                
                for(int j = 0; j < elements.length; j++){
                    rowData[j+1] = elements[j];
                }
                
                //Write new row
                
                
                csvWriter.writeNext(rowData);
                
            }
        }
        
        catch(ParseException e){
            System.err.println(e.toString());
        }
        
        return writer.toString();
        
    }
	
}