package ServicePoint;

import java.util.NoSuchElementException;

public class ServicePointList
{
    // Data field
    private ServicePointNode first;
    private int size;
    private String outTxt;

    // Constructor
    public ServicePointList()
    {
        first = null;
        size = 0;
        outTxt = "";
    }

    // gets & sets
    public ServicePointNode getFirst()
    {
        if(first == null) throw new NullPointerException();
        return first;
    }
    public void setFirst(ServicePointNode newFirst)
    {
        first = newFirst;
    }
    public ServicePointNode getStartPoint()
    {
        ServicePointNode startPoint = getFirst();
        removePointNode(first);
        return startPoint;
    }
    public String getOutTxt() {
        return outTxt;
    }
    public void setOutTxt()
    {
        if(first == null)
        {
            return;
        }
        else
        {
            ServicePointNode currNode = first;
            while(currNode != null)
            {
                currNode.setOutTxt();
                String txt = currNode.getOutTxt();
                outTxt += txt;
                currNode = currNode.getNext();
            }
        }
    }

    // Method field
    // Add service point in front of the list
    public void addFirst(ServicePointNode newNode)
    {
        newNode.setNext(first);
        if(first == null)
        {
            setFirst(newNode);
        }
        else
        {
            newNode.setNext(first);
            first.setPrev(newNode);
            setFirst(newNode);
        }
    }
    // Add service point to the list
    public void add(ServicePointNode newNode)
    {
        if(first == null)
        {
            addFirst(newNode);
            return;
        }

        ServicePointNode currNode = first;
        while(currNode.getNext() != null)
        {
            currNode = currNode.getNext();
        }

        currNode.setNext(newNode);
    }
    // Add element to the list by sorting by box number
    public void addSorted(String boxesNum, String latitude, String longitude)
    {
        ServicePointNode newNode = new ServicePointNode(boxesNum, latitude, longitude);

        if(first == null)
        {
            addFirst(newNode);
        }
        else
        {
            ServicePointNode currNode = getFirst();
            while(currNode != null)
            {
                if(newNode.getBoxesNum() >= currNode.getBoxesNum())
                {
                    if (currNode.equals(first))
                    {
                        addFirst(newNode);
                    }
                    else
                    {
                        newNode.setPrev(currNode.getPrev());
                        currNode.getPrev().setNext(newNode);
                        newNode.setNext(currNode);
                        currNode.setPrev(newNode);
                    }
                    return;
                }
                else
                {
                    if(currNode.getNext() == null)
                    {
                        currNode.setNext(newNode);
                        newNode.setPrev(currNode);
                        return;
                    }
                    else
                    {
                        currNode = currNode.getNext();
                    }
                }
            }
        }
    }
    // select service points available to pick boxes
    public ServicePointNode popPickPoint(ServicePointNode startPoint)
    {
        if(first == null) return null;
        ServicePointNode closestPoint = null;
        double closestDistance = 100000000.0;
        ServicePointNode currNode = first;
        while(currNode != null)
        {
            double distance = currNode.calculateDistance(startPoint);
            currNode.setDistance(distance);
            if(closestDistance > distance)
            {
                closestPoint = currNode;
                closestDistance = distance;
            }
            else if(closestDistance == distance && closestPoint != null)
            {
                if(closestPoint.getLatitude() > currNode.getLatitude())
                {
                    closestPoint = currNode;
                }
                else if(closestPoint.getLongitude() > currNode.getLongitude())
                {
                    closestPoint = currNode;
                }
            }
            currNode = currNode.getNext();
        }

        removePointNode(closestPoint);
        closestPoint.setNext(null);
        closestPoint.setPrev(null);

        return closestPoint;
    }
    // Remove 1 service point from the list
    public void removePointNode(ServicePointNode node)
    {
        if(first == null)
        {
            throw new NoSuchElementException();
        }
        else if(first.equals(node))
        {
            first = first.getNext();
            return;
        }

        ServicePointNode currNode = first;
        while(currNode != null)
        {
            if(currNode.equals(node))
            {
                if(currNode.getPrev() != null)
                {
                    currNode.getPrev().setNext(currNode.getNext());
                }
                if(currNode.getNext() != null)
                {
                    currNode.getNext().setPrev(currNode.getPrev());
                }
            }
            currNode = currNode.getNext();
        }
    }

    // print list to test
//    public void printList()
//    {
//        ServicePointNode currNode = getFirst();
//
//        if(currNode == null) return;
//
//        while(currNode != null)
//        {
//            String printTxt = currNode.getBoxesNum() + " "
//                    + currNode.getLatitude() + " "
//                    + currNode.getLongitude();
//            System.out.println(printTxt);
//            currNode = currNode.getNext();
//        }
//    }
}