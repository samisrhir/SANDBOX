import React, { useState, useEffect } from 'react';
import yaml from 'js-yaml';
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { debounce } from 'lodash';
import Modal from './Modal'; // Adjust the import path as needed
import { width } from '@mui/system';

export function OpenApiPathCard() {
  const [pathUrl, setPathUrl] = useState('/pet');
  const [method, setMethod] = useState('post');
  const [description, setDescription] = useState('Add a new pet to the store');
  const [summary, setSummary] = useState('Add a new pet');
  const [parameters, setParameters] = useState([]);
  const [requestBody, setRequestBody] = useState({ 
    required: true, 
    content: { 
      'application/json': { 
        schema: {
          type: 'object',
          properties: {
            id: { type: 'integer', format: 'int64' },
            name: { type: 'string' },
            category: {
              type: 'object',
              properties: {
                id: { type: 'integer', format: 'int64' },
                name: { type: 'string' }
              }
            },
            photoUrls: { type: 'array', items: { type: 'string' } },
            tags: { 
              type: 'array', 
              items: {
                type: 'object',
                properties: {
                  id: { type: 'integer', format: 'int64' },
                  name: { type: 'string' }
                }
              }
            },
            status: { 
              type: 'string',
              description: 'pet status in the store',
              enum: ['available', 'pending', 'sold']
            }
          },
          required: ['name', 'photoUrls']
        }, 
        example: {
          id: 10,
          name: 'doggie',
          category: {
            id: 1,
            name: 'Dogs'
          },
          photoUrls: ['url1', 'url2'],
          tags: [{ id: 1, name: 'tag1' }],
          status: 'available'
        }
      } 
    } 
  });
  const [responses, setResponses] = useState([{ 
    statusCode: '200', 
    description: 'Successful operation', 
    content: { 
      'application/json': { 
        schema: {
          type: 'object',
          properties: {
            id: { type: 'integer', format: 'int64' },
            name: { type: 'string' },
            category: {
              type: 'object',
              properties: {
                id: { type: 'integer', format: 'int64' },
                name: { type: 'string' }
              }
            },
            photoUrls: { type: 'array', items: { type: 'string' } },
            tags: { 
              type: 'array', 
              items: {
                type: 'object',
                properties: {
                  id: { type: 'integer', format: 'int64' },
                  name: { type: 'string' }
                }
              }
            },
            status: { 
              type: 'string',
              description: 'pet status in the store',
              enum: ['available', 'pending', 'sold']
            }
          },
          required: ['name', 'photoUrls']
        }, 
        example: {
          id: 10,
          name: 'doggie',
          category: {
            id: 1,
            name: 'Dogs'
          },
          photoUrls: ['url1', 'url2'],
          tags: [{ id: 1, name: 'tag1' }],
          status: 'available'
        }
      }
    } 
  }]);

  const [yamlOutput, setYamlOutput] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentResponseIndex, setCurrentResponseIndex] = useState(null);

  useEffect(() => {
    debouncedGenerateYaml();
  }, [pathUrl, method, description, summary, parameters, requestBody, responses]);

  const debouncedGenerateYaml = debounce(() => {
    generateYaml();
  }, 500);

  const generateYaml = () => {
    const openApiDoc = {
      openapi: '3.0.0',
      info: {
        title: 'Petstore API',
        version: '1.0.0',
      },
      paths: {
        [pathUrl]: {
          [method]: {
            description,
            summary,
            parameters,
            requestBody,
            responses: responses.reduce((acc, response) => {
              acc[response.statusCode] = {
                description: response.description,
                content: response.content
              };
              return acc;
            }, {})
          }
        }
      }
    };

    const yamlString = yaml.dump(openApiDoc);
    setYamlOutput(yamlString);
  };

  const exportYaml = () => {
    const yamlFile = new Blob([yamlOutput], { type: 'text/yaml' });
    const url = URL.createObjectURL(yamlFile);
    const a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    a.download = 'openapi.yaml';
    document.body.appendChild(a);
    a.click();
    URL.revokeObjectURL(url);
  };

  const handleInputChange = (setter) => (e) => {
    setter(e.target.value);
  };

  const handleParameterChange = (index, field, value) => {
    const newParameters = [...parameters];
    newParameters[index][field] = value;
    setParameters(newParameters);
  };

  const handleAddParameter = (e) => {
    e.preventDefault(); // Prevent default form behavior
    setParameters([...parameters, { name: '', in: '', description: '', required: false }]);
  };

  const handleResponseChange = (index, field, value) => {
    const newResponses = [...responses];
    newResponses[index][field] = value;
    setResponses(newResponses);
  };

  const handleAddResponse = (e) => {
    e.preventDefault(); // Prevent default form behavior
    setResponses([...responses, { statusCode: '', description: '', content: { 'application/json': { schema: '', example: '' } } }]);
  };

  const handleRequestBodyChange = (field, value) => {
    setRequestBody({
      ...requestBody,
      content: {
        'application/json': {
          ...requestBody.content['application/json'],
          [field]: value
        }
      }
    });
  };

  const openModal = (index) => {
    setCurrentResponseIndex(index);
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentResponseIndex(null);
  };

  const saveExample = (example) => {
    if (currentResponseIndex !== null) {
      const newResponses = [...responses];
      newResponses[currentResponseIndex].content['application/json'].example = example;
      setResponses(newResponses);
    }
  };

  return (
    <div className="flex min-h-screen">
      <Card className="w-full shadow-lg cardee " style={{ height: '90%' }}>
        <CardHeader className="card-header">
          <CardTitle className="card-title">Create API Path</CardTitle>
          <CardDescription className="card-description">Define your API paths and methods.</CardDescription>
          <Button onClick={generateYaml}>Generate API Path</Button>
        </CardHeader>
        <CardContent className="card-content">
          <form>
            <div className="grid gap-4">
              <Label>Path URL</Label>
              <Input
                value={pathUrl}
                onChange={handleInputChange(setPathUrl)}
                className="input"
              />
              <Label>Method</Label>
              <Select value={method} onValueChange={setMethod}>
                <SelectTrigger className="select-trigger">
                  <SelectValue placeholder="Select a method" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="get">GET</SelectItem>
                  <SelectItem value="post">POST</SelectItem>
                  <SelectItem value="put">PUT</SelectItem>
                  <SelectItem value="delete">DELETE</SelectItem>
                </SelectContent>
              </Select>
              <Label>Description</Label>
              <Input
                value={description}
                onChange={handleInputChange(setDescription)}
                className="input"
              />
              <Label>Summary</Label>
              <Input
                value={summary}
                onChange={handleInputChange(setSummary)}
                className="input"
              />
              <Label>Parameters</Label>
              {parameters.map((parameter, index) => (
                <div key={index} className="grid grid-cols-4 gap-2">
                  <Input
                    placeholder="Name"
                    value={parameter.name}
                    onChange={(e) => handleParameterChange(index, 'name', e.target.value)}
                    className="input"
                  />
                  <Select
                    value={parameter.in}
                    onValueChange={(value) => handleParameterChange(index, 'in', value)}
                  >
                    <SelectTrigger className="select-trigger">
                      <SelectValue placeholder="In" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="query">query</SelectItem>
                      <SelectItem value="header">header</SelectItem>
                      <SelectItem value="path">path</SelectItem>
                      <SelectItem value="cookie">cookie</SelectItem>
                    </SelectContent>
                  </Select>
                  <Input
                    placeholder="Description"
                    value={parameter.description}
                    onChange={(e) => handleParameterChange(index, 'description', e.target.value)}
                    className="input"
                  />
                  <Select
                    value={parameter.required ? 'true' : 'false'}
                    onValueChange={(value) => handleParameterChange(index, 'required', value === 'true')}
                  >
                    <SelectTrigger className="select-trigger">
                      <SelectValue placeholder="Required" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="true">true</SelectItem>
                      <SelectItem value="false">false</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              ))}
              <Button variant="outline" onClick={handleAddParameter}>Add Parameter</Button>
              <Label>Request Body</Label>
              <div className="grid gap-2">
                <Input
                  placeholder="Schema"
                  value={requestBody.content['application/json'].schema.type}
                  onChange={(e) => handleRequestBodyChange('schema', e.target.value)}
                  className="input"
                />
                <Input
                  placeholder="Example"
                  value={requestBody.content['application/json'].example}
                  onChange={(e) => handleRequestBodyChange('example', e.target.value)}
                  className="input"
                />
                <Select
                  value={requestBody.required ? 'true' : 'false'}
                  onValueChange={(value) => setRequestBody({ ...requestBody, required: value === 'true' })}
                >
                  <SelectTrigger className="select-trigger">
                    <SelectValue placeholder="Required" />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="true">true</SelectItem>
                    <SelectItem value="false">false</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <Label>Responses</Label>
              {responses.map((response, index) => (
                <div key={index} className="grid grid-cols-4 gap-2">
                  <Input
                    placeholder="Status Code"
                    value={response.statusCode}
                    onChange={(e) => handleResponseChange(index, 'statusCode', e.target.value)}
                    className="input"
                  />
                  <Input
                    placeholder="Description"
                    value={response.description}
                    onChange={(e) => handleResponseChange(index, 'description', e.target.value)}
                    className="input"
                  />
                  <Button
                    className="w-full"
                    onClick={() => openModal(index)}
                  >
                    Edit Example
                  </Button>
                </div>
              ))}
              <Button variant="outline" onClick={handleAddResponse}>Add Response</Button>
            </div>
          </form>
        </CardContent>
        <CardFooter className="card-footer flex justify-between">
        </CardFooter>
      </Card>
      {/* YAML Output */}
      <Card className="w-full shadow-lg cardede">
        <CardHeader className="card-header">
          <CardTitle className="card-title">Generated YAML</CardTitle>
        </CardHeader>
        <CardContent className="card-content">
          <pre className="bg-gray-100 rounded p-4 overflow-auto text-sm dark:bg-slate-700">{yamlOutput}</pre>
        </CardContent>
        <CardFooter className="card-footer">
          <Button onClick={exportYaml}>Export YAML</Button>
        </CardFooter>
      </Card>
      {isModalOpen && (
        <Modal
          isOpen={isModalOpen}
          onClose={closeModal}
          onSave={saveExample}
          example={responses[currentResponseIndex]?.content['application/json'].example || ''}
        />
      )}
    </div>
  );
}

export default function App() {
  return (
    <div className="grid gap-4">
      <OpenApiPathCard />
    </div>
  );
}
