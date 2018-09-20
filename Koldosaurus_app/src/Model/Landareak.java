/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author DM3-2-15
 */
public class Landareak {
    private String Name,CName, Description,Color,Size,Flowers;
    public Landareak(){
        
    }
    public Landareak(String a,String b, String c, String d,Boolean e,String f){
        setName(a);
        setDescription(b);
        setColor(c);
        setSize(d);
        setFlowers(e);
        setCName(f);
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
    public void setSize(String height){
        Size=height;
    }
    public void setFlowers(Boolean flow){
        if (flow==true){
            Flowers="yes";
        }
        else{
            Flowers="no";
        }
        
    }
    public void setCName(String cname){
        CName=cname;
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
    public String getSize(){
        return Size;
    }
    public String getFlowers(){
        return Flowers;
    }
    public String getCName(){
        return CName;
    }
}
