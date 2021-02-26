package io.github.flaxeneel2.AIO;

import io.github.flaxeneel2.AIO.Commands.AIO;
import io.github.flaxeneel2.AIO.Commands.Balance;
import io.github.flaxeneel2.AIO.Commands.SetBalance;
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
    public static SQL db = new SQL();
    public static Main inst;
    @Override
    //this executes on plugin startup
    public void onEnable() {
        settings.checkngenfiles(this); //runs the function in io.github.flaxeneel2.AIO.SettingsManager to generate files
        inst = this;
        sendToConsole("&d==============================");
        sendToConsole("&2&l     AIO Enabled            ");
        sendToConsole("&d==============================");
        try {
            new AIO(this); //registers the command
            new Balance(this);
            new SetBalance(this);
        } catch(Error error) {
            sendToConsole("&cError!");
            error.printStackTrace();
        }
        boolean isSQLEnabled = settings.getConfig().getBoolean("connection.SQL.enabled"); //checks if SQL is enabled in the config file
        if(isSQLEnabled) {
            //just gets the credentials
            sendToConsole("&2SQL is enabled! trying to connect with given credentials");
            String host = getConfig().getString("connection.SQL.host");
            String port = getConfig().getString("connection.SQL.port");
            String dbname = getConfig().getString("connection.SQL.database");
            String username = getConfig().getString("connection.SQL.username");
            String password = getConfig().getString("connection.SQL.password");
            try {
                //tries connecting to the database with given credentials
                db.connect(host, port, dbname, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                sendToConsole("&c&lError!");
                e.printStackTrace();
            } finally {
                sendToConsole("&a&lConnected!");
            }
        }
    }

    @Override
    //this executes on plugin shut down
    public void onDisable() {
        sendToConsole("&d==============================");
        sendToConsole("&c&l     AIO Disabled           ");
        sendToConsole("&d==============================");
    }
    //a function which processes color codes and makes it easier to send messages with color to the console
    public void sendToConsole(String message) {
        log.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    //same as the function above, just for players
    public void sendToPlayer(Player p, String m) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }
}
