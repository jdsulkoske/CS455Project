import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Math.abs;

public class Project3 {

    private int nextIndex3;
    private int nextIndex2;
    private int nextIndex1;


    double[][] dataItems;
    double[][] cz;
    double[][] oldCz;
    ArrayList<Double> row;
    ArrayList<double[][]> groups;
    Scanner input;

    Map<String, Double> map = new HashMap<String, Double>();
    private ArrayList<String> keyValueArray;

    private String[][] dataArr;

    private BufferedWriter br;
    private StringBuilder sb;
    private ArrayList<double[][]> matrix;

    private boolean same = false;
    private ArrayList<double[][]> newArray;
    private int iteration;
    private int numberOfClusters;


    public static void main(String[] args) throws IOException {

        Project3 project = new Project3();

    }

    public Project3() throws IOException {
        formCluster();

    }

    private void formCluster() throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Value of K");
        numberOfClusters = input.nextInt();


        dataItems = new double[10000][4];
        cz = new double[numberOfClusters][4];
        oldCz = new double[numberOfClusters][4];
        row = new ArrayList<Double>();
        groups = new ArrayList<double[][]>();
        newArray = new ArrayList<double[][]>();

        loadInData();
        matrix = new ArrayList<double[][]>();

        dataItems = getCSV2Matrix();
        assignRandomPoint(numberOfClusters);
        iteration = 0;


        do {
            if (checkIfCenterChanged()) break;
            createNewCluster();
            assignIndexToCluster();
            ++iteration;
        } while (iteration >= 0);

        printNewCenterValue();
        printFinalResults(iteration);
    }

    private boolean checkIfCenterChanged() {
        if (cz[0][0] == oldCz[0][0] && cz[1][0] == oldCz[1][0]) {
            System.out.println("New Center for 2: " + cz[0][1]);
            System.out.println("Old Center for 1: " + oldCz[0][1]);
            return true;

        } else {
            groups.clear();
            newArray.clear();
        }
        return false;
    }

    private void loadInData() throws IOException {
        CSVReader bufferReader = null;
        bufferReader = new CSVReader(new FileReader("src/Crimes_-_2016.csv"));
        List<String[]> list = bufferReader.readAll();
        dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);
    }

    private void printNewCenterValue() {
        for (int i = 0; i < cz.length; i++) {
            System.out.print("New C" + (i + 1) + " ");
            for (int j = 0; j < 4; j++) {

                System.out.print(", " + cz[i][j]);
            }
            System.out.println();
        }
    }

    private void printFinalResults(int iter) {
        for (int i = 0; i < groups.size(); i++) {

            System.out.println("Group " + (i + 1));
            for(int j=0;j<groups.get(i).length;j++) {
                for(int k=0;k<4;k++) {
                    //System.out.println(Arrays.deepToString(groups.get(i)));

                    System.out.print(groups.get(i)[j][k]+" , ");


                }
                System.out.print(" Center " + (i+1));
                System.out.println();
            }
            if (i == 0) {
                System.out.println("Group size " + (i + 1) + " " + nextIndex1);
            }
            if (i == 1) {
                System.out.println("Group size " + (i + 1) + " " + nextIndex2);
            }
            if(i==2){
                System.out.println("Group size " + (i + 1) + " " + nextIndex3);

            }

        }
        System.out.println("Number of Itrations: " + iter);
    }

    private void assignIndexToCluster() {
        nextIndex1 = 0;
        nextIndex2 = 0;
        nextIndex3 = 0;
        for (int i = 1; i < dataItems.length; i++) {
            row.clear();
            findDistance(dataItems[i]);

            assignIndexToClosestCentroid(i);

            incrementIndex();

        }

        row.removeAll(row);
        assignNewCenter();
    }

    private void assignNewCenter() {
        for (int i = 0; i < numberOfClusters; i++) {
            for (int j = 0; j < 4; j++) {
                oldCz[i][j] = cz[i][j];
                if (groups.get(i)[i] != null) {
                    cz[i][j] = average(groups.get(i), j);

                }

            }
        }
    }

    private void incrementIndex() {
        if (row.indexOf(Collections.min(row)) == 0) {
            if (nextIndex1 != groups.get(0).length - 1 && !(nextIndex1 >= 10000)) {
                ++nextIndex1;
            }

        }

        if (row.indexOf(Collections.min(row)) == 1) {
            if (nextIndex2 != groups.get(1).length - 1 && !(nextIndex2 >= 10000)) {
                ++nextIndex2;
            }

        }
        if (row.indexOf(Collections.min(row)) == 2) {
            if (nextIndex3 != groups.get(1).length - 1 && !(nextIndex3 >= 10000)) {
                ++nextIndex3;
            }
        }
    }

    private void assignIndexToClosestCentroid(int i) {
        for (int j = 0; j < 4; j++) {

            if (row.indexOf(Collections.min(row)) == 0) {
                groups.get(row.indexOf(Collections.min(row)))[nextIndex1][j] = dataItems[i][j];

            }

            if (row.indexOf(Collections.min(row)) == 1) {

                groups.get(row.indexOf(Collections.min(row)))[nextIndex2][j] = dataItems[i][j];

            }
            if (row.indexOf(Collections.min(row)) == 2) {
                groups.get(row.indexOf(Collections.min(row)))[nextIndex3][j] = dataItems[i][j];
            }
        }
    }

    private void findDistance(double[] dataItem) {
        double dissimilarity = 0;
        for (int l = 0; l < cz.length; l++) {

            for (int j = 0; j < 4; j++) {

//                       double subtractObjects = (abs(cz[l][j] - dataItems[i][j]));
                double temp = (abs(cz[l][j] - dataItem[j]));
                dissimilarity = +temp;

            }

            DecimalFormat df = new DecimalFormat(("#.00"));
            double similarity = Double.parseDouble(df.format(dissimilarity));

            row.add(abs(similarity));
        }

    }

    private void createNewCluster() {
        for (int i = 0; i < numberOfClusters; i++) {
            groups.add(new double[dataArr.length][4]);

        }
    }

    private void assignRandomPoint(int k) {
        for (int i = 0; i < dataItems.length; i++) {
            Random r = new Random();
            int random = r.nextInt(dataArr.length - 1);
            if (i < k) {
                System.out.print("C" + (i + 1) + " is ");
                for (int j = 0; j < 4; j++) {
                    cz[i][j] = (dataItems[random][j]);

                    System.out.print(" , " + cz[i][j]);
                }
                System.out.println();
            }

        }
    }


    public double average(double[][] list, int j) {
        double sum = 0;
        double[][] newList = new double[1][4];
        int length = 0;
        if (list == groups.get(0)) {
            length = nextIndex1;

        }
        if (list == groups.get(1)) {
            length = nextIndex2;


        }
        if(groups.size()==3) {
            if (list == groups.get(2)) {
                length = nextIndex3;
            }
        }

        for (int i = 0; i < list.length; i++) {
            sum = sum + list[i][j];
        }
        return newList[0][j] = sum / length;

    }

    private double[][] getCSV2Matrix() {
        assignNumberValueToKey();
        double[][] matrix = new double[dataArr.length][4];
        for (int i = 1; i < dataArr.length; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = map.get(dataArr[i][j]);
            }
        }

        return matrix;
    }


    private void assignNumberValueToKey() {
        keyValueArray = new ArrayList<String>();
        double count = 0;
        int min = getMin();
        int max = getMax();
        for (int i = 1; i < dataArr.length; i++) {
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

    private int getMax() {
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

    private int getMin() {
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
}