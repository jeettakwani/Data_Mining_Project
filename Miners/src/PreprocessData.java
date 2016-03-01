import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class PreprocessData {

    /**
     * takes the file path to the training data
     * and removes the records for days when the store was closed
     * and separates the data of each store into separate files.
     * @param trainingData the path to training data
     * @param destinationFolder folder where to store the separate files
     * @throws IOException
     */
    public static void separateStoreData(String trainingData,String destinationFolder) throws IOException {

        FileUtils.cleanDirectory(new File(destinationFolder));
        String line;
        boolean flag = true;

        BufferedReader br = new BufferedReader(new FileReader(trainingData));

        while ((line = br.readLine()) != null) {
            String rowData[] = line.split(",");

            //skip the header
            if (flag == true) {
                flag = false;
                continue;
            }


            //create file for the store
            FileWriter fStream = new FileWriter(destinationFolder + "/"+rowData[0] + ".txt", true);
            BufferedWriter out = new BufferedWriter(fStream);


            //keep track of sales to shift it to the end of line
            double sales_index = 0;

            if (!line.contains("a") && !line.contains("b") && !line.contains("c") && Double.parseDouble(rowData[5]) == 1) {
                for (int i = 1; i < rowData.length; ++i) {


                    if (i == 3) {
                        sales_index = Double.parseDouble(rowData[i]);
                        continue;
                    }

                    //do not store number of customers
                    if (i == 4)
                        continue;
                    //do not want indication of open because we are removing closed stores
                    if (i == 5)
                        continue;
                    //do not keep track of state holidays
                    if (i == 7)
                        continue;

                    out.write(rowData[i].replaceAll("\"", ""));
                    out.write("\t");
                }
                out.write(String.valueOf(sales_index));
                out.newLine();
                out.close();
                fStream.close();
            }

        }

        br.close();
    }


    /**
     * separates data of stores with similar type and assortment level
     * into different files
     * @throws IOException
     */
    public static void groupStoreTypes() throws IOException{

        String line;
        boolean flag = true;

        FileUtils.cleanDirectory(new File("data/store_groups"));
        BufferedReader br = new BufferedReader(new FileReader("data/kaggle/store.csv"));
        HashMap<List<String>,List<Integer>> store_group =  new LinkedHashMap<>();

        while ((line = br.readLine()) != null) {
            String rowData[] = line.split(",");

            // To skip the first row in the files
            if (flag == true) {
                flag = false;
                continue;
            }

            String store_type = rowData[1];
            String assortment = rowData[2];

            Integer Store_id = Integer.parseInt(rowData[0]);

            List l = new LinkedList<>();
            l.add(store_type);
            l.add(assortment);

            List s = store_group.get(l);

            if(s==null) {
                s = new LinkedList<>();
                s.add(Store_id);

            }
            else {
                s.add(Store_id);
            }

            store_group.put(l,s);



        }

        br.close();


        FileWriter fStream = new FileWriter("data/store_groups/store_groups.txt",true);     // Output File
        BufferedWriter out = new BufferedWriter(fStream);

        Iterator it = store_group.entrySet().iterator();
        int k =1;
        while(it.hasNext())
        {

            FileWriter fStream1 = new FileWriter("data/store_groups/store_group_"+k+".txt",true);     // Output File
            BufferedWriter out1 = new BufferedWriter(fStream1);



            Map.Entry<List<String>,List<Integer>> pair = (Map.Entry<List<String>,List<Integer>>)it.next();

            List<Integer> s = pair.getValue();

            String line2;
            for(int i=0;i<s.size();i++){

                BufferedReader br2 = new BufferedReader(new FileReader("data/store/"+s.get(i)+".txt"));
                while((line2 = br2.readLine())!=null){

                    out1.write(line2);
                    out1.newLine();
                }

                br2.close();
                out.write(String.valueOf(s.get(i)));
                out.write("\t");
            }


            out.newLine();
            out1.close();
            fStream1.close();
            k++;

        }

        out.close();
        fStream.close();

    }

    public static void SplitCSVData() throws IOException {

        String line;
        String heading="";
        boolean flag = true;
        Boolean pre_store[] = new Boolean[1116];
        Arrays.fill(pre_store,false);

        FileUtils.cleanDirectory(new File("data/store_csv"));

        BufferedReader br = new BufferedReader(new FileReader("data/kaggle/train.csv"));

        while ((line = br.readLine()) != null) {
            String rowData[] = line.split(",");

            // To skip the first row in the files
            if (flag == true) {
                flag = false;
                heading = line;
                continue;
            }

            int store_id = Integer.parseInt(rowData[0]);



            FileWriter fStream = new FileWriter("data/store_csv/"+store_id+".csv",true);     // Output File
            BufferedWriter out = new BufferedWriter(fStream);


            if(pre_store[store_id]==false)
            {
                out.write(heading);
                out.newLine();
                pre_store[store_id]=true;
            }

            out.write(line);
            out.newLine();
            out.close();
            fStream.close();


        }
        br.close();
    }

    /**
     * Takes the train data and for each store
     * creates a separate file with sales of the last num_days
     * @param num_days
     * @throws IOException
     */
    public static void salesLag(int num_days,String dir) throws IOException{

        String line2;

        Boolean pre_store[] = new Boolean[1116];
        Arrays.fill(pre_store, false);


        FileUtils.cleanDirectory(new File(dir));

        for(int i=1;i<1116;i++) {


            FileWriter fStream1 = new FileWriter(dir+"/" + i + ".txt", true);     // Output File
            BufferedWriter out1 = new BufferedWriter(fStream1);


            if(pre_store[i]==false) {
                int count = 0;

                BufferedReader br_strore = new BufferedReader(new FileReader("data/store/"+i+".txt"));

                while ((line2 = br_strore.readLine()) != null && count < num_days) {

                    String rowData1[] = line2.split("\t");


                    int sales = rowData1.length;
                    String s = rowData1[sales-1];

                    out1.write(s);
                    out1.newLine();
                    count++;

                }
                pre_store[i] = true;
                br_strore.close();

                out1.close();
                fStream1.close();

            }





        }





    }


}
