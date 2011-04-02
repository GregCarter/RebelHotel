package edu.unlv.cs.rebelhotel.domain.enums;

public enum Degree {
	HOSSINBSMS, // HOSPITALITY_MANAGEMENT
	HOSSINBSHA,
	HOSBSHA,
	ENTMIN(true), // Entertainment Management Minor
	HBEVBSHA, //HOTEL_ADMINISTRATION_BEVERAGE_MANAGEMENT, 
	HBEVPRBSHA,
	FDMBSHA, //FOOD_SERVICE_MANAGEMENT, 
	FDMPRE,
	LRMBSHA, //LODGING_AND_RESORT_MANAGEMENT, 
	LRMPRE,
    MEMBSHA, //MEETINGS_AND_EVENTS_MANAGEMENT,
    MEMPRE,
    CAMBSCM, // CULINARY_ARTS_MANAGEMENT
    CAMPRE,
    CBEVBSCM, // CULINARY_ARTS_BEVERAGE_MANAGEMENT
    CBEVPRBSCM,
    GAMBSGM, // GAMING_MANAGEMENT
    GAMPRE,
    SLSMSSLS,
    MHASINMHA,
    USINNDSG,
    RECBS(true), // ignore
    RECMIN(true), // ignore
    RECPGMBS(true); // ignore
    
    private boolean ignorable = false;
    private Degree(boolean ignorable){
    	this.ignorable = ignorable;
    }
    
    private Degree(){} 
    
    public boolean isIgnorable(){
    	return this.ignorable;
    }
}
