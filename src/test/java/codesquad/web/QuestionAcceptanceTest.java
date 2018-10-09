package codesquad.web;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class QuestionAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(QuestionAcceptanceTest.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void question_list() throws Exception {
        ResponseEntity<String> response = template().getForEntity("/questions", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
        assertThat(response.getBody().contains("qna-list"), is(true));
    }

    @Test
    public void create_Question_Form() throws Exception {
        ResponseEntity<String> response = template().getForEntity("/questions/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void write_Question_login() throws Exception {
        User loginUser = defaultUser();
        Question question = new Question("title", "content");

        ResponseEntity<String> response = basicAuthTemplate(loginUser)
                .getForEntity(String.format("/question/%d/form", loginUser.getId()+":1"), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(question.getContents()), is(true));
    }

    @Test
    public void write_Question_no_login() throws Exception {
        ResponseEntity<String> response = template().getForEntity(String.format("/question/%d/form", defaultUser().getId()+":2"),
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_Question_login() throws Exception {
        User loginUser = defaultUser();
        Question question = new Question("title", "content");

        ResponseEntity<String> response = basicAuthTemplate(loginUser)
                .getForEntity(String.format("/question/%d/form", loginUser.getId()+":3"), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(question.getContents()), is(true));
    }

    @Test
    public void update_Question_no_login() throws Exception {
        ResponseEntity<String> response = template().getForEntity(String.format("/question/%d/form", defaultUser().getId()),
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }



}
