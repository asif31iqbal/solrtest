package solrtest.model;

public class Employee {
	private String id;
	private String name;
	private String designation;
	private int age;
	private String shortBio;
	
	public String getId() {
		return id;
	}
	public Employee setId(String id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Employee setName(String name) {
		this.name = name;
		return this;
	}
	public String getDesignation() {
		return designation;
	}
	public Employee setDesignation(String designation) {
		this.designation = designation;
		return this;
	}
	public int getAge() {
		return age;
	}
	public Employee setAge(int age) {
		this.age = age;
		return this;
	}
	public String getShortBio() {
		return shortBio;
	}
	public Employee setShortBio(String shortBio) {
		this.shortBio = shortBio;
		return this;
	}
}
