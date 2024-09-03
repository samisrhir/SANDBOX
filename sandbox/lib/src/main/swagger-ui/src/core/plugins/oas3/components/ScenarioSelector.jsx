import React, { useState, useEffect, useCallback } from "react";
import PropTypes from "prop-types";

const ScenarioSelector = () => {
  const [scenarios, setScenarios] = useState([]);
  const [selectedScenarioIndex, setSelectedScenarioIndex] = useState(null);
  const [scenarioPayload, setScenarioPayload] = useState("");

  const fetchScenarios = async () => {
    try {
      const response = await fetch("http://localhost:8080/sandbox-ui/apis/1/scenarios");
      if (!response.ok) {
        throw new Error(`Error fetching scenarios: ${response.statusText}`);
      }
      const data = await response.json();
      setScenarios(data);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchScenarioPayload = async (index) => {
    try {
      const response = await fetch("http://localhost:8080/sandbox-ui/apis/1/scenarios");
      if (!response.ok) {
        throw new Error(`Error fetching scenarios: ${response.statusText}`);
      }
      const data = await response.json();
      const scenario = data[index];
      setScenarioPayload(scenario ? scenario.body : "No body available");
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    fetchScenarios();
  }, []);

  const handleScenarioChange = useCallback(async (e) => {
    const index = parseInt(e.target.value, 10);
    setSelectedScenarioIndex(index);
    fetchScenarioPayload(index);
  }, []);

  return (
    <div className="scenario-selector">
      <select
        value={selectedScenarioIndex !== null ? selectedScenarioIndex : ""}
        onChange={handleScenarioChange}
      >
        <option value="">Select Scenario</option>
        {scenarios.map((scenario, index) => (
          <option key={index} value={index}>
            {scenario.name}
          </option>
        ))}
      </select>
      {selectedScenarioIndex !== null && (
        <div>
          <textarea value={scenarioPayload} readOnly rows="10" cols="50" />
        </div>
      )}
    </div>
  );
};

ScenarioSelector.propTypes = {
  onScenarioChange: PropTypes.func,
};

export default ScenarioSelector;
