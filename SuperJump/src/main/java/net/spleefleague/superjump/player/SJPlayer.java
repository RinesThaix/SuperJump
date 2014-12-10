/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spleefleague.superjump.player;

import net.spleefleague.core.io.DBLoad;
import net.spleefleague.core.io.DBSave;
import net.spleefleague.core.player.GeneralPlayer;
import net.spleefleague.core.queue.Queueable;

/**
 *
 * @author Jonas
 */
public class SJPlayer extends GeneralPlayer implements Queueable {
    
    private int rating;
    
    @DBLoad(fieldName = "rating")
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    @DBSave(fieldName = "rating")
    public int getRating() {
        return rating;
    }
    
    @Override
    public void setDefaults() {
        super.setDefaults();
        rating = 1000;
    }

    @Override
    public void onDequeue(String queue) {
        
    }

    @Override
    public void onQueue(String queue) {
        
    }
}