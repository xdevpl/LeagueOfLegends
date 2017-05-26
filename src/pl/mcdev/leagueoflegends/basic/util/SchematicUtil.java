package pl.mcdev.leagueoflegends.basic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import net.minecraft.server.v1_11_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import pl.mcdev.leagueoflegends.basic.Schematic;

public class SchematicUtil {
	
	public static Schematic loadSchematic(File f) throws IOException {
		FileInputStream stream = new FileInputStream(f);
		NBTTagCompound nbtData = NBTCompressedStreamTools.a(stream);

		short width = nbtData.getShort("Width");
		short height = nbtData.getShort("Height");
		short length = nbtData.getShort("Length");

		byte[] blocks = nbtData.getByteArray("Blocks");
		byte[] data = nbtData.getByteArray("Data");

		return new Schematic(blocks, data, width, length, height);
	}

	@SuppressWarnings("deprecation")
	public static void pasteSchematic(World w, Location l, Schematic schem) {
		byte[] blocks = schem.getBlocks();
		byte[] blockData = schem.getData();

		short length = schem.getLength();
		short width = schem.getWidth();
		short height = schem.getHeight();

		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				for (int z = 0; z < length; ++z) {
					int index = x + (y * length + z) * width;

					Block b = new Location(w, x + l.getX(), y + l.getY(), z + l.getZ()).getBlock();
					b.setTypeIdAndData(blocks[index], blockData[index], true);
				}
			}
		}
	}

}
