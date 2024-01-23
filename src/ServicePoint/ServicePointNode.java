package ServicePoint;

public class ServicePointNode
{
    // Data field
    private int boxesNum;
    private double latitude, longitude;
    private double distance;
    private ServicePointNode next, prev;
    private String outTxt;

    public ServicePointNode(String boxesNum, String latitude, String longitude)
    {
        this.boxesNum = Integer.parseInt(boxesNum);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        next = null;
        prev = null;
        outTxt = "";
    }

    // gets & sets
    public int getBoxesNum()
    {
        return boxesNum;
    }
    public void setBoxesNum(int boxesNum)
    {
        this.boxesNum = boxesNum;
    }
    public Double getLatitude()
    {
        return latitude;
    }
    public Double getLongitude()
    {
        return longitude;
    }
    public ServicePointNode getNext()
    {
        return next;
    }
    public void setNext(ServicePointNode nextNode)
    {
        next = nextNode;
    }
    public ServicePointNode getPrev()
    {
        return prev;
    }
    public void setPrev(ServicePointNode prevNode)
    {
        prev = prevNode;
    }
    public String getOutTxt()
    {
        return outTxt;
    }
    public void setOutTxt()
    {
        String dist = "";
        if(getDistance() == 0.0)
        {
            int distInt = (int) getDistance();
            dist = String.format("%-10d", distInt);
        }
        else
        {
            dist = String.format("%-10.1f", getDistance());
        }

        String out = "Distance:" + dist + "     "
                + "Number of boxes:" + String.format("%-10d", getBoxesNum()) + "     "
                + "Position:(" + getLatitude() + "," + getLongitude() + ")\n";
        outTxt = out;
    }
    public double getDistance()
    {
        return distance;
    }
    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    // Method filed
    // Calculate distance between 2 points using haversine formula
    public double calculateDistance(ServicePointNode startPoint)
    {
        final int radius = 6371000;
        double latitude1 = getLatitude(), latitude2 = startPoint.getLatitude();
        double longitude1 = getLongitude(), longitude2 = startPoint.getLongitude();

        double dLatitude = Math.toRadians(latitude2 - latitude1);
        double dLongitude = Math.toRadians(longitude2 - longitude1);
        latitude1 = Math.toRadians(latitude1);
        latitude2 = Math.toRadians(latitude2);

        distance = Math.pow(Math.sin(dLatitude/2), 2)
                + Math.cos(latitude1)
                * Math.cos(latitude2)
                * Math.pow(Math.sin(dLongitude/2), 2);
        distance = 2 * radius * Math.asin(Math.sqrt(distance));

        return distance;
    }
}