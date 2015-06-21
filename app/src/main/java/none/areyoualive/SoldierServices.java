package none.areyoualive;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface SoldierServices {
    @GET("/json.php")
    void getSoldiers(Callback<SoldierResponse> responseCallback);
    @GET("/updateInfo.php")
    void updateInfo(@Query("Id") int Id,
                    @Query("Name") String name,
                    @Query("Status") String status,
                    @Query("Message") String message,
                    @Query("Longitude") double longitude,
                    @Query("Latitude") double latitude,
                    Callback<UpdateInfoResponse> responseCallback);
}

