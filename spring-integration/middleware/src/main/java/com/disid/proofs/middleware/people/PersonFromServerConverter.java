package com.disid.proofs.middleware.people;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import com.disid.proofs.middleware.people.domain.PersonFromServer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * = PersonFromServerToClientConverter
 * 
 * {@link HttpMessageConverter} that transforms the received JSON to a List of
 * {@link PersonFromServer}.
 */
@Component("personFromServerConverter")
public class PersonFromServerConverter implements HttpMessageConverter<List<PersonFromServer>> {

	@Autowired
	private ObjectMapper objectMapper;

	private static final List<MediaType> SUPPORTED_MEDIA_TYPES = new ArrayList<MediaType>();
	static {
		SUPPORTED_MEDIA_TYPES.add(MediaType.APPLICATION_JSON_UTF8);
		SUPPORTED_MEDIA_TYPES.add(MediaType.APPLICATION_JSON);
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return clazz.equals(List.class);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return SUPPORTED_MEDIA_TYPES;
	}

	/**
	 * This method creates an String from the received JSON and, using the
	 * {@link ObjectMapper} instance, creates a list of the people from the
	 * server side
	 */
	@Override
	public List<PersonFromServer> read(Class<? extends List<PersonFromServer>> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		final String jsonResponse = getResponse(inputMessage.getBody());
		return objectMapper.readValue(jsonResponse, new TypeReference<List<PersonFromServer>>() {
		});
	}

	@Override
	public void write(List<PersonFromServer> t, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// Nothing to do here
	}

	private String getResponse(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			final Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				final Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				inputStream.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

}
