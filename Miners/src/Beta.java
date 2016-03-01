import Jama.Matrix;
import weka.core.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Beta {

    /**  @params: X and Y matrix of training data
     * returns value of beta calculated using the formula beta = ((X^T*X)^ -1)*(X^T*Y)
     */
    public static Matrix closedFormBeta(Matrix trainX, Matrix trainY) throws IOException {

        Matrix b = ((((trainX.transpose()).times(trainX)).inverse()).times((trainX.transpose()).times(trainY)));

        return b;
    }

    /**
     *
     * @param trainX
     * @param trainY
     * @return beta calculated according to online update method
     */
    public static Matrix getOnlineUpdatedBeta(Matrix trainX, Matrix trainY) {


        Matrix b = Matrix.random(trainX.getColumnDimension(),1);
        Matrix betaOld = null;
         int i = 0;

        do {

            for (int row = 0; row < trainX.getRowDimension(); row++) {


                betaOld = b.getMatrix(0, b.getRowDimension() - 1, 0, b.getColumnDimension() - 1);


                Matrix given_y = trainY.getMatrix(row, row, 0, trainY.getColumnDimension() - 1);
                Matrix current_row = trainX.getMatrix(row, row, 0, trainX.getColumnDimension() - 1);
                Matrix calculated_y = current_row.times(betaOld);


                b = betaOld.plus((given_y.minus(calculated_y).times(current_row)).times(2 * 0.001).transpose()).getMatrix(0, b.getRowDimension() - 1, 0, b.getColumnDimension() - 1);

                if (converged(betaOld, b))
                    break;

            }
            i++;
            }while(i<10000);
        //}while(!converged(betaOld,b));


     return b;
    }

    public static boolean converged(Matrix oldBeta, Matrix newBeta){
        for(int row=0; row<oldBeta.getRowDimension(); row++){
            if(!Utils.eq(oldBeta.get(row, 0), newBeta.get(row, 0)))
                return false;
        }
        return true;
    }

    public static Matrix getBeta(int store,String path) throws IOException {

        String line;

        BufferedReader br = new BufferedReader(new FileReader(path+"/"+store+".txt"));

        line = br.readLine();


        String rowData[] = line.split("\t");


        Matrix b = new Matrix(rowData.length,1);

        for(int i =0; i<rowData.length;i++)
        {
            b.set(i,0,Double.parseDouble(rowData[i]));
        }

        br.close();
        return b;

    }


    public static Matrix getLRBeta(int store,String path) throws IOException {

        String line;

        BufferedReader br = new BufferedReader(new FileReader(path+"/"+store+".txt"));

        line = br.readLine();


        String rowData[] = line.split("\t");


        Matrix b = new Matrix(rowData.length-1,1);

        for(int i =1; i<rowData.length;i++)
        {
            b.set(i-1,0,Double.parseDouble(rowData[i]));
        }

        br.close();
        return b;

    }

}
