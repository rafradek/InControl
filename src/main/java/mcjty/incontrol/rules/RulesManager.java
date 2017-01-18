package mcjty.incontrol.rules;

import com.google.gson.*;
import mcjty.incontrol.InControl;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RulesManager {

    private static String path;
    public static List<SpawnRule> rules = new ArrayList<>();
    public static List<PotentialSpawnRule> potentialSpawnRules = new ArrayList<>();
    public static List<LootRule> lootRules = new ArrayList<>();

    public static void reloadRules() {
        rules.clear();
        potentialSpawnRules.clear();
        lootRules.clear();
        readRules();
        readPotentialSpawnRules();
        readLootRules();
    }

    public static void readRules(File directory) {
        path = directory.getPath();
        readRules();
        readPotentialSpawnRules();
        readLootRules();
    }

    private static void readLootRules() {
        File file = new File(path + File.separator + "incontrol", "loot.json");
        if (!file.exists()) {
            // Create an empty rule file
            makeEmptyRuleFile(file);
            return;
        }

        InControl.logger.log(Level.INFO, "Reading loot rules from loot.json");
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            InControl.logger.log(Level.ERROR, "Error reading loot.json!");
            return;
        }

        readLootRulesFromFile(inputstream);
    }

    private static void readLootRulesFromFile(InputStream inputstream) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            InControl.logger.log(Level.ERROR, "Error reading loot.json!");
            return;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(br);
        int i = 0;
        for (JsonElement entry : element.getAsJsonArray()) {
            LootRule rule = LootRule.parse(entry);
            if (rule != null) {
                lootRules.add(rule);
            } else {
                InControl.logger.log(Level.ERROR, "Rule " + i + " in loot.json is invalid, skipping!");
            }
            i++;
        }
    }


    private static void readPotentialSpawnRules() {
        File file = new File(path + File.separator + "incontrol", "potentialspawn.json");
        if (!file.exists()) {
            // Create an empty rule file
            makeEmptyRuleFile(file);
            return;
        }

        InControl.logger.log(Level.INFO, "Reading spawn rules from potentialspawn.json");
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            InControl.logger.log(Level.ERROR, "Error reading potentialspawn.json!");
            return;
        }

        readPotentialSpawnRulesFromFile(inputstream);
    }

    private static void readPotentialSpawnRulesFromFile(InputStream inputstream) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            InControl.logger.log(Level.ERROR, "Error reading potentialspawn.json!");
            return;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(br);
        int i = 0;
        for (JsonElement entry : element.getAsJsonArray()) {
            PotentialSpawnRule rule = PotentialSpawnRule.parse(entry);
            if (rule != null) {
                potentialSpawnRules.add(rule);
            } else {
                InControl.logger.log(Level.ERROR, "Rule " + i + " in potentialspawn.json is invalid, skipping!");
            }
            i++;
        }
    }

    private static void readRules() {
        File file = new File(path + File.separator + "incontrol", "spawn.json");
        if (!file.exists()) {
            // Create an empty rule file
            makeEmptyRuleFile(file);
            return;
        }

        InControl.logger.log(Level.INFO, "Reading spawn rules from spawn.json");
        InputStream inputstream = null;
        try {
            inputstream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            InControl.logger.log(Level.ERROR, "Error reading spawn.json!");
            return;
        }

        readRulesFromFile(inputstream);
    }

    private static void makeEmptyRuleFile(File file) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            InControl.logger.log(Level.ERROR, "Error writing spawn.json!");
            return;
        }
        JsonArray array = new JsonArray();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.print(gson.toJson(array));
        writer.close();
    }

    private static void readRulesFromFile(InputStream inputstream) {
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            InControl.logger.log(Level.ERROR, "Error reading spawn.json!");
            return;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(br);
        int i = 0;
        for (JsonElement entry : element.getAsJsonArray()) {
            SpawnRule rule = SpawnRule.parse(entry);
            if (rule != null) {
                rules.add(rule);
            } else {
                InControl.logger.log(Level.ERROR, "Rule " + i + " in spawn.json is invalid, skipping!");
            }
            i++;
        }
    }

}