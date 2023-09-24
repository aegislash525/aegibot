package org.rathercruel.bot.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.rathercruel.bot.commands.Clear;
import org.rathercruel.bot.commands.JoinToCreate;
import org.rathercruel.bot.commands.roles.RemoveRoleReaction;
import org.rathercruel.bot.commands.roles.RoleManagement;
import org.rathercruel.bot.commands.roles.SetRoleMessage;
import org.rathercruel.bot.event.Events;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rather Cruel
 */
public class BotConfiguration {
    public static String token = "";
    public static String botName = "UNKNOWN";
    public static String botVersion = "0.0";
    public static String wrongChannelMessage = "";
    public static String noPermissionMessage = "[member] You have no permissions to use this command!";
    public static long guildID = 0;
    public static long botChannel = 0;
    public static long defaultChannelID = 0;
    public static long joinToCreateChannelID = 0;
    public static long joinToCreateCategoryID = 0;
    public static long roleChannelID = 0;
    public static long guildTotalMembers = 0;
    public static long guildTotalOnline = 0;
    public static long guildTotalVoiceChannels = 0;
    public static int embedColor = 0xff4a4a;
    public static boolean showStats = false;
    public static boolean joinToCreateBool = false;
    public static boolean giveBoosterRole = false;
    public static boolean giveMemberRole = false;
    public static boolean giveRoleOnReact = false;
    public static List<Long> multipleDefaultRoleIDs = new ArrayList<>();
    public static List<Long> moderatorRoleIDs = new ArrayList<>();
    public static List<Long> boosterRoleIDs = new ArrayList<>();
    public static List<String> greetingMessages = new ArrayList<>();
    public static HashMap<Emoji, Long> emojiRoles = new HashMap<>();

    private static boolean success = false;
    public static void main(String[] args) throws InterruptedException {
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "\\config.json");
        System.out.println("Directory=\"" + file.getPath() + "\"");
        System.out.println("Config exists=\"" + file.exists() + "\"");

        if (file.exists()) {
            JsonConfig jsonConfig = new JsonConfig();
            try {
                jsonConfig.read();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            success = true;
        } else {
            JsonConfig jsonConfig = new JsonConfig();
            jsonConfig.create();
            System.out.println("File \"config.json\" is created! Please configure your bot.");
        }

        if (success) {
            JDABuilder jdaBuilder = JDABuilder.createDefault(token);
            JDA jda = jdaBuilder
                    .setActivity(Activity.watching("je moeder neemt een douche."))
                    .addEventListeners(
                            new Clear(),
                            new Events(),
                            new JoinToCreate(),
                            new RoleManagement(),
                            new SetRoleMessage(),
                            new RemoveRoleReaction()
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
                    .addOption(OptionType.STRING, "reaction", "the reaction to put")
                    .addOption(OptionType.ROLE, "role", "mention the role to give")
                    .addOption(OptionType.STRING, "message-id", "ID of the specific message").queue();

            jda.upsertCommand("remove-message-reactions", "sets reactions on a specific message")
                    .addOption(OptionType.STRING, "reaction", "the reaction to put")
                    .addOption(OptionType.STRING, "message-id", "ID of the specific message").queue();
            jda.updateCommands().queue();
        }
    }
}
