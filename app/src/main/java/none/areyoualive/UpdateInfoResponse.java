package none.areyoualive;
import com.bluelinelabs.logansquare.annotation.JsonObject;
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class UpdateInfoResponse {
    public boolean success;
}
