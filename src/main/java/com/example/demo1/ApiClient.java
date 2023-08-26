package com.example.demo1;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiClient {

    private static final String BASE_URL = "https://api.aen.best";

    private static Retrofit retrofit = null;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    public interface Connection {
        @POST("/connection")
        Call<ResponseBody> login(@Body JsonObject body);
    }

    public interface Users {
        @POST("/getusers")

        Call<ResponseBody> getusers(@Body JsonObject body, @Header("Authorization") String headerValue);

    }


    public interface Planes {
        @POST("/getplanes")

        Call<ResponseBody> getplanes(@Body JsonObject body, @Header("Authorization") String headerValue);

        @POST("/getplanereservations")

        Call<ResponseBody> getplanesreservations(@Body JsonObject body, @Header("Authorization") String headerValue);

    }

    public interface Services {
        @POST("/getservices")

        Call<ResponseBody> getservices(@Body JsonObject body, @Header("Authorization") String headerValue);

    }

    public interface Parking {
        @POST("/getparkingreservations")

        Call<ResponseBody> getreservation(@Body JsonObject body, @Header("Authorization") String headerValue);

    }

    public interface Course {
        @POST("/getcourseparticipations")

        Call<ResponseBody> getreservation(@Body JsonObject body, @Header("Authorization") String headerValue);

    }

    public interface Infos {
        @POST("/infos")

        Call<ResponseBody> getinfos(@Body JsonObject body, @Header("Authorization") String headerValue);

    }


}
