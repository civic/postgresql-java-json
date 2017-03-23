package pg_jdbc;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.postgresql.util.PGobject;

/**
 * JsonbとStringを変換するコンバーター。
 */
@Converter
public class JsonbConverter implements AttributeConverter<String, PGobject>{

    /**
     * String-Pgobjectへの変換
     * @param x
     * @return 
     */
    @Override
    public PGobject convertToDatabaseColumn(String x) {
        PGobject pgobject =new PGobject();
        pgobject.setType("jsonb");
        try {
            pgobject.setValue(x);
        } catch (SQLException ex) {
            Logger.getLogger(JsonbConverter.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        return pgobject;
    }

    @Override
    /**
     * Pgobject-Stringへの変換
     * @param y
     * @return 
     */
    public String convertToEntityAttribute(PGobject y) {
        return y.getValue();
    }
    
}
