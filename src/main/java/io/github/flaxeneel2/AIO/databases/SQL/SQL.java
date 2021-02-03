package io.github.flaxeneel2.AIO.databases.SQL;


import io.github.flaxeneel2.AIO.Main;
import org.bukkit.entity.Player;

import java.sql.*;

@SuppressWarnings("unused")
public class SQL {

    static Connection conn;
    private static Statement statement;

    public static void connect(String host, String port, String dbname, String username, String password) throws SQLException, ClassNotFoundException {
        if(conn != null && !conn.isClosed()) return;
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql//" + host + ":" + port + "/" + dbname, username, password);
        Main.sendToConsole("&2&lConnected to mySQL database!");
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet Tables = metaData.getTables(null, null, "PlayerData", null);
        if(!Tables.next()) {
            conn.createStatement().executeQuery("CREATE TABLE PlayerData(PlayerName varchar(256), Balance integer);");
            Main.sendToConsole("&aData table created!");
        }
        statement = conn.createStatement();
        statement.executeQuery("USE PlayerData;");
    }

    public static Integer getBalance(Player p) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT * FROM PlayerData WHERE PlayerName = " + p.getName());
        Integer Balance = result.getInt("Balance");
        if(result.wasNull()) { setBalance(p, 0); return 0; }
        return Balance;
    }
    public static void setBalance(Player p, Integer b) {
        try {
            statement.executeQuery("INSERT INTO PlayerData (PlayerName, Balance) VALUES (\"" + p.getName() + "\", " + b + ");");
        } catch (SQLException e) {
            Main.sendToConsole("&cError!");
            e.printStackTrace();
        }
    }
}
