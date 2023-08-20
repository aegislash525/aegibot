package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

/**
 * @author Rather Cruel
 */
public class RoleManagement extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getChannel().getType() != ChannelType.VOICE) {
            if (event.getChannel().asTextChannel().getIdLong() == BotConfiguration.roleChannelID) {
                for (int i = 0; i < BotConfiguration.reactionsRoles.size(); i++) {
                    event.getMessage().addReaction(BotConfiguration.emojiArrayList.get(i)).queue();
                }
            }
        }
    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();
        Emoji userReaction = event.getReaction().getEmoji().asUnicode();
        Role role = guild.getRoleById(BotConfiguration.reactionsRoles.get(userReaction));
        User user = event.getUser();

        if (!user.isBot()) {
            if (!member.getRoles().contains(role))
                guild.addRoleToMember(member, role).queue();
            else
                guild.removeRoleFromMember(member, role).queue();
            event.getReaction().removeReaction(user).queue();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        Role role;
        Member member = event.getMember();

        for (int i = 0; i < BotConfiguration.multipleDefaultRoleIDs.size(); i++) {
            role = guild.getRoleById(BotConfiguration.multipleDefaultRoleIDs.get(i));
            guild.addRoleToMember(member, role).queue();
        }
    }
}
