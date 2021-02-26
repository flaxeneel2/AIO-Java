package io.github.flaxeneel2.AIO.Commands;

import io.github.flaxeneel2.AIO.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings("unused")
public class SetBalance implements CommandExecutor {
    public SetBalance(Main plugin) {
        plugin.getCommand("setbalance").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //checks if the user has permissions (or is op)
        if(commandSender.hasPermission("AIO.admin.setbalance") || commandSender.isOp()) {
            if (strings.length < 2) {
                Main.inst.sendToPlayer((Player) commandSender, "&cUsage: /setbal <player> <amount>");
                return true;
            }
            Integer setbalamt;
            try {
                setbalamt = Integer.parseInt(strings[1]);
            } catch (NumberFormatException e) {
                Main.inst.sendToPlayer((Player) commandSender, "&4" + strings[1] + " &cis not a valid number!");
                return false;
            }
            Player target = Bukkit.getPlayer(strings[0]);
            if(target == null) {
                Main.inst.sendToPlayer((Player) commandSender, "&cPlayer not found! Make sure the spelling is correct and the player is online!");
                return false;
            }
            Main.db.setBalance(target, setbalamt);
            Main.inst.sendToPlayer((Player) commandSender, "&aBalance set!");
            return true;
        }
        Main.inst.sendToPlayer((Player) commandSender, "&cInsufficient Permissions!");
        return false;
    }
}
