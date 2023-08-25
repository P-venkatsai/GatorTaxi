# Ride Sharing Service Implementation Readme

This repository contains the implementation of a ride sharing service developed by Venkat Sai Putagumpalla for the Spring 2023 term. The project utilizes data structures like Red-Black Trees and Min Heaps to efficiently manage and track pending ride requests.

## Project Details

- **Name:** Venkat Sai Putagumpalla
- **UFID:** 33088798
- **UF Email:** v.putagumpalla@ufl.edu

## Project Description

The goal of this project is to create a ride sharing service that keeps track of pending ride requests using a combination of Red-Black Trees and Min Heaps. Each ride request is characterized by three attributes: `rideNumber`, `rideCost`, and `tripDuration`. The Min Heap is indexed by `rideCost`, and the nodes in the Red-Black Tree are stored based on `rideNumber`. The implementation aims to perform basic operations, such as insertion, deletion, and search, in O(log n) time complexity (except for the range print operation).

## Data Representation

Each ride has the following attributes:

1. `rideNumber`: A unique identifier for a specific ride.
2. `rideCost`: The cost of the ride.
3. `tripDuration`: The time it takes to complete the trip.

Two classes are defined to represent the data:

### Red-Black Tree Node (`redBlack`)

```java
public class redBlack {
    public int rideNumber;
    public int rideCost;
    public int tripDuration;
    public redBlack left;
    public redBlack right;
    public char color;
    public minHeap corressHeap;
}

In the `redBlack` class:
- `left` and `right` point to the left and right children of the Red-Black Tree.
- `color` stores the color of the node ('r' for red, 'b' for black).
- `corressHeap` holds the corresponding `minHeap` object.


In the `minHeap` class:
- The heap is ordered by `rideCost`.
- `corressRedBlack` links to the corresponding `redBlack` node for quick access.


**Min Heap Node (`minHeap`)**

```java
public class minHeap {
    // Ordered by ride cost
    public int rideNumber;
    public int rideCost;
    public int tripDuration;
    public int index = 0;
    public redBlack corressRedBlack;
}


**Implementation**
**Red-Black Tree**
**Insertion**
- Insert into the Red-Black Tree as if inserting into a normal Binary Search Tree.
- Perform tree rebalancing if necessary:
  - Check if rebalancing is needed based on node colors.
  - Apply rotation operations according to the XYZ notation.

**Deletion**
- Divided the delete cases into two steps.
  - Find the element to be deleted and add nodes along the path.
  - If the node to be deleted is a level two node, replace it with the largest node in the right subtree and delete that node.
- Perform delete and rebalance operations.

**Search**
- Search in the Red-Black Tree is similar to searching in a Binary Search Tree.
- For range printing, avoid traversing branches that do not contain values in the range.

**Min Heap**
- The children of a node with index `i` are at indices `2*i+1` and `2*i+2`.
- Implement operations like `insert`, `removeMin`, and `arbitraryRemove` following the heap property.

**Function Prototypes and Time Complexities**
- Refer to the project's code comments for detailed function prototypes and their respective time and space complexities.

**General Operations**
- Various operations, including inserting into the Red-Black Tree and Min Heap, deleting, searching, and updating rides, are implemented. Refer to the project's code comments for specific details about these operations and their time and space complexities.

**Note**
- This README provides an overview of the project's structure and implementation. Refer to the source code for comprehensive details on the code implementation and usage. For any inquiries, feel free to contact Venkat Sai Putagumpalla at v.putagumpalla@ufl.edu.
