package org.rathercruel.bot.commands.roles;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.commands.stats.ServerStats;
import org.rathercruel.bot.main.BotConfiguration;

import java.util.Random;

/**
 * @author Rather Cruel
 */
public class RoleManagement extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        System.out.println(event.getUser().getName() + " just joined the server.");
        if (BotConfiguration.giveMemberRole) {
            Role role;
            Guild guild = event.getGuild();
            TextChannel textChannel = guild.getTextChannelById(BotConfiguration.defaultChannelID);
            EmbedBuilder eb = new EmbedBuilder();
            Member member = event.getMember();
            Random rand = new Random();

            for (int i = 0; i < BotConfiguration.multipleDefaultRoleIDs.size(); i++) {
                role = guild.getRoleById(BotConfiguration.multipleDefaultRoleIDs.get(i));
                guild.addRoleToMember(member, role).queue();
            }

            int randomInt = rand.nextInt(BotConfiguration.greetingMessages.size());
            String randomMessage = BotConfiguration.greetingMessages.get(randomInt)
                    .replace("[member]", member.getEffectiveName());

            eb.setAuthor(BotConfiguration.botName);
            eb.setTitle("New member in here!");
            eb.setDescription(randomMessage);
            eb.setImage(event.getUser().getAvatarUrl());

            textChannel.sendMessageEmbeds(eb.build()).queue();
            eb.clear();

            if (BotConfiguration.showStats) {
                if (!member.getUser().isBot())
                    ServerStats.updateTotal(guild);
            }
        }
    }
}
