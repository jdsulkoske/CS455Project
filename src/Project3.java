import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Math.abs;

public class Project3 {

    private int nextIndex3;
    private int nextIndex2;
    private int nextIndex1;


    double[][] dataItems;
    double[][] newCenter;
    double[][] oldCenter;
    ArrayList<Double> centerSimilarityList;
    ArrayList<double[][]> clusters;

    private String[][] dataArr;

    private BufferedWriter br;
    private StringBuilder sb;


    private boolean same = false;

    private int iteration;
    private int numberOfClusters;
    private int dataset;
    private DecimalFormat df;


    public static void main(String[] args) throws IOException {

        Project3 project = new Project3();

    }

    public Project3() throws IOException {
        formCluster();

    }

    private void formCluster() throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("Type 1 for Chicago Crime Dataset or Type 2 for Weka dataset");
        dataset = input.nextInt();
        System.out.println("Enter Value of Clusters");
        numberOfClusters = input.nextInt();
        dataItems = new double[10000][4];
        newCenter = new double[numberOfClusters][4];
        oldCenter = new double[numberOfClusters][4];
        centerSimilarityList = new ArrayList<Double>();
        clusters = new ArrayList<double[][]>();

        loadInData();
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
        if (newCenter[0][0] == oldCenter[0][0] && newCenter[1][0] == oldCenter[1][0]) {
            return true;

        } else {
            clusters.clear();
            //newArray.clear();
        }
        return false;
    }

    private void loadInData() throws IOException {
        CSVReader bufferReader = null;
        if(dataset==1) {
            bufferReader = new CSVReader(new FileReader("src/CSV2.csv"));
        }
        if(dataset==2){
            bufferReader = new CSVReader(new FileReader("src/weka.csv"));

        }

        List<String[]> list = bufferReader.readAll();

        dataArr = new String[list.size()][];
        dataArr = list.toArray(dataArr);
    }

    private void printNewCenterValue() throws IOException {
        for (int i = 0; i < newCenter.length; i++) {
            System.out.print("New Center" + (i + 1) + " : ");
            for (int j = 0; j < 4; j++) {

                System.out.print(df.format(newCenter[i][j]) + " , ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printFinalResults(int iter) throws IOException {
        if(dataset==1) {
            br = new BufferedWriter(new FileWriter("CrimeClusterResults.csv"));
        }
        if(dataset==2){
            br = new BufferedWriter(new FileWriter("WekaClusterResults.csv"));
        }

        sb = new StringBuilder();
        for (int i = 0; i < clusters.size(); i++) {
            if (i == 0) {
                System.out.println("Cluster " + (i + 1) +  " size " +" " + nextIndex1);
                sb.append("Cluster " + (i + 1) +  " size " +" " + nextIndex1 + "\n\n");
            }
            if (i == 1) {
                System.out.println("Cluster " + (i + 1) +  " size " + " " + nextIndex2);
                sb.append("Cluster " + (i + 1) +  " size " +" " + nextIndex2+ "\n\n");

            }
            if(i==2){
                System.out.println("Cluster " + (i + 1) +  " size " +" " + nextIndex3);
                sb.append("Cluster " + (i + 1) +  " size " +" " + nextIndex3+ "\n\n");


            }
            System.out.println("Cluster " + (i + 1));
            for(int j = 0; j< clusters.get(i).length; j++) {
                for(int k=0;k<4;k++) {
                    if(clusters.get(i)[j][0]==0.0 && clusters.get(i)[j][1]==0.0 && clusters.get(i)[j][2]==0.0 && clusters.get(i)[j][3]==0.0){
                            //do nothing
                    }
                    else {
                        System.out.print(clusters.get(i)[j][k] + " , ");
                        sb.append(clusters.get(i)[j][k]);
                        sb.append(",");
                    }

                }
                if(clusters.get(i)[j][0]==0.0 && clusters.get(i)[j][1]==0.0 && clusters.get(i)[j][2]==0.0 && clusters.get(i)[j][3]==0.0){
                        //do nothing
                }
                else {
                    sb.append("Cluster" + (i + 1));
                    sb.append("\n");
                    System.out.print(" Cluster " + (i+1));
                    System.out.println();
                }

//                sb.append(" Cluster "+(i+1));
            }

            sb.append("\n\n");


        }
        br.write(sb.toString());
        br.close();
        System.out.println("Number of Itrations: " + iter);
    }

    private void assignIndexToCluster() {
        nextIndex1 = 0;
        nextIndex2 = 0;
        nextIndex3 = 0;
        for (int i = 1; i < dataItems.length; i++) {
            centerSimilarityList.clear();
            findDistance(dataItems[i]);

            assignIndexToClosestCentroid(i);

            incrementIndex();

        }
        centerSimilarityList.removeAll(centerSimilarityList);
        assignNewCenter();
    }

    private void assignNewCenter() {
        for (int i = 0; i < numberOfClusters; i++) {
            for (int j = 0; j < 4; j++) {
                oldCenter[i][j] = newCenter[i][j];
                if (clusters.get(i)[i] != null) {
                    newCenter[i][j] = average(clusters.get(i), j);

                }

            }
        }
    }

    private void incrementIndex() {
        if (centerSimilarityList.indexOf(Collections.min(centerSimilarityList)) == 0) {
            if (nextIndex1 != clusters.get(0).length - 1 && !(nextIndex1 >= 10000)) {
                ++nextIndex1;
            }

        }

        if (centerSimilarityList.indexOf(Collections.min(centerSimilarityList)) == 1) {
            if (nextIndex2 != clusters.get(1).length - 1 && !(nextIndex2 >= 10000)) {
                ++nextIndex2;
            }

        }
        if (centerSimilarityList.indexOf(Collections.min(centerSimilarityList)) == 2) {
            if (nextIndex3 != clusters.get(1).length - 1 && !(nextIndex3 >= 10000)) {
                ++nextIndex3;
            }
        }
    }

    private void assignIndexToClosestCentroid(int i) {
        for (int j = 0; j < 4; j++) {

            if (centerSimilarityList.indexOf(Collections.min(centerSimilarityList)) == 0) {
                clusters.get(centerSimilarityList.indexOf(Collections.min(centerSimilarityList)))[nextIndex1][j] = dataItems[i][j];

            }

            if (centerSimilarityList.indexOf(Collections.min(centerSimilarityList)) == 1) {

                clusters.get(centerSimilarityList.indexOf(Collections.min(centerSimilarityList)))[nextIndex2][j] = dataItems[i][j];

            }
            if (centerSimilarityList.indexOf(Collections.min(centerSimilarityList)) == 2) {
                clusters.get(centerSimilarityList.indexOf(Collections.min(centerSimilarityList)))[nextIndex3][j] = dataItems[i][j];
            }
        }
    }

    private void findDistance(double[] dataItem) {
        double dissimilarity = 1;
        for (int l = 0; l < newCenter.length; l++) {

            for (int j = 0; j < 4; j++) {
                double subtractObjects = (abs(newCenter[l][j] - dataItem[j]));
                double temp = (.25) * subtractObjects;
                dissimilarity = +temp;

            }

            double similarity = Double.parseDouble(String.valueOf(dissimilarity));
            df = new DecimalFormat(("#.00"));
            centerSimilarityList.add(Double.valueOf(df.format(abs(similarity))));
        }

    }

    private void createNewCluster() {
        for (int i = 0; i < numberOfClusters; i++) {
            clusters.add(new double[dataArr.length][4]);

        }
    }

    private void assignRandomPoint(int k) {
        for (int i = 0; i < dataItems.length; i++) {
            Random r = new Random();
            int random = r.nextInt(dataArr.length - 1);
            if (i < k) {
                System.out.print("Random Center" + (i + 1) + " is ");
                for (int j = 0; j < 4; j++) {
                    newCenter[i][j] = (dataItems[random][j]);


                    System.out.print(" , " + newCenter[i][j]);
                }
                System.out.println();
            }

        }
        System.out.println();
    }


    public double average(double[][] list, int j) {
        double sum = 0;
        double[][] newList = new double[1][4];
        int length = 1;
        if (list == clusters.get(0)) {
            length = nextIndex1;

        }
        if (list == clusters.get(1)) {
            length = nextIndex2;


        }
        if(clusters.size()==3) {
            if (list == clusters.get(2)) {
                length = nextIndex3;
            }
        }

        for (int i = 0; i < list.length; i++) {
            sum = sum + list[i][j];
        }
        return newList[0][j] = sum / length;

    }

    private double[][] getCSV2Matrix() {
        double[][] matrix = new double[dataArr.length][4];
        for (int i = 1; i < dataArr.length; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = Double.valueOf(dataArr[i][j]);
            }
        }

        return matrix;
    }


}