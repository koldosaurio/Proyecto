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

    public static ObservableList<Landareak> cargarDatos() {

        return FXCollections.observableArrayList(
                new Landareak("Rose", "A rose is a woody perennial\n flowering plant of the genus Rosa,\n in the family Rosaceae, or the flower\n it bears. There are over three hundred\n species and thousands of cultivars.\n They form a group of plants that\n can be erect shrubs, climbing or trailing\n with stems that are often armed\n with sharp prickles.", "multicolor", "0.15", true, "Rosa Rubiginosa"),
                new Landareak("Tulip", "Tulips (Tulipa) form a genus of spring-blooming\n perennial herbaceous bulbiferous geophytes\n (having bulbs as storage organs). The\n flowers are usually large, showy and\n brightly coloured, generally red, pink,\n yellow, or white (usually in warm colours).", "red,pink,yellow,white", "0.1", true, "Tulipa × gesneriana"),
                new Landareak("Oak", "An oak is a tree or shrub in\n the genus Quercus of the beech family,\n Fagaceae. There are approximately 600\n extant species of oaks. The common\n name \"oak\" also appears in the\n names of species in related genera,\n notably Lithocarpus (stone oaks),\n as well as in those of unrelated species\n such as Grevillea robusta (silky oaks)\n and the Casuarinaceae (she-oaks).\n The genus Quercus is native to the\n Northern Hemisphere, and includes deciduous\n and evergreen species extending from\n cool temperate to tropical latitudes in\n the Americas, Asia, Europe,\n and North Africa. ", "Brown, green", "20", false, "Quercus robur"),
                new Landareak("sour cherry", "Prunus is a genus of trees and\n shrubs, which includes the plums,\n cherries, peaches, nectarines, apricots,\n and almonds.\nNative to the northern temperate regions,\n there are 430 different species classified\n under Prunus. Many members of the\n genus are widely cultivated for their\n fruit and for decorative purposes.", "Brown and green\n (white in bloom)", "13", true, "Prunus cerasus")
        );
    }

}
