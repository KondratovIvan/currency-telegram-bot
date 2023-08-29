package com.example.currency_tg_bot.service;

import com.example.currency_tg_bot.client.NBUClient;
import com.example.currency_tg_bot.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.xml.sax.InputSource;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

@Service
public class ExchangeRatesServiceBean implements ExchangeRatesService{
    @Autowired
    private NBUClient client;

    public static final String USDXPath="/exchange/currency[r030='840']/rate";
    public static final String EURXPath="/exchange/currency[r030='978']/rate";

    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,USDXPath);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,EURXPath);
    }

    private static String parseCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException {
        var source= new InputSource(new StringReader(xml));
        try {
            var xpath = XPathFactory.newInstance().newXPath();
            var document= (Document) xpath.evaluate("/",source, XPathConstants.NODE);

            return xpath.evaluate(xpathExpression, document);
        }
        catch (XPathExpressionException e){
            throw new ServiceException("XML parsing fail", e);
        }
    }
}
