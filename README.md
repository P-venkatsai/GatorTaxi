# Ride Sharing Service Implementation Documentation

**ADS Report - Spring 2023**

Author: Venkat Sai Putagumpalla  
UFID: 33088798  
UF Email: v.putagumpalla@ufl.edu

## Project Description

This project implements a ride-sharing service that manages pending ride requests using a combination of a Red-Black Tree and a Min Heap. Each ride is characterized by three attributes: `rideNumber`, `rideCost`, and `tripDuration`. The Min Heap is used to index rides by `rideCost`, while the Red-Black Tree stores nodes based on `rideNumber`. The goal is to achieve efficient operations with a time complexity of O(log n) for basic operations, except for range printing.

## Data Representation

Ride attributes:

- **Ride Number:** Unique identifier for each ride.
- **Ride Cost:** Cost of the ride.
- **Trip Duration:** Duration of the trip.

## Red-Black Tree Representation

```
public class redBlack {
    public int rideNumber;
    public int rideCost;
    public int tripDuration;
    public redBlack left;
    public redBlack right;
    public char color;
    public minHeap corressHeap;
}
```

## Min Heap Tree Representation

```
public class minHeap {
    // Ordered by ride cost
    public int rideNumber;
    public int rideCost;
    public int tripDuration;
    public int index = 0;
    public redBlack corressRedBlack;
}
```

### Implementation

### Red-Black Tree

#### Insertion

1. Insert into the Red-Black Tree as in a normal binary search tree.
2. Perform tree rebalancing if necessary.
3. Rebalancing is needed when two consecutive nodes are red.
4. Perform rotations according to XYZ notation.

#### Deletion

1. Divided deletion cases into two steps:
   - Find the node to be deleted while tracking the path and swap it with the largest node in the right subtree if it's a level two node.
   - After finding the node to delete, perform the deletion and rebalance the tree if needed.

#### Search

- Searching in the Red-Black Tree is like searching in a BST.
- For range searches, traverse only the branches that might contain values within the range.

### Min Heap

#### Remove Min

1. Replace the last element with the first element in the heap, decrease the heap size.
2. Heapify the element downward by swapping with the smallest child if necessary.

#### Arbitrary Remove

1. Change the value of the arbitrary node to the minimum value, then heapify it up the tree and perform remove min.

#### Insert

1. Insert a new element at the last position in the heap.
2. Heapify up by comparing child's ride cost with parent's ride cost and swapping if needed.

### Function Prototypes and Time Complexities

#### Min Heap

- `int insertIntoHeap(int rideNumber, int rideCost, int tripDuration, minHeap[] mh, int track)`
  - Insert an element into the min heap and heapify.
  - Time Complexity: O(log n)
  - Space Complexity: O(1)

- `public static int deleteMinNode(minHeap[] mh, int track)`
  - Delete the minimum element in the heap and heapify.
  - Time Complexity: O(log n)
  - Space Complexity: O(1)

- `public static void makeCurrentNodeMin(int index, minHeap[] mh, int track)`
  - Make an arbitrary node the minimum and heapify.
  - Time Complexity: O(log n)
  - Space Complexity: O(1)

## Red-Black Trees

1. `delete(root, rideNumber, st)`: Finds and deletes an element, with time and space complexities of O(log n) where n is the number of nodes.

2. `performActualDelete(deleteCount, st)`: Deletes a node and performs tree rebalancing. Time: O(log n), Space: O(1).

3. `findLargestONRight(root, st)`: Finds the largest element in the left subtree of a degree two node. Time: O(log n), Space: O(log n).

4. `insert(rideNumber, rideCost, tripDuration, root, st)`: Inserts a node into the tree. Time: O(log n), Space: O(1).

5. `reBalanceRedBlack(st)`: Rebalances the tree after an insertion. Time: O(log n), Space: O(1).


## Conclusion

This project successfully implements a ride-sharing service using a Red-Black Tree and a Min Heap to efficiently manage ride requests, insertions, deletions, and searches while maintaining logarithmic time complexity for most operations. The combination of these data structures ensures optimal performance and responsiveness for the ride-sharing application.


