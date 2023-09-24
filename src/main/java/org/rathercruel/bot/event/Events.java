package org.rathercruel.bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.commands.stats.ServerStats;
import org.rathercruel.bot.main.BotConfiguration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Rather Cruel
 */
public class Events extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Guild guild = event.getJDA().getGuildById(BotConfiguration.guildID);
        List<Guild> guildList = event.getJDA().getGuilds();

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("[READY] " +
                "Name: " + BotConfiguration.botName + " | Version: " + BotConfiguration.botVersion);

        if (BotConfiguration.showStats) {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    guild.loadMembers().onSuccess(members -> {
                        int totalUsers = 0;
                        for(Member member : members) {
                            if (!member.getUser().isBot())
                                totalUsers++;
                        }
                        System.out.println("Server: " + guild.getName() + " | Total users: " + totalUsers);
                        guild.getVoiceChannelById(BotConfiguration.guildTotalMembers).getManager()
                                .setName("\uD83C\uDF3E Total Members: " + totalUsers).queue();
                    });
                    // ServerStats.updateOnline(guild);
                }
            };
            timer.schedule(timerTask, 20);
        }
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        System.out.println(event.getUser().getName() + " just left the server.");
        if (BotConfiguration.showStats)
            if (!event.getUser().isBot()) ServerStats.updateTotal(event.getGuild());
    }
}
