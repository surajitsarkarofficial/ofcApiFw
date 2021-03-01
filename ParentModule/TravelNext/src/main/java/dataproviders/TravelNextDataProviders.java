package dataproviders;

import properties.TravelNextProperties;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextDataProviders extends GlowDataProviders{
    /**
     * This method extract data from xml file for data providers
     *
     * @param filePath
     * @return Object[][]
     * @throws Exception
     */
    public static Object[][] getDataFromXMLFile(String filePath) throws Exception {
        String xmlInputStream = TravelNextProperties.dataProviderPath + filePath;
        String xpathExpression = "/dataObjects/dataObject";
        Object[][] dataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);

        return dataObjects;
    }
}
