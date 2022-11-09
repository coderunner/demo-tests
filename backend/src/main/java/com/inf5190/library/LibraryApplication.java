package com.inf5190.library;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@SpringBootApplication
@PropertySource("classpath:firebase.properties")
public class LibraryApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryApplication.class);

	@Value("${firebase.project.id}")
	private String firebaseProjectId;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
	Firestore getFirestore() throws IOException {
		if (FirebaseApp.getApps().size() == 0) {
			FileInputStream serviceAccount = new FileInputStream("firebase-key.json");

			FirebaseOptions options = FirebaseOptions.builder()
					.setProjectId(this.firebaseProjectId)
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.build();

			LOGGER.info("Initializing Firebase application.");
			FirebaseApp.initializeApp(options);

		} else {
			LOGGER.info("Firebase application already initialized.");
		}

		return FirestoreClient.getFirestore();
	}
}
