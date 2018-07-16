/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.spboot.root.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author dnikiforov
 */
public final class CommonHelper {

	private static final ObjectMapper mapper = new ObjectMapper();

	private CommonHelper() {

	}

	public static <T> T transformJsonToObject(InputStream inputStream, Class<T> clazz) throws IOException {
		return mapper.readValue(inputStream, clazz);
	}

	public static <T> T transformJsonToObject(String fileName, Class<T> clazz) throws IOException {
		final InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(fileName);
		return transformJsonToObject(inputStream, clazz);
	}
	
	public static <T> String transformObjectToJson(T obj) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
		mapper.writeValue(byteArrayOutputStream, obj);
		return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
	} 

}
