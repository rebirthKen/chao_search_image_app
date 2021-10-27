package com.chao.search.api;

import androidx.annotation.NonNull;
import com.chao.search.App;
import java.io.IOException;
import javax.inject.Inject;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.chao.search.R;

/**
 * This class is the Java class for ImageInterceptor for math 2 java file requirement
 * It is not hook up by any files yet but It is totally equal to ImageInterceptor.kt
 * **/
public class JavaImageInterceptor implements Interceptor {
    @Inject
    App app;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        String apiKey = app.getApplicationContext().getString(R.string.api_key);
        Request request = chain.request()
                .newBuilder()
                .header("Api-Key", apiKey)
                .build();
        return chain.proceed(request);
    }
}
