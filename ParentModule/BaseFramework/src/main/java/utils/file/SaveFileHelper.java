package utils.file;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class SaveFileHelper extends UtilsBase {

	/**
	 * This method will save a file.
	 * @param doc
	 * @param fileName
	 * @param path
	 * @throws IOException
	 */
	public static void saveFile(Document doc, String fileName, String path) throws IOException {
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getRawFormat().setOmitDeclaration(true));
		FileOutputStream file = new FileOutputStream(path + fileName);
		xmlOutput.output(doc, file);
		file.flush();
		file.close();
	}

}
