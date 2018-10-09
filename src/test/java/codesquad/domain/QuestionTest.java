package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class QuestionTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", "javajigi@slipp.net");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", "sanjigi@slipp.net");

    public static Question newQuestion(User loginUser, String title, String content) {
        Question question = new Question(title, content);
        question.writeBy(loginUser);
        return question;
    }

    @Test
    public void update_title() throws Exception {
        Question origin = newQuestion(JAVAJIGI, "title", "content");
        Question target = newQuestion(JAVAJIGI, "title1", "content");
        origin.update(JAVAJIGI, target);

        assertThat(origin.getTitle(), is(target.getTitle()));
        assertThat(origin.getContents(), is(target.getContents()));
    }

    @Test
    public void update_content() throws Exception {
        Question origin = newQuestion(JAVAJIGI, "title", "content");
        Question target = newQuestion(JAVAJIGI, "title", "content1");
        origin.update(JAVAJIGI, target);

        assertThat(origin.getTitle(), is(target.getTitle()));
        assertThat(origin.getContents(), is(target.getContents()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_mismatch_user() {
        Question origin = newQuestion(JAVAJIGI, "title", "content");
        Question target = newQuestion(JAVAJIGI, "title1", "content");
        origin.update(SANJIGI, target);
    }

    @Test
    public void delete_misma

}
