package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

/**
 * @author Rather Cruel
 */
public class ClearReactions extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("clear-message-reactions")) {
            boolean hasNoPermissions = true;
            Guild guild = event.getGuild();
            for(int i = 0; i < BotConfiguration.moderatorRoleIDs.size(); i++) {
                if (event.getMember().getRoles().contains(guild.getRoleById(BotConfiguration.moderatorRoleIDs.get(i)))) {
                    i = BotConfiguration.moderatorRoleIDs.size();
                    String messageID = event.getOption("message-id").getAsRole().getId();
                    TextChannel textChannel = event.getChannel().asTextChannel();
                    Message message = textChannel.getHistory().getMessageById(messageID);
                    for (int j = 0; j < message.getReactions().size(); j++) {
                        message.removeReaction(message.getReactions().get(j).getEmoji());
                    }
                    hasNoPermissions = false;
                }
            }
            if (hasNoPermissions) {
                String errorMessage = BotConfiguration.noPermissionMessage;
                event.reply(errorMessage.replace("[member]", event.getMember().getAsMention()))
                        .setEphemeral(true).queue();
            }
        }
    }
}
