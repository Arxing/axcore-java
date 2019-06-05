package org.arxing.core.network;

import com.google.gson.JsonElement;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.arxing.core.utils.FileUtils;
import org.arxing.core.utils.JParser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static Map<String, ApiManager> map = new ConcurrentHashMap<>();
    private final static String defaultTag = "def";
    private OkHttpClient client;

    private ApiManager(OkHttpClient client) {
        this.client = client;
    }

    public static ApiManager getInstance(String tag, OkHttpClient client) {
        if (!map.containsKey(tag))
            map.put(tag, new ApiManager(client));
        return map.get(tag);
    }

    public static ApiManager getInstance(String tag) {
        return getInstance(tag, newDefaultOkHttpClient());
    }

    public static ApiManager getInstance(OkHttpClient client) {
        return getInstance(defaultTag, client);
    }

    public static ApiManager getInstance() {
        return getInstance(defaultTag, newDefaultOkHttpClient());
    }

    private static OkHttpClient newDefaultOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    public Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .baseUrl(baseUrl)
                                     .client(client)
                                     .build();
    }

    private Request newGetRequest(String url) {
        return new Request.Builder().get().url(url).build();
    }

    public Response request(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    public Response request(String url) throws IOException {
        return request(newGetRequest(url));
    }

    public String requestString(Request request) throws IOException {
        return request(request).body().string();
    }

    public String requestString(String url) throws IOException {
        return requestString(newGetRequest(url));
    }

    public JsonElement requestJson(Request request) throws IOException {
        return JParser.parse(request(request).body().string());
    }

    public JsonElement requestJson(String url) throws IOException {
        return requestJson(newGetRequest(url));
    }

    public <T> T requestJsonBean(Request request, Type type) throws IOException {
        return JParser.fromJsonOrNull(requestJson(request), type);
    }

    public <T> T requestJsonBean(String url, Type type) throws IOException {
        return requestJsonBean(newGetRequest(url), type);
    }

    public void download(Request request, File file) throws IOException {
        if (!file.getParentFile().exists())
            file.mkdirs();
        Response response = request(request);
        FileUtils.write(response.body().byteStream(), file);
    }

    public void download(String url, File file) throws IOException {
        download(newGetRequest(url), file);
    }
}
