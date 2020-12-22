package de.tails.healcommand.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HealCommand extends JavaPlugin implements CommandExecutor {

	@Override
	public void onEnable() {
		getCommand("heal").setExecutor(this);
	}

	@Override
	public void onDisable() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender.hasPermission("heal.cmd"))) {
			sender.sendMessage("§cDazu hast du keine Rechte!");
			return true;
		}
		if(args.length == 0) {
			if(!(sender instanceof Player)) // Nur ein Spieler kann sich selber heilen!
				return true;
			Player player = (Player) sender;
			player.setHealth(20); // Wir setzen das Leben des Spielers auf 20 (1 Leben = ein
									// halbes Herz)
			player.setFoodLevel(20); // Hier ist das gleiche Prinzip wie beim Health
			player.sendMessage("Du wurdest geheilt!");
		} else if(args.length == 1) { // Wenn der Spieler einen anderen heilen möchte haben wir
										// /heal <spielername> und deshalb args.lenght == 1
			if(!(sender.hasPermission("heal.other"))) {
				sender.sendMessage("§cDu hast keine Rechte, um andere Leute zu heilen!");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]); // Hier ist es args[0], da java mit 0 anfängt zu
														// zählen. Wir legen den Spieler fest, den der
														// Spieler also in den Chat reingeschrieben
														// hat.
			if(target != null) { // Wenn der target null ist, ist er offline, falls nicht ist
									// er online
				target.setHealth(20);
				target.setFoodLevel(20);
				sender.sendMessage("§aDer Spieler " + target.getName() + " wurde geheilt!");
				target.sendMessage("Du wurdest geheilt");
			} else {
				sender.sendMessage("Dieser Spieler ist nicht online!");
			}

		} else {
			sender.sendMessage("§cBitte verwende /heal | /heal <Spieler>");
		}
		return true;
	}
}