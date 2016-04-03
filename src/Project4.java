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
        String[][] matrix = new String[100][5];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 5; j++) {
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
        ArrayList<String> countValueArray = new ArrayList<String>();
        ArrayList<Double> resultArray = new ArrayList<>();

        for(int l =0;l<5;l++) {
            for (int i = 1; i < dataItems.length; i++) {
                if (l != 2) {
                    if (dataItems[i][2].equals("TRUE")) {
                        if (map.containsKey(dataItems[i][l] + " is True")) {
                            map.put(dataItems[i][l] + " is True", map.get(dataItems[i][l] + " is True") + 1);

                        } else {
                            map.put(dataItems[i][l] + " is True", 1);

                        }
                    } else if (dataItems[i][2].equals("FALSE")) {
                        if (map.containsKey(dataItems[i][l] + " is False")) {
                            map.put(dataItems[i][l] + " is False", map.get(dataItems[i][l] + " is False") + 1);

                        } else {
                            map.put(dataItems[i][l] + " is False", 1);


                        }
                    }
                }


            }
            for (Object k : map.keySet()) {
                keyValueArray.add((String) k);
                Collections.sort(keyValueArray);
            }
            double results = 0;
            double size = 0;
            for (int j = 0; j < keyValueArray.size(); j += 2) {
                if (j != keyValueArray.size() - 1) {
                    double value = map.get(keyValueArray.get(j));
                    double a = map.get(keyValueArray.get(j + 1));
                    size = value + a;

                    double calc = -(Math.log(value / size) / Math.log(2) * (value / size));
                    double t = -(Math.log(a / size) / Math.log(2)) * (a / size);
                    double r = calc + t;
                    //System.out.println(size);
                    double csize = size / dataItems.length;
                    //System.out.println(csize);
                    double p = csize * r;
                    //System.out.println(p);
                    results = +p;


                }

                // System.out.println(a);
            }
            System.out.println(results);
            System.out.println(map.size());
//            resultArray.add(results);
//            for (int i = 0)
//                System.out.println("Entropy 1 " + results);


//        DecimalFormat df = new DecimalFormat(("#.00"));
//        for (int i = 0; i < map.size(); i++) {
//            double value = (map.get(keyValueArray.get(i)));
//            value = (value / dataArr.length) * 100;
//            String newValue = df.format(value);
//
//            //System.out.println(keyValueArray.get(i) + " is " + newValue + "%" +"; frequency: "+ map.get(keyValueArray.get(i)));
//        }
            System.out.println("______________________________________________");

            map.clear();
            keyValueArray.clear();
        }
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
