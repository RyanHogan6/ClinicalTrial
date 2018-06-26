package clinical;


import utils.RepositoryMgr;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/clinical")
public class JerseyRepositoryMgr {

    @GET
    @Path("/getByInt/{value}")
    @Produces(MediaType.TEXT_HTML)
    public String testGetNctIdsByInterventionName(@PathParam("value") String val) {
        RepositoryMgr repoMgr = new RepositoryMgr();

        return repoMgr.getTrialIdsByInterventionName(val);

    }

    @GET
    @Path("/getByNCT/{nct}")
    @Produces(MediaType.TEXT_HTML)
    public String testGetTrialByNctID(@PathParam("nct") String nct) {
        RepositoryMgr repoMgr = new RepositoryMgr();

        return repoMgr.getTrialByNctID(nct);
    }

    @GET
    @Path("/getConflicts/{nct}")
    @Produces(MediaType.TEXT_HTML)
    public String testGetConflictsByNctID(@PathParam("nct") String nct) {
        RepositoryMgr repoMgr = new RepositoryMgr();
        return repoMgr.getConflictsByNctID(nct);

    }
}
