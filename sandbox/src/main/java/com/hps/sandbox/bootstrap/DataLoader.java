package com.hps.sandbox.bootstrap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hps.sandbox.entity.*;
import com.hps.sandbox.entity.Module;
import com.hps.sandbox.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * For development purposes only.
 */
@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final ProductReleaseRepository productReleaseRepository;
    private final SolutionRepository solutionRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ApiRepository apiRepository;
    private final ScenarioRepository scenarioRepository;
    private final OpenApiHeaderRepository openApiHeaderRepository;
    private final OpenApiRepository openApiRepository;
    private final OpenApiPathRepository openApiPathRepository;
    private final OpenApiMethodRepository openApiMethodRepository;

    private  Scenario scenario;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Product p1 = productRepository.save(Product.builder()
                .name("POWERCARD CONNECTAPI V3.5")
                .description("PowerCARD V3.5 is a comprehensive service-oriented architecture (SOA) suite of solutions that covers the entire payment value chain by enabling innovative payments.")
                .enabled(true)
                .visible(true)
                .build());

        Product p2 = productRepository.save(Product.builder()
                .name("POWERCARD CONNECTAPI V4")
                .description("PowerCARD V4 is an HPS microservices architecture suite of solutions that covers the entire payment value chain by enabling innovative payments.")
                .enabled(true)
                .visible(true)
                .build());


        ProductRelease pr1 = productReleaseRepository.save(
                ProductRelease.builder()
                        .name("POWERCARD 3.2 - CONNECT API RELEASE 4.2\n")
                        .enabled(false)
                        .description("This reference lists the new, enhanced, and removed features, resolved and known issues,documentation notes, and documentation updates in this release 4.2 of Connect'API.")
                        .visible(true)
                        .product(p1)
                        .build());
        ProductRelease pr2 = productReleaseRepository.save(
                ProductRelease.builder()
                        .name("POWERCARD 3.2 - CONNECT API RELEASE 4.2\n")
                        .enabled(true)
                        .description("This reference lists the new, enhanced, and removed features, resolved and known issues,documentation notes, and documentation updates in this release 4.2 of Connect'API.")
                        .visible(true)
                        .product(p1)
                        .build());
        productReleaseRepository.save(
                ProductRelease.builder()
                        .name("POWERCARD 4.0 - CONNECT API RELEASE 4.1\n")
                        .enabled(false)
                        .description("This reference lists the new, enhanced, and removed features, resolved and known issues,documentation notes, and documentation updates in this release 4.2 of Connect'API.")
                        .visible(true)
                        .product(p2)
                        .build());
        List<Solution> solutionList = solutionRepository.saveAll(
                List.of(
                        Solution.builder()
                                .name("POWERCARD-ISSUER")
                                .imageUrl("/issuer.png")
                                .description("PowerCARD-Issuer provides complete services for the issuance and management of all types of payment cards.")
                                .productRelease(pr2)
                                .visible(true)
                                .enabled(true)

                                .build(),
                        Solution.builder()
                                .name("POWERCARD-ACQUIRER")
                                .description("PowerCARD-Acquirer provides complete services for the acquisition and management of all payment .")
                                .productRelease(pr2)
                                .imageUrl("/acquirer.png")
                                .visible(true)
                                .enabled(true)

                                .build(),
                        Solution.builder()
                                .name("POWERCARD-SWITCH")
                                .description("PowerCARD-Switch provides complete services for the switching and routing of all payment ty.")
                                .productRelease(pr2)
                                .imageUrl("/switch.png")
                                .visible(true)
                                .enabled(true)

                                .build(),
                        Solution.builder()
                                .name("POWERCARD-ATM")
                                .description("A comprehensive self-service solution that supports financial institutions and retailers in maximizing their ATMs.")
                                .productRelease(pr2)
                                .visible(true)
                                .imageUrl("/atm.png")
                                .enabled(true)

                                .build(),

                        Solution.builder()
                                .name("POWERCARD-XPOS")
                                .description("PowerCARD-Switch provides complete services for the switching and routing of all payment typ.")
                                .productRelease(pr2)
                                .imageUrl("/xpos.png")
                                .visible(true)
                                .enabled(true)
                                .build()

                )
        );
        // Create API
        Api api1 = apiRepository.save(Api.builder()
                .name("Create Debit CardCreate Debit Card V3.5")
                .enabled(true)
                .description("API to create a debit card")
                .link("\\hps\\v4\\issuer\\payment_instrument\\api\\schemas\\createDebitCard.yaml")
                .visible(true)
                .build());
        Api api2 = apiRepository.save(Api.builder()
                .name("Create Debit Card V4.1")
                        .enabled(true)
                        .description("API ")
                .link("\\hps\\v4\\issuer\\payment_instrument\\api\\schemas\\createDebitCardV41.yaml")
                .visible(true)
                .build());
        //create scenario
        Scenario scenario1 = new Scenario();
        scenario1.setScenarioJson("{\n" +
                "    \"keyValues\": [\n" +
                "        {\n" +
                "            \"key\": \"token\",\n" +
                "            \"data\": \"y\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"requestInfo\": {\n" +
                "        \"requestUID\": \"6948DF80-14BD-4E04-8842-7668D9C001F5\",\n" +
                "        \"requestDate\": \"2020-02-05T00:00:00\",\n" +
                "        \"userID\": \"\"\n" +
                "    },\n" +
                "    \"customerDetails\": {\n" +
                "        \"clientDemog\": {\n" +
                "            \"bankCode\": \"ISS004\",\n" +
                "            \"clientCode\": \"\",\n" +
                "            \"branchCode\": \"010900\",\n" +
                "            \"familyStatus\": \"M\",\n" +
                "            \"numberOfDependents\": 24,\n" +
                "            \"gender\": \"F\",\n" +
                "            \"legalId\": \"C985424\",\n" +
                "            \"taxNumber\": null,\n" +
                "            \"taxRegistryOffice\": null,\n" +
                "            \"birthDate\": \"1992-09-06\",\n" +
                "            \"birthCountry\": \"CZE\",\n" +
                "            \"birthCity\": \"PRAGUE\",\n" +
                "            \"vipLevel\": \"1\",\n" +
                "            \"nationalityCode\": \"203\",\n" +
                "            \"languageCode\": \"CZK\",\n" +
                "            \"titleCode\": \"02\",\n" +
                "            \"familyName\": \"AGUILAR\",\n" +
                "            \"secondFamilyName\": \"SOPHIE\",\n" +
                "            \"firstName\": \"AGUILAR\",\n" +
                "            \"secondFirstName\": \"SOPHIE\",\n" +
                "            \"maidenName\": \"SOPHIE\",\n" +
                "            \"ownerCode\": \"01\",\n" +
                "            \"socioProfessionalCode\": \"0001\",\n" +
                "            \"activityCode\": \"0000\",\n" +
                "            \"customerSegment\": \"001\",\n" +
                "            \"takingOnDate\": null,\n" +
                "            \"employerName\": null,\n" +
                "            \"staffID\": \"1\",\n" +
                "            \"lodgingMode\": \"N\",\n" +
                "            \"channelNumber\": null,\n" +
                "            \"phone1\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone2\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone3\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone4\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"email1\": \"test.email1@email.com\",\n" +
                "            \"email2\": \"\"\n" +
                "        },\n" +
                "        \"primaryAddress\": {\n" +
                "            \"addressType\": \"L3\",\n" +
                "            \"sendingMode\": \"M\",\n" +
                "            \"careOf\": null,\n" +
                "            \"addressLine1\": \"testAddressLine\",\n" +
                "            \"addressLine2\": \"AddressLine2\",\n" +
                "            \"addressLine3\": \"TESTAdrLine3\",\n" +
                "            \"addressLine4\": \"CUTESTAdrLine4\",\n" +
                "            \"zipCode\": \"76233041\",\n" +
                "            \"cityCode\": \"76233\",\n" +
                "            \"regionCode\": \"076\",\n" +
                "            \"countryCode\": \"170\",\n" +
                "            \"cityFreeText\": \"\",\n" +
                "            \"regionFreeText\": \"\"\n" +
                "        },\n" +
                "        \"documents\": [\n" +
                "         \n" +
                "        ],\n" +
                "        \"clientCustomFields\": []\n" +
                "    },\n" +
                "    \"cardDetails\": {\n" +
                "        \"cardDemog\": {\n" +
                "            \"branchCode\": \"010900\",\n" +
                "            \"priorityCode\": \"001\",\n" +
                "            \"gender\": \"F\",\n" +
                "            \"legalId\": \"C985424\",\n" +
                "            \"birthDate\": \"1992-09-06\",\n" +
                "            \"birthCity\": \"PRAGUE\",\n" +
                "            \"vipLevel\": \"1\",\n" +
                "            \"nationalityCode\": \"203\",\n" +
                "            \"languageCode\": \"CZK\",\n" +
                "            \"titleCode\": \"02\",\n" +
                "            \"familyName\": \"CHOI1\",\n" +
                "            \"secondFamilyName\": \"LIDIA\",\n" +
                "            \"firstName\": \"CHOI0\",\n" +
                "            \"secondFirstName\": \"LIDIA\",\n" +
                "            \"maidenName\": \"LIDIA\",\n" +
                "            \"ownerCode\": \"01\",\n" +
                "            \"socioProfessionalCode\": \"0001\",\n" +
                "            \"activityCode\": \"0000\",\n" +
                "            \"customerSegment\": \"001\",\n" +
                "            \"employerName\": \"MRS.  CHOI LIDIA\",\n" +
                "            \"staffID\": \"12\",\n" +
                "            \"channelNumber\": null,\n" +
                "            \"statusCode\": \"N\",\n" +
                "            \"statusReason\": \"\",\n" +
                "            \"catPIProduct\": \"PANVIM\",\n" +
                "            \"catPIProductVersion\": \"1\",\n" +
                "            \"embossedName\": \"MRS.  ORTEGA ROBYN\",\n" +
                "            \"secondEmbossedName\": \"MRS.  ORTEGA ROBYN\",\n" +
                "            \"corporateId\":\"1000000018\",\n" +
                "            \"phone1\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone2\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone3\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone4\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"email1\": \"connectApi@hps.com\",\n" +
                "            \"email2\": \"connectApi@hps.com\",\n" +
                "            \"plasticDeliveryMethod\": \"001\",\n" +
                "            \"pinDeliveryMethod\": \"002\",\n" +
                "            \"promotionCode\": \"\"\n" +
                "        },\n" +
                "        \"primaryAddress\": {\n" +
                "            \"addressType\": \"C1\",\n" +
                "            \"sendingMode\": \"M\",\n" +
                "            \"careOf\": null,\n" +
                "            \"linkFlag\": \"y\",\n" +
                "            \"linkEntityCode\": \"CL\",\n" +
                "            \"linkAddressType\": null,\n" +
                "            \"addressLine1\": \"addressLine11\",\n" +
                "            \"addressLine2\": \"\",\n" +
                "            \"addressLine3\": \"\",\n" +
                "            \"addressLine4\": \"testpr4\",\n" +
                "            \"zipCode\": \"76233041\",\n" +
                "            \"cityCode\": \"76233\",\n" +
                "            \"regionCode\": \"076\",\n" +
                "            \"countryCode\": \"170\",\n" +
                "            \"cityFreeText\": \"\",\n" +
                "            \"regionFreeText\": \"\"\n" +
                "        },\n" +
                "        \"auxiliaryProducts\": [\n" +
                "            {\n" +
                "                \"productIdentifier\": \"PI2\",\n" +
                "                \"panAuxProduct\": \"AAACM1\",\n" +
                "                \"panAuxProductVersion\": \"1\",\n" +
                "                \"contractNumber\": \"090\",\n" +
                "                \"membershipDate\": \"\",\n" +
                "                \"beneficiaryAge\": \"25\",\n" +
                "                \"beneficiaryName\": \"BENEFICIARY BOARD\",\n" +
                "                \"statusCode\": \"N\",\n" +
                "                \"statusDate\": \"2018-04-01\",\n" +
                "                \"insuranceBranchCode\": \"078590\",\n" +
                "                \"insuranceAge\": \"90\",\n" +
                "                \"auxiliaryFeeAmount\": 22015,\n" +
                "                \"policyNumber\": \"7090\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"communicationEvents\": [\n" +
                "            {\n" +
                "                \"communicationStrategy\": \"000001\",\n" +
                "                \"eventCode\": \"0054\",\n" +
                "                \"eventActivationStatus\": \"Y\",\n" +
                "                \"communicationChannels\": [\n" +
                "                    {\n" +
                "                        \"channelType\": \"EM\",\n" +
                "                        \"channelActivationStatus\": \"Y\",\n" +
                "                        \"channelContent\": \"1\",\n" +
                "                        \"channelPhoneType\": \"0\",\n" +
                "                        \"channelAddressFromEntity\": null,\n" +
                "                        \"channelAddressType\": null,\n" +
                "                        \"channelEmailAddress\": \"connectApi@hps.Com\",\n" +
                "                        \"tmpChannelPhone\": null,\n" +
                "                        \"channelPhone\": \"2120678899\",\n" +
                "                        \"tmpPreferenceStartDate\": null,\n" +
                "                        \"tmpPreferenceEndDate\": null,\n" +
                "                        \"tmpPhoneType\": null,\n" +
                "                        \"tmpAddressFromEntity\": null,\n" +
                "                        \"tmpAddressType\": null,\n" +
                "                        \"tmpEmailAddress\": null\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bankAccounts\": [\n" +
                "            {\n" +
                "                \"accountType\": \"00\",\n" +
                "                \"accountSequence\": \"01\",\n" +
                "                \"bankCode\": \"ISS004\",\n" +
                "                \"bankCodeFreetext\": \"\",\n" +
                "                \"branchCode\": \"010900\",\n" +
                "                \"branchCodeFreetext\": \"\",\n" +
                "                \"accountNumber\": \"123411117567657\",\n" +
                "                \"accountCurrency\": \"784\",\n" +
                "                \"iban\": \"FR7017569000509846585889U17\",\n" +
                "                \"swiftCode\": \"TSTQA-TM-89\",\n" +
                "                \"defaultAccount\": \"Y\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"cardCustomFields\": [\n" +
                "           \n" +
                "        ]\n" +
                "    }\n" +
                "}");
        Scenario scenario2 = new Scenario();
        scenario2.setScenarioJson("{\n" +
                "    \"keyValues\": [\n" +
                "        {\n" +
                "            \"key\": \"token\",\n" +
                "            \"data\": \"xx\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"requestInfo\": {\n" +
                "        \"requestUID\": \"6948DF80-14BD-4E04-8842-7668D9C001F5\",\n" +
                "        \"requestDate\": \"2020-02-05T00:00:00\",\n" +
                "        \"userID\": \"\"\n" +
                "    },\n" +
                "    \"customerDetails\": {\n" +
                "        \"existingClient\": {\n" +
                "            \"bankCode\":\"ISS004\",\n" +
                "            \"clientCode\":\"0300000004\"\n" +
                "          \n" +
                "    }\n" +
                "    },\n" +
                "    \"cardDetails\": {\n" +
                "        \"cardDemog\": {\n" +
                "            \"branchCode\": \"010900\",\n" +
                "            \"priorityCode\": \"001\",\n" +
                "            \"gender\": \"F\",\n" +
                "            \"legalId\": \"C985424\",\n" +
                "            \"birthDate\": \"1992-09-06\",\n" +
                "            \"birthCity\": \"PRAGUE\",\n" +
                "            \"vipLevel\": \"1\",\n" +
                "            \"nationalityCode\": \"203\",\n" +
                "            \"languageCode\": \"CZK\",\n" +
                "            \"titleCode\": \"02\",\n" +
                "            \"familyName\": \"CHOI1\",\n" +
                "            \"secondFamilyName\": \"LIDIA\",\n" +
                "            \"firstName\": \"CHOI0\",\n" +
                "            \"secondFirstName\": \"LIDIA\",\n" +
                "            \"maidenName\": \"LIDIA\",\n" +
                "            \"ownerCode\": \"01\",\n" +
                "            \"socioProfessionalCode\": \"0001\",\n" +
                "            \"activityCode\": \"0000\",\n" +
                "            \"customerSegment\": \"001\",\n" +
                "            \"employerName\": \"MRS.  CHOI LIDIA\",\n" +
                "            \"staffID\": \"12\",\n" +
                "            \"channelNumber\": null,\n" +
                "            \"statusCode\": \"N\",\n" +
                "            \"statusReason\": \"\",\n" +
                "            \"catPIProduct\": \"PRDAPI\",\n" +
                "            \"catPIProductVersion\": \"1\",\n" +
                "            \"embossedName\": \"MRS.  ORTEGA ROBYN\",\n" +
                "            \"secondEmbossedName\": \"MRS.  ORTEGA ROBYN\",\n" +
                "            \"corporateId\":\"1000000018\",\n" +
                "            \"phone1\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone2\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone3\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"phone4\": {\n" +
                "                \"phoneType\": \"1\",\n" +
                "                \"phoneNumber\": \"0688992288\"\n" +
                "            },\n" +
                "            \"email1\": \"connectApi@hps.com\",\n" +
                "            \"email2\": \"connectApi@hps.com\",\n" +
                "            \"plasticDeliveryMethod\": \"001\",\n" +
                "            \"pinDeliveryMethod\": \"002\",\n" +
                "            \"promotionCode\": \"\"\n" +
                "        },\n" +
                "        \"primaryAddress\": {\n" +
                "            \"addressType\": \"C1\",\n" +
                "            \"sendingMode\": \"M\",\n" +
                "            \"careOf\": null,\n" +
                "            \"linkFlag\": \"y\",\n" +
                "            \"linkEntityCode\": \"CL\",\n" +
                "            \"linkAddressType\": null,\n" +
                "            \"addressLine1\": \"addressLine11\",\n" +
                "            \"addressLine2\": \"\",\n" +
                "            \"addressLine3\": \"\",\n" +
                "            \"addressLine4\": \"testpr4\",\n" +
                "            \"zipCode\": \"76233041\",\n" +
                "            \"cityCode\": \"76233\",\n" +
                "            \"regionCode\": \"076\",\n" +
                "            \"countryCode\": \"170\",\n" +
                "            \"cityFreeText\": \"\",\n" +
                "            \"regionFreeText\": \"\"\n" +
                "        },\n" +
                "        \"auxiliaryProducts\": [\n" +
                "            {\n" +
                "                \"productIdentifier\": \"PI2\",\n" +
                "                \"panAuxProduct\": \"AAACM1\",\n" +
                "                \"panAuxProductVersion\": \"1\",\n" +
                "                \"contractNumber\": \"090\",\n" +
                "                \"membershipDate\": \"\",\n" +
                "                \"beneficiaryAge\": \"25\",\n" +
                "                \"beneficiaryName\": \"BENEFICIARY BOARD\",\n" +
                "                \"statusCode\": \"N\",\n" +
                "                \"statusDate\": \"2018-04-01\",\n" +
                "                \"insuranceBranchCode\": \"078590\",\n" +
                "                \"insuranceAge\": \"90\",\n" +
                "                \"auxiliaryFeeAmount\": 22015,\n" +
                "                \"policyNumber\": \"7090\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"communicationEvents\": [\n" +
                "            {\n" +
                "                \"communicationStrategy\": \"000001\",\n" +
                "                \"eventCode\": \"0054\",\n" +
                "                \"eventActivationStatus\": \"Y\",\n" +
                "                \"communicationChannels\": [\n" +
                "                    {\n" +
                "                        \"channelType\": \"EM\",\n" +
                "                        \"channelActivationStatus\": \"Y\",\n" +
                "                        \"channelContent\": \"1\",\n" +
                "                        \"channelPhoneType\": \"0\",\n" +
                "                        \"channelAddressFromEntity\": null,\n" +
                "                        \"channelAddressType\": null,\n" +
                "                        \"channelEmailAddress\": \"connectApi@hps.Com\",\n" +
                "                        \"tmpChannelPhone\": null,\n" +
                "                        \"channelPhone\": \"2120678899\",\n" +
                "                        \"tmpPreferenceStartDate\": null,\n" +
                "                        \"tmpPreferenceEndDate\": null,\n" +
                "                        \"tmpPhoneType\": null,\n" +
                "                        \"tmpAddressFromEntity\": null,\n" +
                "                        \"tmpAddressType\": null,\n" +
                "                        \"tmpEmailAddress\": null\n" +
                "                    }\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"bankAccounts\": [\n" +
                "            {\n" +
                "                \"accountType\": \"00\",\n" +
                "                \"accountSequence\": \"01\",\n" +
                "                \"bankCode\": \"ISS004\",\n" +
                "                \"bankCodeFreetext\": \"\",\n" +
                "                \"branchCode\": \"010900\",\n" +
                "                \"branchCodeFreetext\": \"\",\n" +
                "                \"accountNumber\": \"123411117567657\",\n" +
                "                \"accountCurrency\": \"784\",\n" +
                "                \"iban\": \"FR7017569000509846585889U17\",\n" +
                "                \"swiftCode\": \"TSTQA-TM-89\",\n" +
                "                \"defaultAccount\": \"Y\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"cardCustomFields\": [\n" +
                "            {\n" +
                "                \"fieldName\": \"1401\",\n" +
                "                \"fieldValue\": \"Cardholder-Default-Amount1\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");
        System.out.println(scenario2.getScenarioJson());
       Scenario scenario3 =  Scenario.builder()
                        .name("clientDemog")
                        .api(api1)

                .build();
        scenario3.setScenarioJson(scenario1.getScenarioJson());
       scenarioRepository.save(scenario3);
       Scenario scenario4 = Scenario.builder()
                         .name("existingClient")
                         .api(api1)
                 .build();
       scenario4.setScenarioJson(scenario2.getScenarioJson());
        scenarioRepository.save(scenario4);
      //  scenario4 = scenarioRepository.findById(Integer.valueOf(2)).get();

        apiRepository.save(api1);
        apiRepository.save(api2);

        api1.setScenarios(List.of(scenario3,scenario4));
        api2.setScenarios(List.of(scenario4));

   Module module=moduleRepository.save(Module.builder()
                   .name("Customer")
                   .apis(List.of(api1,api2))
                     .description("Customer module")
                     .enabled(true)
                   .solution(solutionList.get(0))
                        .visible(false)
                   .build());
// Iterate over the solutionList and create modules
        for (Solution s : solutionList) {
            moduleRepository.save(Module.builder()
                    .name("Customer")
                    .solution(s)
                    .imagePath("/modules/customer-mgmt_old.png")
                    .enabled(true)
                    .visible(true)
                    .build());

            moduleRepository.save(Module.builder()
                    .name("Loan")
                    .solution(s)
                    .imagePath("/modules/loanold.png")
                    .enabled(true)
                    .visible(true)
                    .build());

            moduleRepository.save(Module.builder()
                    .name("Tokenization")
                    .solution(s)
                    .imagePath("/modules/tokenizationold.png")
                    .enabled(true)
                    .visible(true)
                    .build());

            moduleRepository.save(Module.builder()
                    .name("Financial Account")
                    .solution(s)
                    .imagePath("/modules/finance_account_old.png")
                    .enabled(true)
                    .visible(true)
                    .build());

            moduleRepository.save(Module.builder()
                    .name("Boarding")
                    .solution(s)
                    .imagePath("/modules/boardingold.png")
                    .enabled(true)
                    .visible(true)
                    .build());
        }




        User user1 = userRepository.save(User.builder()
                .username("walid")
                .password(passwordEncoder.encode("sami"))
                .email("walid@gmail.com")
                .enabled(false)
                .createdDate(new Date())
                        .products(Set.of(p1,p2))
                .fullName("Walid")
                .phone("123456789")
                .build());
        User user2 = userRepository.save(User.builder()
                .username("sami")
                .password(passwordEncoder.encode("sami"))
                .email("sami@gmail.com")
                .enabled(true)
                .products(Set.of(p1))

                .fullName("sami")
                .createdDate(new Date())

                .phone("85848381")
                .build());

        User user3 = userRepository.save(User.builder()
                .username("mohamed")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("mohamed@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("mohamed")
                .phone("85848381")
                .createdDate(new Date())
                .build());

        User user4 = userRepository.save(User.builder()
                .username("rachid")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("rachid@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("rachid")
                .phone("85848381")
                .createdDate(new Date())
                .build());

        User user5 = userRepository.save(User.builder()
                .username("amine")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("amine@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("amine")
                .phone("85848381")
                .createdDate(new Date())
                .build());
        User user6 = userRepository.save(User.builder()
                .username("Alex")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("Alex@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("Alex")
                .phone("85848381")
                .createdDate(new Date())
                .build());

        User user7 = userRepository.save(User.builder()
                .username("BOB")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("Bob@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("Bob the builder")
                .phone("8584838123314224")
                .createdDate(new Date())
                .build());

    User user8 = userRepository.save(User.builder()
            .username("Jhon")
            .password(passwordEncoder.encode("sami"))
            .enabled(true)
            .email("Jhon@gmail.com")
            .products(Set.of(p1,p2))
            .fullName("Jhon the")
            .phone("8584838123314224")
            .createdDate(new Date())
            .build());
        User user9 = userRepository.save(User.builder()
                .username("Jame")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("Jame@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("Jame ")
                .phone("8584838123314224")
                .createdDate(new Date())
                .build());
        User user10 = userRepository.save(User.builder()
                .username("Adam")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("Adam@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("Adam ")
                .phone("8584838123314224")
                .createdDate(new Date())
                .build());
        User user11 = userRepository.save(User.builder()
                .username("Salman")
                .password(passwordEncoder.encode("sami"))
                .enabled(true)
                .email("Salman@gmail.com")
                .products(Set.of(p1,p2))
                .fullName("Salman ")
                .phone("8584838123314224")
                .createdDate(new Date())
                .build());
        roleRepository.save(Role.builder().name("USER").build());

        openApiRepository.save(OpenAPiConfig.builder().openApiVersion("3.0.3").build());
        OpenApiHeader openApiHeader=openApiHeaderRepository.save(OpenApiHeader.builder()
                        .infoTitle("Payment Instrument API documentation from data base")
                        .infoDescription(" <p>Payment Instrument and Cardholder operation management include APIs for Payment Instrument maintenance , Payment instrument operations and Status operation.</p>")
                        .infoVersion("PowerCARD-Issuer V4 from data base")
                        .serversUrl("https://api.wemeeting2023.pwcv4.com")
                        .serversDescription("H2")
                .build());
        OpenApiPath openApiPath =  openApiPathRepository.save(OpenApiPath.builder().pathUrl("/payment-instrument/v4.0/cards/create_debit_card").build());

        OpenApiMethod get = openApiMethodRepository.save(OpenApiMethod.builder()
                        .method("get")
                        .openApiPath(openApiPath)
                        .description("get method")
                        .summary("summary")
                        .tags("tags get method")
                .build());
        OpenApiMethod post = openApiMethodRepository.save(OpenApiMethod.builder()
                .method("post")
                .openApiPath(openApiPath)
                .description("<h3>API Overview</h3><p>A debit card allows the cardholder access to a source of funding that is linked to his/her card, typically a saving or a checking account. Users may use this service to send a request to PowerCARD in order to ask for the creation of a client and its debit card.                           </p>                          <p>                              Many details with regards to the card are shared within the request. Those data includes information related to the card. All are then checked by the system before it will proceed by the creation of the debit card.                          </p>                          <p>                           As output the web service send back the result of the processing that could be either successfully or unsuccessfully, in what case, the system will share an error code for investigation.                          </p><h3>Functional Description</h3><img  src=\"/docs/DebitCards.png\" />")
                .summary("summary post")
                .tags("tags post method")
                .build());
        OpenApiMethod put = openApiMethodRepository.save(OpenApiMethod.builder()
                .method("put")
                .openApiPath(openApiPath)
                .description("<h3>API Overview</h3><p>A debit card allows the cardholder access to a source of funding that is linked to his/her card, typically a saving or a checking account. Users may use this service to send a request to PowerCARD in order to ask for the creation of a client and its debit card.                           </p>                          <p>                              Many details with regards to the card are shared within the request. Those data includes information related to the card. All are then checked by the system before it will proceed by the creation of the debit card.                          </p>                          <p>                           As output the web service send back the result of the processing that could be either successfully or unsuccessfully, in what case, the system will share an error code for investigation.                          </p><h3>Functional Description</h3><img  src=\"/docs/DebitCards.png\" />")
                .summary("summary put")
                .tags("tags put method")
                .build());

        openApiPath.setOpenApiMethodList(List.of(get,post));
        openApiPathRepository.save(openApiPath);
        api1.setOpenApiHeader(openApiHeader);
        api1.setOpenApiPath(openApiPath);
        apiRepository.save(api1);
}


}
