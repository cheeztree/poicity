package poicity.entity;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import poicity.entity.others.PoiLinks;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "poi")
public class PointOfInterest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
//    @OneToOne
//    private Coordinates coordinates;
	private double latitude;
	private double longitude;
	private String description;
	private double rating;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_city", referencedColumnName = "id")
	@ToString.Exclude
	private City city;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_poi", referencedColumnName = "id")
	@ToString.Exclude
	private List<PointOfInterestImage> poi;

	@OneToMany(mappedBy = "poi", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@ToString.Exclude
	private List<UsersPoisChoices> usersPoisChoices;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_poi", referencedColumnName = "id")
	private List<PoiTime> poiTime;
	
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_poi", referencedColumnName = "id")
	private List<PoiLinks> links;

}
