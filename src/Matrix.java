import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by FarisShatat on 2/9/16.
 */
public class Matrix {

    private ArrayList<String> csvDataArray = new ArrayList<String>();
    private ArrayList newArray = new ArrayList();
    Map<String, Double> map = new HashMap<String, Double>();
    private ArrayList<String> keyValueArray;
    String[][] dataArr;
  public static void main(String[] args) {
        Matrix data = new Matrix();
        data.parseData();
        System.out.println();


    }

    public void parseData() {
        CSVReader bufferReader = null;
        csvDataArray.clear();
        newArray.clear();


        try {
            bufferReader = new CSVReader(new FileReader("src/Crimes_-_2016.csv"));
            List<String[]> list = bufferReader.readAll();
            dataArr = new String[list.size()][];
            dataArr = list.toArray(dataArr);
            printCSV1(randomMatrix());
            printCSV2(getMatrix());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferReader != null) bufferReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void assignNumberValueToKey() {
        keyValueArray = new ArrayList<String>();
        double count = 0;
        int min = getMin();
        int max = getMax();
        for (int i = 0; i < dataArr.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (map.containsKey(dataArr[i][j])) {
                    if (j == 3) {
                        double s = (Integer.valueOf(dataArr[i][j]) - min);
                        double normalize = (Double.valueOf(dataArr[i][j]) - min) / (max - min);
                        DecimalFormat df = new DecimalFormat(("#.00"));
                        normalize = Double.parseDouble(df.format(normalize));
                        map.put(dataArr[i][j], normalize);
                    }
                    if (dataArr[i][j].equals("TRUE")) {
                        map.put(dataArr[i][j], 1.0);

                    }
                    if (dataArr[i][j].equals("FALSE")) {
                        map.put(dataArr[i][j], 0.0);

                    } else {
                        checkAndConvertCategoricalAttributeToNumberValue(i, j);
                    }
                } else {
                    map.put(dataArr[i][j], count++);

                }
            }


        }

        for (Object k : map.keySet()) {
            keyValueArray.add((String) k);
        }

    }

    public int getMax() {
        int max = 0;
        for (int i = 1; i < dataArr.length; i++) {
            for (int j = 0; j < 4; j++) {
                int number = Integer.parseInt(dataArr[i][3]);
                if (number > max) {
                    max = number;
                }
            }
        }
        return max;
    }

    public int getMin() {
        int min = Integer.parseInt(dataArr[1][3]);
        for (int i = 1; i < dataArr.length; i++) {
            for (int j = 0; j < 4; j++) {
                int number = Integer.parseInt(dataArr[i][3]);
                if (number < min) {
                    min = number;
                }
            }
        }
        return min;


    }

    private void checkAndConvertCategoricalAttributeToNumberValue(int i, int j) {
        if (j == 0 && (dataArr[i][j].equals("LIQUOR LAW VIOLATION")
                || dataArr[i][j].equals("OTHER OFFENSE") ||
                dataArr[i][j].equals("NON-CRIMINAL (SUBJECT SPECIFIED)") ||
                dataArr[i][j].equals("STALKING ")
                ||
                dataArr[i][j].equals("PUBLIC PEACE VIOLATION"))) {
            map.put(dataArr[i][j], 1.0);
        }
        if (j == 0 && (dataArr[i][j].equals("THEFT")
                || dataArr[i][j].equals("BURGLARY") ||
                dataArr[i][j].equals("CRIMINAL TRESPASS") ||
                dataArr[i][j].equals("DECEPTIVE PRACTICE")
                ||
                dataArr[i][j].equals("INTIMIDATION"))) {

            map.put(dataArr[i][j], 2.0);
        }
        if (j == 0 && (dataArr[i][j].equals("NARCOTICS")
                || dataArr[i][j].equals("CRIMINAL DAMAGE") ||
                dataArr[i][j].equals("INTERFERENCE WITH PUBLIC OFFICER") ||
                dataArr[i][j].equals("WEAPONS VIOLATION")
                ||
                dataArr[i][j].equals("BATTERY"))) {
            map.put(dataArr[i][j], 3.0);
        }
        if (j == 0 && (dataArr[i][j].equals("ARSON")
                || dataArr[i][j].equals("PROSTITUTION") ||
                dataArr[i][j].equals("ROBBERY") ||
                dataArr[i][j].equals("MOTOR VEHICLE THEFT")
                ||
                dataArr[i][j].equals("CRIM SEXUAL ASSAULT"))) {
            map.put(dataArr[i][j], 4.0);
        }
        if (j == 0 && (dataArr[i][j].equals("ASSAULT")
                || dataArr[i][j].equals("OFFENSE INVOLVING CHILDREN") ||
                dataArr[i][j].equals("SEX OFFENSE") ||
                dataArr[i][j].equals("KIDNAPPING")
                ||
                dataArr[i][j].equals("HOMICIDE"))) {
            map.put(dataArr[i][j], 5.0);
        } else {
            map.put(dataArr[i][j], map.get(dataArr[i][j]));
        }
    }

    public double[][] getMatrix() {
        assignNumberValueToKey();
        double[][] randomMatrix = new double[1000][4];
        for (int i = 1; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                randomMatrix[i][j] = map.get(dataArr[i][j]);
            }
        }

        return randomMatrix;
    }


    public String[][] randomMatrix() {
        String[][] randomMatrix = new String[1000][4];
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                Integer r = rand.nextInt(dataArr.length);
                randomMatrix[i][j] = dataArr[r][j];
            }

        }

        return randomMatrix;
    }


    public void printCSV2(double[][] array) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter("CSV2.csv"));
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(array[i][j] + ", ");
                sb.append(array[i][j]);
                sb.append(",");

            }
            sb.append("\n");
            System.out.println();

        }
        br.write(sb.toString());
        br.close();

    }

    public void printCSV1(String[][] array) throws IOException {

        BufferedWriter br = new BufferedWriter(new FileWriter("CSV1.csv"));
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(array[i][j] + ", ");
                sb.append(array[i][j]);
                sb.append(",");

            }
            sb.append("\n");
            System.out.println();

        }
        br.write(sb.toString());
        br.close();

    }

}
