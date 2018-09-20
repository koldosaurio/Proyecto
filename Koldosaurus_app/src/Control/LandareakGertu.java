/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Landareak;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author DM3-2-15
 */
public class LandareakGertu {
    
     public static ObservableList<Landareak> cargarDatos(){
        
        return FXCollections.observableArrayList(
            new Landareak("koldo","mokoldo","kolkoldo","izoldo",true,"izaskun"),
            new Landareak("","","","",false,""),
            new Landareak("","","","",true,""),
            new Landareak("","","","",true,""),
            new Landareak("","","","",true,"")
        );
    }
    
}
