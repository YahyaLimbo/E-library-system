//package com.mycompany.treviska;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ContextConfiguration;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import static org.assertj.core.api.Assertions.*;
//
//@DataJpaTest
//@ContextConfiguration(classes=Treviska.class)
//public class MaterialTest {
//    
//    @Autowired
//    private Material testMaterial;
//    private TestEntityManager entityManager;
//    
//    @BeforeEach
//    void setupMaterial() {
//        testMaterial= new Material();
//        testMaterial.setTitle();
//        testMaterial.setAuthor();
//        testMaterial.setIdentifier();
//        testMaterial.setTitle();
//        testMaterial.setType();
//        testMaterial.setSummary();
//        testMaterial.setTitle();
//        testMaterial.setDatePublished();
//        testMaterial.setPublisher();
//        testMaterial.setContribution();
//        testMaterial.setFormat();
//        testMaterial.setLanguage();
//    }
//    @Test void CheckAllFieldNonEmpty() {}
//    @Test void TestValidateCreateMaterial(){}
//    @Test void CheckIdentifierUniqueness() {}
//    @Test void CheckIdentifierNotNull() {}
//    @Test void CheckTitleNotNull() {}
//    @Test void TestTypeEnum() {}
//    @Test void TestFormatEnum() {}
//    @Test void TestUpdateMaterial () {}
//    @Test void ValiateDatePublished() {}
//    @Test void TestupdateAtTimestamp (){}
//    @Test void CheckTitleMaxTitleExceed() {}
//}
