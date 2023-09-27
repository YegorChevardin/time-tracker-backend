package com.reactdevops.timetracker.backend.web.helper;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;

public final class RequestBodyReader {

    private RequestBodyReader() {}

    public static String readBodyFromRequest(HttpServletRequest req) {
        StringBuilder jsonBody = new StringBuilder();
        String line;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonBody.toString();
    }
}