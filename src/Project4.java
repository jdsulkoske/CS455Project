import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by FarisShatat on 3/25/16.
 */
public class Project4 {

    private String[][] dataArr;
    private String[][] dataItems;
    private ArrayList<String> classLabel = new ArrayList<>();
    private String[][] classifiedMatrix;
    private BufferedWriter br;
    private StringBuilder sb;
    Map<String, Integer> map = new HashMap<String, Integer>();
    Map<String, Integer> countMap = new HashMap<String, Integer>();
    private ArrayList<String> keyValueArray;

    public static void main(String[] args) throws IOException {

        Project4 project = new Project4();

    }

    public Project4() throws IOException {

        loadInData();
        dataItems = generateMatrix();
        findFrequency();
    }

    private void loadInData() throws IOException {
        CSVReader bufferReader = null;

        bufferReader = new CSVReader(new FileReader("src/Crimes_-_2016.csv"));

        List<String[]> list = bufferReader.readAll();

        dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);


    }

    private String[][] generateMatrix() {
        String[][] matrix = new String[100][5];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = dataArr[i][j];

            }
        }


        return matrix;
    }


    public void findFrequency() {
        keyValueArray = new ArrayList<String>();
        int numberOfTimesTrue;
        int numberOfTimesFalse;
        double temp = 0;
        double informationGained = 0;
        int rootAttribute = 0;

        for (int l = 0; l < 5; l++) {
            numberOfTimesTrue = 0;
            numberOfTimesFalse = 0;
            for (int i = 1; i < dataItems.length; i++) {
                if (l != 2) {
                    if (dataItems[i][2].equals("TRUE")) {
                        if (map.containsKey(dataItems[i][l] + " is True")) {
                            map.put(dataItems[i][l] + " is True", map.get(dataItems[i][l] + " is True") + 1);
                            numberOfTimesTrue++;

                        } else {
                            map.put(dataItems[i][l] + " is True", 1);
                            numberOfTimesTrue++;

                        }
                    } else if (dataItems[i][2].equals("FALSE")) {
                        if (map.containsKey(dataItems[i][l] + " is False")) {
                            map.put(dataItems[i][l] + " is False", map.get(dataItems[i][l] + " is False") + 1);
                            numberOfTimesFalse++;

                        } else {
                            map.put(dataItems[i][l] + " is False", 1);
                            numberOfTimesFalse++;


                        }
                    }

                }

            }
            for (Object k : map.keySet()) {
                keyValueArray.add((String) k);

            }
            Collections.sort(keyValueArray);
            double entropy = calculateInformationGain();
            System.out.println(entropy);
            double overalResults = getEntropy(numberOfTimesTrue, numberOfTimesFalse);
            // System.out.println(overalResults);

            double cal = overalResults - entropy;
            if (cal > temp) {
                rootAttribute = l;
                informationGained = cal;
                temp = cal;
            }
            System.out.println("______________________________________________");
            map.clear();
            keyValueArray.clear();
        }
        System.out.println("The Information gained " + informationGained);
        System.out.println(rootAttribute);

    }

    private double calculateInformationGain() {
        double entropy = 0;
        double numberOfTimesCategoryOccursInDataSet = 0;
        for (int j = 0; j < keyValueArray.size(); j += 2) {
            if (j != keyValueArray.size() - 1) {
                double value = map.get(keyValueArray.get(j));
                double a = map.get(keyValueArray.get(j + 1));
                numberOfTimesCategoryOccursInDataSet = value + a;

                double truevalue = -(Math.log(value / numberOfTimesCategoryOccursInDataSet) / Math.log(2) * (value / numberOfTimesCategoryOccursInDataSet));
                double falseValue = -(Math.log(a / numberOfTimesCategoryOccursInDataSet) / Math.log(2)) * (a / numberOfTimesCategoryOccursInDataSet);
                double result = truevalue + falseValue;

                double fractionOfASpecificCategory = numberOfTimesCategoryOccursInDataSet / dataItems.length;

                double calculation = fractionOfASpecificCategory * result;

                entropy = +calculation;


            }

        }
        return entropy;
    }

    private double getEntropy(int trueVal, int falseVal) {
        double fractionOfTrueElements = ((double) trueVal / dataItems.length);
        double fractionOfFalseElements = ((double) falseVal / dataItems.length);
        double trueValue = -(Math.log(fractionOfTrueElements) / Math.log(2) * (fractionOfTrueElements));
        double falseValue = -(Math.log(fractionOfFalseElements) / Math.log(2) * (fractionOfFalseElements));
        double results = trueValue + falseValue;

        System.out.println(results);
        return results;

    }
}
