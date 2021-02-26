package io.github.flaxeneel2.AIO.databases.SQL;


import io.github.flaxeneel2.AIO.Main;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

@SuppressWarnings("unused")
public class SQL {

    public static Connection conn;
    private static Statement statement;
    static SQL instance = new SQL();

    public static SQL getInstance() { return instance; }


    //this function is for connecting to the database, usually called on plugin startup only
    public void connect(String host, String port, String dbname, String username, String password) throws SQLException, ClassNotFoundException {
        if(conn != null && !conn.isClosed()) return; //if there is already a connection, this prevents further code from being executed
        Class.forName("com.mysql.jdbc.Driver"); //sees if the jdbc driver is there
        conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + dbname + "?autoReconnect=true", username, password); //connects to the database
        Main.inst.sendToConsole("&2&lConnected to mySQL database!");
        DatabaseMetaData metaData = conn.getMetaData(); //gets the data from the database
        ResultSet Tables = metaData.getTables(null, null, "PlayerData", null);
        //checks if the PlayerData table is there, if it is not then this creates it
        if(!Tables.next()) {
            conn.createStatement().executeUpdate("CREATE TABLE PlayerData(PlayerName varchar(256), Balance integer);");
            Main.inst.sendToConsole("&aData table created!");
        }
        statement = conn.createStatement();
    }

    //this function gets the balance of the player
    public Integer getBalance(Player p) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM PlayerData WHERE PlayerName = " + "\"" + p.getName() + "\""); //SQL Statement
        if(result.next()) {
            return result.getInt("Balance");
        }
        setBalance(p, 0);
        return 0;
    }

    //this function sets the balance of a player
    public void setBalance(Player p, Integer b) {
        //this try catch blocks is for dealing with the error if there is any
        try {
            ResultSet tmp = statement.executeQuery("SELECT * FROM PlayerData WHERE PlayerName = " + "\"" + p.getName() + "\"");
            if(tmp.next()) {
            statement.executeUpdate("UPDATE PlayerData SET PlayerName = \"" + p.getName() + "\", Balance = " + b + " WHERE PlayerName = \"" + p.getName() + "\";");
            } else {
                statement.executeUpdate("INSERT INTO PlayerData (PlayerName, Balance) VALUES (\"" + p.getName() + "\", " + b + ");");
            }
        } catch (SQLException e) {
            Main.inst.sendToConsole("&cError!");
            e.printStackTrace();
        }
    }
}
