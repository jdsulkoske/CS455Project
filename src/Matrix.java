import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by FarisShatat on 2/9/16.
 */
public class Matrix {

    private ArrayList<String> csvDataArray = new ArrayList<String>();
    private ArrayList newArray = new ArrayList();
    private String[] splitData;
    private String input;
    private int index;
    private double average;
    Map<String, Integer> map = new HashMap<String, Integer>();
    private ArrayList<String> keyValueArray;

    public static void main(String[] args) {
        Matrix data = new Matrix();
        data.parseData("Case beat", 0);
        //data.printGraph(RandomArray());
        System.out.println();


    }

    public void parseData(String input, int index) {
        CSVReader bufferReader = null;
        //csvDataArray.clear();
        //newArray.clear();

        this.input = input;
        this.index = index;

        try {
            String[] line;
            bufferReader = new CSVReader(new FileReader("src/CSV2.csv"));


            while ((line = bufferReader.readNext()) != null) {
                for(int i= 0;i<line.length;i++) {
                    splitCSVToArray(line[i]);


                }
                findFrequency();
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

        printGraph(getMatrix());

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

    public void findFrequency() {
        keyValueArray = new ArrayList<String>();
        int count = 0;
        for (int i = 1; i < csvDataArray.size(); i++) {
            if (map.containsKey(csvDataArray.get(i))) {
                map.put(csvDataArray.get(i), map.get(csvDataArray.get(i)));
            } else {
                map.put(csvDataArray.get(i), count++);
            }

        }
        for (Object k : map.keySet()) {
            keyValueArray.add((String) k);
        }

        //map.clear();
        //keyValueArray.clear();
    }

    public int [][]getMatrix(){
        int[][] randomMatrix = new int [1001][4];
        for (int i = 1; i < 1001; i++) {
            for(int j=0;j<4;j++) {

                //System.out.println(i + ". " + map.get(csvDataArray.get(i)));
                randomMatrix[i][j]=map.get(csvDataArray.get(i));
            }
        }
        return randomMatrix;
    }


    public static int[][] RandomArray() {
        int[][] randomMatrix = new int [1000][4];

        Random rand = new Random();
        //rand.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                Integer r = rand.nextInt(10000);
                randomMatrix[i][j] = r;
            }

        }

        return randomMatrix;
    }

    public void printGraph(int[][] array) {
        for (int i = 0; i < 1001; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(array[i][j]+"|");
            }
            System.out.println();
        }
    }
}
