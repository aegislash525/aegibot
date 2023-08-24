package org.rathercruel.bot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Rather Cruel
 */
public class Events extends ListenerAdapter {

    private static int totalUsers = 0;

    @Override
    public void onReady(ReadyEvent event) {
        Guild guild = event.getJDA().getGuildById(BotConfiguration.guildID);

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Bot is up and running. [Version: " + BotConfiguration.botVersion + "]");

        guild.loadMembers().onSuccess(members -> {
            for (Member member : members) {
                if (!member.getUser().isBot())
                    totalUsers++;
            }
            System.out.println("Server: " + guild.getName() + " | Total users: " + totalUsers);
        });

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                updateTotal(guild, event);
                updateTotal(guild);
            }
        };
        timer.schedule(timerTask, 2000);
    }

    private void updateTotal(Guild guild) {
//        Category category = event.getJDA().getGuildById(BotConfiguration.guildID).getCategoryById(1142836639576629461L);
//        int categorySize = category.getVoiceChannels().size() - 1;
//        VoiceChannel vcCountChannel = event.getJDA().getGuildById(BotConfiguration.guildID)
//                .getVoiceChannelById(BotConfiguration.guildVoiceChannelCount);
//        vcCountChannel.getManager().setName("\uD83C\uDF3F VC created: " + categorySize).queue();

        guild.getVoiceChannelById(BotConfiguration.guildTotalMembers).getManager()
                .setName("\uD83C\uDF3E Total Members: " + totalUsers).queue();
    }

    @Override
    public void onGuildMemberUpdateBoostTime(GuildMemberUpdateBoostTimeEvent event) {
        Member member = event.getMember();
        Guild guild = event.getGuild();
        TextChannel textChannel = guild.getTextChannelById(BotConfiguration.defaultChannelID);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor(BotConfiguration.botName);
        eb.setTitle(member.getEffectiveName() + " is boosting here-place.");
        eb.setImage(event.getUser().getAvatarUrl());

        textChannel.sendMessageEmbeds(eb.build()).queue();
        eb.clear();
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        System.out.println(event.getUser().getName() + " just left the server.");
        if (!event.getUser().isBot()) totalUsers--;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        System.out.println(event.getUser().getName() + " just joined the server.");

        Guild guild = event.getGuild();
        TextChannel textChannel = guild.getTextChannelById(BotConfiguration.defaultChannelID);
        EmbedBuilder eb = new EmbedBuilder();
        Member member = event.getMember();
        Random rand = new Random();
        int randomInt = rand.nextInt(BotConfiguration.greetingMessages.length);
        String randomMessage = BotConfiguration.greetingMessages[randomInt]
                .replace("[member]", member.getEffectiveName());

        eb.setAuthor(BotConfiguration.botName);
        eb.setTitle("New member in here!");
        eb.setDescription(randomMessage);
        eb.setImage(event.getUser().getAvatarUrl());

        textChannel.sendMessageEmbeds(eb.build()).queue();
        eb.clear();

        if (!member.getUser().isBot())
            totalUsers++;
        guild.getVoiceChannelById(1143154347417554995L).getManager()
                .setName("\uD83C\uDF3E Total Members: " + totalUsers).queue();
    }
}
