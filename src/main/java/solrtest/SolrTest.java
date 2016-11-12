package solrtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import solrtest.model.Employee;

public class SolrTest {
	private static final String urlString = "http://localhost:8983/solr/test";
	private static final SolrClient solr = new HttpSolrClient.Builder(urlString).build();
	private List<Employee> employees = new ArrayList<Employee>();
	
	public SolrTest() {
		employees.add(new Employee().setId("0901826").setName("Asif Iqbal").setAge(32).setShortBio("Senior Software Engineer, Sysomos, McMaster grad, buet grad"));
		employees.add(new Employee().setId("151533").setName("Anika Tabassum").setAge(26).setShortBio("Software Engineer, FlipGive, McGill grad"));
		employees.add(new Employee().setId("0205032").setName("Jamiul Jahid").setAge(33).setShortBio("Software Engineer, Amazon, UTSA grad, buet grad"));
	}
	
	public void addDocuments() {
		for(Employee employee : employees) {
			addDocument(employee);
		}
	}
	
	public void addDocument(Employee employee) {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", employee.getId());
		document.addField("name_t", employee.getName());
		document.addField("age_i", employee.getAge());
		document.addField("bio_t", employee.getShortBio());
		try {
			UpdateResponse response = solr.add(document);
			solr.commit();
			System.out.println("Updated document for employee " + employee.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void queryDocumnets() {
		String keyword = "buet";
		SolrQuery query = new SolrQuery();
		query.setFields("id", "name_t", "age_i");
		query.set("q", "bio_t:" + keyword);
		try {
			QueryResponse response = solr.query(query);
			SolrDocumentList list = response.getResults();
			for(SolrDocument document : list) {
				System.out.println("Employee id = " + document.getFieldValue("id") + ", name = " + document.getFieldValue("name_t")
						+ ", age = " + document.getFieldValue("age_i"));
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SolrTest solrTest = new SolrTest();
		//solrTest.addDocuments();
		solrTest.queryDocumnets();
	}
}
