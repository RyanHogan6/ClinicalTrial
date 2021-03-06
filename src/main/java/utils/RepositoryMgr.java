package utils;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.rdf4j.StardogRepository;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;

import java.io.StringWriter;
import java.util.ArrayList;

/**
 * Simple interface to the Stardog repository.
 * This class allows a connection to be initialized,
 * and provides methods to run various queries
 * @author Bonnie MacKellar
 *
 */
public class RepositoryMgr {

    private static Repository repo;
    private static RepositoryConnection conn;


    /**
     * This creates the connection to the database
     * @param url URL for Stardog repository
     * @param dbName  the name of the database
     * @param login
     * @param password
     */
    public void init(String url, String dbName, String login, String password) {
        System.out.println("Connecting to " + url);
        try {
            repo = new StardogRepository(ConnectionConfiguration
                    .to(dbName)
                    .server(url)
                    .credentials(login, password));

            repo.initialize();

        } catch (RepositoryException e) {
            if (repo != null)
                repo.shutDown();
            e.printStackTrace();

        }
    }

    /**
     * This returns a list of trial identifiers (NCTID's) for trials
     * that contain an intervention name that matches the parameter
     * @param intName  name of an intervention
     * @return  JSON-LD formatted string
     */
    public String getTrialIdsByInterventionName(String intName) {
        conn = repo.getConnection();
        GraphQuery graphQuery = conn.prepareGraphQuery("construct where {?ct ct:hasIntervention ?int." +
                "?int ct:hasName \"" + intName + "\"." +
                "?t fct:hasConflictRep ?ct." +
                "?t fct:hasNCT ?nctid." +
                "?t fct:hasReqHeader ?reqHeader." +
                "?t fct:hasBriefTitle ?briefTitle." +
                "?t fct:hasOrgStudyId ?orgStudyId." +
                "?t fct:hasOfficialTitle ?officialTitle." +
                "?t fct:hasLeadSponsor ?leadSponsor." +
                "?t fct:hasCollabSponsor ?collabSponsor." +
                "?t fct:hasBriefSummary ?briefSummary." +
                "?t fct:hasDetailedDescrip ?detailDescrip." +
                "?t fct:hasOverallStatus ?overallStatus." +
                "?t fct:hasPhase ?phase." +
                "?t fct:hasStartDate ?startDate." +
                "?t fct:hasCompletionDate ?completeDate." +
                "?t fct:hasCondition ?cond." +
                "?t fct:hasStudyType ?studyType." +
                "?t fct:hasSamplingMethod ?sampMeth." +
                "?t fct:hasCriteriaText ?criteria." +
                "?t fct:hasHealthyVolunteers ?volun." +
                "?t fct:hasGender ?gender." +
                "?t fct:hasMinAge ?minAge." +
                "?t fct:hasMaxAge ?maxAge. }");
        GraphQueryResult results = graphQuery.evaluate();
        return formatResults(results);
    }

    /**
     * This returns a clinical trial whose nctid matches the value in the parameter
     * Currently, the returned clinical trial information consists of the nct id,
     * a list of interventions (name and type), and a list of eligibility criteria, text only
     * The UMLS concepts and criteria classifications generated in the UIMA pipeline
     * are not currently returned.
     * @param nctid
     * @return
     */
    public String getTrialByNctID(String nctid) {
        conn = repo.getConnection();
        GraphQuery gQuery = conn.prepareGraphQuery("construct where { ?t ct:hasNCT \"" + nctid + "\"."
                + "?t ct:hasIntervention ?int."
                + "?int ct:hasName ?intName."
                + "?int ct:hasType ?intType."
                + "?t ct:hasEligCrit ?crit. "
                + "?crit ct:criteriaText ?text.}");

        GraphQueryResult results = gQuery.evaluate();
        return formatResults(results);

    }


    /**
     * This returns the conflicts generated by the trial whose nctid matches the parameter value
     * For each conflict, it returns the conflict resource name, the nctid of the conflicting trial,
     * the UMLS concept id, the phrase in the criterion and the phrase in the intervention that matched,
     * and the full text of the criterion.
     * @param nctid
     * @return
     */
    public String getConflictsByNctID(String nctid) {
        conn = repo.getConnection();
        GraphQuery gQuery = conn.prepareGraphQuery("construct where { ?trial ct:hasNCT \"" + nctid + "\"."
                + "?trial ct:hasConflict ?conflict."
                + "?conflict ct:hasConflictNCT ?nctid."
                + "?conflict ct:hasConflictCui ?cui."
                + "?conflict ct:hasIntCoveredText ?icov."
                + "?conflict ct:hasCriteriaCoveredText ?ccov."
                + "?conflict ct:hasCriterion ?crit.}");


        GraphQueryResult results = gQuery.evaluate();
        return formatResults(results);

    }

    /**
     * This returns the general representation of a clinical trial geared to whose
     * nctid matches the value in the parameter
     * The general representation is intended to contain the bulk of the information
     * from clinicaltrials.gov, such as sponsors, start and end date, phase, etc.
     * It is only a partial representation at present.  Eligibility criteria are returned
     * as in their original representation - a large text block.
     * @param nctid
     * @return
     */
    public String getFullTrialByNctID(String nctid) {
        conn = repo.getConnection();
        GraphQuery gQuery = conn.prepareGraphQuery("construct where {?t fct:hasNCT \"" + nctid + "\"."
                + "?t fct:hasConflictRep ?conflictRep."
                + "?t fct:hasReqHeader ?reqHeader."
                + " ?t fct:hasSecondaryId ?secondaryId."
                + " ?t fct:hasBriefTitle ?briefTitle."
                + "?t fct:hasOrgStudyId ?orgStudyId."
                + " ?t fct:hasOfficialTitle ?officialTitle."
                + " ?t fct:hasLeadSponsor ?leadSponsor."
                + " ?t fct:hasCollabSponsor ?collabSponsor."
                + "  ?t fct:hasBriefSummary ?briefSummary."
                + "  ?t fct:hasDetailedDescrip ?detailDescrip."
                + " ?t fct:hasOverallStatus ?overallStatus."
                + "?t fct:hasOverallContactName ?overallConactName."
                + "?t fct:hasOverallContactPhone ?overallContactPhone."
                +  "?t fct:hasOverallContactEmail ?overallContactEmail."
                + " ?t fct:hasPhase ?phase."
                + "  ?t fct:hasStartDate ?startDate."
                + " ?t fct:hasCompletionDate ?completeDate."
                + " ?t fct:hasCondition ?cond."
                + " ?t fct:hasStudyType ?studyType."
                + " ?t fct:hasSamplingMethod ?sampMeth."
                + " ?t fct:hasCriteriaText ?criteria."
                + " ?t fct:hasHealthyVolunteers ?volun."
                + " ?t fct:hasGender ?gender."
                + " ?t fct:hasMinAge ?minAge."
                + " ?t fct:hasMaxAge ?maxAge. "
                + " ?t fct:hasIntervention ?inter."
                + " ?inter fct:hasInterventionName ?intername."
                + " ?inter fct:hasInterventionType ?intertype."
                + " ?inter fct:hasInterventionDescr ?interdesc."
                + " ?inter fct:hasInterventionArmGroupLabel ?armGrpLbl."
                + "?inter fct:hasInterventionOtherName ?oname."
                + "?t fct:hasLocation ?loc."
                + " ?loc fct:hasLocFacility ?fac."
                + "?loc fct:hasLocStatus ?stat."
                + "?loc fct:hasLocCity ?loccity."
                + " ?loc fct:hasLocCountry ?locCountry."
                + " ?loc fct:hasLocState ?locstate."
                + "?loc fct:hasLocZip ?locZip."
                + "?loc fct:hasInvestigatorName ?invest."
                + " ?loc fct:hasLocContactName ?loccontactname."
                + "?loc fct:hasLocContactPhone ?locContactphone."
                + " ?loc fct:hasLocContactEmail ?locContactemail."
                + "?loc fct:hasLocBackupContactName ?locBkupName."
                + "?loc fct:hasLocBackupContactPhone ?locBkupPhone."
                + " ?loc fct:hasLocBackupContactEmail ?locBkupEmail."
                + "}");

        GraphQueryResult results = gQuery.evaluate();
        return formatResults(results);

    }

    public String getFullTrialsByCondition(String condition) {
        conn = repo.getConnection();

        GraphQuery gQuery = conn.prepareGraphQuery("construct where {?t fct:hasCondition \"" + condition + "\"."
                + "?t fct:hasNCT ?nct."
                + "?t fct:hasConflictRep ?conflictRep."
                + "?t fct:hasReqHeader ?reqHeader."
                + "?t fct:hasSecondaryId ?secondaryId."
                + "?t fct:hasBriefTitle ?briefTitle."
                + "?t fct:hasOrgStudyId ?orgStudyId."
                + "?t fct:hasOfficialTitle ?officialTitle."
                + "?t fct:hasLeadSponsor ?leadSponsor."
                + "?t fct:hasCollabSponsor ?collabSponsor."
                + "?t fct:hasBriefSummary ?briefSummary."
                + "?t fct:hasDetailedDescrip ?detailDescrip."
                + "?t fct:hasOverallStatus ?overallStatus."
                + "?t fct:hasOverallContactName ?overallConactName."
                + "?t fct:hasOverallContactPhone ?overallContactPhone."
                +  "?t fct:hasOverallContactEmail ?overallContactEmail."
                + "?t fct:hasPhase ?phase."
                + "?t fct:hasStartDate ?startDate."
                + "?t fct:hasCompletionDate ?completeDate."
                + " ?t fct:hasCondition ?cond."
                + "?t fct:hasStudyType ?studyType."
                + "?t fct:hasSamplingMethod ?sampMeth."
                + "?t fct:hasCriteriaText ?criteria."
                + "?t fct:hasHealthyVolunteers ?volun."
                + "?t fct:hasGender ?gender."
                + "?t fct:hasMinAge ?minAge."
                + "?t fct:hasMaxAge ?maxAge. "
                +  "?t fct:hasIntervention ?inter."
                +  "?inter fct:hasInterventionName ?intername."
                +  "?inter fct:hasInterventionType ?intertype."
                +  "?inter fct:hasInterventionDescr ?interdesc."
                + " ?inter fct:hasInterventionArmGroupLabel ?armGrpLbl."
                + "?inter fct:hasInterventionOtherName ?oname."
                + "?t fct:hasLocation ?loc."
                + " ?loc fct:hasLocFacility ?fac."
                + "?loc fct:hasLocStatus ?stat."
                + "?loc fct:hasLocCity ?loccity."
                + " ?loc fct:hasLocCountry ?locCountry."
                + " ?loc fct:hasLocState ?locstate."
                + "?loc fct:hasLocZip ?locZip."
                + "?loc fct:hasInvestigatorName ?invest."
                + " ?loc fct:hasLocContactName ?loccontactname."
                + "?loc fct:hasLocContactPhone ?locContactphone."
                + " ?loc fct:hasLocContactEmail ?locContactemail."
                + "?loc fct:hasLocBackupContactName ?locBkupName."
                + "?loc fct:hasLocBackupContactPhone ?locBkupPhone."
                + " ?loc fct:hasLocBackupContactEmail ?locBkupEmail."
                + "}");

        GraphQueryResult results = gQuery.evaluate();
        return formatResults(results);
    }

    public String getRecruitingFullTrialsByCondition(String condition) {

        conn = repo.getConnection();

        GraphQuery gQuery = conn.prepareGraphQuery("construct where {?t fct:hasCondition \"" + condition + "\"."
                + "?t fct:hasOverallStatus \"Recruiting\"."
                + "?t fct:hasNCT ?nct."
                + "?t fct:hasConflictRep ?conflictRep."
                + "?t fct:hasReqHeader ?reqHeader."
                + "?t fct:hasSecondaryId ?secondaryId."
                + "?t fct:hasBriefTitle ?briefTitle."
                + "?t fct:hasOrgStudyId ?orgStudyId."
                + "?t fct:hasOfficialTitle ?officialTitle."
                + "?t fct:hasLeadSponsor ?leadSponsor."
                + "?t fct:hasCollabSponsor ?collabSponsor."
                + "?t fct:hasBriefSummary ?briefSummary."
                + "?t fct:hasDetailedDescrip ?detailDescrip."
                + "?t fct:hasOverallStatus ?overallStatus."
                + "?t fct:hasOverallContactName ?overallConactName."
                + "?t fct:hasOverallContactPhone ?overallContactPhone."
                +  "?t fct:hasOverallContactEmail ?overallContactEmail."
                + "?t fct:hasPhase ?phase."
                + "?t fct:hasStartDate ?startDate."
                + "?t fct:hasCompletionDate ?completeDate."
                + " ?t fct:hasCondition ?cond."
                + "?t fct:hasStudyType ?studyType."
                + "?t fct:hasSamplingMethod ?sampMeth."
                + "?t fct:hasCriteriaText ?criteria."
                + "?t fct:hasHealthyVolunteers ?volun."
                + "?t fct:hasGender ?gender."
                + "?t fct:hasMinAge ?minAge."
                + "?t fct:hasMaxAge ?maxAge. "
                + "?t fct:hasIntervention ?inter."
                + "?inter fct:hasInterventionName ?intername."
                + "?inter fct:hasInterventionType ?intertype."
                + "?inter fct:hasInterventionDescr ?interdesc."
                + " ?inter fct:hasInterventionArmGroupLabel ?armGrpLbl."
                + "?inter fct:hasInterventionOtherName ?oname."
                + "?t fct:hasLocation ?loc."
                + " ?loc fct:hasLocFacility ?fac."
                + "?loc fct:hasLocStatus ?stat."
                + "?loc fct:hasLocCity ?loccity."
                + " ?loc fct:hasLocCountry ?locCountry."
                + " ?loc fct:hasLocState ?locstate."
                + "?loc fct:hasLocZip ?locZip."
                + "?loc fct:hasInvestigatorName ?invest."
                + " ?loc fct:hasLocContactName ?loccontactname."
                + "?loc fct:hasLocContactPhone ?locContactphone."
                + " ?loc fct:hasLocContactEmail ?locContactemail."
                + "?loc fct:hasLocBackupContactName ?locBkupName."
                + "?loc fct:hasLocBackupContactPhone ?locBkupPhone."
                + " ?loc fct:hasLocBackupContactEmail ?locBkupEmail."
                + "}");

        GraphQueryResult results = gQuery.evaluate();

        return formatResults(results);

    }

    /**
     * Get conditions by condition name
     * @param condition
     * @return name of condition
     */
    public String getConditionsByName(String condition) {
        conn = repo.getConnection();

        GraphQuery gQuery = conn.prepareGraphQuery("construct where {?t fct:hasCondition \"" + condition + "\"."
                + " ?t fct:hasNCT ?nct. }");
        GraphQueryResult results = gQuery.evaluate();
        return formatResults(results);
    }

    /**
     * This returns a list of all conditions in the database, in a string with the format
     * [cond1, cond2, ....] which is JSON array format
     * @return JSON array
     */
    public String returnAllDistinctConditions() {
        String resultStr= "[";
        ArrayList<String> condList = new ArrayList<String>();
        conn = repo.getConnection();
        TupleQuery selectQuery = conn.prepareTupleQuery("select distinct ?cond where {?nct fct:hasCondition ?cond} order by ?cond");
        TupleQueryResult result= selectQuery.evaluate();
        while (result.hasNext()) {
            BindingSet bindingSet = result.next();
            Value cond = bindingSet.getValue("cond");
            condList.add(cond.stringValue());
            //resultStr = resultStr + " " + cond.stringValue();
        }
        String listStr = String.join(",",condList);
        resultStr = resultStr + listStr +" ]";
        return resultStr;
    }


    private String formatResults(GraphQueryResult results) {
        StringWriter strWriter = new StringWriter();
        String resultsStr="";
        RDFWriter rdfWriter = Rio.createWriter(RDFFormat.JSONLD, strWriter);
        Rio.write(QueryResults.asModel(results), rdfWriter);
        resultsStr = strWriter.toString();
        return resultsStr;
    }





    public void close() {
        conn.close();
        repo.shutDown();
    }



}