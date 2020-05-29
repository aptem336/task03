package lt.vu.mif.jate.tasks.task03.jpa.base;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import lt.vu.mif.jate.tasks.task03.jpa.model.Company;
import lt.vu.mif.jate.tasks.task03.jpa.model.Gender;
import lt.vu.mif.jate.tasks.task03.jpa.model.Person;
import lt.vu.mif.jate.tasks.task03.jpa.model.Product;
import lt.vu.mif.jate.tasks.task03.jpa.model.Sale;
import lt.vu.mif.jate.tasks.task03.jpa.model.Subject;

public interface ModelTestBase {

    static final Integer SUBJECT_ID = Integer.MAX_VALUE;
    static final String SUBJECT_ADDRESS = "address";
    static final String SUBJECT_CITY = "city";
    static final String SUBJECT_COUNTRY = "country";
    static final String SUBJECT_COUNTY = "county";
    static final String SUBJECT_EMAIL = "email";
    static final String SUBJECT_PHONE = "phone";
    static final String SUBJECT_STATE = "state";
    static final String SUBJECT_WEB = "web";
    static final String SUBJECT_ZIP = "zip";
    static final String PERSON_FIRST_NAME = "vardenis";
    static final String PERSON_LAST_NAME = "pavardenis";
    static final Gender PERSON_GENDER = Gender.M;
    static final String COMPANY_TITLE = "Coca-Cola";
    static final Integer PRODUCT_ID = 9;
    static final String PRODUCT_NAME = "Bottle of Pepsi";
    static final String PRODUCT_BRAND = "Pepsi";
    static final Integer SALE_ID = 10;
    static final BigDecimal SALE_COST = BigDecimal.TEN;
    static final BigInteger SALE_UNITS = new BigInteger("2");
    static final Calendar SALE_TIME = Calendar.getInstance();
    
    default Subject fill(Subject s) {
    	
        s.setId(SUBJECT_ID);
        s.setAddress(SUBJECT_ADDRESS);
        s.setCity(SUBJECT_CITY);
        s.setCountry(SUBJECT_COUNTRY);
        s.setCounty(SUBJECT_COUNTY);
        s.setEmail(SUBJECT_EMAIL);
        s.setPhone(SUBJECT_PHONE);
        s.setState(SUBJECT_STATE);
        s.setWeb(SUBJECT_WEB);
        s.setZip(SUBJECT_ZIP);
        
        return s;
        
    }

    default Person fill(Person p) {
    	
    	fill((Subject) p);
    	
        p.setFirstName(PERSON_FIRST_NAME);
        p.setLastName(PERSON_LAST_NAME);
        p.setGender(PERSON_GENDER);
        
        return p;
        
    }

    default Company fill(Company c) {
    	
    	fill((Subject) c);
    	
        c.setTitle(COMPANY_TITLE);
        
        return c;
        
    }
    
    default Product fill(Product p) {

    	p.setId(PRODUCT_ID);
        p.setName(PRODUCT_NAME);
        p.setBrand(PRODUCT_BRAND);
        
        return p;
    }
    
    default Sale fill(Sale s) {
        
        s.setId(SALE_ID);
        s.setCost(SALE_COST);
        s.setUnits(SALE_UNITS);
        s.setTime(SALE_TIME);
        
        return s;
    }
	
}
