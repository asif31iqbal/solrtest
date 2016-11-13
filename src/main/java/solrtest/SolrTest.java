package solrtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private List<Employee> employees = new ArrayList<Employee>();
	
	public SolrTest() {
		employees.add(new Employee().setId("0901826").setName("Asif Iqbal").setAge(32).setShortBio("Senior Software Engineer, Sysomos, McMaster grad, buet grad"));
		employees.add(new Employee().setId("151533").setName("Anika Tabassum").setAge(26).setShortBio("Software Engineer, FlipGive, McGill grad"));
		employees.add(new Employee().setId("0205032").setName("Jamiul Jahid").setAge(33).setShortBio("Software Engineer, Amazon, UTSA grad, buet grad"));
	}
	
	public void addDocuments() throws Exception {
		for(Employee employee : employees) {
			addDocument(employee);
		}
	}
	
	public void addDocument(Employee employee) throws Exception {
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", employee.getId());
		document.addField("name_t", employee.getName());
		document.addField("age_i", employee.getAge());
		document.addField("bio_t", employee.getShortBio());
		document.addField("designation_s", "Software Engineer");
		try {
			UpdateResponse response = solr.add(document);
			solr.commit();
			System.out.println("Updated document for employee " + employee.getName());
			solr.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateDocument(String employeeId) throws Exception {		 
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		SolrInputDocument sdoc = new SolrInputDocument();
		sdoc.addField("id", employeeId);
		Map<String, Object> fieldModifier = new HashMap<>(1);
		fieldModifier.put("set", "Senior Software Developer");
		sdoc.addField("designation_s", fieldModifier);  // add the map as the field value
		 
		try {
			solr.add(sdoc);
			solr.commit();
			System.out.println("Updated document for employee " + employeeId);
			solr.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public int queryDocuments() throws Exception {
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();
		
		String keyword = "buet";
		SolrQuery query = new SolrQuery();
		query.setFields("id", "name_t", "age_i", "designation_s");
		query.set("q", "bio_t:" + keyword);
		try {
			QueryResponse response = solr.query(query);
			SolrDocumentList list = response.getResults();
			for(SolrDocument document : list) {
				System.out.println("Employee id = " + document.getFieldValue("id") + ", name = " + document.getFieldValue("name_t")
						+ ", age = " + document.getFieldValue("age_i") + ", designation = " + document.getFieldValue("designation_s"));
			}
			solr.close();
			return list.size();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static void main(String[] args) {
		SolrTest solrTest = new SolrTest();
		//solrTest.addDocuments();
		//solrTest.queryDocumnets();
		//solrTest.updateDocument("0901826");
	}
}
