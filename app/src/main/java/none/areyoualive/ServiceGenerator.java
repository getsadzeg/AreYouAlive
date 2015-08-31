package none.areyoualive;
import android.content.Context;
import android.os.Environment;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
public class ServiceGenerator {
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, Context context) {
        File cacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        OkHttpClient client = new OkHttpClient();
        Cache cache = null;
        try {
            cache = new Cache(cacheDirectory, cacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.setCache(cache);
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint("http://areyoualive.org/")
                .setConverter(new LoganSquareConverter())
                .setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}
