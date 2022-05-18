package ccl.microscope;

import org.bukkit.Material;

import java.util.HashMap;

public enum LabEquipment {

    MICROSCOPE(Material.LIGHT_GRAY_DYE),
    ELECTRON_MICROSCOPE(Material.GRAY_DYE),
    //    MONITOR(Material.LIGHT_BLUE_DYE),
    SLIDE_RACK(Material.BROWN_DYE);

    private static HashMap<Material, LabEquipment> BY_MATERIAL = new HashMap<>();

    static {
        for (LabEquipment equipment : values()) {
            BY_MATERIAL.put(equipment.material, equipment);
        }
    }

    public Material material;

    LabEquipment(Material material) {
        this.material = material;
    }

    public static HashMap<Material, LabEquipment> getByMaterial() {
        return BY_MATERIAL;
    }

    public String getName() {
        return MicroscopeUtils.uppercaseFirst(toString()).replace("_", " ");
    }
}
