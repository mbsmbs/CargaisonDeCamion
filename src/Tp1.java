// IFT2015 - TP1 (Été 2023)
// Min Byungsuk et Shao Hang Li

import java.io.*;
import ServicePoint.ServicePointList;
import ServicePoint.ServicePointNode;

public class Tp1
{
    // Data field
    private String inFileName, outFileName;
    private int boxesToGo, truckCapacity;
    private ServicePointList servicePointList, pickPointList;
    private ServicePointNode startPoint;
    private String outTxt;
    public Tp1(String inFileName, String outFileName)
    {
        this.inFileName = inFileName;
        this.outFileName = outFileName;
        servicePointList = new ServicePointList();
        pickPointList = new ServicePointList();
        outTxt = "Truck position: (";
    }

    // gets & sets
    public void setTruckCapacity(String truckCapacity)
    {
        this.truckCapacity = Integer.parseInt(truckCapacity);
    }
    public ServicePointNode getStartPoint()
    {
        return startPoint;
    }
    public void setStartPoint()
    {
        startPoint = servicePointList.getStartPoint();
        if(boxesToGo >= startPoint.getBoxesNum())
        {
            boxesToGo = boxesToGo - startPoint.getBoxesNum();
            startPoint.setBoxesNum(0);
        }
        else
        {
            int boxRemain = startPoint.getBoxesNum() + boxesToGo;
            setBoxesToGo(boxRemain);
            startPoint.setBoxesNum(boxRemain);
        }
        pickPointList.add(startPoint);
    }
    public void setBoxesToGo(int newNum)
    {
        boxesToGo = newNum;
    }
    public void setBoxesToGo(String boxesToTranport)
    {
        this.boxesToGo = Integer.parseInt(boxesToTranport);
    }
    public String getOutTxt()
    {
        return outTxt;
    }
    public void setOutTxt()
    {
        outTxt += startPoint.getLatitude() + "," + startPoint.getLongitude() + ")\n";
        outTxt += pickPointList.getOutTxt();
    }

    // method field

    // Open input files and read & store information
    public void readInFile()
    {
        String inFilePath = System.getProperty("user.dir")
                            + "/input_files/"
                            + inFileName;

        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(inFilePath));
            String line = null;
            int lineCount = 0;

            while((line = br.readLine()) != null)
            {
                if(lineCount == 0)
                {
                    String[] tokens = line.split("[\t\n ]+");
                    setBoxesToGo(tokens[0]);
                    setTruckCapacity(tokens[1]);
                    if(boxesToGo > truckCapacity)
                    {
                        setBoxesToGo(truckCapacity);
                    }
                }
                else
                {
                    String[] tokens = line.split("[\t\n\\s(),]+");
                    int index = 0;
                    while(index < tokens.length - 1)
                    {
                        servicePointList.addSorted(tokens[index++], tokens[index++], tokens[index++]);
                    }
                }
                lineCount++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    // make output file
    public void writeOutFile()
    {
        String outFilePath = System.getProperty("user.dir")
                + "/output_files/"
                + outFileName;

        BufferedWriter bw = null;

        try
        {
            bw = new BufferedWriter(new FileWriter(outFilePath));
            bw.write(getOutTxt());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(bw != null)
            {
                try
                {
                    bw.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    // make list of service points
    public void makePickPointList(ServicePointNode startPoint)
    {
        while(boxesToGo > 0)
        {
            ServicePointNode closestNode = servicePointList.popPickPoint(startPoint);
            if(closestNode == null) return;
            if(boxesToGo >= closestNode.getBoxesNum())
            {
                boxesToGo = boxesToGo - closestNode.getBoxesNum();
                closestNode.setBoxesNum(0);
            }
            else
            {
                int boxRemain = closestNode.getBoxesNum() - boxesToGo;
                setBoxesToGo(0);
                closestNode.setBoxesNum(boxRemain);
            }
            pickPointList.add(closestNode);
        }
    }
    // Run Tp1 application
    public void run()
    {
        readInFile();
        setStartPoint();
        makePickPointList(getStartPoint());
        pickPointList.setOutTxt();
        setOutTxt();
        writeOutFile();
    }

    // print to tests
//    public void print1()
//    {
//        servicePointList.printList();
//    }
//    public void print2()
//    {
//        pickPointList.printList();
//    }

    public static void main(String[] args)
    {
        // receiving data success
        if(args[0] != null && args[1] != null)
        {
            // For theoretical analysis
//            long beforeTime = System.currentTimeMillis();

            // Tp1 instance
            Tp1 tp1 = new Tp1(args[0], args[1]);
            tp1.run();

            // For theoretical analysis
//            long afterTime = System.currentTimeMillis();
//            long programTime = afterTime - beforeTime;
//            System.out.println(programTime);
        }
        else // receiving data failed
        {
              throw new IllegalArgumentException();   // O(1)
        }
    }
}
