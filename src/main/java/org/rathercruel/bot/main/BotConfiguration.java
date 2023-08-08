package org.rathercruel.bot.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.rathercruel.bot.commands.Clear;
import org.rathercruel.bot.commands.EmbedStats;
import org.rathercruel.bot.commands.JoinToCreate;
import org.rathercruel.bot.commands.RoleManagement;
import org.rathercruel.bot.event.Events;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
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
    public static final long botChannel = 1138387062693834803L;

    // Bot wrong channel message:
    public static final String wrongChannelMessage = "You gotta use #check-stats";

    // Give roles when somebody joins the server
    public static List<Long> multipleDefaultRoleIDs = new ArrayList<>();
    static {
        multipleDefaultRoleIDs.add(1134590540025954374L);
        multipleDefaultRoleIDs.add(1137801649386164304L);
    }

    // Moderator roles (roles that may use commands like '/clear' and so on):
    public static List<Long> moderatorRoleIDs = new ArrayList<>();
    static {
        moderatorRoleIDs.add(1134590155932577892L);
        moderatorRoleIDs.add(1138410023693848657L);
    }

    // Default channel's ID (for "hello" messages)
    public static final long defaultChannelID = 1134590113188429865L;

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


    // SERVER STATISTICS CONFIG:

    // Server's online draw channel ID:
    public static final long joinToCreate = 1137783472627392584L;

    public static void main(String[] args) throws LoginException {
        JDABuilder jdaBuilder = JDABuilder.createDefault(token);
        JDA jda = jdaBuilder
                .setActivity(Activity.watching("je moeder neemt een douche."))
                .addEventListeners(
                        new Clear(),
                        new Events(),
                        new JoinToCreate(),
                        new RoleManagement(),
                        new EmbedStats()
                )
                .enableIntents(
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.MESSAGE_CONTENT,
                        GatewayIntent.GUILD_PRESENCES)
                .build();

        jda.upsertCommand("clear", "clears the chat")
                .addOption(OptionType.INTEGER, "number", "How many messages to delete").queue();

        jda.upsertCommand("stats", "check the stats")
                .addOption(OptionType.USER, "user", "Check the member's statistics").queue();

        jda.upsertCommand("server-stats", "check the server's stats").queue();
    }
}
