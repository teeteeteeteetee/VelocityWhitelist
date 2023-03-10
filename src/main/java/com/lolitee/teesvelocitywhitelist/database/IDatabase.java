package com.lolitee.teesvelocitywhitelist.database;

import java.sql.*;

import static com.lolitee.teesvelocitywhitelist.TeesVelocityWhitelist.logger;

public interface IDatabase {
    Connection createConnection();

    default ResultSet execute(AbstractCommand cmd) {
        Connection conn = createConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(cmd.sql());

            for (int i = 0; i < cmd.vals.length; i++) {
                stmt.setObject(i + 1, cmd.vals[i].toString());
            }
            return stmt.getResultSet();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    default boolean executeNonQuery(AbstractCommand cmd) {

        Connection conn = createConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(cmd.sql());

            for (int i = 0; i < cmd.vals.length; i++) {
                stmt.setObject(i + 1, cmd.vals[i].toString());
            }

            return stmt.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
