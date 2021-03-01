package tests.testHelpers.submodules.myGrowth.familyGroup.birthdayFamilyGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.submodules.myGrowth.features.todayBirthdays.GlobersTodayDTO;
import endpoints.submodules.project.birthday.BirthdayEndpoint;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author christian.chacon
 */
public class BirthdayTestHelper extends MyGrowthTestHelper {

    public BirthdayTestHelper(String userName) throws Exception {
        super(userName);
    }

    /**
     * Get the response from the service baseUrl/v1/globers/todaysGlobersBirthday
     * @return Response
     * @author christian.chacon
     */
    public Response getTodayGlobersBirthday(){
        String requestUrl= MyGrowthBaseTest.baseUrl+ BirthdayEndpoint.todayGlobersBirthdayUrl+"1";
        return getMethod(requestUrl, null);
    }

    /**
     * This method will convert all "" values in the family group field to NULL
     * @param globersTodayDTOS
     * @return List<GlobersTodayDto>
     * @auhtor christian.chacon
     */
    public List<GlobersTodayDTO> convertEmptyValuestoNull(List<GlobersTodayDTO> globersTodayDTOS){
        List<GlobersTodayDTO> todayBirthdaysResponseFixedEmptyValues=new ArrayList<>();

        for (GlobersTodayDTO globers: globersTodayDTOS){
            if(globers.getFamilyGroup().equals("")){
                globers.setFamilyGroup(null);
                todayBirthdaysResponseFixedEmptyValues.add(globers);
            }else{
                todayBirthdaysResponseFixedEmptyValues.add(globers);
            }

        }
        return todayBirthdaysResponseFixedEmptyValues;
    }

    /**
     * This method will extract the glober id and family group and put both values in a HashMap
     * @param globersTodayDTOS
     * @return Map<Integer, String>
     * @auhtor christian.chacon
     */
    public Map<Integer, String> getGloberandFamilyGroup(List<GlobersTodayDTO> globersTodayDTOS){
        Map<Integer, String> allBirthdayJsonList=new HashMap<>();
        for (GlobersTodayDTO globersTodayDto : globersTodayDTOS){
            allBirthdayJsonList.put(Integer.parseInt(globersTodayDto.getGloberId()), globersTodayDto.getFamilyGroup());
        }
        return allBirthdayJsonList;
    }

}
