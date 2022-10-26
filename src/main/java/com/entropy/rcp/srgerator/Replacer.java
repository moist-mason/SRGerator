package com.entropy.rcp.srgerator;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.Scanner;

public class Replacer {
    public static CSVReader csvReader;
    private static String[] nextLine;
    public static int rowNumber = 0;

    public static void replacer(File file, FileReader csvFile) throws IOException, CsvValidationException {
        String srgName = "";
        String mcpName = "";
        csvReader = new CSVReaderBuilder(csvFile).withSkipLines(2).build();
        while((nextLine = csvReader.readNext()) != null) {
            rowNumber++;
            for (int i = 0; i < 1; i++) {
                for (int j = 0; j < nextLine.length; j++) {
                    srgName = nextLine[2]; // 3rd column of fields.csv (SRG)
                    mcpName = nextLine[3]; // 4th column of fields.csv (MCP)
                    applyToFile(file, srgName, mcpName);
                }
                System.out.println("Replacing name " + srgName + " with " + mcpName + " in " + file.getName() + "...");
            }
        }
    }

    public static void applyToFile(File file, String srgName, String mcpName) throws IOException {
        Scanner scanner = new Scanner(file);
        StringBuffer buffer = new StringBuffer();
        while(scanner.hasNextLine()) {
            buffer.append(scanner.nextLine() + System.lineSeparator());
        }
        String contents = buffer.toString();
        scanner.close();
        contents = contents.replaceAll(srgName, mcpName);
        FileWriter rgsWriter = new FileWriter(file);
        rgsWriter.append(contents);
        rgsWriter.flush();
    }
}
