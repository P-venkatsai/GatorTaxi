public class redBlack
{
     public int rideNumber;
    public int rideCost;
    public int tripDuration;
    public redBlack left;
    public redBlack right;
    public char color;
    public minHeap corressHeap;
    redBlack(int rideNumber,int rideCost,int tripDuration, redBlack left,redBlack right,char color)
    {
        this.rideNumber=rideNumber;
        this.rideCost=rideCost;
        this.tripDuration=tripDuration;
        this.left=left;
        this.right=right;
        this.color=color;
    }
}
