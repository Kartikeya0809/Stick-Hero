package project.stickhero.Backend;

import javafx.scene.layout.AnchorPane;

public class CourseFactory {
    /*
    * Design Pattern Implemented using this class: Factory
    * Design Patter Used in this Class: Singleton ( Only One Factory is Required );
    * Objects being created  : Pillars, Sticks, Cherries
    * */
    private static CourseFactory Factory;
    private CourseFactory() {
        // Constructor;
    }
    public static CourseFactory getInstanceOf(){
        if ( Factory == null ){
            Factory = new CourseFactory();
        }
        return Factory;
    }
    public PathObstacles getObject( AnchorPane root , String type ){
        if ( type == null ){
            return null;
        }
        else if ( type.equalsIgnoreCase("PILLAR") ){
            return new Pillar( root );

        }else if ( type.equalsIgnoreCase("STICKS") ){
            return new Stick( root );

        }else if ( type.equalsIgnoreCase("CHERRY") ){
            return new Cherry( root );

        }else{
            return null;
        }
    }
}
