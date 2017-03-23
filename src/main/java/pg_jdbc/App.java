package pg_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.postgresql.util.PGobject;

/**
 * JDBC, JPA経由でPostgreSQLのjsonデータ型を使用する
 */
public class App {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:15432/mydb";
        try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {
            insertByPGObject(conn);
            //insertByCast(conn);
            //insertByJPA();
            select(conn);
        }
    }
    /**
     * JDBC経由でjson型列を取得
     * @throws SQLException 
     */
    public static void select(Connection conn) throws SQLException {
        try (
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM json_test");
            ResultSet rs = ps.executeQuery();
        ){
            while (rs.next()) {
                System.out.printf("%d\t%s\n",
                        rs.getInt("id"),
                        rs.getString("info")
                );
            }
        }
    }

    /**
     * PGobjectでパラメータ設定してINSERT
     * @param conn
     * @throws SQLException 
     */
    public static void insertByPGObject(Connection conn) throws SQLException {
        try (
            PreparedStatement ps = conn.prepareStatement("INSERT INTO json_test(info) VALUES(?)");
        ) {
            // {"a": 200, "b": "World"}
            String json_string = "{\"a\": 200, \"b\": \"World\"}";

            //PostgreSQL JDBCドライバのPgObjectでsetObjectする
            PGobject pgobj = new PGobject();
            pgobj.setValue(json_string);
            pgobj.setType("jsonb");
            ps.setObject(1, pgobj);

            ps.executeUpdate();
        }
    }

    /**
     * SQL内でキャストしてINSERT
     * @param conn
     * @throws SQLException 
     */
    public static void insertByCast(Connection conn) throws SQLException {
        try (
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO json_test(info) VALUES(jsonb(?))");
        ) {
            // {"a": 300, "b": "Json"}
            String json_string = "{\"a\": 300, \"b\": \"Json\"}";

            ps.setString(1, json_string);
            ps.executeUpdate();
        }
    }

    /**
     * JPAでのSELECT/INSERT
     */
    public static void insertByJPA() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager em = factory.createEntityManager();

        EntityTransaction entityTransaction = em.getTransaction();

        //1件取得
        JsonTest json_test = em.find(JsonTest.class, 1);
        System.out.println(json_test.getInfo());

        // {"a": 300, "b": "Json"}
        String json_string = "{\"a\": 400, \"b\": \"Json JPA\"}";

        //1件追加
        entityTransaction.begin();
        JsonTest entity = new JsonTest();
        entity.setInfo(json_string);    //文字列で設定(converter設定済み) 
        em.persist(entity);
        entityTransaction.commit();

    }

}
