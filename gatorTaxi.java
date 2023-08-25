import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class gatorTaxi {
    public static redBlack root=null;
    public static minHeap[] mh=new minHeap[2500];
    public static int track=0;
    public static FileWriter fw = null;
    //Reads the input from the input,txt
    public static void main(String[] args) throws NumberFormatException, IOException {
        File f=new File(args[0]);
        fw=new FileWriter("output_file.txt");
        BufferedReader br= new BufferedReader(new FileReader(f));
        String s;
        while((s = br.readLine()) != null)
        {
            //Splitting the arguments in order to extract all the individual data
            String[] arguments=s.split("\\(");
            arguments[1]=arguments[1].substring(0,arguments[1].length()-1);
            String[] newArgs=arguments[1].split(",");
            //Various blocks to match correct argument
            if(arguments[0].equals("Insert"))
            {
                //isDuplicate is made true if the element with rideNumber is already in the list
                manageRedBlack.isDuplicate=false;
                redBlack newRBNode=insertIntoRedBlack(Integer.parseInt(newArgs[0]),Integer.parseInt(newArgs[1]),
                Integer.parseInt(newArgs[2]));
                if(manageRedBlack.isDuplicate)
                {
                    fw.write("Duplicate RideNumber\n");
                    break;
                }
                int currentTrack=insertIntoHeap(Integer.parseInt(newArgs[0]),Integer.parseInt(newArgs[1]),
                Integer.parseInt(newArgs[2]));
                //pointing correspoinding nodes from redblack to minheap and minheap to redblack
                mh[currentTrack].corressRedBlack=newRBNode;
                newRBNode.corressHeap=mh[currentTrack];
            }
            else if(arguments[0].equals("Print"))
            {
                //range print 
                if(newArgs.length==2)
                {
                    ArrayList<redBlack> l=new ArrayList<redBlack>();
                    printRide(root, Integer.parseInt(newArgs[0]), Integer.parseInt(newArgs[1]),l);
                    if(l.size()==0)
                    {
                        fw.write("("+0+","+0+","+0+")\n");
                    }
                    else
                    {
                        int i=0;
                        Collections.sort(l, new Comparator<redBlack>(){
                            public int compare(redBlack s1, redBlack s2) {
                                if(s1.rideNumber<s2.rideNumber)
                                {
                                    return -1;
                                }
                                return 1;
                            }
                        });
                        for(i=0;i<l.size()-1;i++)
                        {
                            fw.write("("+l.get(i).rideNumber+","+l.get(i).rideCost+","+l.get(i).tripDuration+"),");
                        }
                        fw.write("("+l.get(i).rideNumber+","+l.get(i).rideCost+","+l.get(i).tripDuration+")\n");
                    }
                }
                else
                {
                    printRide(root, Integer.parseInt(newArgs[0]));
                }    
            }
            else if(arguments[0].equals("GetNextRide"))
            {
                GetNextRide();
            }
            else if(arguments[0].equals("CancelRide"))
            {
                cancelRide(Integer.parseInt(newArgs[0]));
            }
            else if(arguments[0].equals("UpdateTrip"))
            {
                UpdateTrip(Integer.parseInt(newArgs[0]),Integer.parseInt(newArgs[1]));
            }
        }
        br.close();
        fw.close();
    }
    public static redBlack insertIntoRedBlack(int rideNumber,int rideCost,int tripDuration)
    {
        redBlack rootChange=null;
        //Stack stores all the elmenets in new elment path
        Stack<redBlack> st=new Stack<>();
        if(root==null)
        {
            root=new redBlack(rideNumber,rideCost,tripDuration, null, null,'b');
            return root;
        }
        root=manageRedBlack.insert(rideNumber,rideCost,tripDuration, root,st);
        redBlack ret=st.peek();
        if(manageRedBlack.isDuplicate)
        {
            return null;
        }
        //balance the tree after insertion is done
        rootChange=manageRedBlack.reBalanceRedBlack(st);
        //When rebalancing the tree if root of the tree changes then rootChange will not be equal to null
        if(rootChange!=null)
             root=rootChange;
        return ret;
    }
    public static int insertIntoHeap(int rideNumber,int rideCost,int tripDuration)
     {
        int ret= manageMinHeap.insertIntoHeap(rideNumber,rideCost,tripDuration,mh,track);
        track++;
        return ret;
     }
    public static void printRide(redBlack currRoot,int rideNumber) throws IOException
    {
        //currRoot becomes null if element is not found
        if(currRoot==null)
        {
            fw.write("("+0+","+0+","+0+")\n");
            return;
        }
        if(currRoot.rideNumber==rideNumber)
        {
            fw.write("("+currRoot.rideNumber+","+currRoot.rideCost+","+currRoot.tripDuration+")\n");
            return;
        }
        else
        {
            if(rideNumber>currRoot.rideNumber)
            {
                printRide(currRoot.right, rideNumber);
            }
            else
            {
                printRide(currRoot.left, rideNumber);
            }
        }
    }
    public static void GetNextRide() throws IOException
    {
        if(root==null)
        {
            fw.write("No active ride requests\n");
            return;
        }
        //getting first element in the minheap cause it will be the ride with min cost
        int minRideNumber=mh[0].rideNumber;
        fw.write("("+mh[0].rideNumber+","+mh[0].rideCost+","+mh[0].tripDuration+")\n");
        manageMinHeap.deleteMinNode(mh, track);
        track--;
        Stack<redBlack> st=new Stack<>();
        if(root.left==null&&root.right==null)
        {
            root=null;
            return;
        }
        redBlack deletedRedBlack=manageRedBlack.delete(root, minRideNumber, st);
        redBlack OrginalDeleted=st.peek();
        redBlack rootChange=manageRedBlack.performActualDelete(0,st);
        if(rootChange!=null)
        root=rootChange;
        if(deletedRedBlack!=null)
        {
            deletedRedBlack.rideNumber=OrginalDeleted.rideNumber;
            deletedRedBlack.rideCost=OrginalDeleted.rideCost;
            deletedRedBlack.tripDuration=OrginalDeleted.tripDuration;
            deletedRedBlack.corressHeap=OrginalDeleted.corressHeap;
        }
    }
    public static void cancelRide(int rideNumber)
    {
        Stack<redBlack> st=new Stack<>();
        redBlack deletedRedBlack=manageRedBlack.delete(root, rideNumber, st);
        int reqIndex=0;
        redBlack OrginalDeleted=null;
        if(st.peek()==null)
        {
            return;
        }
        else
        {
            if(deletedRedBlack!=null)
            {
                reqIndex= deletedRedBlack.corressHeap.index;
            }
            else
            {
                reqIndex= st.peek().corressHeap.index;
            }  
            OrginalDeleted=st.peek();  
        }
        if(root.left==null&&root.right==null)
        {
            root=null;
            return;
        }
        redBlack rootChange=manageRedBlack.performActualDelete(0,st);
        if(rootChange!=null)
            root=rootChange;
        if(deletedRedBlack!=null)
        {
            deletedRedBlack.rideNumber=OrginalDeleted.rideNumber;
            deletedRedBlack.rideCost=OrginalDeleted.rideCost;
            deletedRedBlack.tripDuration=OrginalDeleted.tripDuration;
            deletedRedBlack.corressHeap=OrginalDeleted.corressHeap;
        }
        manageMinHeap.makeCurrentNodeMin(reqIndex,mh,track);
        manageMinHeap.deleteMinNode(mh,track);
        track--;
    }
    public static void UpdateTrip(int rideNumber,int new_tripDuration)
    {
        Stack<redBlack> st=new Stack<>();
        redBlack deletedRedBlack=manageRedBlack.delete(root, rideNumber, st);
        redBlack reqNode=null;
        if(deletedRedBlack!=null)
        {
            reqNode=deletedRedBlack;
        }
        else
        {
            reqNode=st.peek();
        }
        int newRideNumber=reqNode.rideNumber;
        int newRideCost=reqNode.rideCost;
        int newTrip=reqNode.tripDuration;
        //if new_tripDuration is twice greater we delete the ride
        if(new_tripDuration>2*reqNode.tripDuration)
        {
            cancelRide(rideNumber);
        }
        //in rest two cases we first delete the ride and insert it again
        else if(reqNode.tripDuration<new_tripDuration&&new_tripDuration<=2*reqNode.tripDuration)
        {
            cancelRide(rideNumber);
            manageRedBlack.isDuplicate=false;
            redBlack newRBNode=insertIntoRedBlack(newRideNumber,newRideCost+10,new_tripDuration);
            int currentTrack=insertIntoHeap(newRideNumber,newRideCost+10,new_tripDuration);
            mh[currentTrack].corressRedBlack=newRBNode;
            newRBNode.corressHeap=mh[currentTrack];
        }
        else
        {
            cancelRide(rideNumber);
            manageRedBlack.isDuplicate=false;
            redBlack newRBNode=insertIntoRedBlack(newRideNumber,newRideCost,new_tripDuration);
            int currentTrack=insertIntoHeap(newRideNumber,newRideCost,new_tripDuration);
            mh[currentTrack].corressRedBlack=newRBNode;
            newRBNode.corressHeap=mh[currentTrack];   
        }
    }
    public static void printRide(redBlack currRoot,int low,int high,ArrayList<redBlack> l)
    {
        if(currRoot==null)
        {
            return;
        }
        if(currRoot.rideNumber>=low&&currRoot.rideNumber<=high)
        {
            l.add(currRoot);
        }
        //entering only the branches of the tree  potentially contains the elemnts
        if(currRoot.rideNumber>low)
        {
            printRide(currRoot.left, low, high,l);
        }
        if(currRoot.rideNumber<high)
        {
            printRide(currRoot.right, low, high,l);
        }
    }
}
