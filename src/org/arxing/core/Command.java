package org.arxing.core;

import com.annimon.stream.function.Consumer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command {
    private ProcessBuilder builder = new ProcessBuilder();
    private List<String> commandBuffer = new ArrayList<>();
    private List<String> runner = new ArrayList<>();

    public Command() {
        this(new String[]{});
    }

    public Command(String[] runner) {
        this.builder.redirectErrorStream(true);
        this.runner.addAll(Arrays.asList(runner));
    }

    public Command cd(File dir) {
        builder.directory(dir);
        return this;
    }

    public Command cd(String dir) {
        return cd(new File(dir));
    }

    public Command exec(String... commands) throws IOException {
        return exec(true, commands);
    }

    public Command exec(boolean print, String... commands) throws IOException {
        List<String> commandList = new ArrayList<>(runner);
        for (String command : commands) {
            commandList.add(encode(command));
        }
        builder.command(commandList);
        Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            line = decode(line);
            if (print)
                System.out.println(line);
            else
                commandBuffer.add(line);
        }
        isr.close();
        reader.close();
        return this;
    }

    public Command println() {
        for (String line : commandBuffer) {
            System.out.println(line);
        }
        commandBuffer.clear();
        return this;
    }

    public Command getPrints(Consumer<String> consumer) {
        for (String line : commandBuffer) {
            consumer.accept(line);
        }
        commandBuffer.clear();
        return this;
    }

    public Command execFormat(String format, Object... objects) throws IOException {
        return exec(String.format(format, objects));
    }

    private String encode(String s) throws UnsupportedEncodingException {
        return s;
    }

    private String decode(String s) throws UnsupportedEncodingException {
        return s;
    }
}
