package none.areyoualive;
import com.bluelinelabs.logansquare.annotation.JsonObject;
@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Soldier {
    public int Id;
    public String Name;
    public String Status;
    public String Message;
    public double Longitude;
    public double Latitude;
}
