package org.rathercruel.bot.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.rathercruel.bot.commands.Clear;
import org.rathercruel.bot.commands.JoinToCreate;
import org.rathercruel.bot.commands.roles.RoleManagement;
import org.rathercruel.bot.commands.roles.SetRoleMessage;
import org.rathercruel.bot.event.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rather Cruel
 */
public class BotConfiguration {

    // The token of your bot:
    private static final String token = Token.TOKEN;

    // The name of the bot:
    public static final String botName = "rAþerNwht?";

    // Current version of the bot:
    public static final String botVersion = "1.7";

    // Your server's ID:
    public static final long guildID = 1112471560394117311L;

    // The colour of embeds bot sends:
    public static final int colour = 0xff4a4a;

    // Bot technical text-channel ID:
    public static final long botChannel = 1131934552504401938L;

    // Bot wrong channel message:
    public static final String wrongChannelMessage = "You gotta use #vc-context";

    // Default channel's ID (for "hello" messages)
    public static final long defaultChannelID = 1112471561266544692L;

    // No permission to run the command message:
    public static final String noPermissionMessage = "[member] You have no permissions to use this command!";

    // List of greeting messages:
    public static String[] greetingMessages = {
            "[member] just joined the server.",
            "Never gonna give [member] up. Never let [member] down!",
            "Hey! Listen! [member] has joined!",
            "[member] has joined THE FIRST GALACTIC EMPIRE!",
            "We've been expecting you, [member].",
            "It's dangerous to go alone, take [member]!",
            "Swoooosh. [member] just landed.",
            "A wild [member] appeared."
    };

    // Join to create:
    public static final long joinToCreateChannelID = 1142836675983179898L;

    public static final long joinToCreateCategoryID = 1142836639576629461L;

    // ROLES:

    // Give roles when somebody joins the server
    public static List<Long> multipleDefaultRoleIDs = new ArrayList<>();

    static {
        multipleDefaultRoleIDs.add(1114251035511361568L);
        multipleDefaultRoleIDs.add(1114251143485333604L);
        multipleDefaultRoleIDs.add(1112721761633583205L);
    }

    // Moderator roles (roles that may use commands like '/clear'):
    public static List<Long> moderatorRoleIDs = new ArrayList<>();

    static {
        moderatorRoleIDs.add(1112472571888275496L);
        moderatorRoleIDs.add(1112721554946658364L);
    }

    // React to get a role channel ID:
    public static final long roleChannelID = 1142403621330366474L;

    // Reaction emoji list:
    public static final List<Emoji> emojiArrayList = new ArrayList<>();

    static {
        emojiArrayList.add(Emoji.fromUnicode("U+1F3B1")); // 8Ball [0]
        emojiArrayList.add(Emoji.fromUnicode("U+1F345")); // Tomato [1]
        emojiArrayList.add(Emoji.fromUnicode("U+1F338")); // Cherry Blossom [2]
        emojiArrayList.add(Emoji.fromUnicode("\uD83E\uDE7B")); // X-Ray [3]
        emojiArrayList.add(Emoji.fromUnicode("U+1F3A9")); // Top Hat [4]
        emojiArrayList.add(Emoji.fromUnicode("U+1F47E")); // Space invader [5]
        emojiArrayList.add(Emoji.fromUnicode("U+1F48E")); // Gem Stone [6]
        emojiArrayList.add(Emoji.fromUnicode("U+1F300")); // Cyclone [7]
        emojiArrayList.add(Emoji.fromUnicode("U+1F4A6")); // Splashing Sweat [8]
        emojiArrayList.add(Emoji.fromUnicode("U+1F40A")); // Crocodile [9]

        emojiArrayList.add(Emoji.fromUnicode("U+1F5FD")); // Statue of Liberty [10]
        emojiArrayList.add(Emoji.fromUnicode("U+26A1")); // Voltage [11]
        emojiArrayList.add(Emoji.fromUnicode("U+1F6B0")); // Glass of water [12]
        emojiArrayList.add(Emoji.fromUnicode("U+1F4BB")); // Computer [13]
        emojiArrayList.add(Emoji.fromUnicode("U+1F391")); // Rice Scene [14]
        emojiArrayList.add(Emoji.fromUnicode("\uD83D\uDDFA\uFE0F")); // World Map [15]
        emojiArrayList.add(Emoji.fromUnicode("U+1F413")); // Rooster [16]
        emojiArrayList.add(Emoji.fromUnicode("U+1F414")); // Chicken [17]
        emojiArrayList.add(Emoji.fromUnicode("U+1F480")); // Skull [18]
    }

    // [Two sets for two messages] Reaction sets:
    public static final HashMap<Emoji, Long> reactionSet1 = new HashMap<>();
    public static final HashMap<Emoji, Long> reactionSet2 = new HashMap<>();

    static {
        reactionSet1.put(emojiArrayList.get(0), 1142567728926969988L);
        reactionSet1.put(emojiArrayList.get(1), 1142748165431373834L);
        reactionSet1.put(emojiArrayList.get(2), 1142748263594856489L);
        reactionSet1.put(emojiArrayList.get(3), 1143838167095595068L);
        reactionSet1.put(emojiArrayList.get(4), 1140617652511985684L);
        reactionSet1.put(emojiArrayList.get(5), 1140617368960245851L);
        reactionSet1.put(emojiArrayList.get(6), 1140617833953382421L);
        reactionSet1.put(emojiArrayList.get(7), 1140617486409154700L);
        reactionSet1.put(emojiArrayList.get(8), 1142748537780699236L);
        reactionSet1.put(emojiArrayList.get(9), 1140617921081659514L);

        reactionSet2.put(emojiArrayList.get(10), 1143128490242215976L);
        reactionSet2.put(emojiArrayList.get(11), 1143115457012969565L);
        reactionSet2.put(emojiArrayList.get(12), 1143124073929527298L);
        reactionSet2.put(emojiArrayList.get(13), 1143115849356558476L);
        reactionSet2.put(emojiArrayList.get(14), 1143837836034977903L);
        reactionSet2.put(emojiArrayList.get(15), 1143858617318117508L);
        reactionSet2.put(emojiArrayList.get(16), 1143128857810051112L);
        reactionSet2.put(emojiArrayList.get(17), 1143128739174170724L);
        reactionSet2.put(emojiArrayList.get(18), 1143128130677112832L);
    }

    // SERVER STATISTICS CONFIG:

    // Server's total members draw channel ID:
    public static final long guildTotalMembers = 1143154347417554995L;

    public static final long guildVoiceChannelCount = 1143154378837082203L;

    public static void main(String[] args) throws InterruptedException {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        JDA jda = jdaBuilder
                .setActivity(Activity.watching("je moeder neemt een douche."))
                .addEventListeners(
                        new Clear(),
                        new Events(),
                        new JoinToCreate(),
                        new RoleManagement(),
                        new SetRoleMessage()
                )
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_VOICE_STATES
                )
                .build().awaitReady();

        jda.upsertCommand("clear", "clears the chat")
                .addOption(OptionType.INTEGER, "number", "How many messages to delete").queue();

        jda.upsertCommand("set-message-reactions", "sets reactions on a specific message")
                .addOption(OptionType.STRING, "message-id", "ID of the specific message").queue();

//        jda.upsertCommand("remove-message-reactions", "sets reactions on a specific message")
//                .addOption(OptionType.STRING, "message-id", "ID of the specific message").queue();

//        jda.upsertCommand("server-stats", "check the server's stats").queue();
    }
}
