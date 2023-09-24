package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.rathercruel.bot.main.BotConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Rather Cruel
 */
public class EmbedStats extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();

        if (event.getChannel().getIdLong() != BotConfiguration.botChannel) {
            event.reply(BotConfiguration.wrongChannelMessage).setEphemeral(true).queue();
            return;
        }

        if (event.getName().equalsIgnoreCase("stats")) {
            User user = event.getOption("user", OptionMapping::getAsUser);
            if (!user.isBot()) {
                eb.setAuthor(event.getUser().getEffectiveName());
                eb.setTitle("Statistics of *" + user.getEffectiveName() + "*");
                eb.addBlankField(false);
                eb.addField("Name: ", user.getName(), false);
                eb.addField("Chat level: ", "0", false);
                eb.addField("Voice-Chat level: ", "0", false);
                eb.addField("Date: ", dtf.format(now), false);
                eb.setImage(user.getAvatarUrl());
                eb.setColor(BotConfiguration.embedColor);
                System.out.println(event.getUser().getName() + " asked for stats of " + user.getName() + ".");
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            } else {
                eb.setAuthor(event.getUser().getEffectiveName());
                eb.setTitle("Bot: *" + user.getEffectiveName() + "*");
                eb.addBlankField(false);
                eb.addField("Name: ", user.getName(), false);
                eb.addField("Date: ", dtf.format(now), false);
                eb.setImage(user.getAvatarUrl());
                eb.setColor(BotConfiguration.embedColor);
                System.out.println(event.getUser().getName() + " asked for stats of a bot [" + user.getName() + "].");
                event.replyEmbeds(eb.build()).queue();
                eb.clear();
            }
        }

        if (event.getName().equalsIgnoreCase("server-stats")) {
            Guild guild = event.getGuild();
            long online = guild.getMembers().stream()
                    .filter(member ->
                            member.getOnlineStatus().equals(OnlineStatus.ONLINE))
                    .count();

            long totalMembers = guild.getMemberCache().size();

            eb.setAuthor(event.getUser().getEffectiveName());
            eb.setTitle("Statistics of the Server");
            eb.addField("Owner: ", guild.getOwner().getEffectiveName(), false);
            eb.addField("Online: ", String.valueOf(online), false);
            eb.addField("Total members: ", String.valueOf(totalMembers), false);
            eb.addField("Custom Voice-Chats created: ", String.valueOf(totalMembers), false);
            eb.addField("Currently in Voice: ", String.valueOf(totalMembers), false);
            eb.addField("Date: ", dtf.format(now), false);
            eb.setImage(guild.getIconUrl());
            eb.setColor(0xff4a4a);
            System.out.println(event.getUser().getName() + " asked for stats of the server.");
            event.replyEmbeds(eb.build()).queue();
            eb.clear();
        }
    }
}
