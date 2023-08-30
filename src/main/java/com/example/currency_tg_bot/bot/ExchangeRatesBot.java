package com.example.currency_tg_bot.bot;

import com.example.currency_tg_bot.exception.ServiceException;
import com.example.currency_tg_bot.service.ExchangeRatesServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

@Component
public class ExchangeRatesBot extends TelegramLongPollingBot {
    @Autowired
    private ExchangeRatesServiceBean exchangeRatesService;

    public static final Logger LOGGER= LoggerFactory.getLogger(ExchangeRatesBot.class);

    public static final String START="/start";
    public static final String HELP="/help";
    public static final String USD="/usd";
    public static final String EUR="/eur";

    public ExchangeRatesBot(@Value("${bot.token}") String botToken){
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(!update.hasMessage() || !update.getMessage().hasText()){
            return;
        }
        var message=update.getMessage().getText();
        var chatId=update.getMessage().getChatId();
        switch (message){
            case START -> {
                String userName=update.getMessage().getChat().getUserName();
                startCommandHandler(chatId, userName);
            }
            case HELP -> {
                helpCommandHandler(chatId);
            }
            case USD -> {
                usdCommandHandler(chatId);
            }
            case EUR -> {
                eurCommandHandler(chatId);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "KondratovCurrencyBot";
    }

    private void startCommandHandler(Long chatId, String userName){
        String text= """
                Welcome to currency bot, %s!
                
                Here you can get current exchange rate from NBU.
                
                To get help on the bot use /help command
                """;
        var formattedText=String.format(text,userName);
        sendMessage(chatId,formattedText);
    }

    private void helpCommandHandler(Long chatId){
        String text= """
                Bot help
                
                To get current valutes exchange rates use commands:
                /usd - American dollar exchange rate
                /eur - euro exchange rate
                """;
        sendMessage(chatId,text);
    }

    private void usdCommandHandler(Long chatId){
        String formattedText;
        try{
            var usd = exchangeRatesService.getUSDExchangeRate();
            var text = "American dollar exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), usd);
        }
        catch (ServiceException e){
            LOGGER.error("American dollar exchange rate getting error", e);
            formattedText = "Getting current American dollar exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }

    private void eurCommandHandler(Long chatId){
        String formattedText;
        try{
            var usd = exchangeRatesService.getEURExchangeRate();
            var text = "Euro exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), usd);
        }
        catch (ServiceException e){
            LOGGER.error("Euro exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }

    private void sendMessage(Long chatId, String text){
        var chatIdStr=String.valueOf(chatId);
        var sendMessage=new SendMessage(chatIdStr,text);
        try{
            execute(sendMessage);
        }
        catch (TelegramApiException e){
            LOGGER.error("Message sending fail", e);
        }
    }
}
