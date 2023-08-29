package com.example.currency_tg_bot.client;

import com.example.currency_tg_bot.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NBUClient {
    @Autowired
    private OkHttpClient client;

    @Value("${https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange}")
    private String url;

    public String getCurrencyRatesXML() throws ServiceException{
        var request = new Request.Builder()
                .url(url)
                .build();
        try(var response=client.newCall(request).execute();){
            var body=response.body();
            if (body==null){
                return null;
            }
            else {
                return body.string();
            }
        }
        catch (IOException e){
            throw new ServiceException("Currency rates getting error", e);
        }
    }
}
