import Jama.Matrix;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class Kaggle {

    public static void runKaggleTestAR(String submissionfile, String betaDir,String storePath,int lag) throws IOException
    {

        String line;
        Boolean flag = true;
        BufferedReader br = new BufferedReader(new FileReader("data/kaggle/test.csv"));
       FileUtils.forceDelete(new File(submissionfile));


        while ((line = br.readLine()) != null) {
            String rowData[] = line.split(",");


            // To skip the first row in the files
            if (flag == true) {
                flag = false;
                continue;
            }


            Matrix test_x = DataUtils.getKaggleStoreTestDataTimeSeries(Integer.parseInt(rowData[1]),storePath,lag);


            Matrix beta = Beta.getBeta(Integer.parseInt(rowData[1]), betaDir);
            //  Matrix lr_beta = MatrixData.getLRClosedBeta(Integer.parseInt(rowData[1]));
            Matrix predictedY = new Matrix(1,1);
            if(rowData[4].isEmpty())
                rowData[4] = String.valueOf(0);

            if(Integer.parseInt(rowData[4].replaceAll("\"",""))==0)
            {
                predictedY.set(0,0,0);
            }
            else {
                predictedY = test_x.times(beta);
            }
            FileWriter fStream = new FileWriter(submissionfile,true);     // Output File
            BufferedWriter out = new BufferedWriter(fStream);

            out.write(String.valueOf(Integer.parseInt(rowData[0])));
            out.write(",");

            out.write(String.valueOf(predictedY.get(0, 0)));
            out.newLine();
            out.close();
            fStream.close();

            //write to file

            DataUtils.updateKaggleStoreTestData(Integer.parseInt(rowData[1]), String.valueOf(predictedY.get(0, 0)),storePath);


        }
        br.close();

    }

    public static void runKaggleTestAR2(String submissionfile, String betaDir_ts,String betaDir_lr,String storePath,int lag,Boolean month) throws IOException
    {

        String line;
        Boolean flag = true;
        BufferedReader br = new BufferedReader(new FileReader("data/kaggle/test_month.csv"));

         FileUtils.forceDelete(new File(submissionfile));
        FileWriter fStream = new FileWriter(submissionfile,true);     // Output File
        BufferedWriter out = new BufferedWriter(fStream);


        while ((line = br.readLine()) != null) {
            String rowData[] = line.split(",");


            // To skip the first row in the files
            if (flag == true) {
                flag = false;
                continue;
            }


            Matrix test_x = DataUtils.getKaggleStoreTestDataTimeSeries(Integer.parseInt(rowData[1]),storePath,lag);
            Matrix testX = null;

            if(month==false) {

               testX = DataUtils.getAdditionalParameters(line);
            }
            else
            {
                 testX = DataUtils.getAdditionalParametersMonth(line);
            }


            Matrix beta = Beta.getBeta(Integer.parseInt(rowData[1]), betaDir_ts);
            Matrix lr_beta = Beta.getLRBeta(Integer.parseInt(rowData[1]), betaDir_lr);
            Matrix predictedY = new Matrix(1,1);
            if(rowData[4].isEmpty())
                rowData[4] = String.valueOf(0);

            if(Integer.parseInt(rowData[4].replaceAll("\"",""))==0)
            {
                predictedY.set(0,0,0);
            }
            else {
                predictedY = test_x.times(beta).plus(testX.times(lr_beta));;
            }
             out.write(String.valueOf(Integer.parseInt(rowData[0])));
            out.write(",");

            out.write(String.valueOf(predictedY.get(0, 0)));
            out.newLine();

            //write to file

            DataUtils.updateKaggleStoreTestData(Integer.parseInt(rowData[1]), String.valueOf(predictedY.get(0, 0)),storePath);


        }
        out.close();
        fStream.close();

        br.close();

    }





    public static void runKaggleTest(String submissionfile, String betaDir) throws IOException
    {
        Matrix kaggleTest = DataUtils.getKaggleTestSet();
        Matrix predictedY = new Matrix(1, 1);

        FileUtils.forceDelete(new File(submissionfile));
        FileWriter fStream = new FileWriter(submissionfile,true);
        BufferedWriter out = new BufferedWriter(fStream);
        out.write("Id,Sales");
        out.newLine();
        for(int i =0; i<= kaggleTest.getRowDimension()-1;i++)
        {
            int store = (int) kaggleTest.get(i,1);
            int id = (int) kaggleTest.get(i,0);
            Matrix train_x = kaggleTest.getMatrix(i, i, 3, kaggleTest.getColumnDimension() - 1);
            //Matrix normalized_trainX = Normalize.z_normalize(train_x);
            Matrix beta = Beta.getBeta(store, betaDir);

            if(kaggleTest.get(i, 2)==0) {

                predictedY.set(0,0,0);
            }
            else {
                predictedY = train_x.times(beta);
            }

            out.write(String.valueOf(id));
            out.write(",");

            out.write(String.valueOf(predictedY.get(0, 0)));
            out.newLine();

        }

        out.close();
        fStream.close();

    }

    public static void runKaggleTestMonth(String submissionfile, String betaDir) throws IOException
    {
        Matrix kaggleTest = DataUtils.getKaggleTestSetMonth();
        Matrix predictedY = new Matrix(1, 1);

        FileUtils.forceDelete(new File(submissionfile));
        FileWriter fStream = new FileWriter(submissionfile,true);
        BufferedWriter out = new BufferedWriter(fStream);
        out.write("Id,Sales");
        out.newLine();
        for(int i =0; i<= kaggleTest.getRowDimension()-1;i++)
        {
            int store = (int) kaggleTest.get(i,1);
            int id = (int) kaggleTest.get(i,0);
            Matrix train_x = kaggleTest.getMatrix(i, i, 3, kaggleTest.getColumnDimension() - 1);
            Matrix beta = Beta.getBeta(store, betaDir);

            if(kaggleTest.get(i, 2)==0) {

                predictedY.set(0,0,0);
            }
            else {
                predictedY = train_x.times(beta);
            }

            out.write(String.valueOf(id));
            out.write(",");

            out.write(String.valueOf(predictedY.get(0, 0)));
            out.newLine();

        }

        out.close();
        fStream.close();

    }
}
