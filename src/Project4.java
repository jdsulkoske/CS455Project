import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArrayList<String> keyValueArray;

    public static void main(String[] args) throws IOException {

        Project4 project = new Project4();

    }

    public Project4() throws IOException {

        loadInData();
        dataItems = getCSV2Matrix();
        //classify();
        //classifiedMatrix = newMatrix();
        //printCSV2();
        findFrequency();
    }

    private void loadInData() throws IOException {
        CSVReader bufferReader = null;

        bufferReader = new CSVReader(new FileReader("src/Crimes_-_2016.csv"));

        List<String[]> list = bufferReader.readAll();

        dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);


    }

    private String[][] getCSV2Matrix() {
        String[][] matrix = new String[100][4];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 4; j++) {
                    matrix[i][j] = dataArr[i][j];

            }
        }


        return matrix;
    }

//    private void classify() {
//        int count1 = 0;
//        int count2 = 0;
//        for (int i = 0; i < dataItems.length; i++) {
//            for (int j = 0; j < 3; j++) {
//                if (dataItems[i][j].contains("ASSAULT") | dataItems[i][j].contains("BATTERY")) {
//                    System.out.println(i + " " + dataItems[i][j]);
//                    classLabel.add(i,"Yes");
//                    count1++;
//                    j=2;
//                } else {
//                    count2++;
//                    j=2;
//                    classLabel.add(i,"No");
//                }
//            }
//
//        }
//        System.out.println(count1);
//        System.out.println(count2);
//    }

    public void findFrequency() {
        keyValueArray = new ArrayList<String>();

        for (int i = 1; i < dataItems.length; i++) {

                 if (dataItems[i][2].equals("TRUE")) {
                    if (map.containsKey(dataItems[i][0] + " is True")) {
                        map.put(dataItems[i][0] + " is True", map.get(dataItems[i][0]+ " is True") + 1);
                    }
                     else{
                        map.put(dataItems[i][0] + " is True", 1);
                    }
                 }
                 else if (dataItems[i][2].equals("FALSE")) {
                     if (map.containsKey(dataItems[i][0] + " is False")) {
                         map.put(dataItems[i][0] + " is False",map.get(dataItems[i][0]+ " is False") + 1);
                     }
                     else{
                         map.put(dataItems[i][0] + " is False", 1);

                     }
                 }


        }
        for (Object k : map.keySet()) {
            keyValueArray.add((String) k);
        }

        DecimalFormat df = new DecimalFormat(("#.00"));
        for (int i = 0; i < map.size(); i++) {
            double value = (map.get(keyValueArray.get(i)));
            value = (value / dataArr.length) * 100;
            String newValue = df.format(value);

            System.out.println(keyValueArray.get(i) + " is " + newValue + "%" +"; frequency: "+ map.get(keyValueArray.get(i)));
        }
        System.out.println("______________________________________________");

        map.clear();
        keyValueArray.clear();
    }

//    private String[][] newMatrix() {
//        String[][] matrix = new String[100][4];
//        for (int i = 0; i < dataItems.length; i++) {
//            for (int j = 0; j < 4; j++) {
//                if (j == 3) {
//                    if(i==0){
//                        matrix[0][3]="Arrest";
//                    }
//                    else {
//                        matrix[i][3] = classLabel.get(i);
//                    }
//                } else {
//                    matrix[i][j] = dataItems[i][j];
//                }
//            }
//        }
//        return matrix;
//    }
//    private void printCSV2() throws IOException {
//        br = new BufferedWriter(new FileWriter("Classification.csv"));
//        sb = new StringBuilder();
//        for (int i = 0; i < classifiedMatrix.length; i++) {
//            for (int j = 0; j < 4; j++) {
//                System.out.print(classifiedMatrix[i][j] + ", ");
//                sb.append(classifiedMatrix[i][j]);
//                sb.append(",");
//
//            }
//            sb.append("\n");
//            System.out.println();
//
//        }
//        br.write(sb.toString());
//       br.close();
//
//    }
}
