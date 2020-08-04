package ru.trofimov.model;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Crutch {

    public static String toUTF8(String string) throws UnsupportedEncodingException {
        return new String(string.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
