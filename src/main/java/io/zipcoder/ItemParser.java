package io.zipcoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {


    public ArrayList<String> parseRawDataIntoStringArray(String rawData){
        String stringPattern = "##";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawData);
        return response;
    }

    public String parseString(String regex, String rawItem) throws ItemParseException{
        Pattern namePattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher nameMatcher = namePattern.matcher(rawItem);
        String name;
        if (nameMatcher.find()) {
            Integer startingIndex = nameMatcher.start();
            Integer endingIndex = nameMatcher.end();
            name = rawItem.substring(startingIndex, endingIndex);
            //System.out.println(name);
        } else {
            throw new ItemParseException();
        }
        return name;

    }

    public Double parseDouble(String rawItem) throws ItemParseException{
        Pattern pricePattern = Pattern.compile("\\d+[.]\\d+", Pattern.CASE_INSENSITIVE);
        Matcher priceMatcher = pricePattern.matcher(rawItem);
        Double price;
        if (priceMatcher.find()) {
            Integer startingIndex = priceMatcher.start();
            Integer endingIndex = priceMatcher.end();
            price = Double.parseDouble(rawItem.substring(startingIndex, endingIndex));
            //System.out.println(price);
        } else {
            throw new ItemParseException();
        }
        return price;
    }


    public Item parseStringIntoItem(String rawItem) throws ItemParseException {
        String name = parseString("(?<=name:)\\w+", rawItem);
        Double price = parseDouble(rawItem);
        String type = parseString("(?<=type:)\\w+",rawItem);
        String expiration = parseString("\\d+[/]\\d+[/]\\d+",rawItem);

        return new Item(name.toLowerCase(), price, type.toLowerCase(), expiration.toLowerCase());
    }

    public ArrayList<String> findKeyValuePairsInRawItemData(String rawItem){
        String stringPattern = "[;@^*%]";
        ArrayList<String> response = splitStringWithRegexPattern(stringPattern, rawItem);
        return response;
    }

    private ArrayList<String> splitStringWithRegexPattern(String stringPattern, String inputString){
        return new ArrayList<String>(Arrays.asList(inputString.split(stringPattern)));
    }



}
