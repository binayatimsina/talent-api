
import com.example.talent_api.TalentApiApplication;
import com.example.talent_api.controller.ApplicationController;
import com.example.talent_api.model.Application;
import com.example.talent_api.model.Job;
import com.example.talent_api.model.User;
import com.example.talent_api.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TalentApiApplication.class)
@ExtendWith(MockitoExtension.class)
class ApplicationControllerTests {

    @Mock
    private ApplicationService applicationService;

    @InjectMocks
    private ApplicationController applicationController;

    private User sampleUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        // Set additional fields as needed
        return user;
    }

    private Job sampleJob() {
        Job job = new Job();
        job.setId(1L);
        job.setJob_title("Software Engineer");
        // Set additional fields as needed
        return job;
    }

    private Application sampleApplication() {
        Application application = new Application();
        application.setId(1L);
        application.setUser(sampleUser());
        application.setJob(sampleJob());
        application.setCover_letter("Sample Cover Letter");
        application.setCustom_resume("Sample Resume");
        application.setApplication_status("Pending");
        application.setDate_applied(LocalDateTime.now());
        return application;
    }

    @Test
    void contextLoads() {
        assertThat(applicationController).isNotNull();
    }

    @Test
    void testGetAllApplications() {
        Application app1 = sampleApplication();
        Application app2 = new Application(sampleUser(), sampleJob(), "Another Cover Letter", "Another Resume", "Accepted");

        when(applicationService.getAllApplications()).thenReturn(Arrays.asList(app1, app2));

        var response = applicationController.getAllApplications();

        assertThat(response).isNotEmpty();
        assertThat(response.size()).isEqualTo(2);
        assertThat(response.get(0).getApplication_status()).isEqualTo("Pending");
        assertThat(response.get(1).getApplication_status()).isEqualTo("Accepted");
    }

    @Test
    void testGetApplicationById() {
        Application app = sampleApplication();

        when(applicationService.getApplicationById(1L)).thenReturn(Optional.of(app));

        var response = applicationController.getAllApplicationById(1L);

        assertThat(response).isPresent();
        assertThat(response.get().getApplication_status()).isEqualTo("Pending");
    }

    @Test
    void testGetAllApplicationsByManagerId() {
        Application app1 = sampleApplication();

        when(applicationService.getApplicationByManagerId(anyLong())).thenReturn(Arrays.asList(app1));

        List<Application> response  = applicationController.getAllApplicationByManagerId(1L);

        assertThat(response).isNotEmpty();
        assertThat(response.get(0).getApplication_status()).isEqualTo("Pending");
    }

    @Test
    void testGetApplicationByJobId() {
        Application app = sampleApplication();

        when(applicationService.getApplicationByJobId(1L)).thenReturn(Optional.of(app));

        Optional<Application> response  = applicationController.getAllApplicationByJobId(1L);

        assertThat(response).isPresent();
        assertThat(response.get().getApplication_status()).isEqualTo("Pending");
    }

    @Test
    void testGetApplicationByUserId() {
        Application app = sampleApplication();

        when(applicationService.getApplicationByUserId(1L)).thenReturn(Optional.of(app));

        Optional<Application> response  = applicationController.getAllApplicationByUserId(1L);

        assertThat(response).isPresent();
        assertThat(response.get().getApplication_status()).isEqualTo("Pending");
    }
}



