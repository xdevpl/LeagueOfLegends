package pl.mcdev.leagueoflegends.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.MinecraftKey;
import net.minecraft.server.v1_11_R1.RegistryMaterials;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CustomEntityRegistry extends RegistryMaterials {
    private final BiMap<MinecraftKey, Class<? extends Entity>> entities;
    private final BiMap<Class<? extends Entity>, MinecraftKey> entityClasses;
    private final Map<Class<? extends Entity>, Integer> entityIds;
    private final RegistryMaterials<MinecraftKey, Class<? extends Entity>> wrapped;
    
    public CustomEntityRegistry(final RegistryMaterials<MinecraftKey, Class<? extends Entity>> original) {
        this.entities = HashBiMap.create();
        this.entityClasses = this.entities.inverse();
        this.entityIds = Maps.newHashMap();
        this.wrapped = original;
    }
    
    
	public void a(final int code, final Object key, final Object v) {
        this.put(code, (MinecraftKey) key, (Class<? extends Entity>) v);
    }
    
	public int a(final Object key) {
        if (this.entityIds.containsKey(key)) {
            return this.entityIds.get(key);
        }
        return this.wrapped.a((Class<? extends Entity>) key);
    }
    
    public Object a(final Random paramRandom) {
        return this.wrapped.a(paramRandom);
    }
    
	public MinecraftKey b(final Object value) {
        if (this.entityClasses.containsKey(value)) {
            return this.entityClasses.get(value);
        }
        return this.wrapped.b((Class<? extends Entity>) value);
    }
    
    public boolean d(final Object paramK) {
        return this.wrapped.d((MinecraftKey) paramK);
    }
    
    public Class<? extends Entity> get(final Object key) {
        if (this.entities.containsKey(key)) {
            return this.entities.get(key);
        }
        return this.wrapped.get((MinecraftKey) key);
    }
    
    public Object getId(final int paramInt) {
        return this.wrapped.getId(paramInt);
    }
    
    public RegistryMaterials<MinecraftKey, Class<? extends Entity>> getWrapped() {
        return this.wrapped;
    }
    
    public Iterator<?> iterator() {
        return this.wrapped.iterator();
    }
    
    public Set<?> keySet() {
        return this.wrapped.keySet();
    }
    
    public void put(final int entityId, final MinecraftKey key, final Class<? extends Entity> entityClass) {
        this.entities.put(key, entityClass);
        this.entityIds.put(entityClass, entityId);
    }
}