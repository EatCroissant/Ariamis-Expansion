package ariamis.help;

import ariamis.items.ItemMagicDust.potion;
import ariamis.items.ItemRegistry;
import net.minecraft.item.Item;

public class TableRecipes {
    private static final TableRecipes table_base = new TableRecipes();
    private ComparableMap<ComparableItemStack, ComparableMap> randoms = new ComparableMap();
    private ComparableMap<String,ComparableItemStack> reag = new ComparableMap();

    public static TableRecipes tableRecipes() {
        return table_base;
    }

    public TableRecipes() {

        initReagents();

        put((ComparableItemStack) reag.get("dandelion"), new int[]{potion.regeneration.getId(), potion.night_vision.getId(), potion.weakness.getId()}, new float[]{0.7f, 0.2f, 0.1f});
        put((ComparableItemStack) reag.get("brush"), new int[]{potion.fire_resistance.getId(), potion.instant_damage.getId(), potion.wither.getId()}, new float[]{0.5f, 0.4f, 0.1f});
        put((ComparableItemStack) reag.get("brown_mushrooms"), new int[]{potion.speed.getId(), potion.water_breathing.getId(), potion.poison.getId()}, new float[]{0.5f, 0.4f, 0.1f});
        put((ComparableItemStack) reag.get("red_mushrooms"), new int[]{potion.poison.getId(), potion.weakness.getId(), potion.slowness.getId()}, new float[]{0.5f, 0.3f, 0.2f});
        put((ComparableItemStack) reag.get("wart"), new int[]{potion.fire_resistance.getId(), potion.night_vision.getId(), potion.water_breathing.getId()}, new float[]{0.3f, 0.3f, 0.4f});
        put((ComparableItemStack) reag.get("cocoa"), new int[]{potion.speed.getId(), potion.night_vision.getId(), potion.strength.getId()}, new float[]{0.5f, 0.3f, 0.2f});
        put((ComparableItemStack) reag.get("wheat"), new int[]{potion.regeneration.getId(), potion.fire_resistance.getId(), potion.night_vision.getId()}, new float[]{0.3f, 0.3f, 0.4f});
        put((ComparableItemStack) reag.get("fish"), new int[]{potion.poison.getId(), potion.weakness.getId(), potion.instant_damage.getId()}, new float[]{0.3f, 0.3f, 0.4f});
        put((ComparableItemStack) reag.get("ink"), new int[]{potion.wither.getId(), potion.slowness.getId(), potion.poison.getId()}, new float[]{0.1f, 0.5f, 0.4f});
        put((ComparableItemStack) reag.get("pumpkin_seeds"), new int[]{potion.instant_health.getId(), potion.weakness.getId(), potion.wither.getId()}, new float[]{0.4f, 0.5f, 0.1f});
        put((ComparableItemStack) reag.get("melon_seeds"), new int[]{potion.instant_health.getId(), potion.weakness.getId(), potion.speed.getId()}, new float[]{0.3f, 0.4f, 0.3f});
        put((ComparableItemStack) reag.get("rotten_flesh"), new int[]{potion.water_breathing.getId(), potion.weakness.getId(), potion.slowness.getId()}, new float[]{0.3f, 0.4f, 0.4f});
        put((ComparableItemStack) reag.get("ender_pearl"), new int[]{potion.speed.getId(), potion.strength.getId(), potion.instant_health.getId()}, new float[]{0.4f, 0.3f, 0.3f});
        put((ComparableItemStack) reag.get("blaze_rod"), new int[]{potion.strength.getId(), potion.fire_resistance.getId(), potion.instant_damage.getId()}, new float[]{0.3f, 0.5f, 0.2f});
        put((ComparableItemStack) reag.get("ghast_tear"), new int[]{potion.regeneration.getId(), potion.fire_resistance.getId(), potion.instant_health.getId()}, new float[]{0.4f, 0.4f, 0.2f});
        put((ComparableItemStack) reag.get("blaze_powder"), new int[]{potion.strength.getId(), potion.fire_resistance.getId(), potion.instant_damage.getId()}, new float[]{0.3f, 0.5f, 0.2f});
        put((ComparableItemStack) reag.get("fermented_spider_eye"), new int[]{potion.instant_health.getId(), potion.weakness.getId(), potion.slowness.getId()}, new float[]{0.2f, 0.4f, 0.4f});
        put((ComparableItemStack) reag.get("ender_eye"), new int[]{potion.instant_damage.getId(), potion.strength.getId(), potion.fire_resistance.getId()}, new float[]{0.2f, 0.4f, 0.4f});
        put((ComparableItemStack) reag.get("magma_cream"), new int[]{potion.fire_resistance.getId(), potion.night_vision.getId(), potion.water_breathing.getId()}, new float[]{0.4f, 0.2f, 0.4f});
        put((ComparableItemStack) reag.get("speckled_melon"), new int[]{potion.regeneration.getId(), potion.instant_health.getId(), potion.slowness.getId()}, new float[]{0.3f, 0.4f, 0.3f});
        put((ComparableItemStack) reag.get("poisonous_potato"), new int[]{potion.poison.getId(), potion.weakness.getId(), potion.slowness.getId()}, new float[]{0.3f, 0.3f, 0.4f});
        put((ComparableItemStack) reag.get("nether_star"), new int[]{potion.fire_resistance.getId(), potion.instant_health.getId(), potion.strength.getId()}, new float[]{0.3f, 0.3f, 0.4f});
        put((ComparableItemStack) reag.get("golden_carrot"), new int[]{potion.speed.getId(), potion.night_vision.getId(), potion.instant_health.getId()}, new float[]{0.3f, 0.4f, 0.3f});
    }


    public boolean _match(ComparableItemStack is) {
        return randoms.containsKey(is);
    }

    public static boolean match(ComparableItemStack is) {
        return table_base._match(is);
    }

    public void put(ComparableItemStack input, int output_id[], float freq[]) {
        float sum = 0;
        boolean null_ = false;
        for (float f : freq) {
            if (f > 0) sum += f;
            else null_ = true;
        }

        if (sum == 0) return;

        int i = 0;
        ComparableMap item = new ComparableMap();
        if (null_)
            for (float f : freq) {
                if (f > 0) {
                    item.put(output_id[i], f / sum);
                }
                i++;
            }
        else for (float f : freq)
            item.put(output_id[i++], f / sum);

        randoms.put(input, item);

    }


    public ComparableMap getResults(ComparableItemStack input[]) {
        ComparableMap items = new ComparableMap();
        for (ComparableItemStack item : input) {
            ComparableMap floats =  randoms.get(item);
            Integer[] keys = (Integer[]) floats.keyArray(new Integer[0]);
            for (Integer key : keys) {
                if (!items.containsKey(key)) items.put(key, (Float) floats.get(key) / input.length);
                else items.put(key, (Float) items.get(key) + (Float) floats.get(key) / input.length);
            }
        }
        return items;
    }

    public ComparableItemStack[] availableResults(ComparableItemStack input[]) {
        ComparableMap idMap = getResults(input).revSortByValues().splice(0, 3);
        Integer[] ids = (Integer[])idMap.keyArray(new Integer[9]);
        ComparableItemStack[] cis = new ComparableItemStack[8];
        for (int i = 0; i < 8; i++) {
            int[] b = {i % 2, i / 2 % 2, i / 4 % 2};
            cis[i] = new ComparableItemStack(ItemRegistry.itemdust, 1, (b[0] << ids[0]) + (b[1] << ids[1]) + (b[2] << ids[2]));
        }
        return cis;
    }
    // for comparable values


    private void initReagents() {
        reag.put("dandelion", new ComparableItemStack(Item.getItemById(37)));
        reag.put("brush", new ComparableItemStack(Item.getItemById(32)));
        reag.put("brown_mushrooms", new ComparableItemStack(Item.getItemById(39)));
        reag.put("red_mushrooms", new ComparableItemStack(Item.getItemById(40)));
        reag.put("wart", new ComparableItemStack(Item.getItemById(372)));
        reag.put("cocoa", new ComparableItemStack(Item.getItemById(127)));
        reag.put("wheat", new ComparableItemStack(Item.getItemById(296)));
        reag.put("fish", new ComparableItemStack(Item.getItemById(349), 1, 3));
        reag.put("ink", new ComparableItemStack(Item.getItemById(351), 1, 0));
        reag.put("pumpkin_seeds", new ComparableItemStack(Item.getItemById(361), 1, 0));
        reag.put("melon_seeds", new ComparableItemStack(Item.getItemById(362), 1, 0));
        reag.put("rotten_flesh", new ComparableItemStack(Item.getItemById(367), 1, 0));
        reag.put("ender_pearl", new ComparableItemStack(Item.getItemById(368), 1, 0));
        reag.put("blaze_rod", new ComparableItemStack(Item.getItemById(369), 1, 0));
        reag.put("ghast_tear", new ComparableItemStack(Item.getItemById(370), 1, 0));
        reag.put("spider_eye", new ComparableItemStack(Item.getItemById(375), 1, 0));
        reag.put("fermented_spider_eye", new ComparableItemStack(Item.getItemById(376), 1, 0));
        reag.put("blaze_powder", new ComparableItemStack(Item.getItemById(377), 1, 0));
        reag.put("magma_cream", new ComparableItemStack(Item.getItemById(378), 1, 0));
        reag.put("ender_eye", new ComparableItemStack(Item.getItemById(381), 1, 0));
        reag.put("speckled_melon", new ComparableItemStack(Item.getItemById(382), 1, 0));
        reag.put("poisonous_potato", new ComparableItemStack(Item.getItemById(394), 1, 0));
        reag.put("golden_carrot", new ComparableItemStack(Item.getItemById(396), 1, 0));
        reag.put("nether_star", new ComparableItemStack(Item.getItemById(399), 1, 0));

        reag.put("sunflower", new ComparableItemStack(Item.getItemById(175)));
        reag.put("lilac", new ComparableItemStack(Item.getItemById(175), 1, 1));
        reag.put("tallgrass", new ComparableItemStack(Item.getItemById(175), 1, 2));
        reag.put("tallfern", new ComparableItemStack(Item.getItemById(175), 1, 3));
        reag.put("rose", new ComparableItemStack(Item.getItemById(175), 1, 4));
        reag.put("tallpeony", new ComparableItemStack(Item.getItemById(175), 1, 5));

        reag.put("poppy", new ComparableItemStack(Item.getItemById(38), 1, 0));
        reag.put("orchid", new ComparableItemStack(Item.getItemById(38), 1, 1));
        reag.put("allium", new ComparableItemStack(Item.getItemById(38), 1, 2));
        reag.put("bluet", new ComparableItemStack(Item.getItemById(38), 1, 3));
        reag.put("red_tulip", new ComparableItemStack(Item.getItemById(38), 1, 4));
        reag.put("orange_tulip", new ComparableItemStack(Item.getItemById(38), 1, 5));
        reag.put("white_tulip", new ComparableItemStack(Item.getItemById(38), 1, 6));
        reag.put("pink_tulip", new ComparableItemStack(Item.getItemById(38), 1, 7));
        reag.put("daisy", new ComparableItemStack(Item.getItemById(38), 1, 0));


    }

}
