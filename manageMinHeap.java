import java.util.*;
public class manageMinHeap {
    public static int insertIntoHeap(int rideNumber,int rideCost,int tripDuration,minHeap[] mh,int track)
    {
        minHeap mh1=new minHeap(rideNumber,rideCost,tripDuration,track);
        //inserting the new element into the last node
        mh[track]=mh1;
        //heapify value indicates that whether the node should be swapped with parents are not
        boolean heapify=true;
        int track1=track;
        int pos=track1;
        while(heapify&&track1>0)
        {
            //calculating parent node index
            int parent=track1/2;
            if(track1%2==0)
             parent-=1;
             //if ridecost is less than parent then swap
            if(mh[track1].rideCost<mh[parent].rideCost)
            {
                minHeap temp=mh[track1];
                mh[track1]=mh[parent];
                mh[parent]=temp;
                mh[track1].index=track1;
                mh[parent].index=parent;
                pos=parent;
            }
            //if ridecosts are equal
            else if(mh[track1].rideCost==mh[parent].rideCost&&mh[track1].tripDuration<mh[parent].tripDuration)
            {
                minHeap temp=mh[track1];
                mh[track1]=mh[parent];
                mh[parent]=temp;
                mh[track1].index=track1;
                mh[parent].index=parent;
                pos=parent;
            }
            //ride cost is greater than parent then stop swapping and exit
            else
            {
                pos=track1;
                heapify=false;
            }
            track1=parent;
        }
        return pos;
    }    
    public static int deleteMinNode(minHeap[] mh,int track)
    {
        /*In delete the min node we first swap the min node with last node and performing swaping down the tree until
        /min heap property is satisfied*/
        if(track==1)
        {
            return track;
        }
        //swapping last node with node
        mh[0]=mh[track-1];
        mh[track-1]=null;
        mh[0].index=0;
        int track1=0;
        track--;
        while(track1<track)
        {
            //caluclating index of children
            int child1=2*track1+1;
            int child2=2*track1+2;
            int child1Val=Integer.MAX_VALUE;
            int child2Val=Integer.MAX_VALUE;
            if(mh[child1]!=null)
            {
                child1Val=mh[child1].rideCost;
            }    
            if(mh[child2]!=null)
            {
                child2Val=mh[child2].rideCost;
            }
            //if ridecost of the parent is less than both children then exit
            if(mh[track1].rideCost<child1Val&&mh[track1].rideCost<child2Val)
            {
                break;
            }
            else
            {
                if(child1Val<child2Val)
                {
                    //if child1val is equal to rideCost of the parent and trip duration of parent is less than child1 than break 
                    if(child1Val==mh[track1].rideCost&&mh[track1].tripDuration<mh[child1].tripDuration)
                    {
                        break;
                    }
                    else
                    {
                        minHeap temp=mh[track1];
                        mh[track1]=mh[child1];
                        mh[child1]=temp;
                        mh[track1].index=track1;
                        mh[child1].index=child1;
                        track1=child1;
                    }
                }
                else if(child2Val<child1Val)
                {
                    if(child2Val==mh[track1].rideCost&&mh[track1].tripDuration<mh[child2].tripDuration)
                    {
                        break;
                    }
                    else
                    {
                        minHeap temp=mh[track1];
                        mh[track1]=mh[child2];
                        mh[child2]=temp;
                        mh[track1].index=track1;
                        mh[child2].index=child2;
                        track1=child2;
                    }
                }
                //Case where the ridecost of the two children are equal
                else
                {
                    //the ride cost of parent is not equal to children 
                    if(mh[track1].rideCost!=child1Val)
                    {
                        if(mh[child1].tripDuration<mh[child2].tripDuration)
                        {
                            minHeap temp=mh[track1];
                            mh[track1]=mh[child1];
                            mh[child1]=temp;
                            mh[track1].index=track1;
                            mh[child1].index=child1;
                            track1=child1;
                        }
                        else
                        {
                            minHeap temp=mh[track1];
                            mh[track1]=mh[child2];
                            mh[child2]=temp;
                            mh[track1].index=track1;
                            mh[child2].index=child2;
                            track1=child2;
                        }
                    }
                    //the ride cost of parent is equal to children we compare the trip duration 
                    else
                    {
                        if(mh[track1].tripDuration<mh[child1].tripDuration&&
                        mh[track1].tripDuration<mh[child2].tripDuration)
                        {
                            break;
                        }
                        if(mh[child1].tripDuration<mh[child2].tripDuration)
                        {
                            minHeap temp=mh[track1];
                            mh[track1]=mh[child1];
                            mh[child1]=temp;
                            mh[track1].index=track1;
                            mh[child1].index=child1;
                            track1=child1;
                        }
                        else
                        {
                            minHeap temp=mh[track1];
                            mh[track1]=mh[child2];
                            mh[child2]=temp;
                            mh[track1].index=track1;
                            mh[child2].index=child2;
                            track1=child2;
                        }
                    }
                }
            }
        }
        return track;
    }
    public static void makeCurrentNodeMin(int index,minHeap[] mh,int track)
    {
        //For deleting the arbitarary node we first make the value of arbitary node to -1 and swap it till it is minnode
        //And then perform delete min
        mh[index].rideCost=Integer.MIN_VALUE;
        while(index>0)
        {
            int parent=index/2;
            if(index%2==0)
             parent-=1;
            minHeap temp=mh[index];
            mh[index]=mh[parent];
            mh[parent]=temp;
            mh[parent].index=index;
            mh[index].index=parent;
            index=parent;
        }
    }    
}
