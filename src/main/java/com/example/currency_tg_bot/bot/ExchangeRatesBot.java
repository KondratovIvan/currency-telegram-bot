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
    public static final String PLN="/pln";
    public static final String CZK="/czk";
    public static final String GBP="/gbp";
    public static final String IDR="/idr";
    public static final String KZT="/kzt";
    public static final String TRY="/try";

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
            case HELP ->helpCommandHandler(chatId);

            case USD -> usdCommandHandler(chatId);
            case EUR -> eurCommandHandler(chatId);
            case PLN -> plnCommandHandler(chatId);
            case CZK -> czkCommandHandler(chatId);
            case GBP -> gbpCommandHandler(chatId);
            case IDR -> idrCommandHandler(chatId);
            case KZT -> kztCommandHandler(chatId);
            case TRY -> tryCommandHandler(chatId);

            default -> {
                unknownCommandHandler(chatId);
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
                
                /usd - american dollar exchange rate
                /eur - euro exchange rate
                /pln - zloty exchange rate
                /czk - czech crown exchange rate
                /gbp - sterling pound exchange rate
                /idr - rupee exchange rate
                /kzt - tenge exchange rate
                /try - turkish lira exchange rate
                """;
        sendMessage(chatId,text);
    }

    private void unknownCommandHandler(Long chatId){
        var text = "Unknown command, enter /help to get command list";
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
            var eur = exchangeRatesService.getEURExchangeRate();
            var text = "Euro exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), eur);
        }
        catch (ServiceException e){
            LOGGER.error("Euro exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }
    private void plnCommandHandler(Long chatId){
        String formattedText;
        try{
            var pln = exchangeRatesService.getPLNExchangeRate();
            var text = "Zloty exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), pln);
        }
        catch (ServiceException e){
            LOGGER.error("Zloty exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }
    private void czkCommandHandler(Long chatId){
        String formattedText;
        try{
            var czk = exchangeRatesService.getCZKExchangeRate();
            var text = "Czech crown exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), czk);
        }
        catch (ServiceException e){
            LOGGER.error("Czech crown exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }
    private void gbpCommandHandler(Long chatId){
        String formattedText;
        try{
            var gbp = exchangeRatesService.getGBPExchangeRate();
            var text = "Sterling pound exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), gbp);
        }
        catch (ServiceException e){
            LOGGER.error("Sterling pound exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }
    private void idrCommandHandler(Long chatId){
        String formattedText;
        try{
            var idr = exchangeRatesService.getIDRExchangeRate();
            var text = "Rupee exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), idr);
        }
        catch (ServiceException e){
            LOGGER.error("Rupee exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }
    private void kztCommandHandler(Long chatId){
        String formattedText;
        try{
            var kzt = exchangeRatesService.getKZTExchangeRate();
            var text = "Tenge exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), kzt);
        }
        catch (ServiceException e){
            LOGGER.error("Tenge exchange rate getting error", e);
            formattedText = "Getting current euro exchange rate fail";
        }
        sendMessage(chatId,formattedText);
    }
    private void tryCommandHandler(Long chatId){
        String formattedText;
        try{
            var try_ = exchangeRatesService.getTRYExchangeRate();
            var text = "Turkish lira exchange rate for %s is %s hryvnia";
            formattedText=String.format(text, LocalDate.now(), try_);
        }
        catch (ServiceException e){
            LOGGER.error("Turkish lira exchange rate getting error", e);
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
