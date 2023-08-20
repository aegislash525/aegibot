package org.rathercruel.bot.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.rathercruel.bot.commands.Clear;
import org.rathercruel.bot.commands.JoinToCreate;
import org.rathercruel.bot.commands.RoleManagement;
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

    // The colour of embeds bot sends:
    public static final int colour = 0xff4a4a;

    // Bot technical text-channel ID:
//    public static final long botChannel = 1138387062693834803L;
    public static final long botChannel = 1131934552504401938L;

    // Bot wrong channel message:
//    public static final String wrongChannelMessage = "You gotta use #check-stats";
    public static final String wrongChannelMessage = "You gotta use #vc-context";

    // Default channel's ID (for "hello" messages)
//    public static final long defaultChannelID = 1134590113188429865L;
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
    public static final Long joinToCreateChannelID = 1142836675983179898L;

    public static final Long joinToCreateCategoryID = 1142836639576629461L;


    // ROLES:

    // Give roles when somebody joins the server
    public static List<Long> multipleDefaultRoleIDs = new ArrayList<>();
//    static {
//        multipleDefaultRoleIDs.add(1134590540025954374L);
//        multipleDefaultRoleIDs.add(1137801649386164304L);
//    }

    static {
        multipleDefaultRoleIDs.add(1114251035511361568L);
        multipleDefaultRoleIDs.add(1114251143485333604L);
        multipleDefaultRoleIDs.add(1112721761633583205L);
    }

    // Moderator roles (roles that may use commands like '/clear'):
    public static List<Long> moderatorRoleIDs = new ArrayList<>();
//    static {
//        moderatorRoleIDs.add(1134590155932577892L);
//        moderatorRoleIDs.add(1138410023693848657L);
//    }

    static {
        moderatorRoleIDs.add(1112472571888275496L);
        moderatorRoleIDs.add(1112721554946658364L);
    }

    // React to get a role channel ID:
    public static final long roleChannelID = 1142403621330366474L;

    // Reaction emoji list:
    public static final List<Emoji> emojiArrayList = new ArrayList<>();
    static {
        emojiArrayList.add(Emoji.fromUnicode("U+0030 U+20E3")); // zero
        emojiArrayList.add(Emoji.fromUnicode("U+0031 U+20E3")); // one
        emojiArrayList.add(Emoji.fromUnicode("U+0032 U+20E3")); // two
        emojiArrayList.add(Emoji.fromUnicode("U+0033 U+20E3")); // three
        emojiArrayList.add(Emoji.fromUnicode("U+0034 U+20E3")); // four
        emojiArrayList.add(Emoji.fromUnicode("U+0035 U+20E3")); // five
        emojiArrayList.add(Emoji.fromUnicode("U+0036 U+20E3")); // six
        emojiArrayList.add(Emoji.fromUnicode("U+0037 U+20E3")); // seven
        emojiArrayList.add(Emoji.fromUnicode("U+0038 U+20E3")); // eight
        emojiArrayList.add(Emoji.fromUnicode("U+0039 U+20E3")); // nine
    }

    // Roles and reactions:
    public static final HashMap<Emoji, Long> reactionsRoles = new HashMap<>();
    static {
        reactionsRoles.put(emojiArrayList.get(0), 1142567728926969988L);
        reactionsRoles.put(emojiArrayList.get(1), 1142748165431373834L);
        reactionsRoles.put(emojiArrayList.get(2), 1142748263594856489L);
        reactionsRoles.put(emojiArrayList.get(3), 1140617652511985684L);
        reactionsRoles.put(emojiArrayList.get(4), 1140617368960245851L);
        reactionsRoles.put(emojiArrayList.get(5), 1140617833953382421L);
        reactionsRoles.put(emojiArrayList.get(6), 1140617486409154700L);
        reactionsRoles.put(emojiArrayList.get(7), 1142748537780699236L);
        reactionsRoles.put(emojiArrayList.get(8), 1140617921081659514L);
        reactionsRoles.put(emojiArrayList.get(9), 1140617254745153629L);
    }


    // SERVER STATISTICS CONFIG:

    // Server's online draw channel ID:
//    public static final long joinToCreate = 1137783472627392584L;

    public static void main(String[] args) throws InterruptedException {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        JDA jda = jdaBuilder
                .setActivity(Activity.watching("je moeder neemt een douche."))
                .addEventListeners(
                        new Clear(),
                        new Events(),
                        new JoinToCreate(),
                        new RoleManagement()
//                        new EmbedStats()
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

//        jda.upsertCommand("stats", "check the stats")
//                .addOption(OptionType.USER, "user", "Check the member's statistics").queue();
//
//        jda.upsertCommand("server-stats", "check the server's stats").queue();
    }
}
