package com.example.currency_tg_bot.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ExchangeRatesBot extends TelegramLongPollingBot {

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
                
                Available Commands:
                /help - get help
                /usd - get hryvnia to American dollar exchange rate
                /eur - get hryvnia to euro exchange rate
                """;
        var formattedText=String.format(text,userName);
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
