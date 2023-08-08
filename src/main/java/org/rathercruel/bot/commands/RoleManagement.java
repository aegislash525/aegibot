package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

/**
 * @author Rather Cruel
 */
public class RoleManagement extends ListenerAdapter {

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

    // Add/remove role commands
}
