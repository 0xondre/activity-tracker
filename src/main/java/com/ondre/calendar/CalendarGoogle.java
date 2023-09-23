package com.ondre.calendar;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * google calendar class
 */
public class CalendarGoogle implements CalendarModel {
    private static final String APPLICATION_NAME = "activity-tracker";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";


    public void addEvent(String name, long startTime, long endTime) {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = getCredentials(httpTransport);

            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME)
                    .build();

            Event event = new Event()
                    .setSummary(name)
                    .setStart(new EventDateTime()
                            .setDateTime(new DateTime(startTime))
                            .setTimeZone("Europe/Prague"))
                    .setEnd(new EventDateTime()
                            .setDateTime(new DateTime(endTime))
                            .setTimeZone("Europe/Prague"));
            String calendarId = "primary";
            event = service.events().insert(calendarId, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
    private static Credential getCredentials(HttpTransport httpTransport) throws IOException {
        try {
            InputStream in = CalendarGoogle.class.getResourceAsStream("/credentials.json");
            if (in == null) {
                throw new RuntimeException("Unable to load credentials.json");
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                GoogleClientSecrets clientSecrets;
                clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);

                GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                        httpTransport, JSON_FACTORY, clientSecrets,
                        Collections.singletonList(CalendarScopes.CALENDAR))
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                        .setAccessType("offline")
                        .build();

                return new AuthorizationCodeInstalledApp(
                        flow, new LocalServerReceiver.Builder().setPort(8888).build())
                        .authorize("user");

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error loading credentials: " + e.getMessage());
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
