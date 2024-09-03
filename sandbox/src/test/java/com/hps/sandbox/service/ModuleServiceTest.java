import com.hps.sandbox.entity.Module;
import com.hps.sandbox.repository.ModuleRepository;
import com.hps.sandbox.service.ModuleService;
import com.hps.sandbox.service.dtos.ModuleDto;
import com.hps.sandbox.service.exceptions.SandboxEntityNotFound;
import com.hps.sandbox.service.mappers.ModuleMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ModuleServiceTest {

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleMapper moduleMapper;

    @BeforeEach
    public void setUp() {
        // Any setup logic can be added here if needed
    }

    @Test
    public void testGetAllModules() {
        // Create test data
        Module module1 = new Module();
        module1.setName("Module 1");
        moduleRepository.save(module1);

        Module module2 = new Module();
        module2.setName("Module 2");
        moduleRepository.save(module2);

        // Test getAllModules method
        List<ModuleDto> modules = moduleService.getAllModules();
        Assertions.assertEquals(2, modules.size());
    }

    @Test
    public void testGetModuleById() throws SandboxEntityNotFound {
        // Create test data
        Module module = new Module();
        module.setName("Test Module");
        Module savedModule = moduleRepository.save(module);

        // Test getModuleById method
        Optional<ModuleDto> moduleDtoOptional = moduleService.getModuleById(savedModule.getModuleId());
        Assertions.assertTrue(moduleDtoOptional.isPresent());
        Assertions.assertEquals("Test Module", moduleDtoOptional.get().getName());
    }

    @Test
    public void testCreateModule() {
        // Create a new ModuleDto
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setName("New Module");

        // Test createModule method
        ModuleDto createdModuleDto = moduleService.createModule(moduleDto);

        Assertions.assertNotNull(createdModuleDto);
        Assertions.assertNotNull(createdModuleDto.getModuleId());
        Assertions.assertEquals("New Module", createdModuleDto.getName());
    }

    @Test
    public void testUpdateModule() throws SandboxEntityNotFound {
        // Create test data
        Module module = new Module();
        module.setName("Old Module");
        Module savedModule = moduleRepository.save(module);

        // Prepare updated data
        ModuleDto moduleDto = new ModuleDto();
        moduleDto.setName("Updated Module");

        // Test updateModule method
        ModuleDto updatedModuleDto = moduleService.updateModule(savedModule.getModuleId(), moduleDto);

        Assertions.assertNotNull(updatedModuleDto);
        Assertions.assertEquals(savedModule.getModuleId(), updatedModuleDto.getModuleId());
        Assertions.assertEquals("Updated Module", updatedModuleDto.getName());
    }

    @Test
    public void testDeleteModuleById() throws SandboxEntityNotFound {
        // Create test data
        Module module = new Module();
        module.setName("Module to delete");
        Module savedModule = moduleRepository.save(module);

        // Test deleteModuleById method
        moduleService.deleteModuleById(savedModule.getModuleId());

        // Verify deletion
        Optional<Module> deletedModuleOptional = moduleRepository.findById(savedModule.getModuleId());
        Assertions.assertTrue(deletedModuleOptional.isEmpty());
    }
}
