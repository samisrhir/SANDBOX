const openApiYaml = (openApiHeader,OpenApiPath,scenarios) => {
  let examplesContent = '';
  if (scenarios) {
    examplesContent = `
                  examples: 
                    ${scenarios.map((scenario, index) => `
                      ${index === 0 ? '' : '\n'}
                      ${scenario.name}: 
                        value:
                          ${JSON.stringify(scenario.scenarioJson)}
                    `).join('')}`;
   }

   return `
openapi: "3.0.3"
info:
  title: ${openApiHeader.infoTitle}
  description: ${openApiHeader.infoDescription}
  version: ${openApiHeader.infoVersion}
servers:
  - url: ${openApiHeader.serversUrl}
    description: ${openApiHeader.serversDescription}
paths:
  ${OpenApiPath.pathUrl}:
    ${OpenApiPath.openApiMethodList && OpenApiPath.openApiMethodList.length > 0 ? OpenApiPath.openApiMethodList.map(endPoint => `
      ${endPoint.method}:
        description: ${endPoint.description}
        summary: ${endPoint.summary}
        requestBody:
          required: true
          content:
            application/json:      
              ${examplesContent}

    `).join('') : ''}
       

security:
  - bearerAuth: []

  `.trim();
}
export default openApiYaml;