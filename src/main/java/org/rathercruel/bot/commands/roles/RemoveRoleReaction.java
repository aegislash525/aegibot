package org.rathercruel.bot.commands.roles;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rathercruel.bot.main.BotConfiguration;
import org.rathercruel.bot.main.JsonConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Rather Cruel
 */
public class RemoveRoleReaction extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("remove-message-reactions")) {
            Guild guild = event.getGuild();
            boolean hasNoPermissions = true;
            for (int i = 0; i < BotConfiguration.moderatorRoleIDs.size(); i++) {
                if (event.getMember().getRoles().contains(guild.getRoleById(BotConfiguration.moderatorRoleIDs.get(i)))) {
                    i = BotConfiguration.moderatorRoleIDs.size();
                    String reaction = event.getOption("reaction").getAsString();
                    String messageID = event.getOption("message-id").getAsString();

                    TextChannel channel = event.getChannel().asTextChannel();
                    Emoji emoji = Emoji.fromFormatted(reaction);

                    channel.removeReactionById(messageID, emoji).queue();
                    event.reply("Removed " + emoji.getAsReactionCode() + " for " +
                            event.getGuild().getRoleById(BotConfiguration.emojiRoles.get(emoji)).getAsMention() + ".").setEphemeral(true).queue();

                    System.out.println(emoji.getFormatted() + ": " +
                            event.getGuild().getRoleById(BotConfiguration.emojiRoles.get(emoji)).getName() + " has been removed from JSON");

                    BotConfiguration.emojiRoles.remove(emoji);
                    StringBuilder sb = new StringBuilder();
                    String dir = System.getProperty("user.dir");
                    File file = new File(dir + "\\config.json");
                    Scanner sc = null;
                    try {
                        sc = new Scanner(file);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    while (sc.hasNext()) {
                        sb.append(sc.nextLine());
                    }
                    JSONObject object = new JSONObject(sb.toString());
                    JSONObject data = object.getJSONObject("bot_data");
                    JSONObject reactRoles = data.getJSONObject("roles").getJSONObject("react_roles");
                    JSONArray roleArr = reactRoles.getJSONArray("roles");

                    for (int j = 0; j < roleArr.toList().size(); j++) {
                        JSONObject role = roleArr.getJSONObject(j);
                        String roleEmoji = role.getString("emoji");
                        if (roleEmoji.equals(emoji.getFormatted())) {
                            roleArr.remove(j);
                            j = roleArr.toList().size();
                        }
                    }

                    try {
                        JsonConfig jsonConfig = new JsonConfig();
                        jsonConfig.update(object);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
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
