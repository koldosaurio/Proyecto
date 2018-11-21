/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Landareak;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author DM3-2-15
 */
public class LandareakGertu {

    public static Connection datuBaseIzenak() {

        Connection con = null;
        try {
            String url = "jdbc:sqlite:DBs\\datuBaseIzenak4.db";
            con = DriverManager.getConnection(url);
        } catch (SQLException sqex) {
            System.out.println(sqex.getMessage());
        }
        return con;
    }

    public static void createTableSQLiteDatabases() {

        try (Connection con = datuBaseIzenak(); Statement stmt = con.createStatement();) {
            String tablasortu = "CREATE TABLE IF NOT EXISTS SQLiteDatubaseak(\n"
                    + "izena text,"
                    + "mota text);";
            stmt.execute(tablasortu);
        } catch (SQLException sqlex) {
            System.out.println("error");
        }

    }

    public static ObservableList<String> datuBaseIzenZerrenda(String mota) {
        ObservableList<String> listia = FXCollections.observableArrayList();
        try (Connection con = datuBaseIzenak();
                Statement stmt = con.createStatement()) {
            String sententzia = "";
            if (mota.equals("SQLite")) {
                sententzia = "Select * from SQLiteDatubaseak WHERE mota = 'SQLite'";
            } else {
                sententzia = "Select * from SQLiteDatubaseak WHERE mota = 'MYSQL'";
            }

            ResultSet rs = stmt.executeQuery(sententzia);
            while (rs.next()) {
                listia.add(rs.getString("izena"));
            }

        } catch (SQLException sqle) {
            System.out.println("error");
        }

        return listia;
    }

    public static Connection connectSQLite(String izena) {
        Connection con = null;
        try {
            String url = "jdbc:sqlite:DBs\\" + izena;
            con = DriverManager.getConnection(url);
            //ez dau tabla sortzen
            String tablasortu = "CREATE TABLE IF NOT EXISTS landareak"
                    + "(izena text,"
                    + "Description text,"
                    + "color text,"
                    + "size integer,"
                    + "flowers text,"
                    + "CName text primary key);";
            Statement stmt = con.createStatement();
            stmt.execute(tablasortu);
        } catch (SQLException sqex) {
            System.out.println(sqex.getMessage());
        }
        return con;
    }

    //Debug missing
    public static ObservableList<Landareak> SQLiteDatuak(String izena) {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        try (Connection con = connectSQLite(izena);
                Statement stmt = con.createStatement()) {
            izena = izena.replace(izena.substring(izena.length() - 3), "");
            if (datuBaseIzenZerrenda("SQLite").contains(izena)) {
                String sententzia = "SELECT * from landareak";
                ResultSet rs = stmt.executeQuery(sententzia);
                while (rs.next()) {
                    Landareak land = new Landareak(rs.getString("izena"), rs.getString("Description"), rs.getString("color"), rs.getInt("size") + "", Boolean.parseBoolean(rs.getString("flowers")), rs.getString("CName"));
                    listia.add(land);
                }
            } else {
                try (Connection con2 = datuBaseIzenak();
                        Statement stmt2 = con2.createStatement()) {
                    String gehituTaula = "insert into SQLiteDatubaseak values('" + izena + "','SQLite');";
                    stmt2.executeUpdate(gehituTaula);
                } catch (SQLException sqlex) {
                    System.out.println(sqlex.getMessage());
                }
            }

        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
        return listia;
    }
    public static void addToDatabase(Landareak landarea, String datubasea){
        try(Connection con=connectSQLite(datubasea+".db");
                Statement stmt=con.createStatement();){
            String inserta = "insert into landareak values('"+landarea.getName()+"','"+landarea.getDescription()+"','"+landarea.getColor()+"',"+Float.parseFloat(landarea.getSize().replace(landarea.getSize().substring(landarea.getSize().length()-1,landarea.getSize().length()), ""))+",'"+landarea.getFlowers()+"','"+landarea.getCName()+"');";
            stmt.executeUpdate(inserta);
        }catch(SQLException exSQL){
            System.out.println("ERROR");
        }
    }
    public static void deleteFromDatabase(Landareak landarea, String datubasea){
        //http://www.sqlitetutorial.net/sqlite-java/delete/
        try(Connection con=connectSQLite(datubasea+".db");
                Statement stmt=con.createStatement();){
            String inserta = "delete from landareak where CName='"+landarea.getCName()+"');";
            stmt.executeUpdate(inserta);
        }catch(SQLException exSQL){
            System.out.println("ERROR");
        }
    }

}
/* public static ObservableList<Landareak> fitxategiaAukeratu(File aukeratua) {

        String ext = aukeratua.getName().substring(aukeratua.getName().length() - 4);
        try {
            if (ext.equals(".txt")) {
                return datuakKargatu(aukeratua);
            } else if (ext.equals(".xml")) {
                return datuakKargatuxml(aukeratua);
            } else if (ext.equals("json")) {
                long sizeinkb=aukeratua.length()/1024;
                if(sizeinkb<5){
                    return listaKargatuJson(aukeratua);
                }
                else{
                    return listaKargatuStringJSON(aukeratua);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LandareakGertu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void datuakGordeFitxategia(ObservableList<Landareak> land, File aukeratua) {
        String ext = aukeratua.getName().substring(aukeratua.getName().length() - 4);
        if (ext.equals(".txt")) {
            lista_gorde(land, aukeratua);
        } else if (ext.equals(".xml")) {
            lista_gordexml(land, aukeratua);
        } else if (ext.equals("json")) {
            long sizeinkb=aukeratua.length()/1024;
                if(sizeinkb<5){
                    listaGordeJson(land, aukeratua);
                }else{
                    DatuakGordeJSONStream(land, aukeratua);
                }
            
        }
    }

    public static ObservableList<Landareak> datuakKargatu(File aukeratua) throws IOException {

        ObservableList<Landareak> listia = FXCollections.observableArrayList();

        String line;
        String[] arrs;

        try {
            BufferedReader br = new BufferedReader(new FileReader(aukeratua));
            while ((line = br.readLine()) != null) {
                arrs = line.split("#");
                Landareak land = new Landareak(arrs[0], arrs[1], arrs[2], arrs[3], Boolean.parseBoolean(arrs[4]), arrs[5]);
                listia.add(land);
            }
            return listia;
        } catch (IOException io) {
            System.out.println("ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
        }
        return null;
    }

    public static void lista_gorde(ObservableList<Landareak> landare, File aukeratua) {
        try {
            PrintWriter output = new PrintWriter(new FileWriter(aukeratua));
            for (Landareak land : landare) {
                output.printf(land.getName() + "#" + land.getDescription() + "#" + land.getColor() + "#" + land.getSize().replace(land.getSize().substring(land.getSize().length() - 1), "") + "#" + land.getFlowers(3) + "#" + land.getCName() + "\r\n");
            }
            output.close();
        } catch (IOException io) {

        }
    }

    public static ObservableList<Landareak> datuakKargatuxml(File aukeratua) throws IOException {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(aukeratua);
            NodeList nList = doc.getElementsByTagName("Landarea");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Element eElement = (Element) nList.item(temp);
                Landareak landare = new Landareak(eElement.getElementsByTagName("Name").item(0).getTextContent(),
                        eElement.getElementsByTagName("Description").item(0).getTextContent(),
                        eElement.getElementsByTagName("Color").item(0).getTextContent(),
                        eElement.getElementsByTagName("Size").item(0).getTextContent(),
                        Boolean.parseBoolean(eElement.getElementsByTagName("Flowers").item(0).getTextContent()),
                        eElement.getElementsByTagName("CName").item(0).getTextContent());
                listia.add(landare);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listia;
    }

    public static void lista_gordexml(ObservableList<Landareak> landare, File aukeratua) {
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
//
            // definimos el elemento raíz del documento
            Element eRaiz = doc.createElement("Landareak");
            doc.appendChild(eRaiz);
            for (Landareak land : landare) {
                Element eLandare = doc.createElement("Landarea");
                eRaiz.appendChild(eLandare);

                Element eName = doc.createElement("Name");
                eName.appendChild(doc.createTextNode(land.getName()));
                eLandare.appendChild(eName);

                Element eDescription = doc.createElement("Description");
                eDescription.appendChild(doc.createTextNode(land.getDescription()));
                eLandare.appendChild(eDescription);

                Element eColor = doc.createElement("Color");
                eColor.appendChild(doc.createTextNode(land.getColor()));
                eLandare.appendChild(eColor);

                Element eSize = doc.createElement("Size");
                /*land.setSize(land.getSize().substring(0, land.getSize().length() - 1));
                System.out.println(land.getSize());
                eSize.appendChild(doc.createTextNode(land.getSize().replace(land.getSize().substring(land.getSize().length() - 1), "")));
                eLandare.appendChild(eSize);

                Element eFlowers = doc.createElement("Flowers");
                eFlowers.appendChild(doc.createTextNode(land.getFlowers()));
                eLandare.appendChild(eFlowers);

                Element eCName = doc.createElement("CName");
                eCName.appendChild(doc.createTextNode(land.getCName()));
                eLandare.appendChild(eCName);
            }
            // clases necesarias finalizar la creación del archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(aukeratua);

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listaGordeJson(ObservableList<Landareak> landare, File aukeratua) {
        JsonArrayBuilder aBuilder = Json.createArrayBuilder();
        try {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            for (Landareak land : landare) {
                builder.add("Name", land.getName());
                builder.add("Description", land.getDescription());
                builder.add("Color", land.getColor());
                builder.add("Size", land.getSize().replace(land.getSize().substring(land.getSize().length() - 1), ""));
                builder.add("Flowers", land.getFlowers());
                builder.add("CName", land.getCName());
                JsonObject landarea = builder.build();
                aBuilder.add(landarea);
            }
            JsonArray landareak = aBuilder.build();
            JsonWriter jsn = Json.createWriter(new FileOutputStream(aukeratua));
            jsn.writeArray(landareak);
            jsn.close();
        } catch (FileNotFoundException r) {
            System.out.println("kkkkk");
        }

    }

    public static ObservableList<Landareak> listaKargatuJson(File aukeratua) {
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        try {
            JsonReader reader = Json.createReader(new FileInputStream(aukeratua));
            JsonArray landareak = reader.readArray();
            reader.close();

            for (int i = 0; i < landareak.size(); i++) {
                JsonObject n = landareak.getJsonObject(i);
                Landareak land = new Landareak();
                land.setName(n.getString("Name"));
                land.setDescription(n.getString("Description"));
                land.setColor(n.getString("Color"));
                land.setSize(n.getString("Size"));
                if (n.getString("Flowers").equals("yes")) {
                    land.setFlowers(true);
                } else {
                    land.setFlowers(false);
                }
                land.setCName(n.getString("CName"));
                listia.add(land);
            }
        } catch (FileNotFoundException e) {

        }
        return listia;
    }
    //Hemendik aurrera json reader eta writer stream erabiliz

    public static ObservableList<Landareak> listaKargatuStringJSON(File aukeratua) throws FileNotFoundException {//Falta implementar al codigo
        JsonParser parser = Json.createParser(new FileInputStream(aukeratua));
        ObservableList<Landareak> listia = FXCollections.observableArrayList();
        ArrayList<String> identifier = new ArrayList<String>();
        boolean a = true;
        while (parser.hasNext()) {
            JsonParser.Event event = parser.next();
            switch (event) {
                case START_ARRAY:
                case END_ARRAY:
                case START_OBJECT:
                case END_OBJECT:
                case VALUE_FALSE:
                case VALUE_NULL:
                case VALUE_TRUE:
                    if (event.toString().equals("END_OBJECT")) {
                        if (identifier.get(4).equals("yes")) {
                            a = true;
                        } else {
                            a = false;
                        }
                        Landareak land = new Landareak(identifier.get(0), identifier.get(1), identifier.get(2), identifier.get(3), a, identifier.get(5));
                        listia.add(land);
                    } else if (event.toString().equals("START_OBJECT")) {
                        identifier.clear();
                    }
                    break;
                case KEY_NAME:
                    break;
                case VALUE_STRING:
                case VALUE_NUMBER:
                    identifier.add(parser.getString());
                    break;
            }
        }
        return listia;
    }

    public static void DatuakGordeJSONStream(ObservableList<Landareak> listia, File aukeratua) {
        FileWriter writer;
        try {
            JsonGenerator gen = Json.createGenerator(new FileWriter(aukeratua));
            gen.writeStartArray();
            for (Landareak i : listia) {
                gen.writeStartObject()
                        .write("Name", i.getName())
                        .write("Description", i.getDescription())
                        .write("Color", i.getColor())
                        .write("Size", i.getSize().replace(i.getSize().substring(i.getSize().length() - 1), ""))
                        .write("Flowers", i.getFlowers())
                        .write("CName", i.getCName())
                        .writeEnd();
            }
            gen.writeEnd();
            gen.close();
        } catch (IOException ex) {
        }

    }
}
 */
