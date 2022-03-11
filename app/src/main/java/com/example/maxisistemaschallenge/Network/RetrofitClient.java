package com.example.maxisistemaschallenge.Network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/* Instancia de Retrofit que actúa como controlador para todas las solicitudes y respuestas.
 * Define la instancia a Retrofit y la url base*/
public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String URL_BASE = "https://dog.ceo";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
