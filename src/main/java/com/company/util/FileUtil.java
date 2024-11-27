/**
 * 
 */
package com.company.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.http.Part;

/**
 * @author 04-14
 *
 */
public class FileUtil {
	static final String SAVE_DIRECTORY = "d:\\kbc_data\\uploads";
	
	
	// Util Sugar
	public static String extractFileName(Part part) {
		String[] rawFilenames = part
					.getHeader("content-disposition")
					.split("filename=");
		return rawFilenames[1].trim().replace("\"", "");
	}
	public static String fileFormat(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}
	
	// Business logic
	public static String generateFileName(String originalFileName) {
		String format = fileFormat(originalFileName);
		String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
				+ String.format("-%09d", LocalTime.now().toNanoOfDay());
		return now + format;
	}

}
