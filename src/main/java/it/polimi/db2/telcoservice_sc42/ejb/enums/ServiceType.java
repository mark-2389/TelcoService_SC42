package it.polimi.db2.telcoservice_sc42.ejb.enums;

public enum ServiceType {
    FIXED_PHONE, MOBILE_PHONE, FIXED_INTERNET, MOBILE_INTERNET;


    // I know it looks awful but it allows us to craft the single strings better
    public String description() {
        if ( this == FIXED_PHONE ) return "Fixed phone";
        if ( this == MOBILE_PHONE ) return "Mobile phone";
        if ( this == FIXED_INTERNET ) return "Fixed internet";
        if ( this == MOBILE_INTERNET ) return "Mobile internet";
        return "";
    }

    public static ServiceType fromString(String string) {
        if ( string.equals(FIXED_PHONE.description()) ) return FIXED_PHONE;
        if ( string.equals(MOBILE_PHONE.description()) ) return MOBILE_PHONE;
        if ( string.equals(FIXED_INTERNET.description()) ) return FIXED_INTERNET;
        if ( string.equals(MOBILE_INTERNET.description()) ) return MOBILE_INTERNET;

        return null;
    }

    public boolean equal(String string) {
        if ( string.equals("Fixed phone") && this == FIXED_PHONE ) return true;
        else if ( string.equals("Mobile phone") && this == MOBILE_PHONE ) return true;
        else if ( string.equals("Fixed internet") && this == FIXED_INTERNET ) return true;
        else return string.equals("Mobile internet") && this == MOBILE_INTERNET;
    }
}
