package codesquad.service;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceTest {

    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QnaService qnaService;

    @Test
    public void test() {
        Question question = new Question("title", "content");

    }



}
