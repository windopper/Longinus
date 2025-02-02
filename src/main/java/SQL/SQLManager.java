package SQL;

import Mob.MobListManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLManager {

    private Player player;
    private String uuid;

    public SQLManager(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId().toString();
    }

    public static void setUpMySQL() {

        Statement stmt = null;

        try {
            Connection conn = Connector.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE SCHEMA `longinus` DEFAULT CHARACTER SET utf8mb4");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {

            }
        }

        try {
            Connection conn = Connector.getConnection();
            stmt = conn.createStatement();

            stmt.executeUpdate("create table if not exists longinus.mainmarket (" +
                    "milli BIGINT NOT NULL,"+
                    "itemname VARCHAR(45) NOT NULL,"+
                    "uuid VARCHAR(45) PRIMARY KEY NOT NULL,"+
                    "seller VARCHAR(45) NOT NULL,"+
                    "altera BIGINT NOT NULL,"+
                    "count INT NOT NULL,"+
                    "item MEDIUMTEXT NOT NULL,"+
                    "selltime DATETIME NOT NULL)");


        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {

            }
        }

        try {
            Connection conn = Connector.getConnection();
            stmt = conn.createStatement();

            stmt.executeUpdate("create table if not exists longinus.mainmarketlog ("+
                    "name TINYTEXT NOT NULL,"+
                    "altera BIGINT NOT NULL,"+
                    "item MEDIUMTEXT NOT NULL,"+
                    "count INT NOT NULL,"+
                    "seller VARCHAR(45) NOT NULL,"+
                    "buyer VARCHAR(45) NOT NULL,"+
                    "selltime DATETIME NOT NULL,"+
                    "buytime DATETIME NOT NULL,"+
                    "milli VARCHAR(45) PRIMARY KEY NOT NULL)");


        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {

            }
        }

        try {
            Connection conn = Connector.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("create table if not exists longinus.user ("+
                    "name VARCHAR(25) NOT NULL,"+
                    "uuid VARCHAR(45) PRIMARY KEY NOT NULL,"+
                    "altera BIGINT NOT NULL DEFAULT '0',"+
                    "classes MEDIUMTEXT,"+
                    "alarms MEDIUMTEXT,"+
                    "samples MEDIUMTEXT,"+
                    "previousclass VARCHAR(45),"+
                    "locx DOUBLE NOT NULL,"+
                    "locy DOUBLE NOT NULL,"+
                    "locz DOUBLE NOT NULL,"+
                    "marketitems MEDIUMTEXT NOT NULL)");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {

            }
        }

        try {
            Connection conn = Connector.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("create table if not exists longinus.userstorages ("+
                    "uuid VARCHAR(45) NOT NULL PRIMARY KEY,"+
                    "storagelimit INT NOT NULL DEFAULT '3',"+
                    "storage1 MEDIUMTEXT DEFAULT NULL,"+
                    "storage2 MEDIUMTEXT DEFAULT NULL,"+
                    "storage3 MEDIUMTEXT DEFAULT NULL,"+
                    "storage4 MEDIUMTEXT DEFAULT NULL,"+
                    "storage5 MEDIUMTEXT DEFAULT NULL,"+
                    "storage6 MEDIUMTEXT DEFAULT NULL,"+
                    "storage7 MEDIUMTEXT DEFAULT NULL,"+
                    "storage8 MEDIUMTEXT DEFAULT NULL,"+
                    "storage9 MEDIUMTEXT DEFAULT NULL,"+
                    "storage10 MEDIUMTEXT DEFAULT NULL)");

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {

            }
        }

        try {
            Connection conn = Connector.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("create table if not exists longinus.itemlist ("+
                    "name VARCHAR(45) NOT NULL,"+
                    "type VARCHAR(45) NOT NULL,"+
                    "grade VARCHAR(45) NOT NULL,"+
                    "class VARCHAR(45) NOT NULL,"+
                    "str INT NOT NULL DEFAULT 0,"+
                    "dex INT NOT NULL DEFAULT 0,"+
                    "def INT NOT NULL DEFAULT 0,"+
                    "agi INT NOT NULL DEFAULT 0,"+
                    "lvl INT NOT NULL DEFAULT 0,"+
                    "dmg VARCHAR(45) NOT NULL DEFAULT '0-0',"+
                    "health VARCHAR(45) NOT NULL DEFAULT '0~0',"+
                    "shieldP VARCHAR(45) NOT NULL DEFAULT '0~0'," +
                    "skilldmgP VARCHAR(45) NOT NULL DEFAULT '0%~0%',"+
                    "walkspeedP VARCHAR(45) NOT NULL DEFAULT '0%~0%',"+
                    "energychargeP VARCHAR(45) NOT NULL DEFAULT '0%~0%',"+
                    "shieldregentime VARCHAR(45) NOT NULL DEFAULT '0~0',"+
                    "shieldaddP VARCHAR(45) NOT NULL DEFAULT '0%~0%',"+
                    "healthregen VARCHAR(45) NOT NULL DEFAULT '0~0'," +
                    "healP VARCHAR(45) NOT NULL DEFAULT '0%~0%',"+
                    "meleedelay VARCHAR(45) NOT NULL DEFAULT '0~0',"+
                    "costP VARCHAR(45) NOT NULL DEFAULT '0%~0%')");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                stmt.close();
            }
            catch(Exception e) {

            }
        }
    }

    public void initData() {

        Connector Connector = new Connector();
        SQL.Converter converter = new SQL.Converter();

        if(!(new PlayerStorage(player)).isDataExist()) {
            (new PlayerStorage(player)).initUserStorages();
        }

        if(isDataExist()) return;

        String name = player.getName();
        String uuid = player.getUniqueId().toString();
        int altera = 0;

        String classes = null;
        String quests = null;

        YamlConfiguration alarmYaml = new YamlConfiguration();
        for(int i=0; i<=100; i++) {
            alarmYaml.set(i+".content", " ");
            alarmYaml.set(i+".type", " ");
            alarmYaml.set(i+".date", " ");
        }

        String alarms = converter.encodeYaml(alarmYaml);

        YamlConfiguration sampleYaml = new YamlConfiguration();

        for(MobListManager.MobList mobList : MobListManager.MobList.values()) {
            if(mobList.isScannable()) {
                sampleYaml.set(mobList.getPlanet()+"."+mobList.name()+".count", 0);
                sampleYaml.set(mobList.getPlanet()+"."+mobList.name()+".firstSeen", " ");
                sampleYaml.set(mobList.getPlanet()+"."+mobList.name()+".lastSeen", " ");
            }
        }

        String samples = converter.encodeYaml(sampleYaml);
        String previousclass = null;

        double locx = player.getLocation().getX();
        double locy = player.getLocation().getY();
        double locz = player.getLocation().getZ();

        YamlConfiguration marketitemsYaml = new YamlConfiguration();
        marketitemsYaml.set("mainMarket", "");
        String marketitems = converter.encodeYaml(marketitemsYaml);

        try {
            Connection conn = Connector.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("insert into longinus.user values ('"+name+"', '"+uuid+"', '"+altera+"', '"
                    +classes+"', '"+alarms+"', '"+samples+"', '"+previousclass+"', '"
                    +locx+"', '"+locy+"', '"+locz+"', '"+marketitems+"')");
            stmt.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData() {

        Connector Connector = new Connector();

        if(!isDataExist()) return;

        (new PlayerSample(player)).updateNewSampleList(); // 새로운 몬스터가 업데이트 된것이 있으면 업데이트


        try {

            String name = player.getName();
            String uuid = player.getUniqueId().toString();

            Connection conn = Connector.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("update longinus.user set name = '"+name+"' where uuid = '"+uuid+"'");

            stmt.close();
            conn.close();

        }
        catch(Exception e) {

        }
    }
    public boolean isDataExist() {

        Connector Connector = new Connector();

        try {
            String uuid = player.getUniqueId().toString();
            Connection conn = Connector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet set = stmt.executeQuery("select exists ( select * from longinus.user where uuid = '"+uuid+
                    "' ) as success");
            if(set.next()) {
                if(set.getInt("success") == 1) {

                    set.close();
                    stmt.close();
                    conn.close();

                    return true;
                }
            }

            set.close();
            stmt.close();
            conn.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
