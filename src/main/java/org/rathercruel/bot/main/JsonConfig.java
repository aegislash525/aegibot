package org.rathercruel.bot.main;

import flexjson.JSONSerializer;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Rather Cruel
 */
public class JsonConfig {
    public void create() {
        Map<String, Object> object = new HashMap<>() {
            {
                put("token", "your token here");
                put("activity_type", "watching, playing, listening");
                put("status_message", "your status text here");
                put("bot_name", "your bot name here");
                put("bot_version", "1.9");
                put("guild_id", 0);
                put("default_channel", 0);
                put("embed_color", "ff4a4a");
                put("technical_channel", 0);

                put("join_to_create", new HashMap<String, Object>() {
                    {
                        put("channel", 0);
                        put("category", 0);
                    }
                });
                put("message_config", new HashMap<String, Object>() {
                    {
                        put("greetings", new Object[] { new HashMap<String, Object>() {
                            {
                                put("message", "[member] just joined the server.");
                            }
                        } });
                        put("wrong_chat_msg", "You must use #vc-context");
                        put("no_permission_msg", "[member] You have no permissions to use this command!");
                    }
                });
                put("roles", new HashMap<String, Object>() {
                    {
                        put("boosters", new HashMap<String, Object>() {
                            {
                                put("enabled", false);
                                put("roles", new Object[] { new HashMap<String, Object>() {
                                    {
                                        put("id", 0);
                                    }
                                } });
                            }
                        });
                        put("member_join", new HashMap<String, Object>() {
                            {
                                put("enabled", false);
                                put("roles", new Object[] { new HashMap<String, Object>() {
                                    {
                                        put("id", 0);
                                    }
                                } });
                            }
                        });
                        put("moderators", new HashMap<String, Object>() {
                            {
                                put("roles", new Object[] { new HashMap<String, Object>() {
                                    {
                                        put("id", 0);
                                    }
                                } });
                            }
                        });
                        put("react_roles", new HashMap<String, Object>() {
                            {
                                put("enabled", false);
                                put("channel", 0);
                                put("roles", new Object[] { new HashMap<String, Object>() {
                                    {
                                        put("emoji", "paste emoji here");
                                        put("id", 0);
                                    }
                                } });
                            }
                        });
                    }
                });
                put("server_stats", new HashMap<String, Object>() {
                    {
                        put("show_stats", false);
                        put("guild_total", 0);
                        put("guild_online", 0);
                        put("guild_custom_vc", 0);
                    }
                });
            }
        };

        JSONSerializer json = new JSONSerializer();
        json.prettyPrint(true);

        try (FileWriter file = new FileWriter("config.json")) {
            file.write(json.deepSerialize(object));
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(JSONObject object) throws IOException {
        try (FileWriter file = new FileWriter("config.json")) {
            file.write(object.toString(5));
            file.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("File \"config.json\" has been updated!");
    }

    public void read() throws FileNotFoundException {
        String dir = System.getProperty("user.dir");
        File file = new File(dir + "\\config.json");
        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        JSONObject data = new JSONObject(sb.toString()).getJSONObject("bot_data");
        BotConfiguration.token = data.getString("token");
        BotConfiguration.statusMessage = data.getString("status_message");

        String botActivityType = data.getString("activity_type");
        BotConfiguration.botActivity = switch (botActivityType) {
            case "watching" -> BotActivity.WATCHING;
            case "playing" -> BotActivity.PLAYING;
            case "listening" -> BotActivity.LISTENING;
            default -> throw new IllegalStateException("Unexpected value: " + botActivityType);
        };

        BotConfiguration.botName = data.getString("bot_name");
        BotConfiguration.botVersion = data.getString("bot_version");
        BotConfiguration.guildID = data.getLong("guild_id");
        BotConfiguration.embedColor = Integer.parseInt(data.getString("embed_color"), 16);
        BotConfiguration.botChannel = data.getLong("technical_channel");
        BotConfiguration.defaultChannelID = data.getLong("default_channel");
        BotConfiguration.roleChannelID = data.getJSONObject("roles").getJSONObject("react_roles").getLong("channel");

        JSONObject messageConfig = data.getJSONObject("message_config");
        BotConfiguration.wrongChannelMessage = messageConfig.getString("wrong_chat_msg");
        BotConfiguration.noPermissionMessage = messageConfig.getString("no_permission_msg");

        JSONObject joinToCreate = data.getJSONObject("join_to_create");
        BotConfiguration.joinToCreateBool = joinToCreate.getBoolean("enabled");
        BotConfiguration.joinToCreateChannelID = joinToCreate.getLong("channel");
        BotConfiguration.joinToCreateCategoryID = joinToCreate.getLong("category");

        JSONObject stats = data.getJSONObject("server_stats");
        BotConfiguration.showStats = stats.getBoolean("show_stats");
        BotConfiguration.guildTotalMembers = stats.getLong("guild_total");
        BotConfiguration.guildTotalOnline = stats.getLong("guild_online");
        BotConfiguration.guildTotalVoiceChannels = stats.getLong("guild_custom_vc");
        for (int i = 0; i < messageConfig.getJSONArray("greetings").toList().size(); i++) {
            JSONObject msg = messageConfig.getJSONArray("greetings").getJSONObject(i);
            BotConfiguration.greetingMessages.add(msg.getString("message"));
        }
        JSONObject roles = data.getJSONObject("roles");
        BotConfiguration.giveBoosterRole = roles.getJSONObject("boosters").getBoolean("enabled");
        BotConfiguration.giveMemberRole = roles.getJSONObject("member_join").getBoolean("enabled");
        BotConfiguration.giveRoleOnReact = roles.getJSONObject("react_roles").getBoolean("enabled");
        for (int i = 0; i < roles.getJSONObject("react_roles").getJSONArray("roles").toList().size(); i++) {
            JSONObject role = roles.getJSONObject("react_roles").getJSONArray("roles").getJSONObject(i);
            BotConfiguration.emojiRoles.put(Emoji.fromUnicode(role.getString("emoji")), role.getLong("id"));
        }
        for (int i = 0; i < roles.getJSONObject("member_join").getJSONArray("roles").toList().size(); i++) {
            JSONObject role = roles.getJSONObject("member_join").getJSONArray("roles").getJSONObject(i);
            BotConfiguration.multipleDefaultRoleIDs.add(role.getLong("id"));
        }
        for (int i = 0; i < roles.getJSONObject("moderators").getJSONArray("roles").toList().size(); i++) {
            JSONObject role = roles.getJSONObject("moderators").getJSONArray("roles").getJSONObject(i);
            BotConfiguration.moderatorRoleIDs.add(role.getLong("id"));
        }
        for (int i = 0; i < roles.getJSONObject("boosters").getJSONArray("roles").toList().size(); i++) {
            JSONObject role = roles.getJSONObject("boosters").getJSONArray("roles").getJSONObject(i);
            BotConfiguration.boosterRoleIDs.add(role.getLong("id"));
        }
    }
}
