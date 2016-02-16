import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Project1 {


    private ArrayList<String> csvDataArray = new ArrayList<String>();
    private ArrayList newArray = new ArrayList();
    private String[] splitData;
    private String input;
    private int index;
    private double average;
    Map<String, Integer> map = new HashMap<String, Integer>();
    private ArrayList<String> keyValueArray;

    public static void main(String[] args) {
        Project1 data = new Project1();
        data.parseData("Case beat", 3);
        System.out.println();
        data.parseData("Community Area", 4);
        data.parseData("Primary Type", 0);
        data.parseData("Desciption", 1);
        data.parseData("Arrest", 2);

    }

    public void parseData(String input, int index) {
        CSVReader bufferReader = null;
        csvDataArray.clear();
        newArray.clear();

        this.input = input;
        this.index = index;
        System.out.println("Results for " +this.input );
        System.out.println();
        try {
            String[] line;
            bufferReader = new CSVReader(new FileReader("src/Crimes_-_2016.csv"));


            while ((line = bufferReader.readNext()) != null) {

                splitCSVToArray(line[this.index]);
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

        if (input.equals("Case beat") || input.equals("Community Area")) {
            getMax();
            getMin();
            findMedian();
            findMean();
            findStandardDeviation();
        } else {
            findFrequency();

        }
    }
    public void getMax() {
        int max = 0;
        for (int i = 1; i < 10000; i++) {
            int number = Integer.parseInt(csvDataArray.get(i));
            if (number > max) {
                max = number;
            }
        }
        System.out.println("The maximum Value for "+input +" " + max);

    }
    public ArrayList<String> splitCSVToArray(String csv) {
        if (csv != null) {
            splitData = csv.split("\\s*,\\s*");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    csvDataArray.add(splitData[i].trim());
                }
            }
        }

        return csvDataArray;
    }

    public void getMin() {
        int min = Integer.parseInt(csvDataArray.get(1));

        for (int i = 1; i < 10000; i++) {
            newArray.add(Integer.parseInt(csvDataArray.get(i)));
            int number = Integer.parseInt(csvDataArray.get(i));
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
        System.out.println("The median value: " + medianValue);
    }

    public void findMean() {
        int sum = 0;

        for (int i = 0; i < newArray.size(); i++)
            sum = sum + Integer.parseInt(String.valueOf(newArray.get(i)));

        average = sum / newArray.size();
        System.out.println("The mean value: " +average);

    }

    public void findStandardDeviation() {
        double mean = average;
        double temp = 0;


        double sd = 0;
        double variance = 0;
        for (Object keyValueArray : newArray) {
            temp += (mean - Double.parseDouble(String.valueOf(keyValueArray)) * (mean - Double.parseDouble(String.valueOf(keyValueArray))));
            variance = temp / newArray.size();
            double number = variance / 2;

        }
        System.out.println("The standard deviation: "+ Math.sqrt(variance));

    }



    public void findFrequency() {
        keyValueArray = new ArrayList<String>();

        for (int i = 1; i < csvDataArray.size(); i++) {
            if (map.containsKey(csvDataArray.get(i))) {
                map.put(csvDataArray.get(i), map.get(csvDataArray.get(i)) + 1);
            } else {
                map.put(csvDataArray.get(i), 1);
            }

        }
        for (Object k : map.keySet()) {
            keyValueArray.add((String) k);
        }

        DecimalFormat df = new DecimalFormat(("#.00"));
        for (int i = 0; i < map.size(); i++) {
            double value = (map.get(keyValueArray.get(i)));
            value = (value / csvDataArray.size()) * 100;
            String newValue = df.format(value);

            System.out.println(keyValueArray.get(i) + " is " + newValue + "%" +"; frequency: "+ map.get(keyValueArray.get(i)));
        }
        System.out.println("______________________________________________");

        map.clear();
        keyValueArray.clear();
    }




}

