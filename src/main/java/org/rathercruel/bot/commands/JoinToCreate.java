package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Rather Cruel
 */
public class JoinToCreate extends ListenerAdapter {
    public void onGuildVoiceJoin(GuildVoiceUpdateEvent event) {
        Guild guild = event.getGuild();
        Member member = event.getMember();

        event.getChannelJoined().asVoiceChannel().getManager().equals(1137783472627392584L);
        System.out.println("Joined");
    }
}
