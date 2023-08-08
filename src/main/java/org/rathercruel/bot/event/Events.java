package org.rathercruel.bot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

import java.util.Random;

/**
 * @author Rather Cruel
 */
public class Events extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Bot is up and running.");
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        System.out.println(event.getUser().getName() + " just left the server.");
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
    }

    // TODO: server boost message
}
