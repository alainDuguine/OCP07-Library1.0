package org.alain.library.webapp.configuration;

import io.swagger.client.api.AuthorApi;
import io.swagger.client.api.BookApi;
import io.swagger.client.api.LoanApi;
import io.swagger.client.api.UserApi;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public Retrofit retrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        return new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    @Bean
    public UserApi userApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }

    @Bean
    public LoanApi loanApi(Retrofit retrofit) {
        return retrofit.create(LoanApi.class);
    }

    @Bean
    public BookApi bookApi(Retrofit retrofit) {
        return retrofit.create(BookApi.class);
    }

    @Bean
    public AuthorApi authorApi(Retrofit retrofit) {
        return retrofit.create(AuthorApi.class);
    }
}
