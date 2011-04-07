package edu.unlv.cs.rebelhotel.domain.enums;

public enum Departments {


	
    HOSSINBSMS, // HOSPITALITY_MANAGEMENT
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
    RECBS, // ignore
    RECMIN, // ignore
    RECPGMBS, 
    NOVALUE; // ignore

    
	public static Departments toDept(String str)
	{
	    try {
	        return valueOf(str);
	    } 
	    catch (Exception ex) {
	        return NOVALUE;
	    }
	}   
}
