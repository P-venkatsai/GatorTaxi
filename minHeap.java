public class minHeap {
    //ordered by ride cost
    public int rideNumber;
    public int rideCost;
    public int tripDuration;
    public int index=0;
    public redBlack corressRedBlack;
    minHeap(int rideNumber,int rideCost,int tripDuration,int index)
    {
        this.rideNumber=rideNumber;
        this.rideCost=rideCost;
        this.tripDuration=tripDuration;
        this.index=index;
    }
}
