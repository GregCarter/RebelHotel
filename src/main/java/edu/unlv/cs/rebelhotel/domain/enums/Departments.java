package edu.unlv.cs.rebelhotel.domain.enums;


public enum Departments {

    FOOD_AND_BEVERAGE, HOTEL_MANAGEMENT, TOURISM_AND_CONVENTION;
    
    public String toString() {
    	switch (this) {
    		case FOOD_AND_BEVERAGE:
    			return "Food and Beverage";
    		case HOTEL_MANAGEMENT:
    			return "Hotel Management";
    		case TOURISM_AND_CONVENTION:
    			return "Tourism and Convention";
    	}
    	return "";
    }
}
