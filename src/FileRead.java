import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class FileRead {


    ArrayList<String> beatCasesArray = new ArrayList<String>();
    ArrayList newArray = new ArrayList();
    String[] splitData;
    String input;
    int index;
    private double average;
    Map<String, Integer> map = new HashMap<String, Integer>();
    private ArrayList<String> a;

    public static void main(String[] args) {
        FileRead data = new FileRead();
        data.parseData("case beat", 5);
        System.out.println();
        data.parseData("community area", 8);
        data.parseData("primary type", 0);
        data.parseData("desciption", 1);
        data.parseData("arrest", 3);

    }

    public void parseData(String s, int i) {
        CSVReader bufferReader = null;
        beatCasesArray.clear();
        newArray.clear();

        input = s;
        index = i;
        try {
            String[] line;
            bufferReader = new CSVReader(new FileReader("src/Crimes_-_2016.csv"));


            while ((line = bufferReader.readNext()) != null) {

                splitCSVToArray(line[index]);
                //data.splitCSVToArray(line[10]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferReader != null) bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (input.equals("case beat") || input.equals("community area")) {
            getMax();
            getMin();
            findMedian();
            findMean();
            findStandardDeviation();
        } else {
            frequence();

        }
    }

    public ArrayList<String> splitCSVToArray(String csv) {
        if (csv != null) {
            splitData = csv.split("\\s*,\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    beatCasesArray.add(splitData[i].trim());
                }
            }
        }

        return beatCasesArray;
    }

    public void frequence() {
        a = new ArrayList<String>();

        for (int i = 1; i < beatCasesArray.size(); i++) {
            if (map.containsKey(beatCasesArray.get(i))) {
                map.put(beatCasesArray.get(i), map.get(beatCasesArray.get(i)) + 1);
            } else {
                map.put(beatCasesArray.get(i), 1);
            }

        }
        for (Object k : map.keySet()) {
            a.add((String) k);
        }
       // System.out.println(map);
        DecimalFormat df = new DecimalFormat(("#.00"));
        for (int i = 0; i < map.size(); i++) {
            double value = (map.get(a.get(i)));
            value = (value / beatCasesArray.size()) * 100;
            String newValue = df.format(value);

            System.out.println(a.get(i) + " is " + newValue + "%" +" frequency: "+ map.get(a.get(i)));
        }
        System.out.println("______________________________________________");

        map.clear();
        a.clear();
    }


    public void getMax() {
        int max = 0;
        for (int i = 1; i < 10000; i++) {
            int number = Integer.parseInt(beatCasesArray.get(i));
            if (number > max) {
                max = number;
            }
        }
        System.out.println("The maximum Value for "+input +" " + max);

    }


    public void getMin() {
        int min = Integer.parseInt(beatCasesArray.get(1));

        for (int i = 1; i < 10000; i++) {
            newArray.add(Integer.parseInt(beatCasesArray.get(i)));
            int number = Integer.parseInt(beatCasesArray.get(i));
            if (number < min) {
                min = number;
            }
        }
        System.out.println("The minimum Value for "+input +" " + min);

    }

    public void findMedian() {
        Arrays.sort(new ArrayList[]{newArray});
        System.out.println(newArray.size());
        int middle = newArray.size() / 2;
        int medianValue = 0;
        if (newArray.size() % 2 == 1) {
            medianValue = Integer.parseInt(String.valueOf(newArray.get(middle)));
        } else {
            medianValue = (Integer.parseInt((String) newArray.get(middle - 1)) + Integer.parseInt((String) newArray.get(middle))) / 2;
        }
        System.out.println(medianValue);
    }

    public void findMean() {
        int sum = 0;

        for (int i = 0; i < newArray.size(); i++)
            sum = sum + Integer.parseInt(String.valueOf(newArray.get(i)));

        average = sum / newArray.size();
        System.out.println(average);

    }

    public void findStandardDeviation() {
        double mean = average;
        double temp = 0;


        double sd = 0;
        double variance = 0;
        for (Object a : newArray) {
            temp += (mean - Double.parseDouble(String.valueOf(a)) * (mean - Double.parseDouble(String.valueOf(a))));
            variance = temp / newArray.size();
            double number = variance / 2;

        }
        System.out.println("The standard deviation for" + input + "is " + Math.sqrt(variance));

    }


}

