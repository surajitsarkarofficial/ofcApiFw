package payloads.submodules.tripCreation.tripCostAllocation;

import dto.submodules.tripCreation.costAllocation.CostAllocationRequestDTO;
import payloads.TravelNextPayloadHelper;
import properties.TravelNextConstants;
import tests.testhelpers.TravelNextBaseHelper;
import java.util.Random;

/**
 * @author josealberto.gomez
 *
 */
public class CostAllocationPayloadHelper extends TravelNextPayloadHelper {
    /**
     * This method will create a CostAllocationRequestDTO for create/update CostAllocation
     *
     * @return CostAllocationRequestDTO
     * @throws Exception
     */
    public static CostAllocationRequestDTO getCostAllocationDTO() throws Exception {
        CostAllocationRequestDTO tmpDTO = new CostAllocationRequestDTO();

        tmpDTO.setProjectName(TravelNextConstants.automationTestLabel + " "
                + TravelNextBaseHelper.nowDateTimeWithFormat(TravelNextConstants.dateTimeLongFormat));
        tmpDTO.setPercentage(new Random().nextInt(100) + 1);
        tmpDTO.setClientId(123456);
        tmpDTO.setClientName(TravelNextConstants.automationTestLabel + " "
                + TravelNextBaseHelper.nowDateTimeWithFormat(TravelNextConstants.dateTimeLongFormat));
        tmpDTO.setTag("Tag");
        tmpDTO.setProjectId(654321);

        return tmpDTO;
    }
}
