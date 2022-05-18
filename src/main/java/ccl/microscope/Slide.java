package ccl.microscope;

public enum Slide {

    EMPTY_SLIDE,
    ACORN_BARNACLE,
    ALGAE,
    ASCORBIC_ACID_BUBBLE,
    BELL_FLOWER_STIGMA,
    BROCCOLI,
    BUTTERFLY_WING,
    CHAMELEON_EMBRYO,
    COPPER_CRYSTALS,
    CURTAIN_FABRIC,
    DANDELION,
    DANDELION_LEAF,
    DANDELION_POLLEN,
    EMPEROR_BUTTERFLY,
    FERN_SORUS,
    FLEA,
    FLOWER,
    FLOWER_BUDS,
    FLOWER_SEED_HEAD,
    FLY_EYE,
    FOSSIL_ZOOPLANKTON,
    FOUND_IN_WATER,
    GEOTHITE_IRON_OXIDE_NEEDLES,
    GLYCERINE_BASED_SOAPY_SOLUTION,
    HUMAN_NEURAL_ROSETTE_PRIMORDIAL_BRAIN_CELLS,
    KARLSBAD_SPRUDELSTEIN_SEDIMENTARY_ROCK,
    LILY_POLLEN,
    LIQUID_CRYSTAL_RIBBON_FILAMENT,
    MOTH_PROBISCUS,
    MOUSE_MAMMARY_GLAND_ORGANOID,
    MOUSE_OVIDUCT_VASCULATURE,
    PLANT_HOPPER_NYMPH,
    PLASTIC_FRACTURING_ON_CREDIT_CARD_HOLOGRAM,
    POLISHED_AGATE,
    PYROMORPHITE_MINERAL,
    RED_SPECKLED_JEWELLED_BEETLE,
    SNOWFLAKE,
    TITMOUSE_DOWN_FEATHER,
    WATER_BLOBS;

    public int getCustomModelData() {
        return ordinal() + 10000;
    }

    public String getName() {
        return MicroscopeUtils.uppercaseFirst(toString()).replace("_", " ");
    }
}
