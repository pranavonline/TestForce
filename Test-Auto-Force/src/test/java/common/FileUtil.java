package common;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FileUtil {
	
	public static File getTestDataFile(String fileName) {
		return new File(
				String.valueOf(
						Objects.requireNonNull(
								FileUtil.class.getClassLoader().getResource(fileName)).getFile()
						)
				);
	}

	public static JsonObject loadOR() {
		final JsonParser JSON_PARSER = new JsonParser();
		File indexFile = getTestDataFile("ObjectRepo.json");
		String fileData = null;
		try {
			fileData = Files.toString(indexFile,Charsets.UTF_8);
		}catch(IOException e) {
			e.printStackTrace();
		}
		assert fileData != null;
		return (JsonObject) JSON_PARSER.parse(fileData);
	}
}
