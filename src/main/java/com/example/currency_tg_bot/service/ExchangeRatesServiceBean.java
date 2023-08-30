package com.example.currency_tg_bot.service;

import com.example.currency_tg_bot.client.NBUClient;
import com.example.currency_tg_bot.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
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
    public static final String PLNXPath="/exchange/currency[r030='985']/rate";
    public static final String CZKXPath="/exchange/currency[r030='203']/rate";
    public static final String GBPXPath="/exchange/currency[r030='826']/rate";
    public static final String IDRXPath="/exchange/currency[r030='360']/rate";
    public static final String KZTXPath="/exchange/currency[r030='398']/rate";
    public static final String TRYXPath="/exchange/currency[r030='949']/rate";

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

    @Override
    public String getPLNExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,PLNXPath);
    }

    @Override
    public String getCZKExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,CZKXPath);
    }

    @Override
    public String getGBPExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,GBPXPath);
    }

    @Override
    public String getIDRExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,IDRXPath);
    }

    @Override
    public String getKZTExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,KZTXPath);
    }

    @Override
    public String getTRYExchangeRate() throws ServiceException {
        var xml=client.getCurrencyRatesXML();
        return parseCurrencyValueFromXML(xml,TRYXPath);
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
