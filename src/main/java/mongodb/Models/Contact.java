package mongodb.Models;

import javax.persistence.Id;

public class Contact {
	@Id
	public String id;
	public String phone;
}
