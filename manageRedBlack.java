
import java.util.*;

public class manageRedBlack {
    public static minHeap[] mh=new minHeap[1000];
    public static boolean isDuplicate=false;
    public static redBlack reBalanceRedBlack(Stack<redBlack> st)
    {
        //notations
        //p-current inserted element
        //pp-parent of current inserted
        //gp-parent of pp
        //gpp parent of gp
        //if stack size is less than three than no rebalancing nedded
        if(st.size()<3)
         return null;
        //poping current,current parent,current grand parent from the stack  
        redBlack p=st.pop();
        redBlack pp=st.pop();
        redBlack gp=st.pop();
        boolean left=(pp==gp.left);
        //since reblanacing is required only when current node color is equal to parent node and both of them are red
        //so checking fot that case 
        if((p.color!=pp.color)||p.color=='b')
        {
            return null;
        }
        //Following XYz notation
        char r='b';
        if(left)
        {
            if(gp.right!=null&&gp.right.color=='r')
            {
                r='r';
            }
        }
        else
        {
            if(gp.left!=null&&gp.left.color=='r')
            {
                r='r';
            }
        }
        //XYr
        if(r=='r')
        {
            gp.color='r';
            gp.right.color='b';
            gp.left.color='b';
            if(st.isEmpty())
            {
                gp.color='b';
            }
            else
            {
                st.push(gp);
                reBalanceRedBlack(st);
            }
        }
        //XYb
        else
        {
            String rotation="";
            redBlack gpp=null;
            if(!st.isEmpty())
             gpp=st.pop();    
            if(gp.left==pp)
               rotation=rotation+"L";
            else
               rotation=rotation+"R";
            if(pp.left==p)
               rotation=rotation+"L";
            else
               rotation=rotation+"R";
            //performing various rotation   
            if(rotation.equals("LL"))
            {
                redBlack rem=pp.right;
                pp.right=gp;
                gp.left=rem;
                gp.color='r';
                pp.color='b';
            }             
            else if(rotation.equals("RR"))
            {
                redBlack rem=pp.left;
                pp.left=gp;
                gp.right=rem;
                gp.color='r';
                pp.color='b';
            }
            else if(rotation.equals("LR"))
            {
                pp.right=p.left;
                gp.left=p.right;
                p.left=pp;
                p.right=gp;
                p.color='b';
                gp.color='r';
            }
            else
            {
                gp.right=p.left;
                pp.left=p.right;
                p.left=gp;
                p.right=pp;
                p.color='b';
                gp.color='r';
            }
            if((rotation.equals("LL")||rotation.equals("RR"))&&gpp!=null)
            {
                if(gpp.left==gp)
                    gpp.left=pp;
                else
                   gpp.right=pp;    
            }
            else if(rotation.equals("LL")||rotation.equals("RR"))
                return pp;
            if((rotation.equals("LR")||rotation.equals("RL"))&&gpp!=null)
            {
                if(gpp.left==gp)
                    gpp.left=p;
                else
                    gpp.right=p;  
            }
            else if(rotation.equals("LR")||rotation.equals("RL"))
             return p;
        }
        return null; 
    }
    
    //this method mainly used insert the elements in the path of inserted element into stack
    public static redBlack delete(redBlack root,int rideNumber,Stack<redBlack> st)
    {
    
        redBlack ret=null;
        st.push(root);
        if(root==null)
        {
            return null;
        }
        if(root.rideNumber==rideNumber)
        {
           //if the node that has to be deleted is degree two node we find it's largest left child so that we can 
           //delete it instead of degree 2 node 
           if(root.left!=null&&root.right!=null)
           {
              findLargestONRight(root.left,st);  
              return root;
           } 
           return null;
        }
        if(rideNumber>root.rideNumber)
        {
            ret=delete(root.right,rideNumber,st);
        }
        else
        {
            ret=delete(root.left,rideNumber,st);
        }
        return ret;
    }

    public static redBlack performActualDelete(int deleteCount,Stack<redBlack> st)
    {
        //Notations
        //Y root of the deficent treee
        //py parent of that deficient root
        //v is right child or left of py based whether the case whether y is right or left child
        //w is right child or left of v based whether the case whether y is right or left child 
        //x is right child or left of w based whether the case whether y is right or left child
        //gy grand parent of y
        redBlack y=null;
        redBlack py=null;
        redBlack rootChange=null;
        if(!st.isEmpty())
        {
            y=st.pop();
        }
        if(!st.isEmpty())
        {
            py=st.pop();
        }
        //if the deleted node is red then it's distrupt the balance of the black nodes so we just return without doing
        //any futher operations
        if(y.color=='r')
        {
            if(py.left==y)
                py.left=null;
            else
                py.right=null;
            return null;    
        }
        //If the deleted node is degree one black node it must have a red child so we just make that red child black
        if((y.right==null&&y.left!=null)||(y.left==null&&y.right!=null))
        {
            if(py==null)
            {
                if(y.left!=null)
                {
                    rootChange=y.left;
                    y.left=null;
                }
                else
                {
                    rootChange=y.right;
                    y.right=null;
                }
                return rootChange;
            }
            if(y.right!=null)
            {
                y.right.color='b';
                if(py.left==y)
                    py.left=y.right;
                else
                    py.right=y.right;
            }
            else
            {
                y.left.color='b';
                if(py.left==y)
                    py.left=y.left;
                else
                    py.right=y.left;
            }
            return null;
        }
        //Forming the XCn
        String deletionType="";
        redBlack v=null;
        if(py.left==y)
        {
            deletionType=deletionType+"L";
            v=py.right;
            if(v.color=='r')
            {
                deletionType=deletionType+"r";
            }
            else
            {
                deletionType=deletionType+"b";
            }
        }
        else
        {
            //right
            deletionType=deletionType+"R";
            v=py.left;
            if(v.color=='r')
            {
                deletionType=deletionType+"r";
            }
            else
            {
                deletionType=deletionType+"b";
            }
        }
        //Going to put comments on just Rb and Rr cause other two cases are absoultely symmetric like perfect mirror images
        //And also i am not explaning every individual cases cause they are exact match cases given red black tree ppt
        if(deletionType.equals("Rb"))
        {
            int redCount=0;
            redBlack w=null;
            //counting red child nodes
            if(v.left!=null&&v.left.color=='r')
            {
                redCount++;
            }
            if(v.right!=null&&v.right.color=='r')
            {
                redCount++;
            }
            //Rb0
            if(redCount==0)
            {
                //case 1
                if(py.color=='b')
                {
                    v.color='r';
                    st.push(py);
                    if(st.size()>1)
                    {
                        performActualDelete(deleteCount+1,st);
                    }
                }
                //case 2
                else
                {
                    py.color='b';
                    y.color='r';
                }
            }
            //Rb1
            else if(redCount==1)
            {
                //case 1
                if(v.left!=null&&v.left.color=='r')
                {
                    py.left=v.right;
                    v.right=py;
                    v.color=py.color;
                    py.color='b';
                }
                //case 2
                else
                {
                    w=v.right;
                    v.right=w.left;
                    py.left=w.right;  
                    w.left=v;
                    w.right=py;
                    w.color=py.color;
                    py.color='b';
                }
            }
            //Rb2
            else
            {
                w=v.right;
                v.right=w.left;
                py.left=w.right;  
                w.left=v;
                w.right=py;
                w.color=py.color;
                py.color='b';
            }
            //here we will checking whether the change of root is required after rotation is performed and
            //also change the child of gy if required
            if((redCount==1&&!(v.left!=null&&v.left.color=='r'))||redCount==2)
            {
                if(st.isEmpty())
                {
                    rootChange=w;
                }
                else
                {
                    redBlack gy=st.pop();
                    if(gy.left==py)
                        gy.left=w;
                    else
                        gy.right=w;
                }
            }
            else if(redCount==1)
            {
                if(st.isEmpty())
                {
                    rootChange= v;
                }
                else
                {
                    redBlack gy=st.pop();
                    if(gy.left==py)
                        gy.left=v;
                    else
                        gy.right=v;
                }
            }
        }
        else if(deletionType.equals("Rr"))
        {
            int redCount=0;
            redBlack w=v.right;
            redBlack x=null;
            //counting red child nodes
            if(w.left!=null&&w.left.color=='r')
            {
                redCount++;
            }
            if(w.right!=null&&w.right.color=='r')
            {
                redCount++;
            }
            //Rr0    
            if(redCount==0)
            {
                py.left=v.right;
                v.right=py;
                v.color='b';
                py.left.color='r';
            }
            //Rr1
            else if(redCount==1)
            {
                //case 1
                if(w.left!=null&&w.left.color=='r')
                {
                    v.right=w.left;
                    py.left=w.right;
                    w.right=py;
                    w.left=v;
                    v.right.color='r';
                }
                //case 2
                else
                {
                    x=w.right;
                    w.right=x.left;
                    py.left=x.right;
                    x.left=v;
                    x.right=py;
                    x.color='b';
                }
            }
            //Rr2 (Rr2 rotation is similar to Rr1 case 2)
            else
            {
                x=w.right;
                w.right=x.left;
                py.left=x.right;
                x.left=v;
                x.right=py;
                x.color='b';
            }
            redBlack gy=null;
            if(!st.isEmpty())
             gy=st.pop();
            //here we will checking whether the change of root is required after rotation is performed and
            //also change the child of gy if required 
            if(redCount==0)
            {
                if(gy==null)
                {
                    rootChange= v;
                }
                else
                {
                    if(gy.left==py)
                     gy.left=v;
                    else
                     gy.right=v; 
                }
            }
            else if(redCount==1&&w.left!=null&&w.left.color=='r')
            {
                if(gy==null)
                {
                    rootChange= w;
                }
                else
                {
                    if(gy.left==py)
                     gy.left=w;
                    else
                     gy.right=w; 
                }
            }    
            else
            {
                if(gy==null)
                {
                    rootChange= x;
                }
                {
                    if(gy.left==py)
                     gy.left=x;
                    else
                     gy.right=x; 
                }
            } 
        }
        else if(deletionType.equals("Lb"))
        {
            int redCount=0;
            redBlack w=null;
            if(v.left!=null&&v.left.color=='r')
            {
                redCount++;
            }
            if(v.right!=null&&v.right.color=='r')
            {
                redCount++;
            }
            if(redCount==0)
            {
                if(py.color=='b')
                {
                    v.color='r';
                    st.push(py);
                    if(st.size()>1)
                    {
                        performActualDelete(deleteCount+1,st);
                    }
                }
                else
                {
                    py.color='b';
                    v.color='r';
                }
            }
            else if(redCount==1)
            {
                if(v.right!=null&&v.right.color=='r')
                {
                    py.right=v.left;
                    v.left=py;
                    v.color=py.color;
                    v.right.color='b';
                    py.color='b';
                }
                else
                {
                    w=v.left;
                    v.left=w.right;
                    py.right=w.left;  
                    w.right=v;
                    w.left=py;
                    w.color=py.color;
                    py.color='b';
                }
            }
            else
            {
                w=v.left;
                v.left=w.right;
                py.right=w.left;  
                w.right=v;
                w.left=py;
                w.color=py.color;
                py.color='b';
            }
            if((redCount==1&&!(v.right!=null&&v.right.color=='r'))||redCount==2)
            {
                if(st.isEmpty())
                {
                    rootChange= w;
                }
                else
                {
                    redBlack gy=st.pop();
                    if(gy.left==py)
                        gy.left=w;
                    else
                        gy.right=w;
                }
            }
            else if(redCount==1)
            {
                if(st.isEmpty())
                {
                    rootChange= v;
                }
                else
                {
                    redBlack gy=st.pop();
                    if(gy.left==py)
                        gy.left=v;
                    else
                        gy.right=v;
                }
            }   
        }
        else if(deletionType.equals("Lr"))
        {
            int redCount=0;
            redBlack w=v.left;
            redBlack x=null;
            if(w.left!=null&&w.left.color=='r')
            {
                redCount++;
            }
            if(w.right!=null&&w.right.color=='r')
            {
                redCount++;
            }
            if(redCount==0)
            {
                py.right=v.left;
                v.left=py;
                v.color='b';
                py.right.color='r';
            }
            else if(redCount==1)
            {
                if(w.right!=null&&w.right.color=='r')
                {
                    v.left=w.right;
                    py.right=w.left;
                    w.left=py;
                    w.right=v;
                    v.left.color='r';
                }
                else
                {
                    x=w.left;
                    w.left=x.right;
                    py.right=x.left;
                    x.right=v;
                    x.left=py;
                    x.color='b';
                }
            }
            else
            {
                x=w.left;
                w.left=x.right;
                py.right=x.left;
                x.right=v;
                x.left=py;
                x.color='b';
            }
            redBlack gy=null;
            if(!st.isEmpty())
             gy=st.pop();
            if(redCount==0)
            {
                if(gy==null)
                {
                    rootChange= v;
                }
                else
                {
                    if(gy.left==py)
                     gy.left=v;
                    else
                     gy.right=v; 
                }
            }
            else if(redCount==1&&w.right!=null&&w.right.color=='r')
            {
                if(gy==null)
                {
                    rootChange= w;
                }
                else
                {
                    if(gy.left==py)
                     gy.left=w;
                    else
                     gy.right=w; 
                }
            }    
            else
            {
                if(gy==null)
                {
                    rootChange= x;
                }
                {
                    if(gy.left==py)
                     gy.left=x;
                    else
                     gy.right=x; 
                }
            } 
        }
        if(deleteCount==0)
        {
            if(py.left==y)
            {
                py.left=null;
            }
            else
            {
                py.right=null;
            }
        }
        return rootChange;
    }
    
    public static void findLargestONRight(redBlack root,Stack<redBlack> st)
    {
        //we keep on right until we reach null cause largest element doesn't have right child
        st.push(root);
        if(root.right==null)
         return;
        findLargestONRight(root.right,st); 
    }

    public static redBlack insert(int rideNumber,int rideCost,int tripDuration,redBlack root,Stack<redBlack> st)
    {
        //The insert case is pretty much basic binary search tree insertion we insert the node where we fall
        //the main difference is that we perform rebalancing after Insertion
        if(root==null)
        {
            redBlack newLeaf=new redBlack(rideNumber,rideCost,tripDuration, null, null,'r');
            st.push(newLeaf);
            return newLeaf;
        }
        if(root.rideNumber==rideNumber)
        {
            isDuplicate=true;
            st.push(root);
            return root;
        }
        st.push(root);
        if(rideNumber>root.rideNumber)
        {
            root.right=insert(rideNumber,rideCost,tripDuration,root.right,st);
        }
        else
        {
            root.left=insert(rideNumber,rideCost,tripDuration, root.left,st);
        }
        return root;
    }

}
