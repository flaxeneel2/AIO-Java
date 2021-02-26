package io.github.flaxeneel2.AIO.Commands;

import io.github.flaxeneel2.AIO.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

@SuppressWarnings("unused")
public class Balance implements CommandExecutor {
    public Balance(Main plugin) {
        plugin.getCommand("balance").setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        //if no arguments (in this case, player name) are given
        if(args.length == 0) {
            try {
                Integer bal = Main.db.getBalance((Player) commandSender);
                Main.inst.sendToPlayer((Player) commandSender, "Balance: " + bal);
                return true;
            } catch (SQLException e) {
                Main.inst.sendToConsole("&cError!");
                e.printStackTrace();
                return false;
            }
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            Main.inst.sendToPlayer((Player) commandSender, "&cPlayer not found! Make sure the spelling is correct and the player is online!");
            return false;
        }
        try {
            Integer baloftarget = Main.db.getBalance(target);
            Main.inst.sendToPlayer((Player) commandSender, "Balance of " + target.getName() + ": " + baloftarget);
        } catch (SQLException e) {
            Main.inst.sendToConsole("&cError!");
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
