package de.zekro.log4forge.logging.sinks;

import de.zekro.log4forge.logging.Entry;
import de.zekro.log4forge.logging.ISink;
import net.minecraftforge.common.config.Configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Abstract implementation of ISink which
 * writes to a file.
 */
public abstract class FileSink implements ISink {

    /**
     * The name of the sink used in the config as
     * category name.
     */
    protected String sinkName = "genericFileSink";
    /**
     * The description of the config category.
     */
    protected String sinkDescription = "A generic file sink.";

    private String fileName;
    private boolean isEnabled = false;
    private long maxEntries = 0;

    @Override
    public void setMaxEntries(long max) {
        this.maxEntries = max;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public void getConfig(Configuration config) {
        config.addCustomCategoryComment(
                this.sinkName, this.sinkDescription);

        this.isEnabled = config.getBoolean(
                "isEnabled", this.sinkName,
                false,
                "Whether or not the sink is enabled.");

        this.fileName = config.getString(
                "fileName", this.sinkName,
                "logs/log4forge.log",
                "The location and name of the log file.");
    }

    @Override
    public void append(List<Entry> entries) throws IOException {
        File file = getFile();
        long lineCount = Files.lines(file.toPath()).count();
        long overflow = lineCount + entries.size() - this.maxEntries;

        File tmpFile = new File(file.getAbsolutePath() + ".tmp");
        tmpFile.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile));
        Scanner scanner = new Scanner(file);
        for (int i = 0; maxEntries > 0 && i < overflow; ++i) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("\n")) writer.newLine();
            else writer.write(line);
            writer.newLine();
        }

        for (String line : entries.stream().map(this::format).collect(Collectors.toList())) {
            writer.write(line);
            writer.newLine();
        }

        scanner.close();
        writer.close();

        file.delete();
        tmpFile.renameTo(file);
    }

    /**
     * Takes an entity and formats it to a single
     * string which is represented as line in the
     * log file.
     * @param entry Entry instance
     * @return formatted string
     */
    public abstract String format(Entry entry);

    private File getFile() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            new File(file.getParent()).mkdirs();
            file.createNewFile();
        }

        return file;
    }
}
