package org.rathercruel.bot.commands.stats;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

/**
 * @author Rather Cruel
 */
public class ServerStats extends ListenerAdapter {

    /*
    * Discord API has cooldown (more, less 10min) before
    * a voice channel's name will be changed.
    * So statistics for Custom VC category won't be
    * valid if user used joinToCreate channel and
    * left a minute later.
    * */

    public static void updateTotal(Guild guild) {
        guild.loadMembers().onSuccess(members -> {
            int totalUsers = 0;
            for(Member member : members) {
                if (!member.getUser().isBot())
                    totalUsers++;
            }
            System.out.println("Updated Total Users: " + totalUsers);
            guild.getVoiceChannelById(BotConfiguration.guildTotalMembers).getManager()
                    .setName("\uD83C\uDF3E Total Members: " + totalUsers).queue();
        });
    }

    // TODO: Doesn't work
    public static void updateOnline(Guild guild) {
        guild.loadMembers().onSuccess(members -> {
            int totalOnline = 0;
            for (Member member: members) {
                if ((!member.getUser().isBot()) &&
                        (member.getOnlineStatus() != OnlineStatus.OFFLINE || member.getOnlineStatus() != OnlineStatus.INVISIBLE
                                || member.getOnlineStatus() != OnlineStatus.UNKNOWN)
                ) {
                    totalOnline++;
                }
            }
            System.out.println("Updated Total Online: " + totalOnline);
            guild.getVoiceChannelById(BotConfiguration.guildTotalOnline).getManager()
                    .setName("\uD83C\uDF3F Total Bots: " + totalOnline).queue();
        });
    }

    // TODO: Don't know how to call
    public static void updateTotalVC(Guild guild) {
        Category category = guild.getCategoryById(BotConfiguration.joinToCreateCategoryID);
        int voiceCategorySize = category.getVoiceChannels().size() - 1;
        System.out.println("Updated Total Online: " + voiceCategorySize);
        guild.getVoiceChannelById(BotConfiguration.guildTotalVoiceChannels).getManager()
                .setName("\uD83C\uDF3F Custom VC: " + voiceCategorySize).queue();
    }
}
