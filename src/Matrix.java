import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by FarisShatat on 2/9/16.
 */
public class Matrix {

    private ArrayList<String> csvDataArray = new ArrayList<String>();
    private ArrayList newArray = new ArrayList();

    Map<String, Integer> map = new HashMap<String, Integer>();
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

            bufferReader = new CSVReader(new FileReader("src/CSV2.csv"));
            List<String[]> list = bufferReader.readAll();
            dataArr = new String[list.size()][];
            dataArr = list.toArray(dataArr);
            printGraph(getMatrix());


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
        int count = 0;
        for (int i = 0; i < dataArr.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (map.containsKey(dataArr[i][j])) {
                    if(j==3){
                        map.put(dataArr[i][j], Integer.valueOf(dataArr[i][j]));
                    }
                    if( dataArr[i][j].equals("TRUE")){
                        map.put(dataArr[i][j], 1);

                    }
                    if( dataArr[i][j].equals("FALSE")){
                        map.put(dataArr[i][j], 0);

                    }
                    else {
                        map.put(dataArr[i][j], map.get(dataArr[i][j]));
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

    public int[][] getMatrix() {
        assignNumberValueToKey();
        int[][] randomMatrix = new int[1000][4];
        for (int i = 1; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                randomMatrix[i][j] = map.get(dataArr[i][j]);

            }
        }

        return randomMatrix;
    }


    public static int[][] RandomArray() {
        int[][] randomMatrix = new int[1000][4];

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
        for (int i = 1; i < 1000; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(array[i][j] + "| ");



            }
            System.out.println();

        }

    }


}
