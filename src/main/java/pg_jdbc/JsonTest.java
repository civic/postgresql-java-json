package pg_jdbc;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * JPAエンティティクラス
 * @author tsasaki
 */
@Entity
@Table(name = "json_test")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JsonTest.findAll", query = "SELECT j FROM JsonTest j")
    , @NamedQuery(name = "JsonTest.findById", query = "SELECT j FROM JsonTest j WHERE j.id = :id")
})
public class JsonTest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "info")
    @Convert(converter = JsonbConverter.class)
    private String info;

    public JsonTest() {
    }

    public JsonTest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JsonTest)) {
            return false;
        }
        JsonTest other = (JsonTest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pg_jdbc.JsonTest[ id=" + id + " ]";
    }
    
}
