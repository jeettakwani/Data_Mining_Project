import Jama.Matrix;

public class Normalize {

    /**
     *
     * @param x the matrix
     * @return z-normalized matrix
     */
    public static Matrix z_normalize(Matrix x){

        Matrix y = x.getMatrix(0,x.getRowDimension()-1,0,x.getColumnDimension()-1);

        for(int col=1;col<x.getColumnDimension();col++){
            Matrix current_col = x.getMatrix(0,x.getRowDimension()-1,col,col);

            Double mean = mean(current_col);

            Double std_dev = std_deviation(current_col,mean);

            for(int row=0; row<x.getRowDimension();row++){
                Double z_score = (x.get(row,col) - mean)/std_dev;

                y.set(row, col, z_score);
            }

        }

        return y;


    }

    /**
     *
     * @param col
     * @return the mean of the column
     */
    private static Double mean(Matrix col){

        Double sum = 0.0;

        for(int row=0;row<col.getRowDimension();row++){
            sum += col.get(row,0);

        }

        return sum/col.getRowDimension();

    }

    /**
     *
     * @param col the column of the matrix
     * @param mean of the column
     * @return the standard deviation of the column
     */
    private static Double std_deviation(Matrix col, Double mean){

        Double sum = 0.0;

        for(int row=0;row<col.getRowDimension();row++){
            sum += Math.pow(col.get(row,0)-mean,2);
        }

        return Math.sqrt(sum/(col.getRowDimension()-1));

    }

    /**
     * Calculates mean square error between given and predicted Y
     * @param predictedY
     * @param givenY
     * @return
     */

    public static Double meanSquareError(Matrix predictedY, Matrix givenY){
        Double sum = 0.0;

        for(int row = 0; row<predictedY.getRowDimension();row++){
            sum += Math.pow(((givenY.get(row,0)-predictedY.get(row,0))/givenY.get(row,0)),2);
        }

        return Math.sqrt(sum/predictedY.getRowDimension());
    }
}
