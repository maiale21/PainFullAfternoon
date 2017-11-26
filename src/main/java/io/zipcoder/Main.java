package io.zipcoder;

import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Main {

    private static ArrayList<Item> itemsList = new ArrayList<Item>();
    private static Integer numberOfErrors = 0;

    public String readRawDataToString() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception {
        String output = (new Main()).readRawDataToString();
        //System.out.println(output);
        ItemParser itemParser = new ItemParser();
        ArrayList<String> itemStrings = itemParser.parseRawDataIntoStringArray(output);
        for (String itemString : itemStrings) {
            //System.out.println(itemString);
            try {
                Item newItem = itemParser.parseStringIntoItem(itemString);
                itemsList.add(newItem);
                //System.out.println(newItem);
            } catch (ItemParseException ipe) {
                numberOfErrors++;
            }
        }
        //Double used for Price, Integer used for Number of Times seen
        HashMap<Double, Integer> milkPriceHashMap = new HashMap<>();
        HashMap<Double, Integer> breadPriceHashMap = new HashMap<>();
        HashMap<Double, Integer> cookiesPriceHashMap = new HashMap<>();
        HashMap<Double, Integer> applesPriceHashMap = new HashMap<>();

        setKeysAndValuesForHashMap("[Mm][Ii][Ll][Kk]", milkPriceHashMap);
        setKeysAndValuesForHashMap("[Bb][Rr][Ee][Aa][Dd]", breadPriceHashMap);
        setKeysAndValuesForHashMap("[cC][0oO]{2}[kK][iI][eE][sS]", cookiesPriceHashMap);
        setKeysAndValuesForHashMap("[aA][pP]{2}[lL][eE][sS]", applesPriceHashMap);

        System.out.println(numberOfErrors);

    }
    //sout just used to see what happens, would be taken out in the end. Prints the correct keys and values,
    //just not the corresponding names for each.
    private static void setKeysAndValuesForHashMap(String regex, HashMap<Double, Integer> map) {
        for (Item item : itemsList) {
            if (item.getName().matches(regex)) {
                if (map.containsKey(item.getPrice())) {
                    Integer value = map.get(item.getPrice());
                    value = value + 1;
                    map.put(item.getPrice(), value);
                }
                else {
                    map.put(item.getPrice(), 1);
                }
            }
        }
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}




