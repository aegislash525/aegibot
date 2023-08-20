package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

/**
 * @author Rather Cruel
 */
public class Clear extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("clear")) {
            Guild guild = event.getGuild();
            int number = event.getOption("number").getAsInt();

            boolean hasNoPermissions = true;
            for (int i = 0; i < BotConfiguration.moderatorRoleIDs.size(); i++) {
                if (event.getMember().getRoles().contains(guild.getRoleById(BotConfiguration.moderatorRoleIDs.get(i)))) {
                    event.getChannel().getIterableHistory()
                            .takeAsync(number)
                            .thenAccept(event.getChannel()::purgeMessages);
                    event.reply("Deleted " + number + " messages.").setEphemeral(true).queue();
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
