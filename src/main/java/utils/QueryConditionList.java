package utils;

import java.util.ArrayList;
import java.util.HashMap;



public class QueryConditionList {
	
	private HashMap<String, TripleCondition> triples = new HashMap<String, TripleCondition>();
	
	public QueryConditionList() {
		String FCT = "fct:";
		TripleCondition nct = new TripleCondition("?trial",FCT + RdfNames.HASNCT,"?nct");
		triples.put(RdfNames.HASNCT, nct);
		TripleCondition condition = new TripleCondition("?trial",FCT+RdfNames.HASCONDITION,"?cond");
		triples.put(RdfNames.HASCONDITION, condition);
		TripleCondition conflictRep= new TripleCondition("?trial",FCT+RdfNames.HASCONFLICTREP,"?conflictRep");
		triples.put(RdfNames.HASCONFLICTREP, conflictRep);
		TripleCondition reqHeader= new TripleCondition("?trial",FCT+RdfNames.HASREQHEADER,"?reqHeader");
		triples.put(RdfNames.HASREQHEADER, reqHeader);
		TripleCondition secondaryId= new TripleCondition("?trial",FCT+RdfNames.HASSECONDARYID,"?secondaryId");
		triples.put(RdfNames.HASSECONDARYID, secondaryId);
		TripleCondition briefTitle= new TripleCondition("?trial",FCT+RdfNames.HASBRIEFTITLE,"?briefTitle");
		triples.put(RdfNames.HASBRIEFTITLE, briefTitle);
		TripleCondition officialTitle= new TripleCondition("?trial",FCT+RdfNames.HASOFFICIALTITLE,"?officialTitle");
		triples.put(RdfNames.HASOFFICIALTITLE, officialTitle);
		TripleCondition orgStudyId= new TripleCondition("?trial",FCT+RdfNames.HASORGSTUDYID,"?orgStudyId");
		triples.put(RdfNames.HASORGSTUDYID, orgStudyId);
		TripleCondition leadSponsor= new TripleCondition("?trial",FCT+RdfNames.HASLEADSPONSOR,"?leadSponsor");
		triples.put(RdfNames.HASLEADSPONSOR, leadSponsor);
		TripleCondition overallStatus= new TripleCondition("?trial",FCT+RdfNames.HASOVERALLSTATUS,"?overallStatus");
		triples.put(RdfNames.HASOVERALLSTATUS, overallStatus);
		TripleCondition overallContactName= new TripleCondition("?trial",FCT+RdfNames.HASOVERALLCONTACTNAME,"?overallContactName");
		triples.put(RdfNames.HASOVERALLCONTACTNAME, overallContactName);
		TripleCondition overallContactPhone= new TripleCondition("?trial",FCT+RdfNames.HASOVERALLCONTACTPHONE,"?overallContactPhone");
		triples.put(RdfNames.HASOVERALLCONTACTPHONE, overallContactPhone);
		TripleCondition overallContactEmail= new TripleCondition("?trial",FCT+RdfNames.HASOVERALLCONTACTEMAIL,"?overallContactEmail");
		triples.put(RdfNames.HASOVERALLCONTACTEMAIL, overallContactEmail);
		TripleCondition phase= new TripleCondition("?trial",FCT+RdfNames.HASPHASE,"?phase");
		triples.put(RdfNames.HASPHASE, phase);
		TripleCondition startDate= new TripleCondition("?trial",FCT+RdfNames.HASSTARTDATE,"?startDate");
		triples.put(RdfNames.HASSTARTDATE, startDate);
		TripleCondition completeDate= new TripleCondition("?trial",FCT+RdfNames.HASCOMPLETEDATE,"?completeDate");
		triples.put(RdfNames.HASCOMPLETEDATE, completeDate);
		TripleCondition studyType= new TripleCondition("?trial",FCT+RdfNames.HASSTUDYTYPE,"?studyType");
		triples.put(RdfNames.HASSTUDYTYPE, studyType);
		TripleCondition samplingMethod= new TripleCondition("?trial",FCT+RdfNames.HASSAMPLINGMETHOD,"?samplingMethod");
		triples.put(RdfNames.HASSAMPLINGMETHOD, samplingMethod);
		TripleCondition criteriaText= new TripleCondition("?trial",FCT+RdfNames.HASCRITERIA,"?criteriaText");
		triples.put(RdfNames.HASCRITERIA, criteriaText);
		TripleCondition volunteers= new TripleCondition("?trial",FCT+RdfNames.HASVOLUNTEERS,"?volunteers");
		triples.put(RdfNames.HASVOLUNTEERS, volunteers);
		TripleCondition gender= new TripleCondition("?trial",FCT+RdfNames.HASGENDER,"?gender");
		triples.put(RdfNames.HASGENDER, gender);
		TripleCondition minAge= new TripleCondition("?trial",FCT+RdfNames.HASMINAGE,"?minAge");
		triples.put(RdfNames.HASMINAGE, minAge);
		TripleCondition maxAge= new TripleCondition("?trial",FCT+RdfNames.HASMAXAGE,"?maxAge");
		triples.put(RdfNames.HASMAXAGE, maxAge);
		TripleCondition intervention= new TripleCondition("?trial",FCT+RdfNames.HASINTERVENTION,"?inter");
		triples.put(RdfNames.HASINTERVENTION, intervention);
		TripleCondition interName= new TripleCondition("?inter",FCT+RdfNames.HASINTERVENTIONNAME,"?interName");
		triples.put(RdfNames.HASINTERVENTIONNAME, interName);
		TripleCondition interType= new TripleCondition("?inter",FCT+RdfNames.HASINTERVENTIONTYPE,"?interType");
		triples.put(RdfNames.HASINTERVENTIONTYPE, interType);
		TripleCondition interDesc= new TripleCondition("?inter",FCT+RdfNames.HASINTERVENTIONDESCR,"?interDesc");
		triples.put(RdfNames.HASINTERVENTIONDESCR, interDesc);
		TripleCondition armGrpLbl= new TripleCondition("?inter",FCT+RdfNames.HASINTERVENTIONARMGRPLBL,"?armGrpLbl");
		triples.put(RdfNames.HASINTERVENTIONARMGRPLBL, armGrpLbl);
		TripleCondition interOtherName= new TripleCondition("?inter",FCT+RdfNames.HASINTERVENTIONOTHERNAME,"?interOtherName");
		triples.put(RdfNames.HASINTERVENTIONOTHERNAME, interOtherName);
		TripleCondition loc= new TripleCondition("?trial",FCT+RdfNames.HASLOCATION,"?loc");
		triples.put(RdfNames.HASLOCATION, loc);
		TripleCondition locFacility= new TripleCondition("?loc",FCT+RdfNames.HASLOCFACILITY,"?locFacility");
		triples.put(RdfNames.HASLOCFACILITY, locFacility);
		TripleCondition locStatus= new TripleCondition("?loc",FCT+RdfNames.HASLOCSTATUS,"?locStatus");
		triples.put(RdfNames.HASLOCSTATUS, locStatus);
		TripleCondition locCity= new TripleCondition("?loc",FCT+RdfNames.HASLOCCITY,"?locCity");
		triples.put(RdfNames.HASLOCCITY, locCity);
		TripleCondition locCountry= new TripleCondition("?loc",FCT+RdfNames.HASLOCCOUNTRY,"?locCountry");
		triples.put(RdfNames.HASLOCCOUNTRY, locCountry);
		TripleCondition locState= new TripleCondition("?loc",FCT+RdfNames.HASLOCSTATE,"?locState");
		triples.put(RdfNames.HASLOCSTATE, locState);
		TripleCondition locZip= new TripleCondition("?loc",FCT+RdfNames.HASLOCZIP,"?locZip");
		triples.put(RdfNames.HASLOCZIP, locZip);
		TripleCondition investigator= new TripleCondition("?loc",FCT+RdfNames.HASINVESTNAME,"?investigator");
		triples.put(RdfNames.HASINVESTNAME, investigator);
		TripleCondition locContactName= new TripleCondition("?loc",FCT+RdfNames.HASLOCCONTACTNAME,"?locContactName");
		triples.put(RdfNames.HASLOCCONTACTNAME, locContactName);
		TripleCondition locContactPhone= new TripleCondition("?loc",FCT+RdfNames.HASLOCCONTACTPHONE,"?locContactPhone");
		triples.put(RdfNames.HASLOCCONTACTPHONE, locContactPhone);
		TripleCondition locContactEmail= new TripleCondition("?loc",FCT+RdfNames.HASLOCCONTACTEMAIL,"?locContactEmail");
		triples.put(RdfNames.HASLOCCONTACTEMAIL, locContactEmail);
		TripleCondition locBkupName= new TripleCondition("?loc",FCT+RdfNames.HASLOCBACKUPCONTACTNAME,"?locBkupName");
		triples.put(RdfNames.HASLOCBACKUPCONTACTNAME, locBkupName);
		TripleCondition locBkupPhone= new TripleCondition("?loc",FCT+RdfNames.HASLOCBACKUPCONTACTPHONE,"?locBkupPhone");
		triples.put(RdfNames.HASLOCBACKUPCONTACTPHONE, locBkupPhone);
		TripleCondition locBkupEmail= new TripleCondition("?loc",FCT+RdfNames.HASLOCBACKUPCONTACTEMAIL,"?locBkupEmail");
		triples.put(RdfNames.HASLOCBACKUPCONTACTEMAIL, locBkupEmail);
		
	//	ry gQuery = conn.prepareGraphQuery("construct where {?t fct:hasCondition \"" + condition + "\"."
		//		   + "?t fct:hasNCT ?nct."          
		//		   + "?t fct:hasConflictRep ?conflictRep."
		//		   + "?t fct:hasReqHeader ?reqHeader."
		//		   + "?t fct:hasSecondaryId ?secondaryId."
		//		   + "?t fct:hasBriefTitle ?briefTitle."
			//	   + "?t fct:hasOrgStudyId ?orgStudyId."
		//		   + "?t fct:hasOfficialTitle ?officialTitle."
	  //			   + "?t fct:hasLeadSponsor ?leadSponsor."
		//		   + "?t fct:hasCollabSponsor ?collabSponsor."
			//	   + "?t fct:hasBriefSummary ?briefSummary."
			//	   + "?t fct:hasDetailedDescrip ?detailDescrip."
		//		   + "?t fct:hasOverallStatus ?overallStatus."
		//		   + "?t fct:hasOverallContactName ?overallContactName."
	       //        + "?t fct:hasOverallContactPhone ?overallContactPhone."
	     //           +  "?t fct:hasOverallContactEmail ?overallContactEmail."
		//		   + "?t fct:hasPhase ?phase."
		//		   + "?t fct:hasStartDate ?startDate."
		//		   + "?t fct:hasCompletionDate ?completeDate."
			//	   + " ?t fct:hasCondition ?cond."
			//	   + "?t fct:hasStudyType ?studyType."
		//		   + "?t fct:hasSamplingMethod ?sampMeth."
		//		   + "?t fct:hasCriteriaText ?criteria."
			//	   + "?t fct:hasHealthyVolunteers ?volun."
			//	   + "?t fct:hasGender ?gender."
			//	   + "?t fct:hasMinAge ?minAge."
		//		   + "?t fct:hasMaxAge ?maxAge. "
		//		   +  "?t fct:hasIntervention ?inter."
			//	   +  "?inter fct:hasInterventionName ?intername."
			//	   +  "?inter fct:hasInterventionType ?intertype."
			//	   +  "?inter fct:hasInterventionDescr ?interdesc."
			//	   + " ?inter fct:hasInterventionArmGroupLabel ?armGrpLbl."
	       //        + "?inter fct:hasInterventionOtherName ?oname."
	       //        + "?t fct:hasLocation ?loc."
	        //       + " ?loc fct:hasLocFacility ?fac."
	          //      + "?loc fct:hasLocStatus ?stat."
	          //       + "?loc fct:hasLocCity ?loccity."
	       //       + " ?loc fct:hasLocCountry ?locCountry."
	      //         + " ?loc fct:hasLocState ?locstate."
	       //         + "?loc fct:hasLocZip ?locZip."
	         //       + "?loc fct:hasInvestigatorName ?invest."
	        //       + " ?loc fct:hasLocContactName ?loccontactname."
	          //       + "?loc fct:hasLocContactPhone ?locContactphone."
	        //        + " ?loc fct:hasLocContactEmail ?locContactemail."
	          //      + "?loc fct:hasLocBackupContactName ?locBkupName."
	          //       + "?loc fct:hasLocBackupContactPhone ?locBkupPhone."
	        //        + " ?loc fct:hasLocBackupContactEmail ?locBkupEmail." */
	}
	
	public String makeRetrievalQuery() {
		String queryStr = "construct where {";
		queryStr = queryStr 
				+ makeTripleStr(RdfNames.HASNCT) 
				+ makeTripleStr(RdfNames.HASCONDITION)
				+ makeTripleStr(RdfNames.HASCONFLICTREP)
				+ makeTripleStr(RdfNames.HASREQHEADER)
				+ makeTripleStr(RdfNames.HASSECONDARYID)
				+ makeTripleStr(RdfNames.HASBRIEFTITLE)
				+ makeTripleStr(RdfNames.HASOFFICIALTITLE)
				+ makeTripleStr(RdfNames.HASORGSTUDYID)
				+ makeTripleStr(RdfNames.HASLEADSPONSOR)
				+ makeTripleStr(RdfNames.HASOVERALLSTATUS)
				+ makeTripleStr(RdfNames.HASOVERALLCONTACTNAME)
				+ makeTripleStr(RdfNames.HASOVERALLCONTACTPHONE)
				+ makeTripleStr(RdfNames.HASOVERALLCONTACTEMAIL)
				+ makeTripleStr(RdfNames.HASPHASE)
				+ makeTripleStr(RdfNames.HASSTARTDATE)
				+ makeTripleStr(RdfNames.HASCOMPLETEDATE)
				+ makeTripleStr(RdfNames.HASSTUDYTYPE)
				+ makeTripleStr(RdfNames.HASSAMPLINGMETHOD)
				+ makeTripleStr(RdfNames.HASCRITERIA)
				+ makeTripleStr(RdfNames.HASVOLUNTEERS)
				+ makeTripleStr(RdfNames.HASGENDER)
				+ makeTripleStr(RdfNames.HASMINAGE)
				+ makeTripleStr(RdfNames.HASMAXAGE)
				+ makeTripleStr(RdfNames.HASINTERVENTION)
				+ makeTripleStr(RdfNames.HASINTERVENTIONNAME)
				+ makeTripleStr(RdfNames.HASINTERVENTIONTYPE)
				+ makeTripleStr(RdfNames.HASINTERVENTIONDESCR)
				+ makeTripleStr(RdfNames.HASINTERVENTIONARMGRPLBL)
				+ makeTripleStr(RdfNames.HASINTERVENTIONNAME)
				+ makeTripleStr(RdfNames.HASLOCATION)
				+ makeTripleStr(RdfNames.HASLOCFACILITY)
				+ makeTripleStr(RdfNames.HASLOCSTATUS)
				+ makeTripleStr(RdfNames.HASLOCCITY)
				+ makeTripleStr(RdfNames.HASLOCCOUNTRY)
				+ makeTripleStr(RdfNames.HASLOCSTATE)
				+ makeTripleStr(RdfNames.HASLOCZIP)
				+ makeTripleStr(RdfNames.HASINVESTNAME)
				+ makeTripleStr(RdfNames.HASLOCCONTACTNAME)
				+ makeTripleStr(RdfNames.HASLOCCONTACTPHONE)
				+ makeTripleStr(RdfNames.HASLOCCONTACTEMAIL)
				+ makeTripleStr(RdfNames.HASLOCBACKUPCONTACTNAME)
				+ makeTripleStr(RdfNames.HASLOCBACKUPCONTACTPHONE)
				+ makeTripleStr(RdfNames.HASLOCBACKUPCONTACTEMAIL)
				+ "}";
		
		return queryStr;
	}
	
	public String makeFacetQuery(String facetName){
		// get the variable name that holds the trial's URI since that is what we are counding
		TripleCondition trialTripleInfo = triples.get(RdfNames.HASNCT);
		String trialVar = trialTripleInfo.getSubject();
		
		// get the right variable name for the facet
		TripleCondition facetTripleInfo = triples.get(facetName);
		String facetVar = facetTripleInfo.getObject();
		String countVariable = trialVar+"Count";
		String queryStr = "select " + facetVar + "(count(distinct " + trialVar + ") as " + countVariable + ") where {";
		queryStr = queryStr 
				+ makeTripleStr(RdfNames.HASNCT) 
				+ makeTripleStr(RdfNames.HASCONDITION)
				+ makeTripleStr(RdfNames.HASCONFLICTREP)
				+ makeTripleStr(RdfNames.HASREQHEADER)
				+ makeTripleStr(RdfNames.HASSECONDARYID)
				+ makeTripleStr(RdfNames.HASBRIEFTITLE)
				+ makeTripleStr(RdfNames.HASOFFICIALTITLE)
				+ makeTripleStr(RdfNames.HASORGSTUDYID)
				+ makeTripleStr(RdfNames.HASLEADSPONSOR)
				+ makeTripleStr(RdfNames.HASOVERALLSTATUS)
				+ makeTripleStr(RdfNames.HASOVERALLCONTACTNAME)
				+ makeTripleStr(RdfNames.HASOVERALLCONTACTPHONE)
				+ makeTripleStr(RdfNames.HASOVERALLCONTACTEMAIL)
				+ makeTripleStr(RdfNames.HASPHASE)
				+ makeTripleStr(RdfNames.HASSTARTDATE)
				+ makeTripleStr(RdfNames.HASCOMPLETEDATE)
				+ makeTripleStr(RdfNames.HASSTUDYTYPE)
				+ makeTripleStr(RdfNames.HASSAMPLINGMETHOD)
				+ makeTripleStr(RdfNames.HASCRITERIA)
				+ makeTripleStr(RdfNames.HASVOLUNTEERS)
				+ makeTripleStr(RdfNames.HASGENDER)
				+ makeTripleStr(RdfNames.HASMINAGE)
				+ makeTripleStr(RdfNames.HASMAXAGE)
				+ makeTripleStr(RdfNames.HASINTERVENTION)
				+ makeTripleStr(RdfNames.HASINTERVENTIONNAME)
				+ makeTripleStr(RdfNames.HASINTERVENTIONTYPE)
				+ makeTripleStr(RdfNames.HASINTERVENTIONDESCR)
				+ makeTripleStr(RdfNames.HASINTERVENTIONARMGRPLBL)
				+ makeTripleStr(RdfNames.HASINTERVENTIONNAME)
				+ makeTripleStr(RdfNames.HASLOCATION)
				+ makeTripleStr(RdfNames.HASLOCFACILITY)
				+ makeTripleStr(RdfNames.HASLOCSTATUS)
				+ makeTripleStr(RdfNames.HASLOCCITY)
				+ makeTripleStr(RdfNames.HASLOCCOUNTRY)
				+ makeTripleStr(RdfNames.HASLOCSTATE)
				+ makeTripleStr(RdfNames.HASLOCZIP)
				+ makeTripleStr(RdfNames.HASINVESTNAME)
				+ makeTripleStr(RdfNames.HASLOCCONTACTNAME)
				+ makeTripleStr(RdfNames.HASLOCCONTACTPHONE)
				+ makeTripleStr(RdfNames.HASLOCCONTACTEMAIL)
				+ makeTripleStr(RdfNames.HASLOCBACKUPCONTACTNAME)
				+ makeTripleStr(RdfNames.HASLOCBACKUPCONTACTPHONE)
				+ makeTripleStr(RdfNames.HASLOCBACKUPCONTACTEMAIL)
				+ "}"
				+ " group by " 
				+ facetVar 
				+ " order by desc(" 
				+ countVariable
				+ ")";
		 return queryStr;
	}
	public String makeTripleStr(String key) {
		TripleCondition tripleCond = triples.get(key);
		return tripleCond.getSubject() + " " + tripleCond.getProperty() + " " + tripleCond.getObject() + ". ";
	}
	
	public void replaceVarsWithValues(ArrayList<NameValuePair> nmPairs){
		for (NameValuePair pair : nmPairs){
			TripleCondition triple = triples.get(pair.getName());
			if (triple != null){
				triple.setObject("\"" + pair.getValue() + "\"");
				triples.replace(pair.getName(), triple);
			}
		}
	}

}
