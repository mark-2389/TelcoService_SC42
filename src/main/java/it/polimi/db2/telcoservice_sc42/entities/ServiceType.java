package it.polimi.db2.telcoservice_sc42.entities;

@SuppressWarnings({"unused"})
public enum ServiceType {
    FIXED_PHONE, MOBILE_PHONE, FIXED_INTERNET, MOBILE_INTERNET;

    // I know it looks awful but it allows us to craft the single strings better
    @Override
    public String toString() {
        if ( this == FIXED_PHONE ) return "Fixed phone";
        if ( this == MOBILE_PHONE ) return "Mobile phone";
        if ( this == FIXED_INTERNET ) return "Fixed internet";
        if ( this == MOBILE_INTERNET ) return "Mobile internet";
        return "";
    }

    public static ServiceType fromString(String string) {
        if ( string.equals(FIXED_PHONE.toString()) ) return FIXED_PHONE;
        if ( string.equals(MOBILE_PHONE.toString()) ) return MOBILE_PHONE;
        if ( string.equals(FIXED_INTERNET.toString()) ) return FIXED_INTERNET;
        if ( string.equals(MOBILE_INTERNET.toString()) ) return MOBILE_INTERNET;

        return null;
    }
}
