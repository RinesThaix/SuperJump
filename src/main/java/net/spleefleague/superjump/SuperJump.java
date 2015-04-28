/*  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package net.spleefleague.superjump;
    
import com.mongodb.DB;
import net.spleefleague.core.SpleefLeague;
import net.spleefleague.core.chat.ChatChannel;
import net.spleefleague.core.chat.ChatManager;
import net.spleefleague.core.chat.Theme;
import net.spleefleague.core.command.CommandLoader;
import net.spleefleague.core.player.PlayerManager;
import net.spleefleague.core.player.Rank;
import net.spleefleague.core.plugin.GamePlugin;
import net.spleefleague.superjump.game.Arena;
import net.spleefleague.superjump.game.Battle;
import net.spleefleague.superjump.game.BattleManager;
import net.spleefleague.superjump.listener.ConnectionListener;
import net.spleefleague.superjump.listener.GameListener;
import net.spleefleague.superjump.player.SJPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
    
/** 
 *   
 * @author Jonas
 */ 
public class SuperJump extends GamePlugin {
    
    private static SuperJump instance;
    private PlayerManager<SJPlayer> playerManager;
    private BattleManager battleManager;
    
    public SuperJump() {
        super("[SuperJump]", ChatColor.GRAY + "[" + ChatColor.GOLD + "SuperJump" + ChatColor.GRAY + "]" + ChatColor.RESET);
    }
    
    @Override
    public void start() {
        instance = this;
        Arena.initialize();
        playerManager = new PlayerManager<>(this, SJPlayer.class);
        battleManager = new BattleManager();
        ChatManager.registerPublicChannel(new ChatChannel("GAME_MESSAGE_SPLEEF_END", "SuperJump game start notifications", Rank.DEFAULT, true));
        ChatManager.registerPublicChannel(new ChatChannel("GAME_MESSAGE_SPLEEF_START", "SuperJump game result messages", Rank.DEFAULT, true));
        ConnectionListener.init();
        GameListener.init();
        CommandLoader.loadCommands(this, "net.spleefleague.superjump.commands");
    }
    
    @Override
    public DB getPluginDB() {
        return SpleefLeague.getInstance().getMongo().getDB("SuperJump");
    }
    
    public PlayerManager<SJPlayer> getPlayerManager() {
        return playerManager;
    }
    
    public BattleManager getBattleManager() {
        return battleManager;
    }
    
    public static SuperJump getInstance() {
        return instance;
    }

    @Override
    public void spectate(Player p) {
        SJPlayer sjp = getPlayerManager().get(p);
    }

    @Override
    public void dequeue(Player p) {
        SJPlayer sjp = getPlayerManager().get(p);
        getBattleManager().dequeue(sjp);
    }

    @Override
    public void cancel(Player p) {
        SJPlayer sjp = getPlayerManager().get(p);
        Battle battle = getBattleManager().getBattle(sjp);
        if(battle != null) {
            battle.cancel();    
            ChatManager.sendMessage(SuperJump.getInstance().getChatPrefix() + Theme.SUPER_SECRET + " The battle on " + battle.getArena().getName() + " has been cancelled.", "STAFF");
        }
    }

    @Override
    public boolean isQueued(Player p) {
        SJPlayer sjp = getPlayerManager().get(p);
        return getBattleManager().isQueued(sjp);
    }

    @Override
    public boolean isIngame(Player p) {
        SJPlayer sjp = getPlayerManager().get(p);
        return getBattleManager().isIngame(sjp);
    }
}   