package vg.civcraft.mc.namelayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import vg.civcraft.mc.civmodcore.annotations.CivConfig;
import vg.civcraft.mc.civmodcore.annotations.CivConfigType;
import vg.civcraft.mc.namelayer.database.AssociationList;

public class NameAPI {
	private static GroupManager groupManager;
	private static AssociationList associations;
	
	private static Map<UUID, String> uuidsToName = new HashMap<UUID, String>();
	private static Map<String, UUID> nameToUUIDS = new HashMap<String, UUID>();
	
	public NameAPI(GroupManager man, AssociationList ass){
		groupManager = man;
		associations =  ass;
		loadAllPlayerInfo();
	}
	
	@CivConfig(name = "persistance.forceloadnamecaching", def = "false", type = CivConfigType.Bool)
	public void loadAllPlayerInfo(){
		uuidsToName.clear();
		nameToUUIDS.clear();
		
		boolean load = NameLayerPlugin.getInstance().GetConfig().get("persistance.forceloadnamecaching").getBool();
		if (!load)
			return;
		Object[] objs = associations.getAllPlayerInfo();
		nameToUUIDS = (HashMap<String, UUID>) objs[0];
		uuidsToName = (HashMap<UUID, String>) objs[1];
		
	}
	
	public static void resetCache(UUID uuid) {
		String name = getCurrentName(uuid);
		uuidsToName.remove(uuid);
		nameToUUIDS.remove(name);
	}
	/**
	 * Returns the UUID of the player on the given server.
	 * @param playerName The playername.
	 * @return Returns the UUID of the player.
	 */
	public static UUID getUUID(String playerName) {
		UUID uuid = nameToUUIDS.get(playerName);
		if (uuid == null){
			uuid = associations.getUUID(playerName);
			nameToUUIDS.put(playerName, uuid);
		}
		return uuid;
	}
	/**
	 * Gets the playername from a given server from their uuid.
	 * @param uuid.
	 * @return Returns the PlayerName from the UUID.
	 */
	public static String getCurrentName(UUID uuid) {
		String name = uuidsToName.get(uuid);
		if (name == null){
			name = associations.getCurrentName(uuid);
			uuidsToName.put(uuid, name);
		}
		return name;
	}
	/**
	 * @return Returns an instance of the GroupManager.
	 */
	public static GroupManager getGroupManager(){
		return groupManager;
	}
	/**
	 * @return Returns an instance of the AssociationList.
	 */
	public static AssociationList getAssociationList(){
		return associations;
	}
}
