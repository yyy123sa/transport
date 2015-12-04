package com.realtimestudio.transport.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVUtils {
	public static List<Map<String, String>> getRecordsFromFile(Reader reader, String[] header_mapping, boolean skipHeader) throws FileNotFoundException, IOException{
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(header_mapping).withSkipHeaderRecord(skipHeader);
		List<Map<String,String>> result = new ArrayList<>();

		try(BufferedReader in = new BufferedReader(reader);
				CSVParser parser = new CSVParser(in, csvFileFormat)){
			
			for(CSVRecord record : parser){
				result.add(record.toMap());
			}			
		}
		return result;
	}
	
	public static List<Map<String, String>> getRecordsFromFile(File file, String[] header_mapping, boolean skipHeader) throws FileNotFoundException, IOException{
		return getRecordsFromFile(new FileReader(file), header_mapping, skipHeader);
	}

}
