/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author DM3-2-15
 */
public class Landareak {
    String Name, Description,Color,AvgHeight;
    Boolean Flowers;
    public Landareak(String a,String b, String c, String d,Boolean e){
        setName(a);
        setDescription(b);
        setColor(c);
        setAverageHeight(d);
        setFlowers(e);
    }
    public void setName(String name){
        Name=name;
    }
    public void setDescription(String desc){
        Description=desc;
    }
    public void setColor(String col){
        Color=col;
    }
    public void setAverageHeight(String height){
        AvgHeight=height;
    }
    public void setFlowers(Boolean flow){
        Flowers=flow;
    }
    public String getName(){
        return Name;
    }
    public String getDescription(){
        return Description;
    }
    public String getColor(){
        return Color;
    }
    public String getAverageHeight(){
        return AvgHeight;
    }
    public Boolean getFlowers(){
        return Flowers;
    }
}
