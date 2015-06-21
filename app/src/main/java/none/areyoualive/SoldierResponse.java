package none.areyoualive;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class SoldierResponse {
    public List<Soldier> soldiers;
}