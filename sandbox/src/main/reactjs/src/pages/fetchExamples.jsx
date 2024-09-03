async function fetchExamples() {
  try {
    const response = await fetch('http://localhost:8080/sandbox-ui/apis/1/scenarios');
    const data = await response.json();

    const examples = {};

    // Assuming data is an array of scenarios
    data.forEach((scenario, index) => {
      // Use scenario data to generate examples for documentation
      examples[`example${index}`] = {
        summary: scenario.name,
        value: scenario.example // Assuming the example is provided in the response
      };
    });

    return examples;
  } catch (error) {
    console.error('Error fetching scenarios:', error);
    throw error;
  }
}

// Usage
async function generateDocumentation() {
  try {
    const examples = await fetchExamples();

    const requestBodyYAML = `
      - Create Debit Card:
          requestBody:
            required: true
            description: >
              When we use this API with the aggregate *Existing Client* (clientCode and bankCode not empty),
              all the other customer details aggregates are ignored.
            content:
              application/json:
                schema:
                  $ref: '#/components/schemas/CreateDebitCardV4Rq'
                examples:
                  ${Object.entries(examples).map(([key, example]) => `
                    ${key}:
                      summary: ${example.summary}
                      value:
                        ${JSON.stringify(example.value, null, 4).replace(/"/g, '')}`).join('\n                  ')}
    `;

    console.log(requestBodyYAML);
  } catch (error) {
    console.error('Error generating documentation:', error);
  }
}

generateDocumentation();
