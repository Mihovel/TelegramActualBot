import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotClass extends AbilityBot {
    static int count = 0;
    Map<Long, Integer> userToCount = new HashMap<>();
    Map<Long, Integer> userToCorrectAnswers = new HashMap<>();


    BotClass(String botToken, String botUsername, DefaultBotOptions options) {
        super(botToken, botUsername, options);
    }

    @Override
    public int creatorId() {
        return 0;
    }

    public String getBotUsername() {
        return "bot";
    }

    public String getBotToken() {
        return "1005113234:AAHg9rfkOYfiftC_sOmISYX5vWpk7Xen7Pg";
    }

    @Override
    public void onUpdateReceived(Update update) {
        String msg = update.toString();
        System.out.println("msg = " + msg);
        Entity.fillListOfQuestions();
        Entity.fillListOfPossibleAnswers();
        Entity.fillTrueFalse();

        if (update.hasMessage()) {
            userToCorrectAnswers.putIfAbsent(update.getMessage().getChatId(), 0);
            userToCount.putIfAbsent(update.getMessage().getChatId(), 0);
            if (update.getMessage().hasText()) {
                if (update.getMessage().getText().equals("Java")) {
                    // for (int i = 0; i < Entity.listOfQuestions.size(); i++) {
                    //Для каждого из вопросов надо обрабатывать эксепшн
                    try {
                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId(),
                                Entity.listOfPossibleAnswers.get(0).getOne(),
                                Entity.listOfPossibleAnswers.get(0).getTwo(),
                                Entity.listOfPossibleAnswers.get(0).getThree(),
                                Entity.listOfPossibleAnswers.get(0).getFour(),

                                Entity.trueFalse.get(0).getOne(),
                                Entity.trueFalse.get(0).getTwo(),
                                Entity.trueFalse.get(0).getThree(),
                                Entity.trueFalse.get(0).getFour(),

                                Entity.listOfQuestions.get(0)));

                        userToCount.replace(update.getMessage().getChatId(), 1);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;
                } else {

                    return;
                }
            }
            sendMsg(update.getMessage().getChatId().toString(), "Если хочешь сыграть в игру - напиши Java");
        }
        if (update.hasCallbackQuery()) {
            if (update.getCallbackQuery().getData().equals("1")) {
                userToCorrectAnswers.merge(update.getCallbackQuery().getMessage().getChatId(), 1, Integer::sum);
            }
            System.out.println("userToCount = " + userToCount);
            System.out.println("userToCorrectAnswers = " + userToCorrectAnswers);
            System.out.println(userToCount.get(update.getCallbackQuery().getMessage().getChatId()).equals(3));


            if (userToCount.get(update.getCallbackQuery().getMessage().getChatId()).equals(3)) {
                if (userToCorrectAnswers.get(update.getCallbackQuery().getMessage().getChatId()).equals(3)) {
                    sendMsg(update.getCallbackQuery().getMessage().getChatId().toString(), "You are the best");
                } else {
                    sendMsg(update.getCallbackQuery().getMessage().getChatId().toString(), "You are looser. " + userToCorrectAnswers.get(update.getCallbackQuery().getMessage().getChatId()) + " correct answers");
                }
            } else {
                try {
                    int currentQuestion = userToCount.get(update.getCallbackQuery().getMessage().getChatId());
                    execute(sendInlineKeyBoardMessage(update.getCallbackQuery().getMessage().getChatId(),
                            Entity.listOfPossibleAnswers.get(currentQuestion).getOne(),
                            Entity.listOfPossibleAnswers.get(currentQuestion).getTwo(),
                            Entity.listOfPossibleAnswers.get(currentQuestion).getThree(),
                            Entity.listOfPossibleAnswers.get(currentQuestion).getFour(),

                            Entity.trueFalse.get(currentQuestion).getOne(),
                            Entity.trueFalse.get(currentQuestion).getTwo(),
                            Entity.trueFalse.get(currentQuestion).getThree(),
                            Entity.trueFalse.get(currentQuestion).getFour(),

                            Entity.listOfQuestions.get(currentQuestion)));

                    userToCount.merge(update.getCallbackQuery().getMessage().getChatId(), 1, Integer::sum);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized void sendMsg(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(e.toString());
        }
    }

    private static SendMessage sendInlineKeyBoardMessage(long chatId, String answer1, String
            answer2, String
                                                                 answer3, String answer4,
                                                         String value1, String value2, String value3, String value4, String question) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(answer1);
        inlineKeyboardButton1.setCallbackData(value1);
        inlineKeyboardButton2.setText(answer2);
        inlineKeyboardButton2.setCallbackData(value2);
        inlineKeyboardButton3.setText(answer3);
        inlineKeyboardButton3.setCallbackData(value3);
        inlineKeyboardButton4.setText(answer4);
        inlineKeyboardButton4.setCallbackData(value4);
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText(question).setReplyMarkup(inlineKeyboardMarkup);
    }
}