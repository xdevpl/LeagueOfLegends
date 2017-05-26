package testes.entity;

import net.minecraft.server.v1_11_R1.Entity;
import net.minecraft.server.v1_11_R1.EntityTypes;
import net.minecraft.server.v1_11_R1.MinecraftKey;

public class Cer {
	
	public static void addCustomEntity(int entityId, String entityName, Class<? extends Entity> entityClass) {
		MinecraftKey minecraftKey = new MinecraftKey(entityName);
		EntityTypes.b.a(entityId, minecraftKey, entityClass);
	}
	
}
