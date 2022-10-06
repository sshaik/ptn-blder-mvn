package com.aq.test.patternbuilder;

import org.junit.Test;

import java.io.*;

public class ScratchTest {

    @Test
    public void test() throws  Exception
    {
        String command = "kinit sshaik";

        try {
          //  Process process = Runtime.getRuntime().exec(command);
            Process process = new ProcessBuilder("kinit","sshaik").start();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(process.getOutputStream()));
            writer.write("yes");
            writer.newLine();
            //writer.flush();
            writer.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
