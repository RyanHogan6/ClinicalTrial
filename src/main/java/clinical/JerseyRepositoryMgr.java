package clinical;


import utils.RepositoryMgr;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This class uses Jersey Restful API to connect the information in the DB
 * to our web app so we can format and convert the information to be read by
 * the front end JS frameworks
 *
 * The @Path creates a path annotation that allows you to access this class in
 * the url by referencing it as '/clinical/
 */
@Path("/clinical")
public class JerseyRepositoryMgr {

    /*
     * - All functions below use @GET annotations in order to access data from the database
     * - The @Path annotation declares a url route to access the method in the http search
     * - The @Produces annotation allows you to specify which format your data will be returned
     *      in, in which our case it would be standard TEXT_HTML, which is essentially plain
     *      text for easy manipulation.
     */

    /**
     * Queries trial information based off intervention names
     * @param val
     * @return trials by intervention
     */
    @GET
    @Path("/getByInt/{value}")
    @Produces(MediaType.TEXT_HTML)
    public String testGetNctIdsByInterventionName(@PathParam("value") String val) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getTrialIdsByInterventionName(val);

    }

    /**
     * Queries trial information based off NCT ID
     * @param nct
     * @return trials by NCT ID
     */
    @GET
    @Path("/getByNCT/{nct}")
    @Produces(MediaType.TEXT_HTML)
    public String testGetTrialByNctID(@PathParam("nct") String nct) {
        RepositoryMgr repoMgr = new RepositoryMgr();

        return repoMgr.getTrialByNctID(nct);
    }

    /**
     * Queries conflict information by NCT ID
     * @param nct
     * @return conflicts by NCT ID
     */
    @GET
    @Path("/getConflicts/{nct}")
    @Produces(MediaType.TEXT_HTML)
    public String testGetConflictsByNctID(@PathParam("nct") String nct) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getConflictsByNctID(nct);

    }

    /**
     * Queries the full information of a clinical trial from NCT ID
     * @param nct
     * @return full trial information by NCT ID
     */
    @GET
    @Path("/getFullTrialInfo/{nct}")
    @Produces(MediaType.TEXT_HTML)
    public String getFullTrialByNCT(@PathParam("nct") String nct) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getFullTrialByNctID(nct);
    }

    /**
     * Queries all clinical trials by condition
     * @param condition
     * @return all trials by condition
     */
    @GET
    @Path("/getTrialsByCondition/{condition}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getTrialsByCondition(@PathParam("condition") String condition) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getFullTrialsByCondition(condition);
    }

    /**
     * Queries trial information by condition for trials that are currently recruiting
     * @param condition
     * @return recruiting trials by condition
     */
    @GET
    @Path("/getRecruitingTrialsByCondition/{condition}")
    @Produces(MediaType.TEXT_HTML)
    public String getRecruitingTrialsByCondition(@PathParam("condition") String condition) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getRecruitingFullTrialsByCondition(condition);
    }

    @GET
    @Path("/getConditionsByName/{condition}")
    @Produces(MediaType.TEXT_HTML)
    public String getConditionsByName(@PathParam("condition") String condition) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getConditionsByName(condition);
    }

    /**
     * Queries for a list of all condition in the DB
     * @return full list of condition
     */
    @GET
    @Path("/getAllConditions")
    @Produces(MediaType.TEXT_HTML)
    public String getAllConditionNames() {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.returnAllDistinctConditions();
    }

}
