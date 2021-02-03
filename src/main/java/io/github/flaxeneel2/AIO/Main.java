package io.github.flaxeneel2.AIO;

import io.github.flaxeneel2.AIO.Commands.AIO;
import io.github.flaxeneel2.AIO.databases.SQL.SQL;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

@SuppressWarnings("unused")
public final class Main extends JavaPlugin implements CommandExecutor {


    private static final ConsoleCommandSender log = Bukkit.getConsoleSender();
    public static SettingsManager settings = new SettingsManager();
    @Override
    public void onEnable() {
        settings.checkngenfiles(this);
        sendToConsole("&d==============================");
        sendToConsole("&2&l     AIO Enabled            ");
        sendToConsole("&d==============================");
        try {
            new AIO(this);
        } catch(Error error) {
            sendToConsole("&cError!\n" + error.toString());
        }
        boolean isSQLEnabled = settings.getConfig().getBoolean("connection.SQL.enabled");
        if(isSQLEnabled) {
            sendToConsole("&2SQL is enabled! trying to connect with given credentials");
            String host = getConfig().getString("connection.SQL.host");
            String port = getConfig().getString("connection.SQL.port");
            String dbname = getConfig().getString("connection.SQL.database");
            String username = getConfig().getString("connection.SQL.username");
            String password = getConfig().getString("connection.SQL.password");
            try {
                SQL.connect(host, port, dbname, username, password);
            }
            catch (ClassNotFoundException | SQLException e) {
                sendToConsole("&c&lError!");
                e.printStackTrace();
            } finally {
                sendToConsole("&a&lConnected!");
            }
        }
    }

    @Override
    public void onDisable() {
        sendToConsole("&d==============================");
        sendToConsole("&c&l     AIO Disabled           ");
        sendToConsole("&d==============================");
    }
    public static void sendToConsole(String message) {
        log.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    public static void sendToPlayer(Player p, String m) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }
}
