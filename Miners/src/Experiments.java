import Jama.Matrix;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class Experiments {

    public static void experiment1() throws IOException{


        FileUtils.cleanDirectory(new File("data/beta/experiment1/stochastic_beta"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreData(i, "data/store");

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);

            Matrix onlineUpdatedBeta_beta = Beta.getOnlineUpdatedBeta(train_x,train_y);

            DataUtils.printBeta(onlineUpdatedBeta_beta,"data/beta/experiment1/stochastic_beta/"+i+".txt");

        }

    }

    public static void experiment2() throws IOException{


        FileUtils.cleanDirectory(new File("data/beta/experiment2/stochastic_beta"));


        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreData(i, "data/store");

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);

            //Normalize the data
            Matrix normalized_train_x = Normalize.z_normalize(train_x);

            Matrix onlineUpdatedBeta_beta = Beta.getOnlineUpdatedBeta(normalized_train_x,train_y);

            DataUtils.printBeta(onlineUpdatedBeta_beta,"data/beta/experiment2/stochastic_beta/"+i+".txt");

        }


        Kaggle.runKaggleTest("submission/experiment2_submission.csv", "data/beta/experiment2/stochastic_beta");


    }

    public static void experiment3() throws IOException {


        FileUtils.cleanDirectory(new File("data/beta/experiment3/closed_form"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreData(i, "data/store");

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);

            //Normalize the data
            //Matrix normalized_train_x = Normalize.z_normalize(train_x);

            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment3/closed_form/"+i+".txt");

        }

        Kaggle.runKaggleTest("submission/experiment3_submission.csv", "data/beta/experiment3/closed_form");

    }

    public static void experiment4() throws IOException {

        PreprocessData.groupStoreTypes();

        FileUtils.cleanDirectory(new File("data/beta/experiment4/closed_form"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreGroupData(i);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);

            //Normalize the data
            //Matrix normalized_train_x = Normalize.z_normalize(train_x);

            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment4/closed_form/"+i+".txt");

        }

        Kaggle.runKaggleTest("submission/experiment4_submission.csv", "data/beta/experiment4/closed_form");




    }

    public static void experiment5() throws IOException {

        FileUtils.cleanDirectory(new File("data/beta/experiment5/closed_form"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreDataMonth(i);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment5/closed_form/"+i+".txt");

        }

        Kaggle.runKaggleTestMonth("submission/experiment5_submission.csv", "data/beta/experiment5/closed_form");


    }

    public static void experiment6() throws IOException {


        FileUtils.cleanDirectory(new File("data/beta/experiment6/closed_form"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreTrainDataTimeSeries(i,3);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment6/closed_form/"+i+".txt");

        }

        PreprocessData.salesLag(3,"data/store_lag");

        Kaggle.runKaggleTestAR("submission/experiment6_submission.csv", "data/beta/experiment6/closed_form", "data/store_lag", 3);

    }

    public static void experiment7() throws IOException {

       FileUtils.cleanDirectory(new File("data/beta/experiment7/closed_form"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreTrainDataTimeSeries(i,7);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment7/closed_form/"+i+".txt");

        }

        PreprocessData.salesLag(7,"data/store_lag_week");

        Kaggle.runKaggleTestAR("submission/experiment7_submission.csv", "data/beta/experiment7/closed_form", "data/store_lag", 7);

    }

    public static void experiment8() throws IOException {

        FileUtils.cleanDirectory(new File("data/beta/experiment8/closed_form_ts"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreTrainDataTimeSeries(i,3);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment8/closed_form_ts/"+i+".txt");

        }

        FileUtils.cleanDirectory(new File("data/beta/experiment8/closed_form_lr"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreData(i, "data/store");

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment8/closed_form_lr/"+i+".txt");

        }




        PreprocessData.salesLag(3,"data/store_lag");

        Kaggle.runKaggleTestAR2("submission/experiment8_submission.csv", "data/beta/experiment8/closed_form_ts", "data/beta/experiment8/closed_form_lr", "data/store_lag", 3,false);



    }

    public static void experiment9() throws IOException {

        FileUtils.cleanDirectory(new File("data/beta/experiment9/closed_form_ts"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreTrainDataTimeSeries(i,3);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment9/closed_form_ts/"+i+".txt");

        }

        FileUtils.cleanDirectory(new File("data/beta/experiment9/closed_form_lr"));

        for(int i=1;i<=1115;i++)
        {
            Matrix trainingData = DataUtils.getStoreDataMonth(i);

            Matrix train_x = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, 0, trainingData.getColumnDimension() - 2);
            Matrix train_y = trainingData.getMatrix(0, trainingData.getRowDimension() - 1, trainingData.getColumnDimension() - 1, trainingData.getColumnDimension() - 1);


            Matrix closed_beta = Beta.closedFormBeta(train_x, train_y);

            DataUtils.printBeta(closed_beta,"data/beta/experiment9/closed_form_lr/"+i+".txt");

        }




        PreprocessData.salesLag(3,"data/store_lag");

        Kaggle.runKaggleTestAR2("submission/experiment9_submission.csv", "data/beta/experiment9/closed_form_ts", "data/beta/experiment9/closed_form_lr", "data/store_lag", 3,true);



    }




}
