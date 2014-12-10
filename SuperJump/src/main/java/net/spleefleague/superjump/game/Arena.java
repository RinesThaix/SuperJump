/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spleefleague.superjump.game;

import com.mongodb.BasicDBList;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.spleefleague.core.io.DBEntity;
import net.spleefleague.core.io.DBLoad;
import net.spleefleague.core.io.DBLoadable;
import net.spleefleague.core.utils.Area;
import net.spleefleague.core.utils.EntityBuilder;
import net.spleefleague.core.utils.TypeConverter;
import net.spleefleague.superjump.SuperJump;
import org.bukkit.Location;

/**
 *
 * @author Jonas
 */
public class Arena extends DBEntity implements DBLoadable{
    
    @DBLoad(fieldName = "border")
    private Area border;
    @DBLoad(fieldName = "spawns", typeConverter = TypeConverter.LocationArrayConverter.class)
    private Location[] spawns;
    @DBLoad(fieldName = "goals", typeConverter = AreaArrayConverter.class)
    private Area[] goals;
    @DBLoad(fieldName = "creator")
    private String creator;
    @DBLoad(fieldName = "name")
    private String name;
    private boolean occupied = false;
    
    public Arena() {
        
    }
    
    public Location[] getSpawns() {
        return spawns;
    }
    
    public Area[] getGoals() {
        return goals;
    }
    
    public Area getBorder() {
        return border;
    }
    
    public String getCreator() {
        return creator;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
    
    private static final Map<String, Arena> arenas;
    
    public static Arena byName(String name) {
        return arenas.get(name);
    }
    
    public static Collection<Arena> getAll() {
        return arenas.values();
    }
    
    static {
        arenas = new HashMap<>();
        DBCursor dbc = SuperJump.getInstance().getPluginDB().getCollection("Arenas").find();
        while(dbc.hasNext()) {
            Arena arena = EntityBuilder.load(dbc.next(), Arena.class);
            arenas.put(arena.getName(), arena);
        }
    }
    
    public static class AreaArrayConverter extends TypeConverter<BasicDBList, Area[]> {

        @Override
        public Area[] convertLoad(BasicDBList t) {
            Area[] areas = new Area[t.size()];
            for(int i = 0; i < areas.length; i++) {
                areas[i] = EntityBuilder.load((DBObject)t.get(i), Area.class);
            }
            return areas;
        }

        @Override
        public BasicDBList convertSave(Area[] v) {
            BasicDBList bdbl = new BasicDBList();
            for(Area area : v) {
                bdbl.add(EntityBuilder.serialize(area));
            }
            return bdbl;
        }   
    }
}
