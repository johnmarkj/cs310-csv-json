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
            JSONArray rowData;
            String[] rows;
            rows = iterator.next();
            for(String field : rows){
                cHeaders.add(field);
            }
            jsonObject.put("colHeaders", cHeaders);
            
            while(iterator.hasNext()){
                rows = iterator.next();
                rowData = new JSONArray();
                rHeaders.add(rows[0]);
                for(int i = 1; i < rows.length; i++){
                    rowData.add(Integer.parseInt(rows[i]));
                }
                data.add(rowData);
            }
            jsonObject.put("data", data);
            jsonObject.put("rowHeaders", rHeaders);
            results = JSONValue.toJSONString(jsonObject);  
        }
        
        catch(IOException e) {
            System.err.println(e.toString());
        } 
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
        String[] csvData;
        String[] rows;
        
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            JSONArray rowHeaders = (JSONArray) jsonObject.get("rowHeaders");
            JSONArray colHeaders = (JSONArray) jsonObject.get("colHeaders");
            JSONArray data = (JSONArray) jsonObject.get("data");
            JSONArray rowData;
            
            csvData = new String[colHeaders.size()];
            
            for(int i = 0; i < colHeaders.size(); i++){
                csvData[i] = (String) colHeaders.get(i);
            }
           
            csvWriter.writeNext(csvData);
            
            
            for(int i = 0; i < rowHeaders.size(); i++){
                rows = new String[colHeaders.size()];
                rows[0] = (String) rowHeaders.get(i);
                rowData = (JSONArray) data.get(i);
                for(int j = 0; j < rowData.size(); j++){
                    rows[j+1] = Long.toString((long)rowData.get(j));
                    
                }
                csvWriter.writeNext(rows);
            }
                
        }
        
        catch(ParseException e){
            System.err.println(e.toString());
        }
        
        return writer.toString().trim();
        
    }
	
}