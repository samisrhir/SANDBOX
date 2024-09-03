const test = (title, t, scenarios) => ({
    openapi: "3.0.1",
    info: {
      title: title,
      version: "1.0.0",
      "x-logo": {
        url: "https://www.ga4gh.org/wp-content/themes/ga4gh-theme/gfx/GA-logo-horizontal-tag-RGB.svg",
      },
      description: `
        ## Executive Summary
  
        ## Standards
  
        The TES API specification is written in OpenAPI and embodies a RESTful
        service philosophy. It uses JSON in requests and responses and standard
        HTTP/HTTPS for information transport. HTTPS should be used rather than plain
        HTTP except for testing or internal-only purposes.
  
        ### Authentication and Authorization
  
        It is envisaged that most TES API instances will require users to
        authenticate to use the endpoints. However, the decision if authentication
        is required should be taken by TES API implementers.
  
        If authentication is required, we recommend that TES implementations use an
        OAuth2 bearer token, although they can choose other mechanisms if
        appropriate.
  
        Checking that a user is authorized to submit TES requests is a
        responsibility of TES implementations.
  
        ### CORS
  
        If TES API implementation is to be used by another website or domain it must
        implement Cross Origin Resource Sharing (CORS). Please refer to
        https://w3id.org/ga4gh/product-approval-support/cors for more information
        about GA4GH’s recommendations and how to implement CORS.
      `
    },
    paths: {
      [`/${title}`]: {
        get: {
          tags: [title],
          summary: "GetServiceInfo",
          description: `
            Provides information about the service
          `,
          operationId: "GetServiceInfo",
          requestBody: {
            content: {
              "application/json": {
                ...(
                 scenarios && {
                   examples: scenarios.reduce((acc, scenario) => {
                     acc[scenario.name] = {value: scenario.scenarioJson};
                     return acc;
                   }, {})
                 }
                   )
              }
              }
            },
          },
          responses: {
            200: {
              description: '',
              content: {
                "application/json": {},
              },
            },
          },
        },
      },

  });
  
  export default test;