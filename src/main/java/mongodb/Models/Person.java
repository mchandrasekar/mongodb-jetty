package mongodb.Models;

import javax.persistence.Id;

public class Person {
	@Id
	public String id;

	public String name;
	
	public int age;	
	
	public Contact contact;
}
