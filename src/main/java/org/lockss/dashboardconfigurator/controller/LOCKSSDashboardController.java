package org.lockss.dashboardconfigurator.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import javassist.bytecode.Descriptor.Iterator;

import org.lockss.dashboardconfigurator.message.Response;
import org.lockss.dashboardconfigurator.model.*;
import org.lockss.dashboardconfigurator.repo.LOCKSSBoxRepository;

@RestController
@RequestMapping("/api/lockssdashboard")

public class LOCKSSDashboardController {

	/** The Constant TARGET_NAMESPACE. Daemon Status Service namespace  */
	private static final String TARGET_NAMESPACE = "http://status.ws.lockss.org/";

	/** The Constant SERVICE_NAME. Daemon Status Service service name*/
	private static final String SERVICE_NAME = "DaemonStatusServiceImplService";

	/** The Constant QUERY. Daemon Status Service query to get all available info in specific order*/
	private static final String QUERY = "select auId, name, volume, pluginName, tdbYear, accessType, contentSize, diskUsage, recentPollAgreement, tdbPublisher, availableFromPublisher, substanceState, creationTime, crawlProxy, crawlWindow, crawlPool, lastCompletedCrawl, lastCrawl, lastCrawlResult, lastCompletedPoll, lastPollResult, currentlyCrawling, currentlyPolling, subscriptionStatus, auConfiguration, newContentCrawlUrls, urlStems, isBulkContent, peerAgreements";

	/** The Constant IPV4_PATTERN. Grep expression to identify IP address of LOCKSS boxes in the LOCKSS xml file*/
	private static final String IPV4_PATTERN = 
			"(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	
	@Autowired
	LOCKSSBoxRepository repository;

//	@RequestMapping(value = "/boxes", method = RequestMethod.POST)
//	public void postLOCKSSBox(@RequestBody LOCKSSBoxInfo lockssBox) {
//
//		repository.save(new LOCKSSBoxInfo(lockssBox.getIpAddress()));
//	}

	@CrossOrigin
	@RequestMapping(value="/boxes", method = RequestMethod.GET)
	public Response findAll(@RequestParam("configUrl") String configUrl) {
		
		// collect IPs from all boxes in the network listed in lockss.xml
		List<LOCKSSBox> plnMembers=new  ArrayList<LOCKSSBox>();
		System.out.println(configUrl);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		try{
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(new URL(configUrl).openStream());
			doc.getDocumentElement().normalize();
			NodeList propertyList = doc.getElementsByTagName("property");
			for (int temp = 0; temp < propertyList.getLength(); temp++) {
				Node propertyNode = propertyList.item(temp);
				if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) propertyNode;

					if (eElement.getAttribute("name").contains("id.initialV3PeerList")) {
						NodeList valuesList = eElement.getElementsByTagName("value");
						for (int i=0; i<valuesList.getLength(); i++) {
							//LOGGER.info("Value : " + valuesList.item(i).getTextContent());
							Pattern p = Pattern.compile(IPV4_PATTERN);
							Matcher m = p.matcher(valuesList.item(i).getTextContent());
							while (m.find()) {
								plnMembers.add(new LOCKSSBox(m.group())) ;
							}
						}
					}
				}
			}

		}
		catch(Exception e){
			//LOGGER.error(e.toString());
		}
		
		
		// collect IPs from all boxes in the network listed in the postgres database
		Iterable<LOCKSSBoxInfo> lockssBoxesInfo = repository.findAll();
		
		//new array for boxes known and unknown to the database
		List<LOCKSSBoxInfo> allLOCKSSBoxInfo = new ArrayList<LOCKSSBoxInfo> ();
		
		// check if LOCKSS box IPs collected from lockss.xml are known in the database, if not create a LOCKSSBoxInfo with dashboard=false, database=false
		Iterator<LOCKSSBox> itLOCKSSBox =plnMembers.iterator();
		
		while(itLOCKSSBox.hasNext()) {
			Boolean boxInDatabase = false;
			String IP = itLOCKSSBox.next().getIpAddress();
			Iterator<LOCKSSBoxInfo> itLOCKSSBoxInfo = lockssBoxesInfo.iterator();
			while (itLOCKSSBoxInfo.hasNext()) {
				
				LOCKSSBoxInfo currentLOCKSSBoxInfo = itLOCKSSBoxInfo.next();
				
				if (currentLOCKSSBoxInfo.getBox().getIpAddress().equals(IP) ) {
					boxInDatabase= true;
					currentLOCKSSBoxInfo.setDatabase(true);
					allLOCKSSBoxInfo.add(currentLOCKSSBoxInfo);
					break;
				}

			}
			// if box was not found in the database, add a new box
			if (! boxInDatabase) {
				allLOCKSSBoxInfo.add(new LOCKSSBoxInfo (new LOCKSSBox(IP), "unknown", "unknown", 0.0 , 0.0, "unknown", "unknown", false, false));
			}
			
		}
		
		//lockssBoxesInfo.
		return new Response("Done", allLOCKSSBoxInfo);
	}

//	@RequestMapping("/boxes/{id}")
//	public Response findLOCKSSBoxById(@PathVariable("id") long id) {
//
//		LOCKSSBox lockssBox = repository.findOne(id);
//
//		return new Response("Done", lockssBox);
//	}
//
//	@RequestMapping("/boxes/findbyIpAddress")
//	public Response findByIpAddress(@RequestParam("ipAddress") String ipAddress) {
//
//		List<LOCKSSBox> lockssBoxes = repository.findByIpAddress(ipAddress);
//
//		return new Response("Done", lockssBoxes);
//	}

}
