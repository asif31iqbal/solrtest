package solrtest;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class SolrTest {
	private static final String urlString = "http://localhost:8983/solr/techproducts";
	private static final SolrClient solr = new HttpSolrClient.Builder(urlString).build();
	
	public SolrTest() {
		
	}
	
	public void addDocument() {
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "0901826");
		document.addField("designation", "Senior Dev");
		document.addField("gpa", "3.82");
		try {
			UpdateResponse response = solr.add(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SolrTest solrTest = new SolrTest();
	}
}
