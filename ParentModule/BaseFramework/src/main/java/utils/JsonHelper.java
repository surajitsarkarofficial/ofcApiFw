package utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	/**
	 * The the json file
	 * 
	 * @param filePath
	 * @return
	 */
	public static File loadJsonSchemaFile(String filePath) {
		File jsonFile = new File(filePath);
		return jsonFile;
	}

	/**
	 * Read the json fields
	 * 
	 * @param file
	 * @return
	 */
	public static Map<String, Object> readJsonFields(String file) {

		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> mapObject = null;
		File jsonFile = loadJsonSchemaFile(file);
		try {
			mapObject = mapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapObject;
	}

}
