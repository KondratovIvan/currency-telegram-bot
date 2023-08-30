package com.example.currency_tg_bot.service;

import com.example.currency_tg_bot.exception.ServiceException;

public interface ExchangeRatesService {
    String getUSDExchangeRate() throws ServiceException;

    String getEURExchangeRate() throws ServiceException;
    String getPLNExchangeRate() throws ServiceException;
    String getCZKExchangeRate() throws ServiceException;
    String getGBPExchangeRate() throws ServiceException;
    String getIDRExchangeRate() throws ServiceException;
    String getKZTExchangeRate() throws ServiceException;
    String getTRYExchangeRate() throws ServiceException;

}
