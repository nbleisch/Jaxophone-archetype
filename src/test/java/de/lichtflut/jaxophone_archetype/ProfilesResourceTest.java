package de.lichtflut.jaxophone_archetype;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.lichtflut.jaxophone.test.BaseIntegrationTest;
import de.lichtflut.jaxophone.transfermodel.ProfileCollectionRep;
import de.lichtflut.jaxophone.transfermodel.ProfileRep;
import de.lichtflut.jaxophone.transfermodel.builder.ProfileRepBuilder;
 

public class ProfilesResourceTest extends BaseIntegrationTest{

	ProfileRep maxM, erikaM;
	
	@Before
	public void createFixtures(){
		maxM = new ProfileRepBuilder().withForename("Max").withSurename("Mustermann").build();
		erikaM = new ProfileRepBuilder().withForename("Erika").withSurename("Musterfrau").build();
	}
	
	//StageOne----------------------------------------------------:
	@Ignore
	@Test
	public void createProfile(){
		String locationOfMax = createProfile(maxM, HttpStatus.SC_CREATED);
		maxM = getProfile(locationOfMax, HttpStatus.SC_OK);
		Assert.assertNotNull(maxM);
	}
	
	@Test
	@Ignore
	public void createAndGetProfiles(){
		createProfile(maxM, HttpStatus.SC_CREATED);
		createProfile(erikaM, HttpStatus.SC_CREATED);
		ProfileCollectionRep profiles = getProfiles(HttpStatus.SC_OK);
		if(profiles.getProfile()!=null){
			Assert.assertEquals(profiles.getProfile().size(), 2);
		}else{
			Assert.assertEquals(profiles.getProfileId().size(), 2);
		}
	}


	
	//StageTwo-----------------------------------------------------:
	@Ignore
	@Test
	public void deleteProfile(){
		String location = createProfile(maxM, HttpStatus.SC_CREATED);
		getProfile(location, HttpStatus.SC_OK);
		deleteProfile(location, HttpStatus.SC_NO_CONTENT);
		getProfile(location, HttpStatus.SC_NOT_FOUND);
	}
	
	@Ignore
	@Test
	public void updateProfile(){
		String locationOfMax = createProfile(maxM, HttpStatus.SC_CREATED);
		maxM = getProfile(locationOfMax, HttpStatus.SC_OK);
		//Update acts as replace, just reset the forename. beside that, each other field should be null
		ProfileRep peter = new ProfileRepBuilder().withForename("Peter");
		updateProfile(locationOfMax, peter, HttpStatus.SC_NO_CONTENT);
		maxM = getProfile(locationOfMax, HttpStatus.SC_OK);
		Assert.assertEquals(maxM, peter);
	}
	
	
	//StageThree-----------------------------------------------------:
	@Ignore
	@Test
	public void connectProfiles(){
		String locationOfMax = createProfile(maxM, HttpStatus.SC_CREATED);
		erikaM.getContacts().getProfileId().add(locationOfMax);
		String locationOfErika = createProfile(erikaM, HttpStatus.SC_CREATED);
		
		maxM = getProfile(locationOfMax, HttpStatus.SC_OK);
		Assert.assertTrue(maxM.getReferredBy().getProfileId().contains(locationOfErika));		
	}
	
	@Ignore
	@Test
	public void removeAContactAssociation(){
		String locationOfMax = createProfile(maxM, HttpStatus.SC_CREATED);
		erikaM.getContacts().getProfileId().add(locationOfMax);
		String locationOfErika = createProfile(erikaM, HttpStatus.SC_CREATED);
		maxM = getProfile(locationOfMax, HttpStatus.SC_OK);
		Assert.assertTrue(maxM.getReferredBy().getProfileId().contains(locationOfErika));	
		
		erikaM.getContacts().getProfileId().remove(locationOfMax);
		updateProfile(locationOfErika, erikaM, HttpStatus.SC_NO_CONTENT);
		maxM = getProfile(locationOfMax, HttpStatus.SC_OK);
		Assert.assertFalse(maxM.getReferredBy().getProfileId().contains(locationOfErika));	
		
	}

	@Override
	protected String getMediaType() {
		return MediaType.APPLICATION_JSON;
	}

	@Override
	protected String getProfilesBasePath() {
		//TODO: Replace this with the correct path
		return null;
	}

	
}
