package support.test;

import codesquad.web.HtmlFormDataBuilder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final String DEFAULT_LOGIN_USER = "javajigi";

    @Autowired
    private TestRestTemplate template;
    
    @Autowired
    private UserRepository userRepository;
    
    public TestRestTemplate template() {
        return template;
    } 
    
    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }
    
    public TestRestTemplate basicAuthTemplate(User loginUser) {
        return template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
    }
    
    protected User defaultUser() {
        return findByUserId(DEFAULT_LOGIN_USER);
    }
    
    protected User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }

    protected ResponseEntity<String> update(TestRestTemplate template) throws Exception {
        HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm();

        htmlFormDataBuilder.addParameter("_method", "put");
        htmlFormDataBuilder.addParameter("password", "password2");
        htmlFormDataBuilder.addParameter("name", "자바지기2");
        htmlFormDataBuilder.addParameter("email", "javajigi@slipp.net");
        HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();

        return template.postForEntity(String.format("/users/%d", defaultUser().getId()), request, String.class);
    }
}
