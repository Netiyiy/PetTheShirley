package com.kevin.pettheshirley;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listeners extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e)
    {
        User author = e.getAuthor();
        String authorID = author.getId();
        Message message = e.getMessage();
        String messageContent = message.getContentDisplay();
        if (!author.isBot() && messageContent.startsWith("-"))
        {
            if (!PetTheShirley.getInstance().getIDtoPlayerMap().containsKey(authorID)) {
                PetTheShirley.getInstance().loadDataFromID(authorID);
            }
            Player player = PetTheShirley.getInstance().getIDtoPlayerMap().get(authorID);
            if (messageContent.equals("-pet shirley"))
            {
                int result = (int) (Math.random() * 100) + 1;
                if (result <= 30) {
                    int randomDeathMessage = (int) (Math.random() * PetTheShirley.getInstance().getDeathMessages().size());
                    String deathMessage = PetTheShirley.getInstance().getDeathMessages().get(randomDeathMessage);
                    message.reply(deathMessage).queue();
                    player.death();
                }
                else {
                    int wallet = player.getWallet();
                    if (wallet <= 0) {
                        wallet = 1;
                    }
                    message.reply(
                            "You pet Shirley and she hisses at you" +
                                    "\n\u00A3" + (wallet * 2) + ", your wallet has been doubled"
                    ).queue();
                    player.setWallet(wallet * 2);
                }
            }
            else if (messageContent.equals("-bal") || messageContent.startsWith("-balance"))
            {
                message.reply(
                        "**" + author.getName() + "'s balance**" +
                                "\n`Bank:` \u00A3" + player.getBank() +
                                "\n`Wallet:` \u00A3" + player.getWallet()
                ).queue();
            }
            else if (messageContent.equals("-dep")) {
                message.reply("**You deposited \u00A3" + player.getWallet() + "**").queue();
                player.deposit();
            }
            else if (messageContent.startsWith("-dep "))
            {
                if (messageContent.length() >= 6) {
                    String amount = messageContent.substring(5);
                    int amountInt;
                    try {
                        amountInt = Integer.parseInt(amount);
                    } catch (NumberFormatException ex) {
                        message.reply("**Invalid amount**").queue();
                        return;
                    }
                    int amountDep = player.deposit(amountInt);
                    message.reply("**You deposited \u00A3" + amountDep +"**").queue();
                }
            }
            else if (messageContent.equals("-wd"))
            {
                message.reply("**You withdrawn \u00A3" + player.getBank() + "**").queue();
                player.withdrawal();
            }
            else if (messageContent.startsWith("-wd ")) {
                if (messageContent.length() >= 5) {
                    String amount = messageContent.substring(4);
                    int amountInt;
                    try {
                        amountInt = Integer.parseInt(amount);
                    } catch (NumberFormatException ex) {
                        message.reply("**Invalid amount**").queue();
                        return;
                    }
                    int amountWd = player.withdrawal(amountInt);
                    message.reply("**You withdrawn \u00A3" + amountWd +"**").queue();
                }
            }
        }
    }

}
