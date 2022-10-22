

package com.entropy.internal;

import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SRGerator {
    public static final String MC_VERSION = "a1.2.6";
    public static final File rgsScript = new File("src\\main\\resources\\" + MC_VERSION + ".rgs");
    public static final File patchFile = new File("src\\main\\resources\\" + "minecraft.patch");
    public static final FileReader fields;
    public static final FileReader methods;

    static {
        try {
            fields = new FileReader("src\\main\\resources\\csv\\fields.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    static {
        try {
            methods = new FileReader("src\\main\\resources\\csv\\methods.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws CsvValidationException, IOException {
        if (!rgsScript.exists()) {
            generateRGSCopy();
        }
        fixFiles(rgsScript);

        if(!patchFile.exists()) {
            generatePatchCopy();
        }
        fixFiles(patchFile);
    }

    public static void fixFiles(File script) throws CsvValidationException, IOException {
        SRGReplacer.replacer(script, fields);
        SRGReplacer.replacer(script, methods);
    }

    public static void generateRGSCopy() throws IOException {
        File input = new File("src\\main\\resources\\" + MC_VERSION + "-base.rgs");
        Files.copy(input.getAbsoluteFile().toPath(), rgsScript.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void generatePatchCopy() throws IOException {
        File input = new File("src\\main\\resources\\" + "minecraft-base.patch");
        Files.copy(input.getAbsoluteFile().toPath(), patchFile.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
