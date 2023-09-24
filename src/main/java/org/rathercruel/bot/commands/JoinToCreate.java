package org.rathercruel.bot.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.rathercruel.bot.main.BotConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rather Cruel
 */
public class JoinToCreate extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if (BotConfiguration.joinToCreateBool) {
            AudioChannel audioChannel = event.getChannelJoined();
            Guild guild = event.getGuild();
            Member member = event.getMember();

            if (audioChannel != null) {
                long channelID = audioChannel.getIdLong();
                List<Long> allow = new ArrayList<>();
                allow.add(Permission.ALL_CHANNEL_PERMISSIONS);
                long test = Permission.ALL_CHANNEL_PERMISSIONS;
                long deny = 0;

                if (channelID == BotConfiguration.joinToCreateChannelID) {
                    audioChannel.createCopy().setName("[\uD83E\uDD1D] " + member.getEffectiveName() + "'s Voice").queue((newChannel) -> {
                        guild.moveVoiceMember(member, (AudioChannel) newChannel).queue();
                        ((AudioChannel) newChannel).getManager().putMemberPermissionOverride(member.getIdLong(), test, deny).queue();
                    });
                }
            } else {
                Category category = event.getGuild().getCategoryById(BotConfiguration.joinToCreateCategoryID);
                List<VoiceChannel> vcList = category.getVoiceChannels();
                for (int i = 1; i < vcList.size(); i++) {
                    if (vcList.get(i).getMembers().size() == 0) {
                        long tempID = vcList.get(i).getIdLong();
                        guild.getVoiceChannelById(tempID).delete().queue();
                    }
                }
            }
        }
    }
}
