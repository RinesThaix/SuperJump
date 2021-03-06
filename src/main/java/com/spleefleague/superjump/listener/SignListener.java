/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spleefleague.superjump.listener;

import com.spleefleague.superjump.SuperJump;
import com.spleefleague.superjump.game.signs.GameSign;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *
 * @author Jonas
 */
public class SignListener implements Listener {

    private static Listener instance;

    public static void init() {
        if (instance == null) {
            instance = new SignListener();
            Bukkit.getPluginManager().registerEvents(instance, SuperJump.getInstance());
        }
    }

    private SignListener() {

    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                for (GameSign gameSign : GameSign.getAll()) {
                    if (gameSign.getLocation().equals(event.getClickedBlock().getLocation())) {
                        event.getPlayer().performCommand("superjump " + gameSign.getArena().getName());
                        break;
                    }
                }
            }
        }
    }
}
