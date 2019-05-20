package Shapes;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Polygon extends Shape {
    //This should be better managed dictionary or something
    private List<Integer[]> points= new ArrayList<Integer[]>();
    private int nPoints;

    public void Polygon() {

    }

    public void addPoints(Integer[] point){
        points.add(point);
    }

    public Integer[] getPoints(int index){
        Integer[] point=points.get(index);
        return point;
    }

    @Override
    public void draw(Graphics2D g) {
        //get the first point, initialize the first point with it
        int previousx=points.get(0)[0], previousy=points.get(0)[1];
        for (Integer[] point : points.subList(1,points.size()-1)) {
            g.drawLine(previousx, previousy, point[0], point[1]);
            previousx = point[0];
            previousy=point[1];

            if (point== points.get(0)) {
                break;
            }
        }
    }

    public String getCommand(){
        //place holder
        String command="POLYGON ";
        for (Integer[] point : points){
            command+= String.format("%1$d %2$d ", point[0], point[1]);
        }

        return command;
    }
}
