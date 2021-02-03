package io.github.flaxeneel2.AIO.databases.SQL;


import io.github.flaxeneel2.AIO.Main;
import org.bukkit.entity.Player;

import java.sql.*;

@SuppressWarnings("unused")
public class SQL {

    static Connection conn;
    private static Statement statement;


    //this function is for connecting to the database, usually called on plugin startup only
    public static void connect(String host, String port, String dbname, String username, String password) throws SQLException, ClassNotFoundException {
        if(conn != null && !conn.isClosed()) return; //if there is already a connection, this prevents further code from being executed
        Class.forName("com.mysql.cj.jdbc.Driver"); //sees if the jdbc driver is there
        conn = DriverManager.getConnection("jdbc:mysql//" + host + ":" + port + "/" + dbname, username, password); //connects to the database
        Main.sendToConsole("&2&lConnected to mySQL database!");
        DatabaseMetaData metaData = conn.getMetaData(); //gets the data from the database
        ResultSet Tables = metaData.getTables(null, null, "PlayerData", null);
        //checks if the PlayerData table is there, if it is not then this creates it
        if(!Tables.next()) {
            conn.createStatement().executeQuery("CREATE TABLE PlayerData(PlayerName varchar(256), Balance integer);");
            Main.sendToConsole("&aData table created!");
        }
        statement = conn.createStatement();
        //switches to the table being used to store the player data
        statement.executeQuery("USE PlayerData;");
    }

    //this function gets the balance of the player
    public static Integer getBalance(Player p) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM PlayerData WHERE PlayerName = " + p.getName()); //SQL Statement
        Integer Balance = result.getInt("Balance"); //gets the integer value from the "Balance" column
        if(result.wasNull()) { setBalance(p, 0); return 0; } //if the balance of the player is not set, this sets the balance to 0 and returns 0 as the balance.
        return Balance; //if the balance was set, this returns the balance
    }

    //this function sets the balance of a player
    public static void setBalance(Player p, Integer b) {
        //this try catch blocks is for dealing with the error if there is any
        try {
            statement.executeQuery("INSERT INTO PlayerData (PlayerName, Balance) VALUES (\"" + p.getName() + "\", " + b + ");");
        } catch (SQLException e) {
            Main.sendToConsole("&cError!");
            e.printStackTrace();
        }
    }
}
