package game.entity;

import javax.persistence.*;

@Entity
@Table(name="movie", schema="public")
public class Movie {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dap_audit_id_seq")
    @SequenceGenerator(name = "dap_audit_id_seq", sequenceName = "public.dap_audit_id_seq", initialValue=1, allocationSize = 1)
    @Column(name="ID", nullable=false)
    private Integer id  ;

   public String name;
   public int year;

}
