package poicity.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "poi_choices")
public class PoiChoices {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition="tinyint")
    private Long id;
    @Column(columnDefinition="varchar(20)", unique=true)
    private String choice;
    
    @OneToMany(mappedBy = "poiChoices", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<UsersPoisChoices> usersPoisChoices;
    
}
