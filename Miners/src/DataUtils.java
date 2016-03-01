import Jama.Matrix;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataUtils {

    /**
     *returns the data of the store except the month column
     * @param store_number the store for which data is needed
     * @return
     * @throws IOException
     */
    public static Matrix getStoreData(int store_number, String directoryPath) throws IOException {

        List<double[]> listData = new ArrayList<>();
        String line;

        BufferedReader br = new BufferedReader(new FileReader(directoryPath+"/"+store_number+".txt"));

        while ((line = br.readLine()) != null) {

            String rowData[] = line.split("\t");
            //skip month
            double rowDoubleData[] = new double[rowData.length];

            rowDoubleData[0]=1;
            int j = 1;
            // To convert String data into double
            for (int i = 0; i < rowData.length; ++i) {
                //skip month
                if(i==1)
                    continue;
                rowDoubleData[j++] = Double.parseDouble(rowData[i]);
            }
            listData.add(rowDoubleData);
        }

        int data_cols = listData.get(0).length;
        int data_rows = listData.size();

        Matrix matrixData = new Matrix(data_rows, data_cols);

        // storing data in Matrix
        for (int r = 0; r < data_rows; r++) {
            for (int c = 0; c < data_cols ; c++) {
                matrixData.set(r, c, listData.get(r)[c]);
            }
        }


        return matrixData;

    }

    public static void printBeta(Matrix beta, String filePath) throws IOException {
        FileWriter fStream = new FileWriter(filePath,true);     // Output File
        BufferedWriter out = new BufferedWriter(fStream);


        for (int row =0; row<beta.getRowDimension(); row++) {
            out.write(String.valueOf(beta.get(row, 0)));
            out.write("\t");
        }

        out.newLine();
        out.close();


    }

    /**
     * returns matrix of test data set without date col
     * @return
     * @throws IOException
     */
    public static Matrix getKaggleTestSet() throws IOException {
        List<double[]> listData = new ArrayList<>();
        String line;
        boolean flag = true;

        BufferedReader br = new BufferedReader(new FileReader("data/kaggle/test.csv"));

        while ((line = br.readLine()) != null) {

            // To skip the first row in the files
            if (flag == true) {
                flag = false;
                continue;
            }

            String rowData[] = line.split(",");

            double rowDoubleData[] = new double[7];

            int j = 0;
            rowDoubleData[0] = Double.parseDouble(rowData[0]);
            rowDoubleData[1] = Double.parseDouble(rowData[1]);
            rowDoubleData[3] = 1;
            j = 4;
            double k = 0;
            if(rowData[4].isEmpty())
                rowData[4] = String.valueOf(0);
            rowDoubleData[2] = Double.parseDouble(rowData[4]);

            // To convert String data into double

            for (int i = 2; i < rowData.length; ++i) {

                //skip date
                if (i == 3) {

                    continue;
                }


                if (i == 4) {

                    continue;
                }
                if (i == 6)
                    continue;
                rowDoubleData[j] = Double.parseDouble(rowData[i].replaceAll("\"", ""));
                j++;

            }

            listData.add(rowDoubleData);


        }



        int data_cols = listData.get(0).length;
        int data_rows = listData.size();

        Matrix matrixData = new Matrix(data_rows, data_cols);

        // storing data in Matrix
        for (int r = 0; r < data_rows; r++) {
            for (int c = 0; c < data_cols ; c++) {
                matrixData.set(r, c, listData.get(r)[c]);
            }
        }
        return matrixData;
    }


    /**
     * returns matrix of test data set without date col
     * @return
     * @throws IOException
     */
    public static Matrix getKaggleTestSetMonth() throws IOException {
        List<double[]> listData = new ArrayList<>();
        String line;
        boolean flag = true;

        BufferedReader br = new BufferedReader(new FileReader("data/kaggle/test_month.csv"));

        while ((line = br.readLine()) != null) {

            // To skip the first row in the files
            if (flag == true) {
                flag = false;
                continue;
            }

            String rowData[] = line.split(",");

            double rowDoubleData[] = new double[8];

            int j = 0;
            rowDoubleData[0] = Double.parseDouble(rowData[0]);
            rowDoubleData[1] = Double.parseDouble(rowData[1]);
            rowDoubleData[3] = 1;
            j = 4;
            double k = 0;
            if(rowData[4].isEmpty())
                rowData[4] = String.valueOf(0);
            rowDoubleData[2] = Double.parseDouble(rowData[4]);

            // To convert String data into double

            for (int i = 2; i < rowData.length; ++i) {



                if (i == 4) {

                    continue;
                }
                if (i == 6)
                    continue;
                rowDoubleData[j] = Double.parseDouble(rowData[i].replaceAll("\"", ""));
                j++;

            }

            listData.add(rowDoubleData);


        }



        int data_cols = listData.get(0).length;
        int data_rows = listData.size();

        Matrix matrixData = new Matrix(data_rows, data_cols);

        // storing data in Matrix
        for (int r = 0; r < data_rows; r++) {
            for (int c = 0; c < data_cols ; c++) {
                matrixData.set(r, c, listData.get(r)[c]);
            }
        }
        return matrixData;
    }
    /**
     *
     * @param store_number
     * @return
     * @throws IOException
     */
    public static Matrix getStoreGroupData(int store_number) throws IOException {
        List<double[]> listData = new ArrayList<>();
        String line;

        BufferedReader br = new BufferedReader(new FileReader("data/store_groups/store_groups.txt"));

        int k =1;
        int store = 0;
        while ((line = br.readLine()) != null) {

            if (line.contains(String.valueOf(store_number))) {
                store = k;
                br.close();
                break;
            }
            else{
                k++;
            }



        }


        BufferedReader br2 = new BufferedReader(new FileReader("data/store_groups/store_group_"+store+".txt"));

        while ((line = br2.readLine()) != null) {

            String rowData[] = line.split("\t");
            double rowDoubleData[] = new double[rowData.length];

            rowDoubleData[0] =1;
            int j =1;
            // To convert String data into double
            for (int i = 0; i < rowData.length; ++i) {
                if(i==1)
                    continue;
                rowDoubleData[j++] = Double.parseDouble(rowData[i]);
            }
            listData.add(rowDoubleData);
        }



        int data_cols = listData.get(0).length;
        int data_rows = listData.size();

        Matrix matrixData = new Matrix(data_rows, data_cols);


        // storing data in Matrix
        for (int r = 0; r < data_rows; r++) {
            for (int c = 0; c < data_cols ; c++) {
                matrixData.set(r, c, listData.get(r)[c]);
            }
        }


        br2.close();
        return matrixData;

    }

    /**
     *gets store data along with month
     * @param store_number
     * @return
     * @throws IOException
     */
    public static Matrix getStoreDataMonth(int store_number) throws IOException {
        List<double[]> listData = new ArrayList<>();
        String line;

        BufferedReader br = new BufferedReader(new FileReader("data/store/"+store_number+".txt"));

        while ((line = br.readLine()) != null) {

            String rowData[] = line.split("\t");
            double rowDoubleData[] = new double[rowData.length+1];

            rowDoubleData[0] =1;
            // To convert String data into double
            for (int i = 0; i < rowData.length; ++i) {
                rowDoubleData[i+1] = Double.parseDouble(rowData[i]);
            }
            listData.add(rowDoubleData);
        }

        int data_cols = listData.get(0).length;
        int data_rows = listData.size();

        Matrix matrixData = new Matrix(data_rows, data_cols);

        // storing data in Matrix
        for (int r = 0; r < data_rows; r++) {
            for (int c = 0; c < data_cols ; c++) {
                matrixData.set(r, c, listData.get(r)[c]);
            }
        }


        return matrixData;

    }




    /**
     *
     * @param store_number
     * @return
     * @throws IOException
     */
    public static Matrix getStoreTrainDataTimeSeries(int store_number,int num_days) throws IOException {
        List<double[]> listData = new ArrayList<>();
        String line;

        BufferedReader br = new BufferedReader(new FileReader("data/store/"+store_number+".txt"));

        while ((line = br.readLine()) != null) {

            String rowData[] = line.split("\t");
            double rowDoubleData[] = new double[1];

            rowDoubleData[0] = Double.parseDouble(rowData[rowData.length-1]);
            listData.add(rowDoubleData);
        }

        int data_rows = listData.size();

        int num_cols = 1 + num_days + 1;

        Matrix matrixData = new Matrix(data_rows, num_cols);

        // storing data in Matrix
        for (int r = 0; r < data_rows-num_days; r++) {
            matrixData.set(r, 0, 1);
            for (int c = 1; c < num_days+1 ; c++) {
                matrixData.set(r, c, listData.get(r+c)[0]);
            }
            matrixData.set(r,num_cols-1,listData.get(r)[0]);
        }


        br.close();
        return matrixData;

    }

    public static Matrix getKaggleStoreTestDataTimeSeries(int store_number,String filePath, int num_days) throws IOException{


        List<double[]> listData = new ArrayList<>();
        String line;

        BufferedReader br = new BufferedReader(new FileReader(filePath+"/"+store_number+".txt"));

        while ((line = br.readLine()) != null) {

            String rowData[] = line.split("\t");
            double rowDoubleData[] = new double[1];

            rowDoubleData[0] = Double.parseDouble(rowData[0]);

            listData.add(rowDoubleData);

        }

        int data_rows = listData.size()-1;
        int num_cols = num_days + 1;
        Matrix matrixData = new Matrix(1, num_cols);

        matrixData.set(0, 0, 1);
        for (int c = 0; c <= num_days-1 ; c++) {
            matrixData.set(0, c+1, listData.get(data_rows-c)[0]);
        }


        return matrixData;


    }

    public static void updateKaggleStoreTestData(int store_number, String sales,String dir) throws IOException
    {

        FileWriter fStream = new FileWriter(dir+"/"+store_number+".txt",true);
        BufferedWriter out = new BufferedWriter(fStream);


        out.write(sales);
        out.newLine();
        out.close();
        fStream.close();


    }

    public static Matrix getAdditionalParameters(String line){

        Matrix x = new Matrix(1,3);
        String rowData[] = line.split(",");

        x.set(0,0,Integer.parseInt(rowData[2]));

        x.set(0,1,Integer.parseInt(rowData[5]));

        x.set(0,2,Integer.parseInt(rowData[7].replaceAll("\"","")));

        return x;
    }

    public static Matrix getAdditionalParametersMonth(String line){

        Matrix x = new Matrix(1,4);
        String rowData[] = line.split(",");

        x.set(0,0,Integer.parseInt(rowData[2]));

        x.set(0,1,Integer.parseInt(rowData[3]));

        x.set(0,2,Integer.parseInt(rowData[5]));

        x.set(0,3,Integer.parseInt(rowData[7].replaceAll("\"","")));

        return x;
    }


}
