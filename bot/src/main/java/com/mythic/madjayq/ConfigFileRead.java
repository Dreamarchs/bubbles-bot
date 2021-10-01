package com.mythic.madjayq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConfigFileRead {
    public static String configRead(int stringSkip, int charSkip) throws IOException {

            BufferedReader in = new BufferedReader(new FileReader("config.txt"));
            for (int i = 0; i < stringSkip; i++) {
                in.readLine();
            }
            in.skip(charSkip);

            return in.readLine().trim();
    }
}
