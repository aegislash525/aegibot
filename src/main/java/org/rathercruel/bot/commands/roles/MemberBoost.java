package org.rathercruel.bot.commands.roles;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

/**
 * @author Rather Cruel
 */
public class MemberBoost extends ListenerAdapter {
    @Override
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        if (BotConfiguration.giveBoosterRole) {
            Member member = event.getMember();
            Guild guild = event.getGuild();
            TextChannel textChannel = guild.getTextChannelById(BotConfiguration.defaultChannelID);
            for (int i = 0; i < BotConfiguration.boosterRoleIDs.size(); i++) {
                guild.addRoleToMember(member, guild.getRoleById(BotConfiguration.boosterRoleIDs.get(i))).queue();
            }
            EmbedBuilder eb = new EmbedBuilder();
            eb.setAuthor(BotConfiguration.botName);
            eb.setTitle(member.getEffectiveName() + " is boosting here-place.");
            eb.setImage(event.getUser().getAvatarUrl());

            textChannel.sendMessageEmbeds(eb.build()).queue();
            eb.clear();
        }
    }
}
