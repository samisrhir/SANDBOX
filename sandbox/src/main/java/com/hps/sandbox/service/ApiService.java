package com.hps.sandbox.service;

import com.hps.sandbox.entity.Api;
import com.hps.sandbox.entity.Scenario;
import com.hps.sandbox.repository.ApiRepository;
import com.hps.sandbox.repository.ScenarioRepository;
import com.hps.sandbox.service.dtos.ApiDto;
import com.hps.sandbox.service.mappers.ApiMapper;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiService {

    private final ApiRepository apiRepository;
    private final ApiMapper apiMapper;
    private final ScenarioRepository scenarioRepository;

    // Get all APIs
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public List<ApiDto> getAllApis() {
        List<Api> apis = apiRepository.findAll();
        return apis.stream()
                .map(apiMapper::toDto)
                .toList();
    }

    // Get API by ID
    @Transactional(readOnly = true) // avoid hibernate automatic dirty check and snapshot copy
    public Optional<ApiDto> getApiById(Integer id) throws SandboxEntityNotFound {
        Optional<Api> api = apiRepository.findById(id);
        Api api1 = api.get();
        System.out.printf("+++++++++ " + api1.getScenarios().size() );
        return api.map(apiMapper::toDto);
    }

    // Create API
    public ApiDto createApi(ApiDto apiDto) {
        Api api = apiMapper.toEntity(apiDto);

        List<Scenario> scenarioList = new ArrayList<>();
        for(Scenario scenario: api.getScenarios()){
            scenarioRepository.save(scenario);
            scenarioList.add(scenario);
        }
        api.setScenarios(scenarioList);
        Api savedApi = apiRepository.save(api);
        return apiMapper.toDto(savedApi);
    }

    // Update API
    public ApiDto updateApi(Integer id, ApiDto apiDto) throws SandboxEntityNotFound {
        Api existingApi = apiRepository.findById(id)
                .orElseThrow(() -> new SandboxEntityNotFound("API with ID " + id + " not found"));

        apiMapper.updateApi(apiDto, existingApi);

        Api updatedApi = apiRepository.save(existingApi);

        return apiMapper.toDto(updatedApi);
    }


    // Delete API by ID
    public void deleteApiById(Integer id) throws SandboxEntityNotFound {
        if (apiRepository.existsById(id)) {
            apiRepository.deleteById(id);
            log.info("API with Id: {} is Deleted", id);
        } else {
            throw new SandboxEntityNotFound("API with ID " + id + " was not found");
        }
    }




}
