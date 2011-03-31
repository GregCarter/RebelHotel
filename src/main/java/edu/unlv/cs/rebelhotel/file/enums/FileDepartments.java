package edu.unlv.cs.rebelhotel.file.enums;

// FileDepartments HOSSINBSMS = new FileDepartments(true)
// FileDepartments HOSBSHA = new FileDepartments()
public enum FileDepartments {

    HOSSINBSMS, // HOSPITALITY_MANAGEMENT
    HOSBSHA,
    ENTMIN, // Entertainment Management Minor
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
    RECBS(true), // ignore
    RECMIN(true), // ignore
    RECPGMBS(true); // ignore
    
    private boolean ignorable = false;
    private FileDepartments(boolean ignorable){
    	this.ignorable = ignorable;
    }
    
    private FileDepartments(){} 
    
    public boolean isIgnorable(){
    	return this.ignorable;
    }
}
