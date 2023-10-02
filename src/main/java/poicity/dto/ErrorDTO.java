package poicity.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

	private Date timestamp;
	private String message;
	
	public ErrorDTO(String message) {
		timestamp = new Date();
		this.message = message;
	}
	
}
