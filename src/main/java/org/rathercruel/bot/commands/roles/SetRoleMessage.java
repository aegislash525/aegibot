package org.rathercruel.bot.commands.roles;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rather Cruel
 */
public class SetRoleMessage extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equalsIgnoreCase("set-message-reactions")) {
            String channelID = event.getChannel().getId();
            String messageID = event.getOption("message-id").getAsString();

            Guild guild = event.getGuild();

            List<Emoji> newEmojiList = new ArrayList<>();

            newEmojiList.add(Emoji.fromUnicode("U+1F5FD"));
            newEmojiList.add(Emoji.fromUnicode("U+26A1"));
            newEmojiList.add(Emoji.fromUnicode("U+1F6B0"));
            newEmojiList.add(Emoji.fromUnicode("U+1F4BB"));
            newEmojiList.add(Emoji.fromUnicode("U+1F391"));
            newEmojiList.add(Emoji.fromUnicode("\uD83D\uDDFA\uFE0F"));
            newEmojiList.add(Emoji.fromUnicode("U+1F413"));
            newEmojiList.add(Emoji.fromUnicode("U+1F414"));
            newEmojiList.add(Emoji.fromUnicode("U+1F480"));

            boolean hasNoPermissions = true;
            for (int i = 0; i < BotConfiguration.moderatorRoleIDs.size(); i++) {
                if (event.getMember().getRoles().contains(guild.getRoleById(BotConfiguration.moderatorRoleIDs.get(i)))) {
                    messageReactionAdd(event, channelID, messageID, newEmojiList);
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

    // Not `onMessageReactionAdd`. This one adds reactions from the list to the message
    private void messageReactionAdd(SlashCommandInteractionEvent event, String channelID, String messageID, List<Emoji> emojiList) {
        Guild guild = event.getGuild();
        TextChannel channel = guild.getTextChannelById(channelID);

        if (channel != null) {
            for (int i = 0; i < emojiList.size(); i++) {
                channel.addReactionById(messageID, emojiList.get(i)).queue();
            }
            event.reply("Added " + emojiList.size() + " emojis to the message.").setEphemeral(true).queue();
        } else {
            event.reply("Unknown message.").setEphemeral(true).queue();
        }
    }

    // If user adds a reaction on a message
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getChannel().asTextChannel().getIdLong() == BotConfiguration.roleChannelID) {
            HashMap<Emoji, Long> reactionRoles = new HashMap<>();

            if (event.getMessageId().equals("1143141678308392981"))
                reactionRoles.putAll(BotConfiguration.reactionSet1);
            else if (event.getMessageId().equals("1143145372366143648"))
                reactionRoles.putAll(BotConfiguration.reactionSet2);

            Guild guild = event.getGuild();
            Member member = event.getMember();
            Emoji userReaction = event.getReaction().getEmoji().asUnicode();
            Role role = guild.getRoleById(reactionRoles.get(userReaction));
            User user = event.getUser();

            if (role != null) {
                if (!user.isBot()) {
                    if (!member.getRoles().contains(role))
                        guild.addRoleToMember(member, role).queue();
                    else
                        guild.removeRoleFromMember(member, role).queue();
                    event.getReaction().removeReaction(user).queue();
                }
            }
        }
    }

}
